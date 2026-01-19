package ec.edu.monster.ws;

import ec.edu.monster.dal.CuentaDAO;
import ec.edu.monster.dal.SucursalDAO;
import ec.edu.monster.models.Cuenta;
import ec.edu.monster.models.dto.RespuestaDTO;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio Web SOAP para gestión de Cuentas Bancarias
 */
@WebService(serviceName = "ServicioCuenta")
public class ServicioCuenta {

    private static final Logger LOGGER = Logger.getLogger(ServicioCuenta.class.getName());
    private final CuentaDAO cuentaDAO = new CuentaDAO();
    private final SucursalDAO sucursalDAO = new SucursalDAO();

    /**
     * Lista todas las cuentas activas
     */
    @WebMethod(operationName = "listarCuentasActivas")
    public RespuestaDTO listarCuentasActivas() {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            List<Cuenta> cuentas = cuentaDAO.listarCuentasActivas();
            respuesta.setExitoso(true);
            respuesta.setMensaje("Cuentas obtenidas correctamente");
            respuesta.setDatos(cuentas);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar cuentas", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener cuentas: " + e.getMessage());
            respuesta.setCodigoError("CUE001");
        }
        return respuesta;
    }

    /**
     * Obtiene una cuenta por su código
     */
    @WebMethod(operationName = "obtenerCuenta")
    public RespuestaDTO obtenerCuenta(@WebParam(name = "codigo") String codigo) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Cuenta cuenta = cuentaDAO.obtenerPorCodigo(codigo);
            if (cuenta != null) {
                respuesta.setExitoso(true);
                respuesta.setMensaje("Cuenta encontrada");
                respuesta.setDatos(cuenta);
            } else {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Cuenta no encontrada");
                respuesta.setCodigoError("CUE002");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener cuenta", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener cuenta: " + e.getMessage());
            respuesta.setCodigoError("CUE003");
        }
        return respuesta;
    }

    /**
     * Obtiene el saldo de una cuenta
     */
    @WebMethod(operationName = "obtenerSaldo")
    public RespuestaDTO obtenerSaldo(@WebParam(name = "codigoCuenta") String codigoCuenta) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            BigDecimal saldo = cuentaDAO.obtenerSaldo(codigoCuenta);
            if (saldo != null) {
                respuesta.setExitoso(true);
                respuesta.setMensaje("Saldo obtenido correctamente");
                respuesta.setDatos(saldo);
            } else {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Cuenta no encontrada");
                respuesta.setCodigoError("CUE002");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener saldo", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener saldo: " + e.getMessage());
            respuesta.setCodigoError("CUE004");
        }
        return respuesta;
    }

    /**
     * Abre una nueva cuenta bancaria
     */
    @WebMethod(operationName = "abrirCuenta")
    public RespuestaDTO abrirCuenta(@WebParam(name = "cuenta") Cuenta cuenta) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            // Validar datos básicos
            if (cuenta.getCodigoCliente() == null || cuenta.getCodigoSucursal() == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Cliente y Sucursal son obligatorios");
                respuesta.setCodigoError("CUE005");
                return respuesta;
            }

            // Generar código de cuenta
            String codigoCuenta = cuentaDAO.generarCodigoCuenta(cuenta.getCodigoSucursal());
            cuenta.setCodigo(codigoCuenta);

            // Establecer valores por defecto
            if (cuenta.getSaldo() == null) {
                cuenta.setSaldo(BigDecimal.ZERO);
            }
            if (cuenta.getFechaCreacion() == null) {
                cuenta.setFechaCreacion(LocalDate.now());
            }
            if (cuenta.getEstado() == null) {
                cuenta.setEstado("ACTIVO");
            }
            if (cuenta.getContadorMovimientos() == 0) {
                cuenta.setContadorMovimientos(0);
            }

            cuentaDAO.insertar(cuenta);
            sucursalDAO.incrementarContadorCuentas(cuenta.getCodigoSucursal());

            respuesta.setExitoso(true);
            respuesta.setMensaje("Cuenta abierta correctamente");
            respuesta.setDatos(cuenta);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al abrir cuenta", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al abrir cuenta: " + e.getMessage());
            respuesta.setCodigoError("CUE006");
        }
        return respuesta;
    }

    /**
     * Cancela una cuenta (cambio de estado)
     */
    @WebMethod(operationName = "cancelarCuenta")
    public RespuestaDTO cancelarCuenta(@WebParam(name = "codigoCuenta") String codigoCuenta) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Cuenta cuenta = cuentaDAO.obtenerPorCodigo(codigoCuenta);
            if (cuenta == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Cuenta no encontrada");
                respuesta.setCodigoError("CUE002");
                return respuesta;
            }

            // Verificar que tenga saldo cero
            if (cuenta.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("La cuenta debe tener saldo cero para cancelarse");
                respuesta.setCodigoError("CUE007");
                return respuesta;
            }

            cuentaDAO.cancelar(codigoCuenta);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Cuenta cancelada correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cancelar cuenta", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al cancelar cuenta: " + e.getMessage());
            respuesta.setCodigoError("CUE008");
        }
        return respuesta;
    }

    /**
     * Valida la clave de una cuenta
     */
    @WebMethod(operationName = "validarClave")
    public RespuestaDTO validarClave(
            @WebParam(name = "codigoCuenta") String codigoCuenta,
            @WebParam(name = "clave") String clave) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            boolean valida = cuentaDAO.validarClave(codigoCuenta, clave);
            respuesta.setExitoso(valida);
            respuesta.setMensaje(valida ? "Clave válida" : "Clave incorrecta");
            if (!valida) {
                respuesta.setCodigoError("CUE009");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al validar clave", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al validar clave: " + e.getMessage());
            respuesta.setCodigoError("CUE010");
        }
        return respuesta;
    }
}
