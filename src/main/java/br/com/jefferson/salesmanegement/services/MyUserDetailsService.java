package br.com.jefferson.salesmanegement.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.jefferson.salesmanegement.domain.models.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userService.findByEmail(username);

        if(user.isPresent()) {
            return new org.springframework.security.core.userdetails
            .User(user.get().getMail(), user.get().getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("O usuario informado não existe");
        }
    }

}
