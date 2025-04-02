package org.db.cliente;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.db.configuracao.ConfiguracaoAmbienteTestes.configurarRequisicao;

public class ClienteStatusAplicacao {
    public static Response consultarStatusAplicacao() {
        return configurarRequisicao()
                .get("/test");
    }
}
