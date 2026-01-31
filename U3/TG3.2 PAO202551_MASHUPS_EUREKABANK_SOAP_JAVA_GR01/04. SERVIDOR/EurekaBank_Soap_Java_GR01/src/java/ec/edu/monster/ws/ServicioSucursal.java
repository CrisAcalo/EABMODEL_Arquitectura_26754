package ec.edu.monster.ws;

import ec.edu.monster.dal.SucursalDAO;
import ec.edu.monster.models.Sucursal;
import ec.edu.monster.models.dto.RespuestaDTO;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio Web SOAP para gestión de Sucursales
 */
@WebService(serviceName = "ServicioSucursal")
public class ServicioSucursal {

    private static final Logger LOGGER = Logger.getLogger(ServicioSucursal.class.getName());
    private final SucursalDAO sucursalDAO = new SucursalDAO();

    @WebMethod(operationName = "listarSucursales")
    public List<Sucursal> listarSucursales() {
        try {
            return sucursalDAO.obtenerTodas();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar sucursales", e);
            return new java.util.ArrayList<>();
        }
    }

    /**
     * Obtiene una sucursal por su código
     */
    @WebMethod(operationName = "obtenerSucursal")
    public RespuestaDTO obtenerSucursal(@WebParam(name = "codigo") String codigo) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Sucursal sucursal = sucursalDAO.obtenerPorCodigo(codigo);
            if (sucursal != null) {
                respuesta.setExitoso(true);
                respuesta.setMensaje("Sucursal encontrada");
                respuesta.setDatos(sucursal);
            } else {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Sucursal no encontrada");
                respuesta.setCodigoError("SUC002");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener sucursal", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener sucursal: " + e.getMessage());
            respuesta.setCodigoError("SUC003");
        }
        return respuesta;
    }

    /**
     * Registra una nueva sucursal
     */
    @WebMethod(operationName = "registrarSucursal")
    public RespuestaDTO registrarSucursal(@WebParam(name = "sucursal") Sucursal sucursal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            if (sucursal.getNombre() == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("El nombre es obligatorio");
                respuesta.setCodigoError("SUC004");
                return respuesta;
            }

            if (sucursal.getCodigo() == null || sucursal.getCodigo().isEmpty()) {
                sucursal.setCodigo(sucursalDAO.generarCodigoSucursal());
            }

            Sucursal existente = sucursalDAO.obtenerPorCodigo(sucursal.getCodigo());
            if (existente != null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Ya existe una sucursal con ese código: " + sucursal.getCodigo());
                respuesta.setCodigoError("SUC005");
                return respuesta;
            }

            if (sucursal.getContadorCuenta() == 0) {
                sucursal.setContadorCuenta(0);
            }

            sucursalDAO.insertar(sucursal);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Sucursal registrada correctamente");
            respuesta.setDatos(sucursal);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar sucursal", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al registrar sucursal: " + e.getMessage());
            respuesta.setCodigoError("SUC006");
        }
        return respuesta;
    }

    /**
     * Actualiza una sucursal
     */
    @WebMethod(operationName = "actualizarSucursal")
    public RespuestaDTO actualizarSucursal(@WebParam(name = "sucursal") Sucursal sucursal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Sucursal existente = sucursalDAO.obtenerPorCodigo(sucursal.getCodigo());
            if (existente == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Sucursal no encontrada");
                respuesta.setCodigoError("SUC002");
                return respuesta;
            }

            sucursalDAO.actualizar(sucursal);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Sucursal actualizada correctamente");
            respuesta.setDatos(sucursal);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar sucursal", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al actualizar sucursal: " + e.getMessage());
            respuesta.setCodigoError("SUC007");
        }
        return respuesta;
    }

    /**
     * Elimina una sucursal
     */
    @WebMethod(operationName = "eliminarSucursal")
    public RespuestaDTO eliminarSucursal(@WebParam(name = "codigo") String codigo) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Sucursal existente = sucursalDAO.obtenerPorCodigo(codigo);
            if (existente == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Sucursal no encontrada");
                respuesta.setCodigoError("SUC002");
                return respuesta;
            }

            sucursalDAO.eliminar(codigo);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Sucursal eliminada correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar sucursal", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al eliminar sucursal: " + e.getMessage());
            respuesta.setCodigoError("SUC008");
        }
        return respuesta;
    }
}
