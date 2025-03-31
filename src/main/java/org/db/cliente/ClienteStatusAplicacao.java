package org.db.cliente;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ClienteStatusAplicacao {
    public static Response consultarStatusAplicacao() {
        return given()
                .baseUri("https://dummyjson.com")
                .when()
                .get("/test");
    }
}
