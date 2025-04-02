package org.db.cliente;

import io.restassured.response.Response;
import org.db.modelos.CriarProduto;

import static org.db.configuracao.ConfiguracaoAmbienteTestes.configurarRequisicao;

public class ClienteCriarProduto {
    public static Response criarProduto(CriarProduto conteudo){
        return configurarRequisicao()
                .body(conteudo)
                .post("/products/add");
    }

    public static Response criarProduto(String json) {
        return configurarRequisicao()
                .body(json)
                .post("/products/add");
    }
}
