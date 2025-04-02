package api;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.db.cliente.ClientProdutosComAutenticacao;
import org.db.cliente.ClienteAutenticacaoDeUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TesteProdutosComAutenticacao {
    @Test
    @DisplayName("CT01 - Deve retornar produtos com usuario autenticado com estrutura válida")
    void deveRetornarProdutosAutenticadosComTokenValido() {
        // Obter token válido
        Response authResponse = ClienteAutenticacaoDeUsuario.autenticarUsuario("emilys", "emilyspass");
        String token = authResponse.jsonPath().getString("accessToken");

        Response resposta = ClientProdutosComAutenticacao.buscarProdutos(token);

        // Validações básicas
        assertEquals(200, resposta.getStatusCode(), "Status code deve ser 200 OK");

        // Validação do schema
        resposta.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/produtos-autenticados-schema.json"));

        // Validações de dados
        List<Map<String, Object>> produtos = resposta.jsonPath().getList("products");
        int totalProdutos = resposta.jsonPath().getInt("total");

        assertAll("Validações de integridade dos dados",
                () -> assertFalse(produtos.isEmpty(), "Lista de produtos não deve estar vazia"),
                () -> assertTrue(totalProdutos > 0, "Total de produtos deve ser positivo"),
                () -> produtos.forEach(produto -> {
                    assertNotNull(produto.get("id"), "ID não pode ser nulo");
                })
        );
    }

    @Test
    @DisplayName("CT02 - Não deve retornar produtos com token inválido")
    void naoDeveRetornarProdutosComTokenInvalido() {
        Response resposta = ClientProdutosComAutenticacao.buscarProdutos("token_invalido_123");

        assertEquals(401, resposta.getStatusCode(), "Status code deve ser 401 Unauthorized");
        assertEquals("Invalid/Expired Token!",
                resposta.jsonPath().getString("message"),
                "Mensagem de erro deve corresponder à documentação");
        assertEquals("JsonWebTokenError",
                resposta.jsonPath().getString("name"),
                "Mensagem de erro deve corresponder à documentação");
    }

    @Test
    @DisplayName("CT03 - Não deve retornar produtos sem token de autenticação")
    void naoDeveRetornarProdutosSemAutenticacao() {
        Response resposta = ClientProdutosComAutenticacao.buscarProdutosSemTokenDeAutenticacao();

        assertEquals(403, resposta.getStatusCode(), "Status code deve ser 403 Forbidden");
        assertEquals("Authentication Problem",
                resposta.jsonPath().getString("message"),
                "Mensagem de erro deve indicar problema de autenticação");
    }

    @Test
    @DisplayName("CT04 - Deve validar estrutura do JSON para erro 401 Unauthorized")
    void deveValidarEstruturaJsonErro401() {
        Response resposta = ClientProdutosComAutenticacao.buscarProdutos("token_invalido_123");

        resposta.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/erro-401-produto-autenticado-schema.json"));

        assertAll("Validações específicas do erro 401",
                () -> assertEquals("Invalid/Expired Token!", resposta.jsonPath().getString("message"), "Mensagem deve mencionar token"),
                () -> assertEquals("JsonWebTokenError", resposta.jsonPath().getString("name"), "Nome do erro deve ser específico")

        );
    }

    @Test
    @DisplayName("CT05 - Deve validar estrutura do JSON para erro 403 Forbidden")
    void deveValidarEstruturaJsonErro403() {
        Response resposta = ClientProdutosComAutenticacao.buscarProdutosSemTokenDeAutenticacao();

        resposta.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/erro-403-produto-autenticado-schema.json"));

        assertEquals("Authentication Problem", resposta.jsonPath().getString("message"), "Mensagem deve ser igual a da documentação");
    }

}
