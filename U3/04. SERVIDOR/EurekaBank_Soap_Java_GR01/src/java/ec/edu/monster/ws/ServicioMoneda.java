package ec.edu.monster.ws;

import ec.edu.monster.dal.MonedaDAO;
import ec.edu.monster.models.Moneda;
import ec.edu.monster.models.dto.RespuestaDTO;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio Web SOAP para gestión de Monedas
 */
@WebService(serviceName = "ServicioMoneda")
public class ServicioMoneda {

    private static final Logger LOGGER = Logger.getLogger(ServicioMoneda.class.getName());
    private final MonedaDAO monedaDAO = new MonedaDAO();

    /**
     * Lista todas las monedas
     */
    @WebMethod(operationName = "listarMonedas")
    public RespuestaDTO listarMonedas() {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            List<Moneda> monedas = monedaDAO.obtenerTodas();
            respuesta.setExitoso(true);
            respuesta.setMensaje("Monedas obtenidas correctamente");
            respuesta.setDatos(monedas);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar monedas", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener monedas: " + e.getMessage());
            respuesta.setCodigoError("MON001");
        }
        return respuesta;
    }

    /**
     * Obtiene una moneda por su código
     */
    @WebMethod(operationName = "obtenerMoneda")
    public RespuestaDTO obtenerMoneda(@WebParam(name = "codigo") String codigo) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Moneda moneda = monedaDAO.obtenerPorCodigo(codigo);
            if (moneda != null) {
                respuesta.setExitoso(true);
                respuesta.setMensaje("Moneda encontrada");
                respuesta.setDatos(moneda);
            } else {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Moneda no encontrada");
                respuesta.setCodigoError("MON002");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener moneda", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener moneda: " + e.getMessage());
            respuesta.setCodigoError("MON003");
        }
        return respuesta;
    }

    /**
     * Registra una nueva moneda
     */
    @WebMethod(operationName = "registrarMoneda")
    public RespuestaDTO registrarMoneda(@WebParam(name = "moneda") Moneda moneda) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            if (moneda.getCodigo() == null || moneda.getDescripcion() == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Código y descripción son obligatorios");
                respuesta.setCodigoError("MON004");
                return respuesta;
            }

            Moneda existente = monedaDAO.obtenerPorCodigo(moneda.getCodigo());
            if (existente != null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Ya existe una moneda con ese código");
                respuesta.setCodigoError("MON005");
                return respuesta;
            }

            monedaDAO.insertar(moneda);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Moneda registrada correctamente");
            respuesta.setDatos(moneda);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar moneda", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al registrar moneda: " + e.getMessage());
            respuesta.setCodigoError("MON006");
        }
        return respuesta;
    }

    /**
     * Actualiza una moneda
     */
    @WebMethod(operationName = "actualizarMoneda")
    public RespuestaDTO actualizarMoneda(@WebParam(name = "moneda") Moneda moneda) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Moneda existente = monedaDAO.obtenerPorCodigo(moneda.getCodigo());
            if (existente == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Moneda no encontrada");
                respuesta.setCodigoError("MON002");
                return respuesta;
            }

            monedaDAO.actualizar(moneda);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Moneda actualizada correctamente");
            respuesta.setDatos(moneda);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar moneda", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al actualizar moneda: " + e.getMessage());
            respuesta.setCodigoError("MON007");
        }
        return respuesta;
    }

    /**
     * Elimina una moneda
     */
    @WebMethod(operationName = "eliminarMoneda")
    public RespuestaDTO eliminarMoneda(@WebParam(name = "codigo") String codigo) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Moneda existente = monedaDAO.obtenerPorCodigo(codigo);
            if (existente == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Moneda no encontrada");
                respuesta.setCodigoError("MON002");
                return respuesta;
            }

            monedaDAO.eliminar(codigo);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Moneda eliminada correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar moneda", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al eliminar moneda: " + e.getMessage());
            respuesta.setCodigoError("MON008");
        }
        return respuesta;
    }
}
