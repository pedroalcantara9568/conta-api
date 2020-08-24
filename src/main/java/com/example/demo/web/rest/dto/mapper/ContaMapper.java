package com.example.demo.web.rest.dto.mapper;

import com.example.demo.entity.ContaEntity;
import com.example.demo.web.rest.dto.ContaDTO;

public class ContaMapper {

    public static ContaEntity dtoToEntity (ContaDTO contaDTO) {
        ContaEntity entity = new ContaEntity();
        entity.setCpf(contaDTO.getCpf());
        entity.setNome(contaDTO.getNome());
        entity.setSaldo(contaDTO.getSaldo());
        entity.setId(contaDTO.getId());
        return entity;
    }

    public static ContaDTO entityToDto (ContaEntity contaEntity) {
        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setCpf(contaEntity.getCpf());
        contaDTO.setNome(contaEntity.getNome());
        contaDTO.setSaldo(contaEntity.getSaldo());
        contaDTO.setId(contaEntity.getId());
        return contaDTO;
    }
}
