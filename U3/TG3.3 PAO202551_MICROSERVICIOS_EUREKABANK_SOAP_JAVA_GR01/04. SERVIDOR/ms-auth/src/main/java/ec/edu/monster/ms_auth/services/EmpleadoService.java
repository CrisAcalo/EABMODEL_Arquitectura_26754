package ec.edu.monster.ms_auth.services;

import ec.edu.monster.ms_auth.dtos.RespuestaDTO;
import ec.edu.monster.ms_auth.models.Empleado;
import java.util.List;

public interface EmpleadoService {
    RespuestaDTO registrar(Empleado empleado);

    RespuestaDTO actualizar(Empleado empleado);

    RespuestaDTO eliminar(String codigo);

    RespuestaDTO obtener(String codigo);

    List<Empleado> listar();

    RespuestaDTO cambiarClave(String codigo, String claveActual, String claveNueva);
}
