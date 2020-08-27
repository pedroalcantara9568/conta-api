package com.example.demo.web.rest.dto.mapper;

import com.example.demo.entity.Conta;
import com.example.demo.web.rest.dto.ContaDTO;

public class ContaMapper {

    public static Conta dtoToEntity (ContaDTO contaDTO) {
        Conta entity = new Conta();
        entity.setCpf(contaDTO.getCpf());
        entity.setNome(contaDTO.getNome());
        entity.setSaldo(contaDTO.getSaldo());
        entity.setId(contaDTO.getId());
        entity.setNumeroConta(contaDTO.getNumeroCartao());
        return entity;
    }

    public static ContaDTO entityToDto (Conta conta) {
        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setCpf(conta.getCpf());
        contaDTO.setNome(conta.getNome());
        contaDTO.setSaldo(conta.getSaldo());
        contaDTO.setId(conta.getId());
        contaDTO.setNumeroCartao(conta.getNumeroConta());
        return contaDTO;
    }
}
