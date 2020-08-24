package com.example.demo.service;

import com.example.demo.entity.ContaEntity;
import com.example.demo.exception.CpfInvalidoException;
import com.example.demo.exception.SaldoInicialInvalidoException;
import com.example.demo.repository.ContaRepository;
import com.example.demo.web.rest.dto.DepositoDTO;
import com.example.demo.web.rest.dto.SaqueDTO;
import com.example.demo.web.rest.dto.TransferenciaDTO;
import com.example.demo.web.rest.dto.mapper.ContaMapper;
import com.example.demo.web.rest.dto.ContaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import javax.transaction.Transactional;


@Service
public class ContaService  {

    @Autowired
    ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public ContaDTO salvaConta(ContaDTO contaDTO) {
        ContaEntity entity = ContaMapper.dtoToEntity(contaDTO);
        contaEhValida(entity);
        contaRepository.saveAndFlush(entity);
        ContaDTO conta = ContaMapper.entityToDto(entity);
        return conta;
    }

    public void realizaDeposito(DepositoDTO depositoDTO) {
        ContaEntity entity = contaRepository.findById(depositoDTO.getNumeroDaConta()).get();
        if(entity != null){
            entity.depositar(depositoDTO.getValorDeposito());
            contaRepository.save(entity);
        }

    }

    public void realizaSaque(SaqueDTO saqueDTO) throws OperationNotSupportedException {
        ContaEntity entity = contaRepository.getOne(saqueDTO.getNumeroDaConta());
        if(entity.saque(saqueDTO.getValorDoSaque())){
            contaRepository.save(entity);
        }
    }

    public void realizaTransferencia(TransferenciaDTO transferenciaDTO) {
        ContaEntity solicitante = contaRepository.getOne(transferenciaDTO.getContaDoSolicitante());
        ContaEntity beneficiario = contaRepository.getOne(transferenciaDTO.getContaDoBeneficiario());
        solicitante.saque(transferenciaDTO.getValorDaTransferencia());
        beneficiario.depositar(transferenciaDTO.getValorDaTransferencia());
        contaRepository.save(solicitante);
        contaRepository.save(beneficiario);
    }

    public void contaEhValida(ContaEntity contaEntity) {
        cpfEhValido(contaEntity.getCpf());
        saldoIncialEhValido(contaEntity.getSaldo());
    }

    public void cpfEhValido(String cpf) {
        if (cpf.isEmpty()) {
            throw new CpfInvalidoException("É necessário informar um cpf para abertura de nova conta.");
        }
        if (cpf.length() != 11) {
            throw new CpfInvalidoException("CPF informado para criação de conta está inválido.");
        }
        if (!ehNumerico(cpf)){
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

}
