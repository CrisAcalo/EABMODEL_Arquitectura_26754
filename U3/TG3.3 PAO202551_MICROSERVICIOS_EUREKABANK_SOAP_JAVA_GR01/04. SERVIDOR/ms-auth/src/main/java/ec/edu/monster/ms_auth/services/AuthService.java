package ec.edu.monster.ms_auth.services;

import ec.edu.monster.ms_auth.dtos.RespuestaDTO;

public interface AuthService {
    RespuestaDTO login(String usuario, String clave);
}
