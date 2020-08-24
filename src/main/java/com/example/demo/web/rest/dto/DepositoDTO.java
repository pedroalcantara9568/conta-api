package com.example.demo.web.rest.dto;

import java.io.Serializable;

public class DepositoDTO implements Serializable {

    private Long numeroDaConta;

    private Double valorDeposito;

    public DepositoDTO() {
    }

    public Long getNumeroDaConta() {
        return numeroDaConta;
    }

    public void setNumeroDaConta(Long numeroDaConta) {
        this.numeroDaConta = numeroDaConta;
    }

    public Double getValorDeposito() {
        return valorDeposito;
    }

    public void setValorDeposito(Double valorDeposito) {
        this.valorDeposito = valorDeposito;
    }

    @Override
    public String toString() {
        return "DepositoDTO{" +
                "numeroDaConta=" + numeroDaConta +
                ", valorDeposito=" + valorDeposito +
                '}';
    }
}
