package com.example.demo.web.rest.dto.request;

public class SaqueDTO {
    
    private String numeroDaConta;

    private Double valorDoSaque;

    public SaqueDTO() {
    }

    public String getNumeroDaConta() {
        return numeroDaConta;
    }

    public void setNumeroDaConta(String numeroDaConta) {
        this.numeroDaConta = numeroDaConta;
    }

    public Double getValorDoSaque() {
        return valorDoSaque;
    }

    public void setValorDoSaque(Double valorDoSaque) {
        this.valorDoSaque = valorDoSaque;
    }
}
