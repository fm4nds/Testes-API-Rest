package org.db.cliente;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ClienteConsultaUsuarios {

    public static Response consultarUsuarios() {
        return given()
                .baseUri("https://dummyjson.com")
                .when()
                .get("/users");
    }
}
