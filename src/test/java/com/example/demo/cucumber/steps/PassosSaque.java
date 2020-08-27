package com.example.demo.cucumber.steps;

import com.example.demo.web.rest.dto.request.SaqueDTO;
import gherkin.deps.com.google.gson.Gson;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class PassosSaque {

    SaqueDTO saqueDTO = new SaqueDTO();

    @Autowired
    PassosCriarConta passosCriarConta;

    @Autowired
    PassosDeposito passosDeposito;

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    PassosPadroesConta passosPadroesConta;

    @Before
    public void limpaBanco () {
        this.passosDeposito.contaRepository.deleteAll();
    }

    @PostConstruct
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Dado("que seja solicitado um saque de {string}")
    public void queSejaSolicitadoUmSaqueDe (String valorDoSaque) {
        this.saqueDTO.setNumeroDaConta(passosDeposito.conta.getNumeroCartao());
        this.saqueDTO.setValorDoSaque(Double.parseDouble(valorDoSaque));
    }

    @Quando("for executada a operação de saque")
    public void forExecutadaAOperacaoDeSaque( ) throws Exception {
        MvcResult result = mockMvc.perform(post("http://localhost:8080/conta/saque")
                .content(new Gson().toJson(this.saqueDTO))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print()).andReturn();
        this.passosPadroesConta.mvcResult = result;
        this.passosPadroesConta.content = result.getResponse().getContentAsString();
    }
}
