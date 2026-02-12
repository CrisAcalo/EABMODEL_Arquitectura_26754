package ec.edu.monster.ms_transacciones.services;

import ec.edu.monster.ms_transacciones.dtos.RespuestaDTO;
import ec.edu.monster.ms_transacciones.models.Movimiento;

import java.time.LocalDate;
import java.util.List;

public interface TransaccionService {
    RespuestaDTO realizarDeposito(String codigoCuenta, String claveCuenta,
            java.math.BigDecimal importe, String codigoEmpleado);

    RespuestaDTO realizarRetiro(String codigoCuenta, String claveCuenta,
            java.math.BigDecimal importe, String codigoEmpleado);

    RespuestaDTO realizarTransferencia(String cuentaOrigen, String cuentaDestino,
            String claveCuentaOrigen, java.math.BigDecimal importe,
            String codigoEmpleado);

    List<Movimiento> listarMovimientos(String codigoCuenta, LocalDate fechaInicio, LocalDate fechaFin);
}
