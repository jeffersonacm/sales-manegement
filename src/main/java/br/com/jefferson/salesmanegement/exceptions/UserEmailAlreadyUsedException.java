package br.com.jefferson.salesmanegement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "O email informado não está disponível")
public class UserEmailAlreadyUsedException extends RuntimeException {

    static final long serialVersionUID = 1L;

}
