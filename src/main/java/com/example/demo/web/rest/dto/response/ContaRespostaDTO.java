package com.example.demo.web.rest.dto.response;

import java.io.Serializable;

public class ContaRespostaDTO implements Serializable {

    private String numeroDaConta;

    private String mensagem;

    public ContaRespostaDTO(String numeroDaConta, String mensagem) {
        this.numeroDaConta = numeroDaConta;
        this.mensagem = mensagem;
    }

    public ContaRespostaDTO(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNumeroDaConta() {
        return numeroDaConta;
    }

    public void setNumeroDaConta(String numeroDaConta) {
        this.numeroDaConta = numeroDaConta;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}