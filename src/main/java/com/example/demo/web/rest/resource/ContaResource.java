package com.example.demo.web.rest.resource;


import com.example.demo.exception.OperacaoNaoAutorizadaException;
import com.example.demo.exception.SaldoInicialInvalidoException;
import com.example.demo.service.ContaService;
import com.example.demo.web.rest.dto.ContaDTO;
import com.example.demo.web.rest.dto.DepositoDTO;
import com.example.demo.web.rest.dto.SaqueDTO;
import com.example.demo.web.rest.dto.TransferenciaDTO;
import com.example.demo.web.rest.dto.response.ContaRespostaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.OperationNotSupportedException;

@Controller
@RequestMapping("/conta")
public class ContaResource {

    @Autowired
    private ContaService contaService;

    public ContaResource(ContaService contaService) {
        this.contaService = contaService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Object> cadastraConta(@RequestBody ContaDTO contaDTO) throws SaldoInicialInvalidoException {
        ContaDTO contaCadastrada = contaService.salvaConta(contaDTO);
        return ResponseEntity.ok(new ContaRespostaDTO(contaCadastrada.getId(),"Conta cadastrada com sucesso!"));
    }

    @PostMapping("/deposito")
    public ResponseEntity<Object> depositoEmConta(@RequestBody DepositoDTO depositoDTO) throws OperacaoNaoAutorizadaException {
        contaService.realizaDeposito(depositoDTO);
        return ResponseEntity.ok(new ContaRespostaDTO( depositoDTO.getNumeroDaConta(),"Depósito realizado com sucesso!"));
    }

    @PostMapping("/saque")
    public ResponseEntity<Object> sacarDaConta(@RequestBody SaqueDTO saqueDTO) throws OperationNotSupportedException {
        contaService.realizaSaque(saqueDTO);
        return ResponseEntity.ok(saqueDTO);
    }

    @PostMapping("/transfencia")
    public ResponseEntity<Object> transferir(@RequestBody TransferenciaDTO transferenciaDTO) {
        contaService.realizaTransferencia(transferenciaDTO);
        return ResponseEntity.ok(transferenciaDTO);
    }

}
