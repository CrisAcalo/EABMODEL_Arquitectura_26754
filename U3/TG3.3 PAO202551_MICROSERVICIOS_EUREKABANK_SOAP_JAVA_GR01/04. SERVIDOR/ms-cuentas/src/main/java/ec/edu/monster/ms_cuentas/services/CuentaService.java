package ec.edu.monster.ms_cuentas.services;

import ec.edu.monster.ms_cuentas.models.Cuenta;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CuentaService {
    List<Cuenta> listarActivas();

    Optional<Cuenta> obtenerPorCodigo(String codigo);

    BigDecimal obtenerSaldo(String codigo);

    Cuenta abrirCuenta(Cuenta cuenta) throws Exception;

    void cancelarCuenta(String codigo) throws Exception;

    boolean validarClave(String codigo, String clave);
}
