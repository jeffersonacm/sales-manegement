package br.com.jefferson.salesmanegement.services;

import br.com.jefferson.salesmanegement.domain.models.Categorie;
import br.com.jefferson.salesmanegement.domain.models.Product;
import br.com.jefferson.salesmanegement.domain.repository.ProductRepository;
import br.com.jefferson.salesmanegement.exceptions.InvalidArgumentException;
import br.com.jefferson.salesmanegement.exceptions.ResourceNotFoundException;
import br.com.jefferson.salesmanegement.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categorieService;

    @Autowired
    private RequestUtil requestUtil;

    public Optional<Product> findById(Long id) {
        return productRepository.findByIdAndUser(id, requestUtil.getUserRequest());
    }

    public List<Product> findAllByCategorieId(Long id) {
        if (categorieService.isCategorieExist(id)) {
            throw new InvalidArgumentException("Não foi possível encontrar uma categoria com o id: " + id);
        } else {
            Categorie categorie = categorieService.findById(id).get();
            return productRepository.findAllByCategorieAndUser(categorie, requestUtil.getUserRequest());
        }
    }

    public Product save(Product product) {
        Categorie categorie = product.getCategorie();
        if (categorie.getId() != null && categorieService.isCategorieExist(categorie.getId())) {
            product.setUser(requestUtil.getUserRequest());

            return productRepository.save(product);
        } else {
            throw new InvalidArgumentException("Não foi possível encontrar uma categoria com o id: " + categorie.getId());
        }
    }

    public Boolean isProductExist(Long id) {
        return this.findById(id).isPresent();
    }

    public Boolean isStockProductAvaliable(Long id, int quantitySold) {
        Optional<Product> product = this.findById(id);

        if(Boolean.FALSE.equals(product.isPresent())) {
            throw new InvalidArgumentException("Não foi possível encontrar um produto com o id: " + id);
        }

        int stock = product.get().getStock();
        if (stock >= quantitySold) {
            return true;
        } else {
            return false;
        }
    }
}
