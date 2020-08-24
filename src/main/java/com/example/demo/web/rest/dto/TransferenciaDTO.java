package com.example.demo.web.rest.dto;

public class TransferenciaDTO {

    private Long contaDoSolicitante;

    private Double valorDaTransferencia;

    private Long contaDoBeneficiario;

    public Long getContaDoSolicitante() {
        return contaDoSolicitante;
    }

    public void setContaDoSolicitante(Long contaDoSolicitante) {
        this.contaDoSolicitante = contaDoSolicitante;
    }

    public Double getValorDaTransferencia() {
        return valorDaTransferencia;
    }

    public void setValorDaTransferencia(Double valorDaTransferencia) {
        this.valorDaTransferencia = valorDaTransferencia;
    }

    public Long getContaDoBeneficiario() {
        return contaDoBeneficiario;
    }

    public void setContaDoBeneficiario(Long contaDoBeneficiario) {
        this.contaDoBeneficiario = contaDoBeneficiario;
    }
}
