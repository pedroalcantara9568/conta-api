package com.example.demo.web.rest.dto.request;

public class TransferenciaDTO {

    private String contaDoSolicitante;

    private Double valorDaTransferencia;

    private String contaDoBeneficiario;

    public String getContaDoSolicitante() {
        return contaDoSolicitante;
    }

    public void setContaDoSolicitante(String contaDoSolicitante) {
        this.contaDoSolicitante = contaDoSolicitante;
    }

    public Double getValorDaTransferencia() {
        return valorDaTransferencia;
    }

    public void setValorDaTransferencia(Double valorDaTransferencia) {
        this.valorDaTransferencia = valorDaTransferencia;
    }

    public String getContaDoBeneficiario() {
        return contaDoBeneficiario;
    }

    public void setContaDoBeneficiario(String contaDoBeneficiario) {
        this.contaDoBeneficiario = contaDoBeneficiario;
    }
}
