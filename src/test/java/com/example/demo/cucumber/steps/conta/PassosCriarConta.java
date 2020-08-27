package com.example.demo.cucumber.steps.conta;

import com.example.demo.cucumber.steps.def.PassosPadroesConta;
import com.example.demo.web.rest.dto.ContaDTO;
import com.example.demo.web.rest.dto.response.ContaRespostaDTO;
import gherkin.deps.com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class PassosCriarConta {

    ContaDTO conta;

    @Autowired
    private PassosPadroesConta passosPadroesConta;

    @Dado("que seja solicitada a criação de uma nova conta com os seguintes valores")
    public void queSejaSolicitadaAhCriacaoDeUmaNovaContaComOsSeguintesValores(DataTable tabela) {
        this.conta = deTabelaParaContaDto(tabela);
    }

    @Quando("for enviada a solicitação de criação de nova conta")
    public void forEnviadaASolicitacaoDeCriacaoDeNovaConta() throws Exception {
        MvcResult result = passosPadroesConta.mockMvc.perform(post("/conta")
                .content(new Gson().toJson(conta))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print()).andReturn();
        passosPadroesConta.mvcResult = result;
        passosPadroesConta.content = result.getResponse().getContentAsString();
    }

    @Então("deverá ser retornado o número da conta criada")
    public void deveraSerRetornadoONumeroDaContaCriada() {
        ContaRespostaDTO contaRespostaDTO = new Gson().fromJson(passosPadroesConta.content, ContaRespostaDTO.class);
        Assert.assertNotNull(contaRespostaDTO.getNumeroDaConta());
    }

    private ContaDTO deTabelaParaContaDto(DataTable tabela) {
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
