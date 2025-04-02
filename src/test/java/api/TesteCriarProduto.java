package api;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.db.cliente.ClienteCriarProduto;
import org.db.modelos.CriarProduto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TesteCriarProduto {
    @Test
    @DisplayName("CT01 - Deve criar produto com dados válidos")
    void deveCriarProdutoComDadosValidos() {
        CriarProduto requisicao = CriarProduto.criarProdutoValido();

        var resposta = ClienteCriarProduto.criarProduto(requisicao);

        assertEquals(201, resposta.getStatusCode());
        resposta.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/produto-criado-schema.json"));
        assertAll("Validação dos dados do produto criado",
                () -> resposta.jsonPath().getInt("id"),
                () -> assertEquals(requisicao.getTitle(), resposta.jsonPath().getString("title")),
                () -> assertEquals(requisicao.getPrice(), resposta.jsonPath().getDouble("price")),
                () -> assertEquals(requisicao.getStock(), resposta.jsonPath().getInt("stock")),
                () -> assertEquals(requisicao.getRating(), resposta.jsonPath().getDouble("rating")),
                () -> assertEquals(requisicao.getThumbnail(), resposta.jsonPath().getString("thumbnail")),
                () -> assertEquals(requisicao.getBrand(), resposta.jsonPath().getString("brand")),
                () -> assertEquals(requisicao.getCategory(), resposta.jsonPath().getString("category")),
                () -> assertTrue(resposta.jsonPath().getString("description").contains("Produto teste"))
        );
    }

    @Test
    @DisplayName("CT02 - Não deve criar produto sem campos obrigatórios")
    void naoDeveCriarProdutosComAtrubutosNulos() {
        CriarProduto request = CriarProduto.criarProdutoComCamposFaltando();

        var resposta = ClienteCriarProduto.criarProduto(request);

        assertEquals(400, resposta.getStatusCode());
    }

}
