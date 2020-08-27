package com.example.demo.entity;


import com.example.demo.exception.OperacaoNaoAutorizadaException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


@Entity
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String numeroConta;

    private String nome;

    private String cpf;

    private Double saldo;

    public Conta() {
    }

    public boolean depositar(Double valor) {
        if (valor > 0) {
            this.saldo += valor;
            return true;
        }
        return false;
    }

    public boolean saque(Double valor) throws OperacaoNaoAutorizadaException {
        if (valor > saldo) {
            throw new OperacaoNaoAutorizadaException("Saldo insuficiente para a operação.");
        }
        if (valor > 500) {
            throw new OperacaoNaoAutorizadaException("Operação de transferência tem um limite máximo de 500 por operação.");
        }
        if (valor > 0) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroCartao) {
        this.numeroConta = numeroCartao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
