package br.com.jefferson.salesmanegement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ArgumentNotInformedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ArgumentNotInformedException(String name) {
        super("O atributo " + name + " não foi informado");
    }

}
