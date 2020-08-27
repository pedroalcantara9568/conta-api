package com.example.demo.service;

import com.example.demo.entity.Conta;
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
import java.util.Optional;


@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ContaDTO salvaConta(ContaDTO contaDTO) {
        Conta entity = ContaMapper.dtoToEntity(contaDTO);
        entity.contaEhValida(entity);
        entity.gerarNumeroConta(entityManager);
        contaRepository.save(entity);
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


}
