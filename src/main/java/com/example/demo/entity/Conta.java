package com.example.demo.entity;


import com.example.demo.exception.CpfInvalidoException;
import com.example.demo.exception.OperacaoNaoAutorizadaException;
import com.example.demo.exception.SaldoInicialInvalidoException;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


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
    public void cpfEhValido(String cpf) {
        if (cpf.isEmpty()) {
            throw new CpfInvalidoException("É necessário informar um cpf para abertura de nova conta.");
        }
        if (cpf.length() != 11) {
            throw new CpfInvalidoException("CPF informado para criação de conta está inválido.");
        }
        if (!ehNumerico(cpf)) {
            throw new CpfInvalidoException("CPF informado para criação de conta está inválido.");
        }

    }

    public void saldoIncialEhValido(Double valor) {
        if (valor < 50) {
            throw new SaldoInicialInvalidoException("Saldo insuficiente para abertura de nova conta.");
        }
    }

    public boolean ehNumerico(String strNumber) {
        if (strNumber == null) return false;
        return strNumber.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public void contaEhValida(Conta conta) {
        cpfEhValido(conta.getCpf());
        saldoIncialEhValido(conta.getSaldo());
    }

    public void gerarNumeroConta(EntityManager entityManager) {
        int anoAtual = LocalDateTime.now().getYear();
        int digitosFinais = gerarDigitosFinais( entityManager);
        String digitosFinaisPreenchidos = preencherComZerosAEsquerda(digitosFinais);
        this.numeroConta = anoAtual + digitosFinaisPreenchidos;
    }

    private String preencherComZerosAEsquerda(int digitosFinais) {
        return String.format("%05d", digitosFinais);
    }

    private int gerarDigitosFinais(EntityManager entityManager) {
        Query consultaPeloProximoValor = entityManager.createNativeQuery("select numero_conta_seq.nextval from dual");
        Object valorDaSequence = consultaPeloProximoValor.getSingleResult();
        return Integer.parseInt(String.valueOf(valorDaSequence));
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNumeroConta() {
        return this.numeroConta;
    }
}
