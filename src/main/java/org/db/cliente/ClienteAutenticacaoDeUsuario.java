package org.db.cliente;

import io.restassured.response.Response;
import org.db.configuracao.ConfiguracaoAmbienteTestes;

import java.util.HashMap;
import java.util.Map;

public class ClienteAutenticacaoDeUsuario {
    public static Response autenticarUsuario(String usuario, String senha) {
        Map<String, String> body = new HashMap<>();
        body.put("username", usuario);
        body.put("password", senha);

        return ConfiguracaoAmbienteTestes.configurarRequisicao()
                .body(body)
                .post("/auth/login");
    }
}
