package ec.edu.monster.ms_auth.services;

import ec.edu.monster.ms_auth.dtos.RespuestaDTO;
import ec.edu.monster.ms_auth.models.Ventanilla;
import java.util.List;

public interface VentanillaService {
    RespuestaDTO registrar(Ventanilla ventanilla);

    RespuestaDTO actualizar(Ventanilla ventanilla);

    RespuestaDTO eliminar(String codigo);

    RespuestaDTO obtener(String codigo);

    List<Ventanilla> listar();

    List<Ventanilla> listarActivas();

    RespuestaDTO asignarEmpleado(String codigoVentanilla, String codigoEmpleado);
}
