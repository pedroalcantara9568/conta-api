package com.example.demo.entity;


import com.example.demo.exception.CpfInvalidoException;
import com.example.demo.exception.OperacaoNaoAutorizadaException;
import com.example.demo.exception.SaldoInicialInvalidoException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Conta implements Serializable {

    private static final String CONSULTA_NUMERO = "select numero_conta_seq.nextval from dual";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String numeroConta;

    private String nome;

    private String cpf;

    private Double saldo;

    public void depositar(Double valor) throws OperacaoNaoAutorizadaException {
        if (valor > 0) {
            this.saldo += valor;
            return;
        }
        throw new OperacaoNaoAutorizadaException("Não é possivel depositar valor negativo");
    }

    public boolean saque(Double valor) throws OperacaoNaoAutorizadaException {
        if (valor > saldo) {
            throw new OperacaoNaoAutorizadaException("Saldo insuficiente para a operação.");
        }
        if (valor > 500) {
            throw new OperacaoNaoAutorizadaException("Operação de transferência tem um limite máximo de 500 por operação.");
        }
        if (!(valor > 0)) {
            throw new OperacaoNaoAutorizadaException("Não é possível sacar um valor negativo");
        }
        saldo -= valor;
        return true;
    }

    public void cpfEhValido(String cpf) throws CpfInvalidoException {
        if (cpf.isEmpty()) {
            throw new CpfInvalidoException("É necessário informar um cpf para abertura de nova conta.");
        }
        if (cpf.length() != 11) {
            throw new CpfInvalidoException("CPF informado para criação de conta está inválido.");
        }
        if (!ehNumerico(cpf)) {
            throw new CpfInvalidoException("CPF informado para criação deve ser numérico.");
        }
    }

    public void saldoIncialEhValido(Double valor) throws SaldoInicialInvalidoException {
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
        int digitosFinais = gerarDigitosFinais(entityManager);
        String digitosFinaisPreenchidos = preencherComZerosAEsquerda(digitosFinais);
        numeroConta = anoAtual + digitosFinaisPreenchidos;
    }

    private String preencherComZerosAEsquerda(int digitosFinais) {
        return String.format("%05d", digitosFinais);
    }

    private int gerarDigitosFinais(EntityManager entityManager) {
        Query consultaPeloProximoValor = entityManager.createNativeQuery(CONSULTA_NUMERO);
        Object valorDaSequence = consultaPeloProximoValor.getSingleResult();
        return Integer.parseInt(String.valueOf(valorDaSequence));
    }

}
