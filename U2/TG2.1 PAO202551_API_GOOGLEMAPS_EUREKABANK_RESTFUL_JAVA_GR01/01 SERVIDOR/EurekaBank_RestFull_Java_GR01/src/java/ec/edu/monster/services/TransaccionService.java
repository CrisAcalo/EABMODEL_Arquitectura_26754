package ec.edu.monster.services;

import ec.edu.monster.constants.EstadosConstants;
import ec.edu.monster.constants.MensajesConstants;
import ec.edu.monster.constants.TipoMovimientoConstants;
import ec.edu.monster.dal.CuentaDAO;
import ec.edu.monster.dal.MovimientoDAO;
import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.dtos.*;
import ec.edu.monster.models.Cuenta;
import ec.edu.monster.models.Movimiento;
import ec.edu.monster.validators.CuentaValidator;
import ec.edu.monster.validators.TransaccionValidator;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * Servicio de lógica de negocio para transacciones bancarias
 *
 * @author EurekaBank
 */
public class TransaccionService {

    private final CuentaDAO cuentaDAO;
    private final MovimientoDAO movimientoDAO;

    public TransaccionService() {
        this.cuentaDAO = new CuentaDAO();
        this.movimientoDAO = new MovimientoDAO();
    }

    /**
     * Realiza un depósito en una cuenta
     *
     * @param datos Datos de la transacción
     * @return RespuestaDTO con el resultado
     */
    public RespuestaDTO realizarDeposito(TransaccionDTO datos) {
        Connection conn = null;
        try {
            // 1. Validar importe
            if (!TransaccionValidator.importeValido(datos.getImporte())) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_IMPORTE_INVALIDO);
                respuesta.setCodigoError("VAL005");
                return respuesta;
            }

            // 2. Obtener cuenta
            Cuenta cuenta = cuentaDAO.obtenerPorCodigo(datos.getCodigoCuenta());
            if (!CuentaValidator.existe(cuenta)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_CUENTA_NO_EXISTE);
                respuesta.setCodigoError("CTA001");
                return respuesta;
            }

            // 3. Validar que la cuenta esté activa
            if (!CuentaValidator.esActiva(cuenta)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_CUENTA_INACTIVA);
                respuesta.setCodigoError("CTA002");
                return respuesta;
            }

            // Iniciar transacción
            conn = ConexionDB.obtenerConexion();
            conn.setAutoCommit(false);

            // 4. Generar número de movimiento
            int numeroMovimiento = movimientoDAO.obtenerUltimoNumero(conn, datos.getCodigoCuenta()) + 1;

            // 5. Crear movimiento de depósito
            Movimiento movimiento = new Movimiento();
            movimiento.setCodigoCuenta(datos.getCodigoCuenta());
            movimiento.setNumero(numeroMovimiento);
            movimiento.setFecha(new Date());
            movimiento.setCodigoEmpleado(datos.getCodigoEmpleado());
            movimiento.setCodigoTipo(TipoMovimientoConstants.DEPOSITO);
            movimiento.setImporte(datos.getImporte());
            movimiento.setCuentaReferencia(null);

            // 6. Insertar movimiento
            boolean movimientoInsertado = movimientoDAO.insertar(conn, movimiento);
            if (!movimientoInsertado) {
                conn.rollback();
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_OPERACION_FALLIDA);
                respuesta.setCodigoError("DB002");
                return respuesta;
            }

            // 7. Actualizar saldo
            BigDecimal nuevoSaldo = cuenta.getSaldo().add(datos.getImporte());
            cuentaDAO.actualizarSaldo(conn, datos.getCodigoCuenta(), nuevoSaldo);

            // 8. Incrementar contador de movimientos
            cuentaDAO.incrementarContadorMovimientos(conn, datos.getCodigoCuenta());

            // Confirmar transacción
            conn.commit();

            // Crear resultado
            DepositoResultDTO resultado = new DepositoResultDTO();
            resultado.setNumeroMovimiento(numeroMovimiento);
            resultado.setSaldoAnterior(cuenta.getSaldo());
            resultado.setSaldoNuevo(nuevoSaldo);
            resultado.setImporte(datos.getImporte());

            RespuestaDTO respuesta = new RespuestaDTO();
            respuesta.setExitoso(true);
            respuesta.setMensaje(MensajesConstants.DEPOSITO_EXITOSO);
            respuesta.setDatos(resultado);
            return respuesta;

        } catch (SQLException ex) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            RespuestaDTO respuesta = new RespuestaDTO();
            respuesta.setExitoso(false);
            respuesta.setMensaje(MensajesConstants.ERROR_BASE_DATOS + ": " + ex.getMessage());
            respuesta.setCodigoError("DB001");
            return respuesta;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Realiza un retiro de una cuenta
     *
     * @param datos Datos de la transacción
     * @return RespuestaDTO con el resultado
     */
    public RespuestaDTO realizarRetiro(TransaccionDTO datos) {
        Connection conn = null;

        try {
            // Validaciones
            if (!TransaccionValidator.importeValido(datos.getImporte())) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_IMPORTE_INVALIDO);
                respuesta.setCodigoError("VAL005");
                return respuesta;
            }

            Cuenta cuenta = cuentaDAO.obtenerPorCodigo(datos.getCodigoCuenta());
            if (!CuentaValidator.existe(cuenta)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_CUENTA_NO_EXISTE);
                respuesta.setCodigoError("CTA001");
                return respuesta;
            }

            if (!CuentaValidator.esActiva(cuenta)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_CUENTA_INACTIVA);
                respuesta.setCodigoError("CTA002");
                return respuesta;
            }

            if (!CuentaValidator.claveCorrecta(cuenta, datos.getClaveCuenta())) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_CLAVE_INCORRECTA);
                respuesta.setCodigoError("CTA003");
                return respuesta;
            }

            BigDecimal itf = TransaccionValidator.calcularITF(datos.getImporte());
            BigDecimal costoPorMovimiento = TransaccionValidator.debeAplicarCostoPorMovimiento(cuenta.getContadorMovimientos())
                    ? TransaccionValidator.obtenerCostoPorMovimiento(cuenta.getCodigoMoneda())
                    : BigDecimal.ZERO;

            BigDecimal totalADescontar = datos.getImporte().add(itf).add(costoPorMovimiento);

            if (!CuentaValidator.tieneSaldoSuficiente(cuenta.getSaldo(), totalADescontar)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(String.format("%s. Requiere: %.2f, Disponible: %.2f",
                        MensajesConstants.ERROR_SALDO_INSUFICIENTE, totalADescontar, cuenta.getSaldo()));
                respuesta.setCodigoError("CTA004");
                return respuesta;
            }

            // Usar transacción SQL
            conn = ConexionDB.obtenerConexion();
            conn.setAutoCommit(false);

            try {
                int numeroMovimiento = movimientoDAO.obtenerUltimoNumero(conn, datos.getCodigoCuenta());

                // Movimiento de retiro
                numeroMovimiento++;
                Movimiento movRetiro = new Movimiento();
                movRetiro.setCodigoCuenta(datos.getCodigoCuenta());
                movRetiro.setNumero(numeroMovimiento);
                movRetiro.setFecha(new Date());
                movRetiro.setCodigoEmpleado(datos.getCodigoEmpleado());
                movRetiro.setCodigoTipo(TipoMovimientoConstants.RETIRO);
                movRetiro.setImporte(datos.getImporte());
                movRetiro.setCuentaReferencia(null);
                movimientoDAO.insertar(conn, movRetiro);

                // Movimiento de ITF
                numeroMovimiento++;
                Movimiento movITF = new Movimiento();
                movITF.setCodigoCuenta(datos.getCodigoCuenta());
                movITF.setNumero(numeroMovimiento);
                movITF.setFecha(new Date());
                movITF.setCodigoEmpleado(datos.getCodigoEmpleado());
                movITF.setCodigoTipo(TipoMovimientoConstants.ITF);
                movITF.setImporte(itf);
                movITF.setCuentaReferencia(null);
                movimientoDAO.insertar(conn, movITF);

                // Movimiento de cargo si aplica
                if (costoPorMovimiento.compareTo(BigDecimal.ZERO) > 0) {
                    numeroMovimiento++;
                    Movimiento movCargo = new Movimiento();
                    movCargo.setCodigoCuenta(datos.getCodigoCuenta());
                    movCargo.setNumero(numeroMovimiento);
                    movCargo.setFecha(new Date());
                    movCargo.setCodigoEmpleado(datos.getCodigoEmpleado());
                    movCargo.setCodigoTipo(TipoMovimientoConstants.CARGO_MOVIMIENTO);
                    movCargo.setImporte(costoPorMovimiento);
                    movCargo.setCuentaReferencia(null);
                    movimientoDAO.insertar(conn, movCargo);
                }

                // Actualizar saldo
                BigDecimal nuevoSaldo = cuenta.getSaldo().subtract(totalADescontar);
                cuentaDAO.actualizarSaldo(conn, datos.getCodigoCuenta(), nuevoSaldo);

                // Incrementar contador
                int movimientosRegistrados = costoPorMovimiento.compareTo(BigDecimal.ZERO) > 0 ? 3 : 2;
                for (int i = 0; i < movimientosRegistrados; i++) {
                    cuentaDAO.incrementarContadorMovimientos(conn, datos.getCodigoCuenta());
                }

                conn.commit();

                // Crear resultado
                RetiroResultDTO resultado = new RetiroResultDTO();
                resultado.setSaldoAnterior(cuenta.getSaldo());
                resultado.setSaldoNuevo(nuevoSaldo);
                resultado.setImporteRetiro(datos.getImporte());
                resultado.setItf(itf);
                resultado.setCostoPorMovimiento(costoPorMovimiento);
                resultado.setTotalDescontado(totalADescontar);

                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(true);
                respuesta.setMensaje(MensajesConstants.RETIRO_EXITOSO);
                respuesta.setDatos(resultado);
                return respuesta;

            } catch (SQLException ex) {
                if (conn != null) {
                    conn.rollback();
                }
                throw ex;
            }

        } catch (SQLException ex) {
            RespuestaDTO respuesta = new RespuestaDTO();
            respuesta.setExitoso(false);
            respuesta.setMensaje(MensajesConstants.ERROR_BASE_DATOS + ": " + ex.getMessage());
            respuesta.setCodigoError("DB001");
            return respuesta;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    // Log error
                }
            }
        }
    }

    /**
     * Realiza una transferencia entre dos cuentas
     *
     * @param datos Datos de la transferencia
     * @return RespuestaDTO con el resultado
     */
    public RespuestaDTO realizarTransferencia(TransferenciaDTO datos) {
        Connection conn = null;

        try {
            // Validaciones
            if (!TransaccionValidator.importeValido(datos.getImporte())) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_IMPORTE_INVALIDO);
                respuesta.setCodigoError("VAL005");
                return respuesta;
            }

            Cuenta cuentaOrigen = cuentaDAO.obtenerPorCodigo(datos.getCuentaOrigen());
            Cuenta cuentaDestino = cuentaDAO.obtenerPorCodigo(datos.getCuentaDestino());

            if (!CuentaValidator.existe(cuentaOrigen)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje("Cuenta origen no existe");
                respuesta.setCodigoError("CTA001");
                return respuesta;
            }

            if (!CuentaValidator.existe(cuentaDestino)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje("Cuenta destino no existe");
                respuesta.setCodigoError("CTA001");
                return respuesta;
            }

            if (!CuentaValidator.esActiva(cuentaOrigen)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje("Cuenta origen no está activa");
                respuesta.setCodigoError("CTA002");
                return respuesta;
            }

            if (!CuentaValidator.esActiva(cuentaDestino)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje("Cuenta destino no está activa");
                respuesta.setCodigoError("CTA002");
                return respuesta;
            }

            if (!CuentaValidator.claveCorrecta(cuentaOrigen, datos.getClaveCuentaOrigen())) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_CLAVE_INCORRECTA);
                respuesta.setCodigoError("CTA003");
                return respuesta;
            }

            if (!CuentaValidator.mismaMoneda(cuentaOrigen, cuentaDestino)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_MONEDA_DIFERENTE);
                respuesta.setCodigoError("CTA005");
                return respuesta;
            }

            BigDecimal itf = TransaccionValidator.calcularITF(datos.getImporte());
            BigDecimal costoPorMovimiento = TransaccionValidator.debeAplicarCostoPorMovimiento(cuentaOrigen.getContadorMovimientos())
                    ? TransaccionValidator.obtenerCostoPorMovimiento(cuentaOrigen.getCodigoMoneda())
                    : BigDecimal.ZERO;

            BigDecimal totalADescontar = datos.getImporte().add(itf).add(costoPorMovimiento);

            if (!CuentaValidator.tieneSaldoSuficiente(cuentaOrigen.getSaldo(), totalADescontar)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(String.format("%s. Requiere: %.2f, Disponible: %.2f",
                        MensajesConstants.ERROR_SALDO_INSUFICIENTE, totalADescontar, cuentaOrigen.getSaldo()));
                respuesta.setCodigoError("CTA004");
                return respuesta;
            }

            // Transacción SQL
            conn = ConexionDB.obtenerConexion();
            conn.setAutoCommit(false);

            try {
                // CUENTA ORIGEN
                int numMovOrigen = movimientoDAO.obtenerUltimoNumero(conn, datos.getCuentaOrigen());

                numMovOrigen++;
                Movimiento movSalida = new Movimiento();
                movSalida.setCodigoCuenta(datos.getCuentaOrigen());
                movSalida.setNumero(numMovOrigen);
                movSalida.setFecha(new Date());
                movSalida.setCodigoEmpleado(datos.getCodigoEmpleado());
                movSalida.setCodigoTipo(TipoMovimientoConstants.TRANSFERENCIA_SALIDA);
                movSalida.setImporte(datos.getImporte());
                movSalida.setCuentaReferencia(datos.getCuentaDestino());
                movimientoDAO.insertar(conn, movSalida);

                numMovOrigen++;
                Movimiento movITF = new Movimiento();
                movITF.setCodigoCuenta(datos.getCuentaOrigen());
                movITF.setNumero(numMovOrigen);
                movITF.setFecha(new Date());
                movITF.setCodigoEmpleado(datos.getCodigoEmpleado());
                movITF.setCodigoTipo(TipoMovimientoConstants.ITF);
                movITF.setImporte(itf);
                movITF.setCuentaReferencia(null);
                movimientoDAO.insertar(conn, movITF);

                if (costoPorMovimiento.compareTo(BigDecimal.ZERO) > 0) {
                    numMovOrigen++;
                    Movimiento movCargo = new Movimiento();
                    movCargo.setCodigoCuenta(datos.getCuentaOrigen());
                    movCargo.setNumero(numMovOrigen);
                    movCargo.setFecha(new Date());
                    movCargo.setCodigoEmpleado(datos.getCodigoEmpleado());
                    movCargo.setCodigoTipo(TipoMovimientoConstants.CARGO_MOVIMIENTO);
                    movCargo.setImporte(costoPorMovimiento);
                    movCargo.setCuentaReferencia(null);
                    movimientoDAO.insertar(conn, movCargo);
                }

                BigDecimal nuevoSaldoOrigen = cuentaOrigen.getSaldo().subtract(totalADescontar);
                cuentaDAO.actualizarSaldo(conn, datos.getCuentaOrigen(), nuevoSaldoOrigen);

                int movimientosOrigen = costoPorMovimiento.compareTo(BigDecimal.ZERO) > 0 ? 3 : 2;
                for (int i = 0; i < movimientosOrigen; i++) {
                    cuentaDAO.incrementarContadorMovimientos(conn, datos.getCuentaOrigen());
                }

                // CUENTA DESTINO
                int numMovDestino = movimientoDAO.obtenerUltimoNumero(conn, datos.getCuentaDestino());

                numMovDestino++;
                Movimiento movIngreso = new Movimiento();
                movIngreso.setCodigoCuenta(datos.getCuentaDestino());
                movIngreso.setNumero(numMovDestino);
                movIngreso.setFecha(new Date());
                movIngreso.setCodigoEmpleado(datos.getCodigoEmpleado());
                movIngreso.setCodigoTipo(TipoMovimientoConstants.TRANSFERENCIA_INGRESO);
                movIngreso.setImporte(datos.getImporte());
                movIngreso.setCuentaReferencia(datos.getCuentaOrigen());
                movimientoDAO.insertar(conn, movIngreso);

                BigDecimal nuevoSaldoDestino = cuentaDestino.getSaldo().add(datos.getImporte());
                cuentaDAO.actualizarSaldo(conn, datos.getCuentaDestino(), nuevoSaldoDestino);
                cuentaDAO.incrementarContadorMovimientos(conn, datos.getCuentaDestino());

                conn.commit();

                // Crear resultado
                CuentaResumenDTO resumenOrigen = new CuentaResumenDTO();
                resumenOrigen.setCodigo(datos.getCuentaOrigen());
                resumenOrigen.setSaldoAnterior(cuentaOrigen.getSaldo());
                resumenOrigen.setSaldoNuevo(nuevoSaldoOrigen);

                CuentaResumenDTO resumenDestino = new CuentaResumenDTO();
                resumenDestino.setCodigo(datos.getCuentaDestino());
                resumenDestino.setSaldoAnterior(cuentaDestino.getSaldo());
                resumenDestino.setSaldoNuevo(nuevoSaldoDestino);

                TransferenciaResultDTO resultado = new TransferenciaResultDTO();
                resultado.setCuentaOrigen(resumenOrigen);
                resultado.setCuentaDestino(resumenDestino);
                resultado.setImporteTransferido(datos.getImporte());
                resultado.setItf(itf);
                resultado.setCostoPorMovimiento(costoPorMovimiento);
                resultado.setTotalDescontado(totalADescontar);

                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(true);
                respuesta.setMensaje(MensajesConstants.TRANSFERENCIA_EXITOSA);
                respuesta.setDatos(resultado);
                return respuesta;

            } catch (SQLException ex) {
                if (conn != null) {
                    conn.rollback();
                }
                throw ex;
            }

        } catch (SQLException ex) {
            RespuestaDTO respuesta = new RespuestaDTO();
            respuesta.setExitoso(false);
            respuesta.setMensaje(MensajesConstants.ERROR_BASE_DATOS + ": " + ex.getMessage());
            respuesta.setCodigoError("DB001");
            return respuesta;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    // Log error
                }
            }
        }
    }
}
