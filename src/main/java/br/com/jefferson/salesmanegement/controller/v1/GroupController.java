package br.com.jefferson.salesmanegement.controller.v1;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.jefferson.salesmanegement.domain.models.Group;
import br.com.jefferson.salesmanegement.domain.models.User;
import br.com.jefferson.salesmanegement.services.GroupService;

@RestController
@RequestMapping(value="/v1/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping(value = "{id}")
    public ResponseEntity<Group> findById(@PathVariable Long id) {
        Optional<Group> group = groupService.findById(id);

        if(group.isPresent()) {
            return ResponseEntity.ok(group.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<Group> save(@Valid @RequestBody Group group) {
        Group groupSave = groupService.save(group);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(groupSave.getId())
            .toUri();

        return ResponseEntity.created(location).build();
    }

}