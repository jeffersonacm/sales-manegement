package br.com.jefferson.salesmanegement.controller.v1;

import br.com.jefferson.salesmanegement.exceptions.InvalidArgumentException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.jefferson.salesmanegement.domain.dto.UserDto;
import br.com.jefferson.salesmanegement.domain.models.User;
import br.com.jefferson.salesmanegement.services.UserService;

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

@RestController
@RequestMapping(value="/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().toUserDto());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/mail/{mail}")
    public ResponseEntity<UserDto> findByMail(@PathVariable String mail) {
        Optional<User> user = userService.findByMail(mail);

        if (user.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody User user) {
        if (userService.findByMail(user.getMail()).isPresent()) {
            throw new InvalidArgumentException("O email informado não está disponível");
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
