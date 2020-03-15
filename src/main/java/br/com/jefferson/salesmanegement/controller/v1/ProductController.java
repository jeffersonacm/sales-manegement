package br.com.jefferson.salesmanegement.controller.v1;

import br.com.jefferson.salesmanegement.domain.models.Product;
import br.com.jefferson.salesmanegement.exceptions.ResourceNotFoundException;
import br.com.jefferson.salesmanegement.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);

        if(product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/categorie/{id}")
    public ResponseEntity<List<Product>> findAllByCategorie(@PathVariable Long id) {
        List<Product> productList = productService.findAllByCategorieId(id);

        if (productList.size() > 0) {
            return ResponseEntity.ok(productList);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @PostMapping
    public ResponseEntity<Product> save(@Valid @RequestBody Product product) {
        Product productSave = productService.save(product);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productSave.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Product> update(@Valid @RequestBody Product product, @PathVariable Long id) {
        Optional<Product> productFind = productService.findById(id);

        if (productFind.isPresent()) {
            product.setId(id);
            productService.save(product);

            return ResponseEntity.ok().build();
        } else {
            throw new ResourceNotFoundException("Não foi possível encontrar um produto com o id: " + id);
        }
    }

}
