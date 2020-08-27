package com.example.demo.cucumber.steps;


import com.example.demo.entity.Conta;
import com.example.demo.repository.ContaRepository;
import com.example.demo.service.ContaService;
import com.example.demo.web.rest.dto.ContaDTO;
import com.example.demo.web.rest.dto.request.DepositoDTO;
import com.example.demo.web.rest.dto.mapper.ContaMapper;
import com.example.demo.web.rest.resource.ContaResource;
import gherkin.deps.com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class PassosDeposito {

    ContaDTO conta;

    DepositoDTO depositoDoCenario;

    MockMvc mockMvc;

    @Autowired
    ContaResource contaResource;

    @Autowired
    ContaService contaService;

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    PassosPadroesConta passosPadroesConta;

    @PostConstruct
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Dado("que existam as seguintes contas")
    public void queExistamAsSeguintesContas(DataTable tabela) throws Exception {
        deTabelaParaBanco(tabela);
    }

    private void deTabelaParaBanco(DataTable tabela) {
        conta = new ContaDTO();
        List<Map<String, String>> linhas = tabela.asMaps(String.class, String.class);
        for (Map<String, String> columns : linhas) {
            conta.setCpf("12345678912");
            conta.setNome("Pedro Henrique Silva de Alcântara");
            conta.setSaldo(Double.parseDouble(columns.get("Saldo")));
            conta.setNumeroCartao((columns.get("Numero Conta")));
            contaRepository.save(ContaMapper.dtoToEntity(conta));
        }

    }

    @E("que seja solicitado um depósito de {string}")
    public void queSejaSolicitadoUmDepositoDe(String valorDeposito) {
        DepositoDTO depositoDTO = new DepositoDTO();
        depositoDTO.setNumeroDaConta(this.conta.getNumeroCartao());
        depositoDTO.setValorDeposito(Double.parseDouble(valorDeposito));
        this.depositoDoCenario = depositoDTO;
    }

    @Quando("for executada a operação de depósito")
    public void forExecutadaAOperacaoDeDeposito() throws Exception {
        MvcResult result = mockMvc.perform(post("http://localhost:8080/conta/deposito")
                .content(new Gson().toJson(this.depositoDoCenario))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print()).andReturn();
        this.passosPadroesConta.mvcResult = result;
        this.passosPadroesConta.content = result.getResponse().getContentAsString();
    }
    

}
