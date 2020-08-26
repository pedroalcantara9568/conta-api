package com.example.demo.entity;


import com.example.demo.exception.OperacaoNaoAutorizadaException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class ContaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;

    private Double saldo;

    public boolean depositar(Double valor) {
        if (valor > 0) {
            this.saldo += valor;
            return true;
        }
        return false;
    }

    public boolean saque(Double valor) throws OperacaoNaoAutorizadaException {
        if (valor > 500) {
            throw new OperacaoNaoAutorizadaException("Operação de transferência tem um limite máximo de 500 por operação.");
        }
        if (saldo >= valor && valor > 0) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }

    public ContaEntity() {
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
