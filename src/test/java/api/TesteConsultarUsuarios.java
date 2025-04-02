package api;

import org.db.cliente.ClienteConsultaUsuarios;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TesteConsultarUsuarios {
    @Test
    @DisplayName("CT01 - Valida a consulta e estrutura da lista de usuários via GET /users")
    void verificarUsuariosComUsernameEPasswordValidos() {
        Response response = ClienteConsultaUsuarios.consultarUsuarios();

        assertEquals(200, response.getStatusCode());

        List<Object> usuarios = response.jsonPath().getList("users");
        assertFalse(usuarios.isEmpty(), "Lista de usuários vazia");

        List<String> usernames = response.jsonPath().getList("users.username");
        List<String> passwords = response.jsonPath().getList("users.password");
        usernames.forEach(username -> assertNotNull(username, "Usuário não pode estar em branco"));
        passwords.forEach(password -> assertNotNull(password, "Senha não pode estar em branco"));

    }

    @Test
    @DisplayName("CT02 - Valida se os campos username e password são do tipo string na resposta de GET /users")
    void verificarSeUsernameEPasswordSaoTipoString() {
        Response response = ClienteConsultaUsuarios.consultarUsuarios();

        List<Map<String, Object>> usuarios = response.jsonPath().getList("users");
        assertFalse(usuarios.isEmpty(), "Lista de usuários vazia");

        usuarios.forEach(usuario -> {
            assertInstanceOf(String.class, usuario.get("username"), "Username deve ser String");
            assertInstanceOf(String.class, usuario.get("password"), "Password deve ser String");
        });
    }
}
