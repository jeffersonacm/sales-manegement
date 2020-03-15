package br.com.jefferson.salesmanegement.controller.v1;

import br.com.jefferson.salesmanegement.domain.models.ProductSold;
import br.com.jefferson.salesmanegement.exceptions.ResourceNotFoundException;
import br.com.jefferson.salesmanegement.services.ProductSoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/productSold")
public class ProductSoldController {

    @Autowired
    private ProductSoldService productSoldService;

    @GetMapping(value = "{id}")
    public ResponseEntity<ProductSold> findById(@PathVariable Long id) {
        Optional<ProductSold> productSold = productSoldService.findById(id);

        if(productSold.isPresent()) {
            return ResponseEntity.ok(productSold.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductSold>> findAll() {
        List<ProductSold> productSoldList = productSoldService.findAll();

        if (productSoldList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(productSoldList);
        }
    }

    @GetMapping(value = "/sale/{saleId}")
    public ResponseEntity<List<ProductSold>> findAllByGroupAndSale(@PathVariable Long saleId) {
        List<ProductSold> productSoldList = productSoldService.findAllBySale(saleId);

        if (productSoldList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(productSoldList);
        }
    }

    @PostMapping
    public ResponseEntity<ProductSold> save(@Valid @RequestBody ProductSold productSold) {
        ProductSold productSoldSave = productSoldService.prepareSave(productSold);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productSoldSave.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<ProductSold> update(@Valid @RequestBody ProductSold productSold, @PathVariable Long id) {
        Optional<ProductSold> productSoldFindOp = productSoldService.findById(id);

        if (productSoldFindOp.isPresent()) {
            ProductSold productSoldFind = productSoldFindOp.get();

            Integer oldQuantitySold = productSoldFind.getQuantity();
            productSoldFind.setQuantity(productSold.getQuantity());
            productSoldFind.setDateSold(productSold.getDateSold());

            productSoldService.prepareUpdate(productSoldFind, oldQuantitySold);

            return ResponseEntity.ok().build();
        } else {
            throw new ResourceNotFoundException("Não foi possível encontrar um produto a venda com o id: " + id);
        }
    }

}
