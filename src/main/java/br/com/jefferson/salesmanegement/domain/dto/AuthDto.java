package br.com.jefferson.salesmanegement.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AuthDto {

    @NotNull
    @Email
    @Size(max = 128)
    private String mail;

    @NotNull
    @Size(min = 6, max = 128)
    private String password;

    public AuthDto() {

    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}