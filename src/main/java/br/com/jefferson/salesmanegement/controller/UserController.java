package br.com.jefferson.salesmanegement.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.jefferson.salesmanegement.domain.models.User;
import br.com.jefferson.salesmanegement.exceptions.UserEmailAlreadyUsedException;
import br.com.jefferson.salesmanegement.services.UserService;

import java.net.URI;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.servlet.Servlet;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value="/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@RequestParam Long id) {
        Optional<User> user = userService.findById(id);

        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody User user) {
        if(userService.findByEmail(user.getMail()).isPresent()) {
            throw new UserEmailAlreadyUsedException();
        }

        User userSave = userService.save(user);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userSave.getId())
            .toUri();

        return ResponseEntity.created(location).build();
    }
}