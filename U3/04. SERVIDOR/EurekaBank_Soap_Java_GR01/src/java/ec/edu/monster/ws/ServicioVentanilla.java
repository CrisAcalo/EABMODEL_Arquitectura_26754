package ec.edu.monster.ws;

import ec.edu.monster.dal.VentanillaDAO;
import ec.edu.monster.models.Ventanilla;
import ec.edu.monster.models.dto.RespuestaDTO;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio Web SOAP para gestión de Ventanillas
 */
@WebService(serviceName = "ServicioVentanilla")
public class ServicioVentanilla {

    private static final Logger LOGGER = Logger.getLogger(ServicioVentanilla.class.getName());
    private final VentanillaDAO ventanillaDAO = new VentanillaDAO();

    /**
     * Lista todas las ventanillas
     */
    @WebMethod(operationName = "listarVentanillas")
    public RespuestaDTO listarVentanillas() {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            List<Ventanilla> ventanillas = ventanillaDAO.obtenerTodas();
            respuesta.setExitoso(true);
            respuesta.setMensaje("Ventanillas obtenidas correctamente");
            respuesta.setDatos(ventanillas);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar ventanillas", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener ventanillas: " + e.getMessage());
            respuesta.setCodigoError("VEN001");
        }
        return respuesta;
    }

    /**
     * Lista ventanillas activas
     */
    @WebMethod(operationName = "listarVentanillasActivas")
    public RespuestaDTO listarVentanillasActivas() {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            List<Ventanilla> ventanillas = ventanillaDAO.obtenerActivas();
            respuesta.setExitoso(true);
            respuesta.setMensaje("Ventanillas activas obtenidas correctamente");
            respuesta.setDatos(ventanillas);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar ventanillas activas", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener ventanillas: " + e.getMessage());
            respuesta.setCodigoError("VEN001");
        }
        return respuesta;
    }

    /**
     * Obtiene una ventanilla por su código
     */
    @WebMethod(operationName = "obtenerVentanilla")
    public RespuestaDTO obtenerVentanilla(@WebParam(name = "codigo") String codigo) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Ventanilla ventanilla = ventanillaDAO.obtenerPorCodigo(codigo);
            if (ventanilla != null) {
                respuesta.setExitoso(true);
                respuesta.setMensaje("Ventanilla encontrada");
                respuesta.setDatos(ventanilla);
            } else {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Ventanilla no encontrada");
                respuesta.setCodigoError("VEN002");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener ventanilla", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener ventanilla: " + e.getMessage());
            respuesta.setCodigoError("VEN003");
        }
        return respuesta;
    }

    /**
     * Registra una nueva ventanilla
     */
    @WebMethod(operationName = "registrarVentanilla")
    public RespuestaDTO registrarVentanilla(@WebParam(name = "ventanilla") Ventanilla ventanilla) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            if (ventanilla.getCodigo() == null || ventanilla.getNombre() == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Código y nombre son obligatorios");
                respuesta.setCodigoError("VEN004");
                return respuesta;
            }

            // Verificar si ya existe
            Ventanilla existente = ventanillaDAO.obtenerPorCodigo(ventanilla.getCodigo());
            if (existente != null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Ya existe una ventanilla con ese código");
                respuesta.setCodigoError("VEN005");
                return respuesta;
            }

            ventanillaDAO.insertar(ventanilla);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Ventanilla registrada correctamente");
            respuesta.setDatos(ventanilla);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar ventanilla", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al registrar ventanilla: " + e.getMessage());
            respuesta.setCodigoError("VEN006");
        }
        return respuesta;
    }

    /**
     * Actualiza una ventanilla
     */
    @WebMethod(operationName = "actualizarVentanilla")
    public RespuestaDTO actualizarVentanilla(@WebParam(name = "ventanilla") Ventanilla ventanilla) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Ventanilla existente = ventanillaDAO.obtenerPorCodigo(ventanilla.getCodigo());
            if (existente == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Ventanilla no encontrada");
                respuesta.setCodigoError("VEN002");
                return respuesta;
            }

            ventanillaDAO.actualizar(ventanilla);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Ventanilla actualizada correctamente");
            respuesta.setDatos(ventanilla);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar ventanilla", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al actualizar ventanilla: " + e.getMessage());
            respuesta.setCodigoError("VEN007");
        }
        return respuesta;
    }

    /**
     * Asigna un empleado a una ventanilla
     */
    @WebMethod(operationName = "asignarEmpleado")
    public RespuestaDTO asignarEmpleado(
            @WebParam(name = "codigoVentanilla") String codigoVentanilla,
            @WebParam(name = "codigoEmpleado") String codigoEmpleado) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Ventanilla ventanilla = ventanillaDAO.obtenerPorCodigo(codigoVentanilla);
            if (ventanilla == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Ventanilla no encontrada");
                respuesta.setCodigoError("VEN002");
                return respuesta;
            }

            ventanillaDAO.asignarEmpleado(codigoVentanilla, codigoEmpleado);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Empleado asignado correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al asignar empleado", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al asignar empleado: " + e.getMessage());
            respuesta.setCodigoError("VEN008");
        }
        return respuesta;
    }

    /**
     * Elimina una ventanilla
     */
    @WebMethod(operationName = "eliminarVentanilla")
    public RespuestaDTO eliminarVentanilla(@WebParam(name = "codigo") String codigo) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Ventanilla existente = ventanillaDAO.obtenerPorCodigo(codigo);
            if (existente == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Ventanilla no encontrada");
                respuesta.setCodigoError("VEN002");
                return respuesta;
            }

            ventanillaDAO.eliminar(codigo);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Ventanilla eliminada correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar ventanilla", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al eliminar ventanilla: " + e.getMessage());
            respuesta.setCodigoError("VEN009");
        }
        return respuesta;
    }
}
