package br.com.jefferson.salesmanegement.controller.v1;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import br.com.jefferson.salesmanegement.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.jefferson.salesmanegement.domain.models.Sale;
import br.com.jefferson.salesmanegement.exceptions.InvalidArgumentException;
import br.com.jefferson.salesmanegement.services.SaleService;
import br.com.jefferson.salesmanegement.utils.RequestUtil;

@RestController
@RequestMapping("/v1/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private RequestUtil requestUtil;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Sale> findById(@PathVariable Long id) {
        Optional<Sale> sale = saleService.findById(id);

        if (sale.isPresent()) {
            return ResponseEntity.ok(sale.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Sale>> findAll() {
        List<Sale> sales = saleService.findAll();

        if (sales.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(sales);
        }
    }

    @PostMapping
    public ResponseEntity<Sale> save(@Valid @RequestBody Sale sale) {
        Sale saleSave = saleService.save(sale);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saleSave.getId())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Sale> update(@RequestBody Sale sale, @PathVariable Long id) {
        if (sale.getName() == null) {
            throw new InvalidArgumentException("nome");
        }

        Optional<Sale> saleFind = saleService.findById(id);

        if (saleFind.isPresent()) {
            Sale saleUpdate = saleFind.get();
            saleUpdate.setName(sale.getName());
            saleService.save(saleUpdate);

            return ResponseEntity.ok().build();
        } else {
            throw new ResourceNotFoundException("Não foi possível encontrar uma venda com o id: " + id);
        }
    }

}
