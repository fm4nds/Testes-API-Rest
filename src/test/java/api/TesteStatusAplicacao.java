package api;

import org.db.cliente.ClienteStatusAplicacao;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TesteStatusAplicacao {
    @Test
    @DisplayName("Valida status da aplicação via GET /test")
    void verificarStatusValidoDaAplicacao() {
        Response response = ClienteStatusAplicacao.consultarStatusAplicacao();

       assertEquals(200, response.getStatusCode());
       assertEquals("ok", response.jsonPath().getString("status"));
       assertEquals("GET", response.jsonPath().getString("method"));

    }
}
