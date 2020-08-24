package com.example.demo.web.rest.dto;

public class SaqueDTO {
    private Long numeroDaConta;

    private Double valorDoSaque;

    public SaqueDTO() {
    }

    public Long getNumeroDaConta() {
        return numeroDaConta;
    }

    public void setNumeroDaConta(Long numeroDaConta) {
        this.numeroDaConta = numeroDaConta;
    }

    public Double getValorDoSaque() {
        return valorDoSaque;
    }

    public void setValorDoSaque(Double valorDoSaque) {
        this.valorDoSaque = valorDoSaque;
    }
}
