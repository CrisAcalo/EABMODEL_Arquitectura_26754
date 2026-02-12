package ec.edu.monster.ms_auth.services.impl;

import ec.edu.monster.ms_auth.dtos.RespuestaDTO;
import ec.edu.monster.ms_auth.models.Empleado;
import ec.edu.monster.ms_auth.repositories.EmpleadoRepository;
import ec.edu.monster.ms_auth.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public RespuestaDTO login(String usuario, String clave) {
        Optional<Empleado> empleadoOpt = empleadoRepository.findByUsuario(usuario);

        if (empleadoOpt.isPresent()) {
            Empleado empleado = empleadoOpt.get();
            // Verificar hash
            if (ec.edu.monster.ms_auth.utils.PasswordUtils.verificarPassword(clave, empleado.getClave())) {
                // Por seguridad, no devolvemos la clave en la respuesta
                empleado.setClave(null);
                return RespuestaDTO.exito("Autenticación correcta", empleado);
            }
        }

        return RespuestaDTO.error("Usuario o clave incorrectos", "AUTH_FAIL");
    }
}
