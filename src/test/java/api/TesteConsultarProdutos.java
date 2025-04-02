package api;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.db.cliente.ClienteConsultaProdutos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class TesteConsultarProdutos {
    @Test
    @DisplayName("CT01 - Deve retornar lista de produtos com estrutura válida e status 200")
    void validarConsultaDeProdutosComSucesso() {
        Response resposta = ClienteConsultaProdutos.consultarProdutos();

        assertEquals(200, resposta.getStatusCode(), "Status code deve ser 200 OK");

        resposta.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/produtos-schema.json"));

        List<Map<String, Object>> produtos = resposta.jsonPath().getList("products");
        assertAll("Validações de integridade dos dados",
                () -> assertFalse(produtos.isEmpty(), "Lista de produtos não deve estar vazia"),
                () -> produtos.forEach(produto -> {
                    assertNotNull(produto.get("id"), "ID não pode ser nulo");
                    assertTrue((Integer) produto.get("id") > 0, "ID deve ser positivo");

                })
        );
    }

    @Test
    @DisplayName("CT02 - Deve validar paginação com parâmetros limit e skip")
    void validarPaginacaoProdutos() {
        int limite = 2;
        int offset = 1;

        Response resposta = ClienteConsultaProdutos.consultarProdutosComParametros(limite, offset);

        List<Map<String, Object>> produtos = resposta.jsonPath().getList("products");
        assertEquals(limite, produtos.size(), "Quantidade de produtos deve corresponder ao limite");
        assertEquals(offset + 1, produtos.get(0).get("id"), "Deve aplicar corretamente o offset");
    }


    @Test
    @DisplayName("CT03 - Deve validar performance da requisição")
    void validarPerformanceConsulta() {
        long tempoMaximoResposta = 2000L;

        Response resposta = ClienteConsultaProdutos.consultarProdutos();
        long tempoResposta = resposta.time();

        assertTrue(tempoResposta <= tempoMaximoResposta,
                "Tempo de resposta deve ser menor que " + tempoMaximoResposta + "ms. Tempo atual: " + tempoResposta + "ms");
    }

}