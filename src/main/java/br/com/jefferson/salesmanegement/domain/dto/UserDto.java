package br.com.jefferson.salesmanegement.domain.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class UserDto {

    private Long id;

    @NotNull
    private String name;

    @Column(length = 128, unique = true)
    @NotNull
    private String mail;

    public UserDto() {

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}