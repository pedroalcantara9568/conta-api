package com.example.demo.web.rest.dto.request;

import java.io.Serializable;

public class DepositoDTO implements Serializable {

    private String numeroDaConta;

    private Double valorDeposito;

    public DepositoDTO(Double valorDoDeposito, String numeroDaConta) {
        this.valorDeposito = valorDoDeposito;
        this.numeroDaConta = numeroDaConta;
    }

    public String getNumeroDaConta() {
        return numeroDaConta;
    }


    public Double getValorDeposito() {
        return valorDeposito;
    }


}
