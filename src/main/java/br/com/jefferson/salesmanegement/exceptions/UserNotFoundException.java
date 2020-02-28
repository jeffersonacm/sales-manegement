package br.com.jefferson.salesmanegement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "O usuário informado não foi encontrado")
public class UserNotFoundException extends RuntimeException {

    static final long serialVersionUID = 1L;

}
