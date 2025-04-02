package org.db.cliente;

import io.restassured.response.Response;

import static org.db.configuracao.ConfiguracaoAmbienteTestes.configurarRequisicao;

public class ClienteConsultarProdutosPorId {
    public static Response consultarProdutoPorId(int id) {
        return configurarRequisicao()
                .get("/products/{id}", id);
    }
}
