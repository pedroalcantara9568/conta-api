package com.example.demo.web.rest.dto.response;

import java.io.Serializable;

public class ContaRespostaDTO implements Serializable {

    private Long numeroDaConta;

    private String mensagem;

    public ContaRespostaDTO(Long numeroDaConta, String mensagem) {
        this.numeroDaConta = numeroDaConta;
        this.mensagem = mensagem;
    }

    public ContaRespostaDTO(String mensagem) {
    }

    public Long getNumeroDaConta() {
        return numeroDaConta;
    }

    public void setNumeroDaConta(Long numeroDaConta) {
        this.numeroDaConta = numeroDaConta;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}