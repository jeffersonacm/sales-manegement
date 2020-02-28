package br.com.jefferson.salesmanegement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class GroupNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GroupNotFoundException() {
        super("O grupo informado não existe");
    }
}
