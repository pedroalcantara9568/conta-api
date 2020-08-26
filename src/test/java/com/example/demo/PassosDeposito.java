package com.example.demo;


import com.example.demo.entity.ContaEntity;
import com.example.demo.repository.ContaRepository;
import com.example.demo.service.ContaService;
import com.example.demo.web.rest.dto.ContaDTO;
import com.example.demo.web.rest.dto.DepositoDTO;
import com.example.demo.web.rest.dto.response.ContaRespostaDTO;
import com.example.demo.web.rest.resource.ContaResource;
import gherkin.deps.com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
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
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class PassosDeposito {

    ContaDTO conta;

    DepositoDTO depositoDoCenario;

    MockMvc mockMvc;

    MvcResult mvcResult;

    String content;


    @Autowired
    ContaResource contaResource;

    @Autowired
    ContaService contaService;

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    EntityManager entityManager;
    
    @Autowired
    PassosCriarConta passosCriarConta;

    @PostConstruct
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Dado("que existam as seguintes contas")
    public void queExistamAsSeguintesContas(DataTable tabela) throws Exception {
        this.conta = deTabelaParaContaDto(tabela);
        mockMvc.perform(post("/conta")
                .content(new Gson().toJson(this.conta))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print()).andReturn();
    }

    private ContaDTO deTabelaParaContaDto(DataTable tabela) {
        conta = new ContaDTO();
        List<Map<String, String>> linhas = tabela.asMaps(String.class, String.class);
        for (Map<String, String> columns : linhas) {
            conta.setCpf("12345678912");
            conta.setNome("Pedro Henrique Silva de Alcântara");
            conta.setSaldo(Double.parseDouble(columns.get("Saldo")));
            conta.setId(Long.parseLong(columns.get("Numero Conta")));
        }
        return conta;
    }

    @E("que seja solicitado um depósito de {string}")
    public void queSejaSolicitadoUmDepositoDe(String valorDeposito) {
        DepositoDTO depositoDTO = new DepositoDTO();
        depositoDTO.setNumeroDaConta(this.conta.getId());
        depositoDTO.setValorDeposito(Double.parseDouble(valorDeposito));
        this.depositoDoCenario = depositoDTO;
    }

    @Quando("for executada a operação de depósito")
    public void forExecutadaAOperaçãoDeDepósito() throws Exception {
        MvcResult result = mockMvc.perform(post("http://localhost:8080/conta/deposito")
                .content(new Gson().toJson(this.depositoDoCenario))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print()).andReturn();

        this.mvcResult = result;
        this.content = result.getResponse().getContentAsString();
    }
    
    @E("o saldo da conta {string} deverá ser de {string}")
    public void oSaldoDaContaDeveráSerDe(String numeroDaConta, String saldoDaConta) {
        ContaEntity entity = contaRepository.findById(Long.parseLong(numeroDaConta)).get();
        entity.getSaldo().equals(Double.parseDouble(saldoDaConta));
    }
}
