package ec.edu.monster.ms_auth.services;

import ec.edu.monster.ms_auth.dtos.RespuestaDTO;
import ec.edu.monster.ms_auth.models.Sucursal;
import java.util.List;

public interface SucursalService {
    RespuestaDTO registrar(Sucursal sucursal);

    RespuestaDTO actualizar(Sucursal sucursal);

    RespuestaDTO eliminar(String codigo);

    RespuestaDTO obtener(String codigo);

    List<Sucursal> listar();
}
