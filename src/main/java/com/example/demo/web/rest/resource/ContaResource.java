package com.example.demo.web.rest.resource;


import com.example.demo.service.ContaService;
import com.example.demo.web.rest.dto.ContaDTO;
import com.example.demo.web.rest.dto.request.DepositoDTO;
import com.example.demo.web.rest.dto.request.SaqueDTO;
import com.example.demo.web.rest.dto.request.TransferenciaDTO;
import com.example.demo.web.rest.dto.response.ContaRespostaDTO;
import com.example.demo.web.rest.exception.OperacaoNaoAutorizadaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conta")
public class ContaResource {

    final private ContaService contaService;

    @Autowired
    public ContaResource(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<Object> cadastraConta(@RequestBody ContaDTO contaDTO) {
        return ResponseEntity.ok(new ContaRespostaDTO(contaService.salvaConta(contaDTO).getNumeroConta(), "Conta cadastrada com sucesso!"));

    }

    @PostMapping("/deposito")
    public ResponseEntity<Object> depositoEmConta(@RequestBody DepositoDTO depositoDTO) throws OperacaoNaoAutorizadaException {
        contaService.realizaDeposito(depositoDTO);
        return ResponseEntity.ok(new ContaRespostaDTO(depositoDTO.getNumeroDaConta(), "Depósito realizado com sucesso!"));
    }

    @PostMapping("/saque")
    public ResponseEntity<Object> sacarDaConta(@RequestBody SaqueDTO saqueDTO) {
        contaService.realizarSaque(saqueDTO);
        return ResponseEntity.ok(new ContaRespostaDTO("Saque realizado com sucesso!"));
    }

    @PostMapping("/transfencia")
    public ResponseEntity<Object> transferir(@RequestBody TransferenciaDTO transferenciaDTO) {
        contaService.realizaTransferencia(transferenciaDTO);
        return ResponseEntity.ok(new ContaRespostaDTO("Transferência realizada com sucesso!"));
    }

}
