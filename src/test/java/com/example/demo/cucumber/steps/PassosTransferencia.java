package com.example.demo.cucumber.steps;

import com.example.demo.repository.ContaRepository;
import com.example.demo.web.rest.dto.ContaDTO;
import com.example.demo.web.rest.dto.mapper.ContaMapper;
import com.example.demo.web.rest.dto.request.TransferenciaDTO;
import gherkin.deps.com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class PassosTransferencia {

    private TransferenciaDTO transferenciaDTO;

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    PassosPadroesConta passosPadroesConta;

    @Autowired
    ContaRepository contaRepository;

    @PostConstruct
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Before
    public void limpaBanco() {
        contaRepository.deleteAll();
    }

    @Dado("que seja solicitada um transferência com as seguintes informações")
    public void queSejaSolicitadaUmTransferenciaComAsSeguintesInformacoes(DataTable tabela) {
        transferenciaDTO = new TransferenciaDTO();
        List<Map<String, String>> linhas = tabela.asMaps(String.class, String.class);
        for (Map<String, String> columns : linhas) {
            transferenciaDTO.setContaDoBeneficiario(columns.get("Conta do Beneficiário"));
            transferenciaDTO.setContaDoSolicitante(columns.get("Conta do Solicitante"));
            transferenciaDTO.setValorDaTransferencia(Double.parseDouble(columns.get("Valor")));
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
