package br.com.jefferson.salesmanegement.services;

import br.com.jefferson.salesmanegement.domain.models.Product;
import br.com.jefferson.salesmanegement.domain.models.ProductSold;
import br.com.jefferson.salesmanegement.domain.repository.ProductSoldRepository;
import br.com.jefferson.salesmanegement.exceptions.InvalidArgumentException;
import br.com.jefferson.salesmanegement.exceptions.ResourceNotFoundException;
import br.com.jefferson.salesmanegement.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSoldService {

    @Autowired
    private ProductSoldRepository productSoldRepository;

    @Autowired
    private RequestUtil requestUtil;

    @Autowired
    private SaleService saleService;

    @Autowired
    private  ProductService productService;

    public Optional<ProductSold> findById(Long id) {
        return productSoldRepository.findByIdAndUser(id, requestUtil.getUserRequest());
    }

    public List<ProductSold> findAll() {
        return productSoldRepository.findAllByUser(requestUtil.getUserRequest());
    }

    public List<ProductSold> findAllBySale(Long saleId) {
        if (Boolean.FALSE.equals(saleService.isSaleExist(saleId))) {
            return productSoldRepository.findAllByUserAndGroupAndSale(requestUtil.getUserRequest().getId(), saleId);
        } else {
            throw new ResourceNotFoundException("Não foi possível encontrar uma venda com o id: " + saleId);
        }
    }

    public ProductSold save(ProductSold productSold) {
        return productSoldRepository.save(productSold);
    }

    public ProductSold prepareSave(ProductSold productSold) {
        Long saleId = productSold.getSale().getId();
        Long productId = productSold.getProduct().getId();

        isValidProductSoldValues(saleId, productId);

        productSold.setId(null);
        productSold.setUser(requestUtil.getUserRequest());
        productSold.setSale(saleService.findById(saleId).get());

        this.productStock(productId, productSold.getQuantity());
        return this.save(productSold);
    }

    public ProductSold prepareUpdate(ProductSold productSold, Integer oldQuantitySold) {
        Integer newQuantitySold = productSold.getQuantity();
        if(newQuantitySold == 0) {
            throw new InvalidArgumentException("A quantidade de produtos vendidos não pode ser 0");
        } else if (newQuantitySold != oldQuantitySold) {
            newQuantitySold = newQuantitySold - oldQuantitySold;
        }

        this.productStock(productSold.getProduct().getId(), newQuantitySold);

        return this.save(productSold);
    }

    private Boolean isValidProductSoldValues(Long saleId, Long productId) {
        if (saleId == null) {
            throw new InvalidArgumentException("Não foi informado o id da venda");
        }

        if (Boolean.FALSE.equals(saleService.isSaleExist(saleId))) {
            throw new InvalidArgumentException("Não foi possível encontrar uma venda com o id: " + saleId);
        }

        if (productId == null) {
            throw new InvalidArgumentException("Não foi informado o id do produto");
        }

        if (Boolean.FALSE.equals(productService.isProductExist(productId))) {
            throw new InvalidArgumentException("Não foi possível encontrar um produto com o id: " + productId);
        }

        return true;
    }

    private void productStock(Long productId, Integer quantitySold) {
        if (quantitySold == 0) {
            throw new InvalidArgumentException("A quantidade de produtos vendidos não pode ser 0");
        } else if (Boolean.FALSE.equals(this.productService.isStockProductAvaliable(productId, quantitySold))) {
            throw new InvalidArgumentException("A quantidade de produtos vendidos não se encontra disponível em estoque");
        } else {
            Product product = productService.findById(productId).get();

            Integer stock = product.getStock();
            product.setStock(stock - quantitySold);

            this.productService.save(product);
        }
    }

}
