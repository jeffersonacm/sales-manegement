package br.com.jefferson.salesmanegement.utils;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jefferson.salesmanegement.exceptions.ResourceNotFoundException;
import br.com.jefferson.salesmanegement.domain.models.User;
import br.com.jefferson.salesmanegement.services.UserService;

@Service
public class RequestUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    public String getToken() {
        String jwt = request.getHeader("Authorization");
        String jwtToken = null;

        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwtToken = jwt.substring(7);
        }

        return jwtToken;
    }

    public User getUserRequest() {
        String username = jwtUtil.extractUsername(getToken());
        Optional<User> user = userService.findByMail(username);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ResourceNotFoundException("O usuário informado não foi encontrado");
        }
    }
}