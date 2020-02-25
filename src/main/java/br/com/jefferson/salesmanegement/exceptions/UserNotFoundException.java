package br.com.jefferson.salesmanegement.exceptions;

public class UserNotFoundException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super("O usuário informado não foi encontrado");
    }

}