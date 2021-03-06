package com.example.demo.cucumber.steps.conta;

import com.example.demo.cucumber.steps.conta.shared.PassosPadroesConta;
import com.example.demo.web.rest.dto.request.TransferenciaDTO;
import gherkin.deps.com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import cucumber.api.java.pt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class PassosTransferencia {

    private TransferenciaDTO transferenciaDTO;

    private MockMvc mockMvc;

    @Autowired
    private PassosPadroesConta passosPadroesConta;

    @PostConstruct
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(passosPadroesConta.context)
                .build();
    }

    @Dado("que seja solicitada um transferência com as seguintes informações")
    public void queSejaSolicitadaUmTransferenciaComAsSeguintesInformacoes(DataTable tabela) {
        transferenciaDTO = new TransferenciaDTO();
        List<Map<String, String>> linhas = tabela.asMaps(String.class, String.class);
        for (Map<String, String> columns : linhas) {
            transferenciaDTO.setContaDoBeneficiario(columns.get("Conta do Beneficiário"));
            transferenciaDTO.setContaDoSolicitante(columns.get("Conta do Solicitante"));
            transferenciaDTO.setValorDaTransferencia(Double.parseDouble(columns.get("Valor da Transferência")));
        }
    }

    @Quando("for executada a operação de transferência")
    public void forExecutadaAOperacaoDeTransferencia() throws Exception {
        MvcResult result = mockMvc.perform(post("http://localhost:8080/conta/transfencia")
                .content(new Gson().toJson(this.transferenciaDTO))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print()).andReturn();
        this.passosPadroesConta.mvcResult = result;
        this.passosPadroesConta.content = result.getResponse().getContentAsString();
    }
}
