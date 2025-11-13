package ec.edu.monster.services;

import ec.edu.monster.constants.MensajesConstants;
import ec.edu.monster.constants.TipoMovimientoConstants;
import ec.edu.monster.dal.CuentaDAO;
import ec.edu.monster.dal.MovimientoDAO;
import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Cuenta;
import ec.edu.monster.models.Movimiento;
import ec.edu.monster.models.dto.*;
import ec.edu.monster.validators.CuentaValidator;
import ec.edu.monster.validators.TransaccionValidator;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio de lógica de negocio para transacciones bancarias
 * IMPORTANTE: Retiro y Transferencia usan transacciones SQL
 */
public class TransaccionService {

    private static final Logger LOGGER = Logger.getLogger(TransaccionService.class.getName());
    private final CuentaDAO cuentaDAO;
    private final MovimientoDAO movimientoDAO;

    public TransaccionService() {
        this.cuentaDAO = new CuentaDAO();
        this.movimientoDAO = new MovimientoDAO();
    }

    /**
     * Realiza un depósito en una cuenta
     * NO usa transacción SQL (operación simple)
     */
    public RespuestaDTO realizarDeposito(TransaccionDTO datos) {
        try {
            // Validar importe
            TransaccionValidator.validarImporte(datos.getImporte());

            // Obtener y validar cuenta
            Cuenta cuenta = cuentaDAO.obtenerPorCodigo(datos.getCodigoCuenta());
            CuentaValidator.validarParaDeposito(cuenta, datos.getClaveCuenta(), datos.getImporte());

            // Guardar saldo anterior
            BigDecimal saldoAnterior = cuenta.getSaldo();

            // Calcular nuevo saldo
            BigDecimal nuevoSaldo = saldoAnterior.add(datos.getImporte());

            // Obtener siguiente número de movimiento
            int numeroMovimiento = movimientoDAO.obtenerUltimoNumero(cuenta.getCodigo()) + 1;

            // Registrar movimiento
            Movimiento movimiento = new Movimiento(
                    cuenta.getCodigo(),
                    numeroMovimiento,
                    LocalDate.now(),
                    datos.getCodigoEmpleado(),
                    TipoMovimientoConstants.DEPOSITO,
                    datos.getImporte(),
                    null
            );
            movimientoDAO.insertar(movimiento);

            // Actualizar saldo y contador
            cuentaDAO.actualizarSaldo(cuenta.getCodigo(), nuevoSaldo);
            cuentaDAO.incrementarContadorMovimientos(cuenta.getCodigo());

            // Crear DTO de resultado
            DepositoResultDTO resultado = new DepositoResultDTO(
                    cuenta.getCodigo(),
                    datos.getImporte(),
                    saldoAnterior,
                    nuevoSaldo,
                    numeroMovimiento
            );

            LOGGER.info("Depósito exitoso en cuenta: " + cuenta.getCodigo());
            return RespuestaDTO.exito(MensajesConstants.DEPOSITO_EXITOSO, resultado);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error en depósito", e);
            return RespuestaDTO.error(e.getMessage());
        }
    }

    /**
     * Realiza un retiro de una cuenta
     * USA TRANSACCIÓN SQL (puede generar múltiples movimientos)
     */
    public RespuestaDTO realizarRetiro(TransaccionDTO datos) {
        Connection conn = null;
        try {
            // Validar importe
            TransaccionValidator.validarImporte(datos.getImporte());

            // Obtener cuenta
            Cuenta cuenta = cuentaDAO.obtenerPorCodigo(datos.getCodigoCuenta());

            // Calcular costos
            BigDecimal itf = TransaccionValidator.calcularITF(datos.getImporte());
            BigDecimal cargo = TransaccionValidator.calcularCargoMovimiento(cuenta);
            BigDecimal totalDescontar = datos.getImporte().add(itf).add(cargo);

            // Validar cuenta y saldo suficiente
            CuentaValidator.validarParaRetiro(cuenta, datos.getClaveCuenta(), totalDescontar);

            // Guardar saldo anterior
            BigDecimal saldoAnterior = cuenta.getSaldo();
            BigDecimal nuevoSaldo = saldoAnterior.subtract(totalDescontar);

            // INICIAR TRANSACCIÓN SQL
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false);

            // Obtener número base de movimiento
            int numeroBase = movimientoDAO.obtenerUltimoNumero(conn, cuenta.getCodigo());

            // 1. Registrar retiro principal
            Movimiento movRetiro = new Movimiento(
                    cuenta.getCodigo(),
                    ++numeroBase,
                    LocalDate.now(),
                    datos.getCodigoEmpleado(),
                    TipoMovimientoConstants.RETIRO,
                    datos.getImporte(),
                    null
            );
            movimientoDAO.insertar(conn, movRetiro);
            cuentaDAO.incrementarContadorMovimientos(conn, cuenta.getCodigo());

            Integer numeroMovimientoITF = null;
            Integer numeroMovimientoCargo = null;

            // 2. Registrar ITF (siempre se cobra)
            Movimiento movITF = new Movimiento(
                    cuenta.getCodigo(),
                    ++numeroBase,
                    LocalDate.now(),
                    datos.getCodigoEmpleado(),
                    TipoMovimientoConstants.ITF,
                    itf,
                    null
            );
            movimientoDAO.insertar(conn, movITF);
            cuentaDAO.incrementarContadorMovimientos(conn, cuenta.getCodigo());
            numeroMovimientoITF = movITF.getNumero();

            // 3. Registrar cargo por movimiento (si aplica)
            if (cargo.compareTo(BigDecimal.ZERO) > 0) {
                Movimiento movCargo = new Movimiento(
                        cuenta.getCodigo(),
                        ++numeroBase,
                        LocalDate.now(),
                        datos.getCodigoEmpleado(),
                        TipoMovimientoConstants.CARGO_MOVIMIENTO,
                        cargo,
                        null
                );
                movimientoDAO.insertar(conn, movCargo);
                cuentaDAO.incrementarContadorMovimientos(conn, cuenta.getCodigo());
                numeroMovimientoCargo = movCargo.getNumero();
            }

            // 4. Actualizar saldo
            cuentaDAO.actualizarSaldo(conn, cuenta.getCodigo(), nuevoSaldo);

            // CONFIRMAR TRANSACCIÓN
            conn.commit();

            // Crear DTO de resultado
            RetiroResultDTO resultado = new RetiroResultDTO(
                    cuenta.getCodigo(),
                    datos.getImporte(),
                    itf,
                    cargo,
                    totalDescontar,
                    saldoAnterior,
                    nuevoSaldo,
                    movRetiro.getNumero(),
                    numeroMovimientoITF,
                    numeroMovimientoCargo
            );

            LOGGER.info("Retiro exitoso de cuenta: " + cuenta.getCodigo());
            return RespuestaDTO.exito(MensajesConstants.RETIRO_EXITOSO, resultado);

        } catch (Exception e) {
            // REVERTIR TRANSACCIÓN
            if (conn != null) {
                try {
                    conn.rollback();
                    LOGGER.warning("Transacción revertida debido a error");
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Error al revertir transacción", ex);
                }
            }
            LOGGER.log(Level.SEVERE, "Error en retiro", e);
            return RespuestaDTO.error(e.getMessage());

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Error al cerrar conexión", e);
                }
            }
        }
    }

    /**
     * Realiza una transferencia entre dos cuentas
     * USA TRANSACCIÓN SQL (afecta dos cuentas)
     */
    public RespuestaDTO realizarTransferencia(TransferenciaDTO datos) {
        Connection conn = null;
        try {
            // Validar importe
            TransaccionValidator.validarImporte(datos.getImporte());

            // Obtener cuentas
            Cuenta cuentaOrigen = cuentaDAO.obtenerPorCodigo(datos.getCuentaOrigen());
            Cuenta cuentaDestino = cuentaDAO.obtenerPorCodigo(datos.getCuentaDestino());

            // Validar transferencia
            CuentaValidator.validarParaTransferencia(
                    cuentaOrigen,
                    cuentaDestino,
                    datos.getClaveCuentaOrigen(),
                    datos.getImporte()
            );

            // Guardar saldos anteriores
            BigDecimal saldoAnteriorOrigen = cuentaOrigen.getSaldo();
            BigDecimal saldoAnteriorDestino = cuentaDestino.getSaldo();

            // Calcular nuevos saldos
            BigDecimal nuevoSaldoOrigen = saldoAnteriorOrigen.subtract(datos.getImporte());
            BigDecimal nuevoSaldoDestino = saldoAnteriorDestino.add(datos.getImporte());

            // INICIAR TRANSACCIÓN SQL
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false);

            // Obtener números de movimiento
            int numeroMovimientoOrigen = movimientoDAO.obtenerUltimoNumero(conn, cuentaOrigen.getCodigo()) + 1;
            int numeroMovimientoDestino = movimientoDAO.obtenerUltimoNumero(conn, cuentaDestino.getCodigo()) + 1;

            // 1. Registrar movimiento de salida en cuenta origen
            Movimiento movOrigen = new Movimiento(
                    cuentaOrigen.getCodigo(),
                    numeroMovimientoOrigen,
                    LocalDate.now(),
                    datos.getCodigoEmpleado(),
                    TipoMovimientoConstants.TRANSFERENCIA_SALIDA,
                    datos.getImporte(),
                    cuentaDestino.getCodigo()
            );
            movimientoDAO.insertar(conn, movOrigen);
            cuentaDAO.incrementarContadorMovimientos(conn, cuentaOrigen.getCodigo());

            // 2. Registrar movimiento de ingreso en cuenta destino
            Movimiento movDestino = new Movimiento(
                    cuentaDestino.getCodigo(),
                    numeroMovimientoDestino,
                    LocalDate.now(),
                    datos.getCodigoEmpleado(),
                    TipoMovimientoConstants.TRANSFERENCIA_INGRESO,
                    datos.getImporte(),
                    cuentaOrigen.getCodigo()
            );
            movimientoDAO.insertar(conn, movDestino);
            cuentaDAO.incrementarContadorMovimientos(conn, cuentaDestino.getCodigo());

            // 3. Actualizar saldos
            cuentaDAO.actualizarSaldo(conn, cuentaOrigen.getCodigo(), nuevoSaldoOrigen);
            cuentaDAO.actualizarSaldo(conn, cuentaDestino.getCodigo(), nuevoSaldoDestino);

            // CONFIRMAR TRANSACCIÓN
            conn.commit();

            // Crear DTO de resultado
            TransferenciaResultDTO resultado = new TransferenciaResultDTO(
                    cuentaOrigen.getCodigo(),
                    cuentaDestino.getCodigo(),
                    datos.getImporte(),
                    saldoAnteriorOrigen,
                    nuevoSaldoOrigen,
                    saldoAnteriorDestino,
                    nuevoSaldoDestino,
                    numeroMovimientoOrigen,
                    numeroMovimientoDestino
            );

            LOGGER.info("Transferencia exitosa de " + cuentaOrigen.getCodigo()
                    + " a " + cuentaDestino.getCodigo());
            return RespuestaDTO.exito(MensajesConstants.TRANSFERENCIA_EXITOSA, resultado);

        } catch (Exception e) {
            // REVERTIR TRANSACCIÓN
            if (conn != null) {
                try {
                    conn.rollback();
                    LOGGER.warning("Transacción revertida debido a error");
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Error al revertir transacción", ex);
                }
            }
            LOGGER.log(Level.SEVERE, "Error en transferencia", e);
            return RespuestaDTO.error(e.getMessage());

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Error al cerrar conexión", e);
                }
            }
        }
    }
}
