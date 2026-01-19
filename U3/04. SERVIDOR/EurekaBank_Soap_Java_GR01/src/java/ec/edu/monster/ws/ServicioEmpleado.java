package ec.edu.monster.ws;

import ec.edu.monster.dal.EmpleadoDAO;
import ec.edu.monster.models.Empleado;
import ec.edu.monster.models.dto.RespuestaDTO;
import ec.edu.monster.utils.PasswordUtils;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio Web SOAP para gestión de Empleados
 * Incluye operaciones CRUD completas
 */
@WebService(serviceName = "ServicioEmpleado")
public class ServicioEmpleado {

    private static final Logger LOGGER = Logger.getLogger(ServicioEmpleado.class.getName());
    private final EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    /**
     * Lista todos los empleados
     */
    @WebMethod(operationName = "listarEmpleados")
    public RespuestaDTO listarEmpleados() {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            List<Empleado> empleados = empleadoDAO.obtenerTodos();
            respuesta.setExitoso(true);
            respuesta.setMensaje("Empleados obtenidos correctamente");
            respuesta.setDatos(empleados);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar empleados", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener empleados: " + e.getMessage());
            respuesta.setCodigoError("EMP001");
        }
        return respuesta;
    }

    /**
     * Obtiene un empleado por su código
     */
    @WebMethod(operationName = "obtenerEmpleado")
    public RespuestaDTO obtenerEmpleado(@WebParam(name = "codigo") String codigo) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Empleado empleado = empleadoDAO.obtenerPorCodigo(codigo);
            if (empleado != null) {
                respuesta.setExitoso(true);
                respuesta.setMensaje("Empleado encontrado");
                respuesta.setDatos(empleado);
            } else {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Empleado no encontrado");
                respuesta.setCodigoError("EMP002");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener empleado", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener empleado: " + e.getMessage());
            respuesta.setCodigoError("EMP003");
        }
        return respuesta;
    }

    /**
     * Obtiene un empleado por su usuario
     */
    @WebMethod(operationName = "obtenerEmpleadoPorUsuario")
    public RespuestaDTO obtenerEmpleadoPorUsuario(@WebParam(name = "usuario") String usuario) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Empleado empleado = empleadoDAO.obtenerPorUsuario(usuario);
            if (empleado != null) {
                respuesta.setExitoso(true);
                respuesta.setMensaje("Empleado encontrado");
                respuesta.setDatos(empleado);
            } else {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Empleado no encontrado");
                respuesta.setCodigoError("EMP002");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener empleado por usuario", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener empleado: " + e.getMessage());
            respuesta.setCodigoError("EMP003");
        }
        return respuesta;
    }

    /**
     * Registra un nuevo empleado
     */
    @WebMethod(operationName = "registrarEmpleado")
    public RespuestaDTO registrarEmpleado(@WebParam(name = "empleado") Empleado empleado) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            // Validar datos obligatorios
            if (empleado.getUsuario() == null || empleado.getClave() == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Usuario y clave son obligatorios");
                respuesta.setCodigoError("EMP004");
                return respuesta;
            }

            // Verificar si el usuario ya existe
            Empleado existente = empleadoDAO.obtenerPorUsuario(empleado.getUsuario());
            if (existente != null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Ya existe un empleado con ese usuario");
                respuesta.setCodigoError("EMP005");
                return respuesta;
            }

            // Generar código si no tiene
            if (empleado.getCodigo() == null || empleado.getCodigo().isEmpty()) {
                empleado.setCodigo(empleadoDAO.generarCodigoEmpleado());
            }

            empleadoDAO.registrar(empleado);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Empleado registrado correctamente");
            respuesta.setDatos(empleado);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar empleado", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al registrar empleado: " + e.getMessage());
            respuesta.setCodigoError("EMP006");
        }
        return respuesta;
    }

    /**
     * Actualiza los datos de un empleado (sin cambiar usuario ni clave)
     */
    @WebMethod(operationName = "actualizarEmpleado")
    public RespuestaDTO actualizarEmpleado(@WebParam(name = "empleado") Empleado empleado) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Empleado existente = empleadoDAO.obtenerPorCodigo(empleado.getCodigo());
            if (existente == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Empleado no encontrado");
                respuesta.setCodigoError("EMP002");
                return respuesta;
            }

            empleadoDAO.actualizar(empleado);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Empleado actualizado correctamente");
            respuesta.setDatos(empleado);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar empleado", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al actualizar empleado: " + e.getMessage());
            respuesta.setCodigoError("EMP007");
        }
        return respuesta;
    }

    /**
     * Cambia la clave de un empleado
     */
    @WebMethod(operationName = "cambiarClave")
    public RespuestaDTO cambiarClave(
            @WebParam(name = "codigo") String codigo,
            @WebParam(name = "claveActual") String claveActual,
            @WebParam(name = "claveNueva") String claveNueva) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Empleado empleado = empleadoDAO.obtenerPorCodigo(codigo);
            if (empleado == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Empleado no encontrado");
                respuesta.setCodigoError("EMP002");
                return respuesta;
            }

            // Verificar clave actual
            String hashActual = empleadoDAO.obtenerHashPorUsuario(empleado.getUsuario());
            if (!PasswordUtils.verificarPassword(claveActual, hashActual)) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("La clave actual es incorrecta");
                respuesta.setCodigoError("EMP008");
                return respuesta;
            }

            // Validar nueva clave
            if (claveNueva == null || claveNueva.length() < 6) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("La nueva clave debe tener al menos 6 caracteres");
                respuesta.setCodigoError("EMP009");
                return respuesta;
            }

            empleadoDAO.actualizarClave(codigo, claveNueva);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Clave actualizada correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cambiar clave", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al cambiar clave: " + e.getMessage());
            respuesta.setCodigoError("EMP010");
        }
        return respuesta;
    }

    /**
     * Elimina un empleado
     */
    @WebMethod(operationName = "eliminarEmpleado")
    public RespuestaDTO eliminarEmpleado(@WebParam(name = "codigo") String codigo) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Empleado existente = empleadoDAO.obtenerPorCodigo(codigo);
            if (existente == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Empleado no encontrado");
                respuesta.setCodigoError("EMP002");
                return respuesta;
            }

            empleadoDAO.eliminar(codigo);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Empleado eliminado correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar empleado", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al eliminar empleado: " + e.getMessage());
            respuesta.setCodigoError("EMP011");
        }
        return respuesta;
    }
}
