package org.db.cliente;

import io.restassured.response.Response;

import static org.db.configuracao.ConfiguracaoAmbienteTestes.configurarRequisicao;

public class ClientProdutosComAutenticacao {
    public static Response buscarProdutos(String token) {
        return configurarRequisicao()
                .header("Authorization", "Bearer " + token)
                .get("/auth/products");
    }

    public static Response buscarProdutosSemTokenDeAutenticacao() {
        return configurarRequisicao()
                .get("/auth/products");
    }
}
