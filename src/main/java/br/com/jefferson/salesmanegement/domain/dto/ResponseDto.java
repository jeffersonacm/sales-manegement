package br.com.jefferson.salesmanegement.domain.dto;

public class ResponseDto {

    private String jwt;

    public ResponseDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return this.jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}