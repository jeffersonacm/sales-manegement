package br.com.jefferson.salesmanegement.exceptions;

public class UserEmailAlreadyUsedException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public UserEmailAlreadyUsedException() {
        super("O email informado não está disponível");
    }

}