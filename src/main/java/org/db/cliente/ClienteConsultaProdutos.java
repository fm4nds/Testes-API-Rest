package org.db.cliente;


import io.restassured.response.Response;

import static org.db.configuracao.ConfiguracaoAmbienteTestes.configurarRequisicao;


public class ClienteConsultaProdutos {

    public static Response consultarProdutos() {
        return configurarRequisicao()
                .get("/products");
    }

    public static Response consultarProdutosComParametros(int limite, int proxima) {
        return configurarRequisicao()
                .queryParam("limit", limite)
                .queryParam("skip", proxima)
                .get("/products");
    }
}
