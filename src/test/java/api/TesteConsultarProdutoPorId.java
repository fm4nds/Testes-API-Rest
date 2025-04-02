package api;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.db.cliente.ClienteConsultarProdutosPorId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TesteConsultarProdutoPorId {
    @Test
    @DisplayName("CT01 - Deve retornar um produto válido ao consultar por ID")
    void validarConsultaDeProdutoPorId() {
        List<Integer> idProdutos = List.of(1, 2, 5);

        idProdutos.forEach(
                idProduto -> {
                    Response resposta = ClienteConsultarProdutosPorId.consultarProdutoPorId(idProduto);
                    assertEquals(200, resposta.getStatusCode(), "Status code deve ser 200 OK");
                    resposta.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/produto-schema.json"));
                    assertAll("Validações do produto retornado",
                            () -> assertEquals(idProduto, resposta.jsonPath().getInt("id"), "ID do produto deve ser igual ao solicitado")
                    );
                });

    }

    @Test
    @DisplayName("CT02 - Deve retornar erro 404 ao buscar um produto inexistente")
    void validarConsultaDeProdutoInexistente() {
        List<Integer> idProdutosInvalidos = List.of(0, -1, -11);

        idProdutosInvalidos.forEach(
                idProdutoInvalido ->{
                    Response resposta = ClienteConsultarProdutosPorId.consultarProdutoPorId(idProdutoInvalido);
                    assertEquals(404, resposta.getStatusCode(), "Status code deve ser 404 Not Found");

                    String mensagemEsperada = String.format("Product with id '%d' not found", idProdutoInvalido);
                    assertEquals(mensagemEsperada, resposta.jsonPath().getString("message"),
                            "Mensagem de erro deve indicar que o produto não foi encontrado");
                }
        );

    }
}
