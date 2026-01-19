package ec.edu.monster.ws;

import ec.edu.monster.dal.BloqueoDAO;
import ec.edu.monster.dal.CuentaDAO;
import ec.edu.monster.dal.VentanillaDAO;
import ec.edu.monster.models.Bloqueo;
import ec.edu.monster.models.Cuenta;
import ec.edu.monster.models.Ventanilla;
import ec.edu.monster.models.dto.RespuestaDTO;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio Web SOAP para gestión de bloqueos de cuentas
 * Implementa el sistema de concurrencia para ventanillas
 */
@WebService(serviceName = "ServicioBloqueo")
public class ServicioBloqueo {

    private static final Logger LOGGER = Logger.getLogger(ServicioBloqueo.class.getName());
    private final BloqueoDAO bloqueoDAO = new BloqueoDAO();
    private final CuentaDAO cuentaDAO = new CuentaDAO();
    private final VentanillaDAO ventanillaDAO = new VentanillaDAO();

    /**
     * Bloquea una cuenta para una ventanilla
     */
    @WebMethod(operationName = "bloquearCuenta")
    public RespuestaDTO bloquearCuenta(
            @WebParam(name = "codigoCuenta") String codigoCuenta,
            @WebParam(name = "codigoVentanilla") String codigoVentanilla) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            // Validar que la cuenta existe
            Cuenta cuenta = cuentaDAO.obtenerPorCodigo(codigoCuenta);
            if (cuenta == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Cuenta no encontrada");
                respuesta.setCodigoError("BLQ001");
                return respuesta;
            }

            // Validar que la ventanilla existe
            Ventanilla ventanilla = ventanillaDAO.obtenerPorCodigo(codigoVentanilla);
            if (ventanilla == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Ventanilla no encontrada");
                respuesta.setCodigoError("BLQ002");
                return respuesta;
            }

            // Intentar bloquear
            boolean bloqueado = bloqueoDAO.bloquearCuenta(codigoCuenta, codigoVentanilla);

            if (bloqueado) {
                respuesta.setExitoso(true);
                respuesta.setMensaje("Cuenta bloqueada exitosamente");
                respuesta.setDatos(bloqueoDAO.obtenerBloqueo(codigoCuenta));
            } else {
                // La cuenta ya está bloqueada, obtener info del bloqueo
                Bloqueo bloqueoExistente = bloqueoDAO.obtenerBloqueo(codigoCuenta);
                respuesta.setExitoso(false);
                respuesta.setMensaje("La cuenta está siendo usada por otra ventanilla");
                respuesta.setCodigoError("BLQ003");
                respuesta.setDatos(bloqueoExistente);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al bloquear cuenta", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al bloquear cuenta: " + e.getMessage());
            respuesta.setCodigoError("BLQ004");
        }
        return respuesta;
    }

    /**
     * Libera el bloqueo de una cuenta
     */
    @WebMethod(operationName = "liberarCuenta")
    public RespuestaDTO liberarCuenta(
            @WebParam(name = "codigoCuenta") String codigoCuenta,
            @WebParam(name = "codigoVentanilla") String codigoVentanilla) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            boolean liberada = bloqueoDAO.liberarCuenta(codigoCuenta, codigoVentanilla);

            if (liberada) {
                respuesta.setExitoso(true);
                respuesta.setMensaje("Cuenta liberada exitosamente");
            } else {
                respuesta.setExitoso(false);
                respuesta.setMensaje("No se encontró bloqueo activo para liberar");
                respuesta.setCodigoError("BLQ005");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al liberar cuenta", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al liberar cuenta: " + e.getMessage());
            respuesta.setCodigoError("BLQ006");
        }
        return respuesta;
    }

    /**
     * Verifica si una cuenta está bloqueada
     */
    @WebMethod(operationName = "verificarBloqueo")
    public RespuestaDTO verificarBloqueo(@WebParam(name = "codigoCuenta") String codigoCuenta) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            boolean bloqueada = bloqueoDAO.estaBloqueada(codigoCuenta);
            respuesta.setExitoso(true);
            respuesta.setMensaje(bloqueada ? "Cuenta bloqueada" : "Cuenta disponible");

            if (bloqueada) {
                respuesta.setDatos(bloqueoDAO.obtenerBloqueo(codigoCuenta));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al verificar bloqueo", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al verificar bloqueo: " + e.getMessage());
            respuesta.setCodigoError("BLQ007");
        }
        return respuesta;
    }

    /**
     * Obtiene todos los bloqueos activos
     */
    @WebMethod(operationName = "listarBloqueosActivos")
    public RespuestaDTO listarBloqueosActivos() {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            List<Bloqueo> bloqueos = bloqueoDAO.obtenerBloqueosActivos();
            respuesta.setExitoso(true);
            respuesta.setMensaje("Bloqueos obtenidos correctamente");
            respuesta.setDatos(bloqueos);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar bloqueos", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener bloqueos: " + e.getMessage());
            respuesta.setCodigoError("BLQ008");
        }
        return respuesta;
    }

    /**
     * Extiende el tiempo de un bloqueo (heartbeat)
     */
    @WebMethod(operationName = "extenderBloqueo")
    public RespuestaDTO extenderBloqueo(
            @WebParam(name = "codigoCuenta") String codigoCuenta,
            @WebParam(name = "codigoVentanilla") String codigoVentanilla) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            boolean extendido = bloqueoDAO.extenderBloqueo(codigoCuenta, codigoVentanilla);

            if (extendido) {
                respuesta.setExitoso(true);
                respuesta.setMensaje("Bloqueo extendido exitosamente");
                respuesta.setDatos(bloqueoDAO.obtenerBloqueo(codigoCuenta));
            } else {
                respuesta.setExitoso(false);
                respuesta.setMensaje("No se encontró bloqueo para extender");
                respuesta.setCodigoError("BLQ009");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al extender bloqueo", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al extender bloqueo: " + e.getMessage());
            respuesta.setCodigoError("BLQ010");
        }
        return respuesta;
    }

    /**
     * Libera todos los bloqueos de una ventanilla
     */
    @WebMethod(operationName = "liberarTodosDeVentanilla")
    public RespuestaDTO liberarTodosDeVentanilla(@WebParam(name = "codigoVentanilla") String codigoVentanilla) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            bloqueoDAO.liberarTodosDeVentanilla(codigoVentanilla);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Todos los bloqueos de la ventanilla han sido liberados");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al liberar bloqueos de ventanilla", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al liberar bloqueos: " + e.getMessage());
            respuesta.setCodigoError("BLQ011");
        }
        return respuesta;
    }
}
