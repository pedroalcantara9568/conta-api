package com.example.demo.cucumber.steps;

import com.example.demo.repository.ContaRepository;
import com.example.demo.service.ContaService;
import com.example.demo.web.rest.dto.ContaDTO;
import com.example.demo.web.rest.dto.response.ContaRespostaDTO;
import com.example.demo.web.rest.resource.ContaResource;
import gherkin.deps.com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class PassosCriarConta {

    ContaDTO conta;

    ContaResource contaResource;

    MockMvc mockMvc;

    @Autowired
    PassosPadroesConta passosPadroesConta;

    @Autowired
    ContaService contaService;

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    @PostConstruct
    public void setUp() {
        jackson2HttpMessageConverter.setDefaultCharset(Charset.defaultCharset());
        contaResource = new ContaResource(contaService);
        contaService = new ContaService(contaRepository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(contaResource)
                .setControllerAdvice(new RuntimeException())
                .setMessageConverters(jackson2HttpMessageConverter)
                .build();
    }

    @Dado("que seja solicitada a criação de uma nova conta com os seguintes valores")
    public void queSejaSolicitadaAhCriacaoDeUmaNovaContaComOsSeguintesValores(DataTable tabela) {
        this.conta = deTabelaParaContaDto(tabela);
    }

    @Quando("for enviada a solicitação de criação de nova conta")
    public void forEnviadaASolicitacaoDeCriacaoDeNovaConta() throws Exception {
        MvcResult result = mockMvc.perform(post("/conta")
                .content(new Gson().toJson(conta))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print()).andReturn();
        this.passosPadroesConta.mvcResult = result;
        this.passosPadroesConta.content = result.getResponse().getContentAsString();
    }

    @Então("deverá ser retornado o número da conta criada")
    public void deveraSerRetornadoONumeroDaContaCriada() {
        ContaRespostaDTO contaRespostaDTO = new Gson().fromJson(this.passosPadroesConta.content, ContaRespostaDTO.class);
        Assert.assertNotNull(contaRespostaDTO.getNumeroDaConta());
    }

    private ContaDTO deTabelaParaContaDto(DataTable tabela){
        conta = new ContaDTO();
        List<Map<String, String>> linhas = tabela.asMaps(String.class, String.class);
        for (Map<String, String> columns : linhas) {
            this.conta.setCpf(columns.get("Cpf"));
            this.conta.setNome(columns.get("Nome"));
            this.conta.setSaldo(Double.parseDouble(columns.get("Saldo")));
        }
        return conta;
    }
}
