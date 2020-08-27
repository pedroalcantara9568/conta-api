package com.example.demo.cucumber.steps.def;

import com.example.demo.entity.Conta;
import com.example.demo.repository.ContaRepository;
import com.example.demo.web.rest.dto.response.ContaRespostaDTO;
import gherkin.deps.com.google.gson.Gson;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

public class PassosPadroesConta {

    public MockMvc mockMvc;

    public MvcResult mvcResult;

    public String content;

    @Autowired
    public WebApplicationContext context;

    @Autowired
    public ContaRepository contaRepository;

    @PostConstruct
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Então("deverá ser apresentada a seguinte mensagem de erro {string}")
    public void deveraSerApresentadaASeguinteMensagemDeErro(String mensagem) {
        Assert.assertEquals(mvcResult.getResolvedException().getMessage(), mensagem);
    }

    @E("deverá ser apresentada a seguinte mensagem {string}")
    public void deveraSerApresentadaASeguinteMensagem(String mensagem) {
        ContaRespostaDTO contaRespostaDTO = new Gson().fromJson(content, ContaRespostaDTO.class);
        Assert.assertEquals(contaRespostaDTO.getMensagem(), mensagem);
    }

    @E("o saldo da conta {string} deverá ser de {string}")
    public void oSaldoDaContaDeveraSerDe(String numeroDaConta, String saldoDaConta) {
        Conta entity = contaRepository.findByNumeroConta(numeroDaConta).get();
        assert entity.getSaldo().equals(Double.parseDouble(saldoDaConta));
    }
}
