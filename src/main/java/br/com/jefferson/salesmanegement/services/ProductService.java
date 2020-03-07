package br.com.jefferson.salesmanegement.services;

import br.com.jefferson.salesmanegement.domain.models.Categorie;
import br.com.jefferson.salesmanegement.domain.models.Product;
import br.com.jefferson.salesmanegement.domain.repository.ProductRepository;
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
        Optional<Categorie> categorie = categorieService.findById(id);

        if (categorie.isPresent()) {
            return productRepository.findAllByCategorieAndUser(categorie.get(), requestUtil.getUserRequest());
        } else {
            throw new ResourceNotFoundException("Não foi possível encontrar uma categoria com o id: " + id);
        }
    }

    public Product save(Product product) {
        product.setUser(requestUtil.getUserRequest());
        return productRepository.save(product);
    }

}
