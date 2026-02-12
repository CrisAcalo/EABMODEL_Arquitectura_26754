package ec.edu.monster.ms_auth.ws;

import ec.edu.monster.ms_auth.dtos.LoginRequest;
import ec.edu.monster.ms_auth.dtos.LoginResponse;
import ec.edu.monster.ms_auth.dtos.RespuestaDTO;
import ec.edu.monster.ms_auth.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class AuthEndpoint {

    private static final String NAMESPACE_URI = "http://monster.edu.ec/ms-auth/ws";

    @Autowired
    private AuthService authService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
    @ResponsePayload
    public LoginResponse login(@RequestPayload LoginRequest request) {
        RespuestaDTO respuesta = authService.login(request.getUsuario(), request.getClave());

        // Envolvemos en LoginResponse (que tiene un campo 'return' o 'respuesta')
        return new LoginResponse(respuesta);
    }
}
