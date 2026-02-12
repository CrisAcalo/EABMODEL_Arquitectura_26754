package ec.edu.monster.ms_cuentas.services;

import ec.edu.monster.ms_cuentas.models.Moneda;
import java.util.List;
import java.util.Optional;

public interface MonedaService {
    List<Moneda> listarTodas();

    Optional<Moneda> obtenerPorCodigo(String codigo);

    Moneda guardar(Moneda moneda);

    void eliminar(String codigo);
}
