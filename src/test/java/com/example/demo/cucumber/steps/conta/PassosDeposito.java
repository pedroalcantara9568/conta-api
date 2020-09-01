package com.example.demo.cucumber.steps.conta;


import com.example.demo.cucumber.steps.conta.shared.PassosPadroesConta;
import com.example.demo.repository.ContaRepository;
import com.example.demo.web.rest.dto.ContaDTO;
import com.example.demo.web.rest.dto.mapper.ContaMapper;
import com.example.demo.web.rest.dto.request.DepositoDTO;
import gherkin.deps.com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class PassosDeposito {

    protected DepositoDTO depositoDoCenario;

    protected ContaDTO contaDTO;

    @Autowired
    private PassosPadroesConta passosPadroesConta;

    @Autowired
    private ContaRepository contaRepository;

    @PostConstruct
    public void setUp() {
        passosPadroesConta.mockMvc = MockMvcBuilders
                .webAppContextSetup(passosPadroesConta.context)
                .build();
        contaRepository.deleteAll();
    }

    @Dado("que existam as seguintes contas")
    public void queExistamAsSeguintesContas(DataTable tabela) {
        deTabelaParaBanco(tabela);
    }

    private void deTabelaParaBanco(DataTable tabela) {
        contaDTO = new ContaDTO();
        List<Map<String, String>> linhas = tabela.asMaps(String.class, String.class);
        for (Map<String, String> columns : linhas) {
            contaDTO.setCpf(columns.get("Cpf"));
            contaDTO.setNome(columns.get("Nome"));
            contaDTO.setSaldo(Double.parseDouble(columns.get("Saldo")));
            contaDTO.setNumeroConta((columns.get("Numero Conta")));
            passosPadroesConta.contaRepository.save(ContaMapper.dtoToEntity(contaDTO));
        }
    }



    @Quando("for executada a operação de depósito")
    public void forExecutadaAOperacaoDeDeposito() throws Exception {
        MvcResult result = passosPadroesConta.mockMvc.perform(post("http://localhost:8080/conta/deposito")
                .content(new Gson().toJson(this.depositoDoCenario))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print()).andReturn();
        passosPadroesConta.mvcResult = result;
        passosPadroesConta.content = result.getResponse().getContentAsString();
    }


    @E("que seja solicitado um depósito de {double} na conta {string}")
    public void queSejaSolicitadoUmDepositoDeNaConta(Double valorDoDeposito, String numeroDaConta) {
        this.depositoDoCenario = new DepositoDTO(valorDoDeposito, numeroDaConta);
    }

    @E("o saldo da conta {string} deverá ser de {double}")
    public void oSaldoDaContaDeveraSerDe(String numeroDaConta, Double saldoFinal) {

    }
}
