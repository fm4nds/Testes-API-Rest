package org.db.configuracao;

import io.restassured.specification.RequestSpecification;

import java.net.URI;

import static io.restassured.RestAssured.given;

public class ConfiguracaoAmbienteTestes {
    private static final String BASE_URL = "https://dummyjson.com";

    static {
        try {
            new URI(BASE_URL);
        } catch (Exception e) {
            throw new RuntimeException("URL base inválida: " + BASE_URL, e);
        }
    }

    public static RequestSpecification configurarRequisicao() {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .log().all();
    }


}
