package api;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.db.cliente.ClienteAutenticacaoDeUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TesteAutenticacaoDeUsuario {

    @Test
    @DisplayName("CT01 - Deve autenticar um usuário válido e retornar um token")
    void deveAutenticarUsuarioValido() {
        Response resposta = ClienteAutenticacaoDeUsuario.autenticarUsuario("emilys", "emilyspass");

        assertEquals(201, resposta.getStatusCode(), "Status code deve ser 201 OK");
        resposta.then().assertThat()
                .body("token", notNullValue())
                .body("username", equalTo("emilys"))
                .body("email", equalTo("emily.johnson@x.dummyjson.com"));
    }

    @Test
    @DisplayName("CT02 - Não deve autenticar um usuário com credenciais inválidas")
    void naoDeveAutenticarUsuarioInvalido() {
        Response resposta = ClienteAutenticacaoDeUsuario.autenticarUsuario("usuario_invalido", "senha_errada");

        assertEquals(401, resposta.getStatusCode(), "Status code deve ser 401 Unauthorized");
        resposta.then().assertThat()
                .body("message", equalTo("Invalid credentials"));
    }

    @Test
    @DisplayName("CT03 - Não deve autenticar um usuário sem fornecer credenciais")
    void naoDeveAutenticarSemCredenciais() {
        Response resposta = ClienteAutenticacaoDeUsuario.autenticarUsuario("", "");

        assertEquals(400, resposta.getStatusCode(), "Status code deve ser 400 Bad Request");
        assertEquals("Username and password required",
                resposta.jsonPath().getString("message"),
                "Mensagem de erro deve indicar que usuário e senha são obrigatórios");
    }

    @Test
    @DisplayName("CT04 - Validar estrutura JSON da resposta de autenticação com sucesso")
    void validarEstruturaJsonRespostaAutenticacao() {
        Response resposta = ClienteAutenticacaoDeUsuario.autenticarUsuario("emilys", "emilyspass");

        resposta.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/autenticacao_de_usuario_com_sucesso.json"));
    }
}
