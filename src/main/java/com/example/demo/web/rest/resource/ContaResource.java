package com.example.demo.web.rest.resource;


import com.example.demo.web.rest.exception.OperacaoNaoAutorizadaException;
import com.example.demo.service.ContaService;
import com.example.demo.web.rest.dto.ContaDTO;
import com.example.demo.web.rest.dto.request.DepositoDTO;
import com.example.demo.web.rest.dto.request.SaqueDTO;
import com.example.demo.web.rest.dto.request.TransferenciaDTO;
import com.example.demo.web.rest.dto.response.ContaRespostaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;

@RestController
@RequestMapping("/conta")
public class ContaResource {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<Object> cadastraConta(@RequestBody ContaDTO contaDTO) throws IOException {
        return ResponseEntity.ok(new ContaRespostaDTO(contaService.salvaConta(contaDTO).getNumeroConta(), "Conta cadastrada com sucesso!"));

    }

    @PostMapping("/deposito")
    public ResponseEntity<Object> depositoEmConta(@RequestBody DepositoDTO depositoDTO) throws OperacaoNaoAutorizadaException {
        contaService.realizaDeposito(depositoDTO);
        return ResponseEntity.ok(new ContaRespostaDTO(depositoDTO.getNumeroDaConta(), "Depósito realizado com sucesso!"));
    }

    @PostMapping("/saque")
    public ResponseEntity<Object> sacarDaConta(@RequestBody SaqueDTO saqueDTO) throws OperationNotSupportedException {
        contaService.realizaSaque(saqueDTO);
        return ResponseEntity.ok(new ContaRespostaDTO("Saque realizado com sucesso!"));
    }

    @PostMapping("/transfencia")
    public ResponseEntity<Object> transferir(@RequestBody TransferenciaDTO transferenciaDTO) {
        contaService.realizaTransferencia(transferenciaDTO);
        return ResponseEntity.ok(new ContaRespostaDTO("Transferência realizada com sucesso!"));
    }

}
