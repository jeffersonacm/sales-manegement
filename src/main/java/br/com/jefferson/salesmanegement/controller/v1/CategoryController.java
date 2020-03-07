package br.com.jefferson.salesmanegement.controller.v1;

import br.com.jefferson.salesmanegement.domain.models.Categorie;
import br.com.jefferson.salesmanegement.exceptions.ResourceNotFoundException;
import br.com.jefferson.salesmanegement.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "{/id}")
    public ResponseEntity<Categorie> findById(@PathVariable Long id) {
        Optional<Categorie> categorie = categoryService.findById(id);

        if(categorie.isPresent()) {
            return ResponseEntity.ok(categorie.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Categorie>> findAll() {
        List<Categorie> categorieList = categoryService.findAll();

        if (categorieList.size() > 0) {
            return ResponseEntity.ok(categorieList);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @PostMapping
    public ResponseEntity<Categorie> save(@Valid @RequestBody Categorie categorie) {
        Categorie categorieSave = categoryService.save(categorie);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categorieSave.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "{/id}")
    public ResponseEntity<Categorie> update(@Valid @RequestBody Categorie categorie, @PathVariable Long id) {
        Optional<Categorie> categoryFind = categoryService.findById(id);

        if (categoryFind.isPresent()) {
            categorie.setId(id);
            categoryService.save(categorie);

            return ResponseEntity.ok().build();
        } else {
            throw new ResourceNotFoundException("Não foi possível encontrar uma categoria com o id: " + id);
        }
    }


}
