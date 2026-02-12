package ec.edu.monster.ms_cuentas.services;

import ec.edu.monster.ms_cuentas.models.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> listarTodos();

    Optional<Cliente> obtenerPorCodigo(String codigo);

    Optional<Cliente> obtenerPorDni(String dni);

    Cliente registrar(Cliente cliente) throws Exception;

    Cliente actualizar(Cliente cliente) throws Exception;

    void eliminar(String codigo) throws Exception;
}
