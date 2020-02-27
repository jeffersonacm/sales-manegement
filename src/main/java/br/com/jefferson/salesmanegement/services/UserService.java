package br.com.jefferson.salesmanegement.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jefferson.salesmanegement.domain.models.User;
import br.com.jefferson.salesmanegement.domain.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByMail(String mail) {
        return userRepository.findByMail(mail);
    }

}