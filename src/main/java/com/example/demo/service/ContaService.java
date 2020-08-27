package com.example.demo.service;

import com.example.demo.entity.Conta;
import com.example.demo.exception.CpfInvalidoException;
import com.example.demo.exception.SaldoInicialInvalidoException;
import com.example.demo.repository.ContaRepository;
import com.example.demo.web.rest.dto.ContaDTO;
import com.example.demo.web.rest.dto.mapper.ContaMapper;
import com.example.demo.web.rest.dto.request.DepositoDTO;
import com.example.demo.web.rest.dto.request.SaqueDTO;
import com.example.demo.web.rest.dto.request.TransferenciaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public ContaDTO salvaConta(ContaDTO contaDTO) {
        Conta entity = ContaMapper.dtoToEntity(contaDTO);
        contaEhValida(entity);
        contaRepository.saveAndFlush(entity);
        entity.setNumeroConta(gerarNumeroConta());
        return ContaMapper.entityToDto(entity);
    }

    public void realizaDeposito(DepositoDTO depositoDTO) {
        Optional<Conta> byId = contaRepository.findByNumeroConta(depositoDTO.getNumeroDaConta());
        if (byId.isPresent()) {
            Conta entity = contaRepository.findByNumeroConta(depositoDTO.getNumeroDaConta()).get();
            entity.depositar(depositoDTO.getValorDeposito());
            contaRepository.save(entity);
        }

    }

    public void realizaSaque(SaqueDTO saqueDTO) throws OperationNotSupportedException {
        Optional<Conta> byId = contaRepository.findByNumeroConta(saqueDTO.getNumeroDaConta());
        if (byId.isPresent()) {
            Conta entity = contaRepository.findByNumeroConta(saqueDTO.getNumeroDaConta()).get();
            if (entity.saque(saqueDTO.getValorDoSaque())) {
                contaRepository.save(entity);
            }
        }

    }

    public void validaTransferencia(TransferenciaDTO transferenciaDTO) {
        Optional<Conta> byIdSolicitiante = contaRepository.findByNumeroConta(transferenciaDTO.getContaDoSolicitante());
        Optional<Conta> byIdBeneficiario = contaRepository.findByNumeroConta(transferenciaDTO.getContaDoBeneficiario());

        if (byIdBeneficiario.isPresent() && byIdSolicitiante.isPresent()) {
            concluiTransferencia(transferenciaDTO);
        }

    }

    private void concluiTransferencia(TransferenciaDTO transferenciaDTO) {
        Conta contaDoSolicitante = contaRepository.findByNumeroConta(transferenciaDTO.getContaDoSolicitante()).get();
        Conta contaDoBeneficiario = contaRepository.findByNumeroConta(transferenciaDTO.getContaDoBeneficiario()).get();
        contaDoSolicitante.saque(transferenciaDTO.getValorDaTransferencia());
        contaDoBeneficiario.depositar(transferenciaDTO.getValorDaTransferencia());
        contaRepository.save(contaDoSolicitante);
        contaRepository.save(contaDoBeneficiario);
    }

    public void contaEhValida(Conta conta) {
        cpfEhValido(conta.getCpf());
        saldoIncialEhValido(conta.getSaldo());
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

    public String gerarNumeroConta() {
        int anoAtual = LocalDateTime.now().getYear();
        int digitosFinais = gerarDigitosFinais();
        String digitosFinaisPreenchidos = preencherComZerosAEsquerda(digitosFinais);
        return anoAtual + digitosFinaisPreenchidos;
    }

    private String preencherComZerosAEsquerda(int digitosFinais) {
        return String.format("%05d", digitosFinais);
    }

    private int gerarDigitosFinais() {
        Query consultaPeloProximoValor = entityManager.createNativeQuery("select numero_conta_seq.nextval from dual");
        Object valorDaSequence = consultaPeloProximoValor.getSingleResult();
        return Integer.parseInt(String.valueOf(valorDaSequence));
    }
}
