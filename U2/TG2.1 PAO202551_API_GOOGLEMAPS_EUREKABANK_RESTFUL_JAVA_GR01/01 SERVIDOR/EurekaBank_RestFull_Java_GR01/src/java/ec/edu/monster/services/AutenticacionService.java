package ec.edu.monster.services;

import ec.edu.monster.constants.MensajesConstants;
import ec.edu.monster.dal.EmpleadoDAO;
import ec.edu.monster.dtos.RespuestaDTO;
import ec.edu.monster.models.Empleado;
import ec.edu.monster.validators.EmpleadoValidator;
import java.sql.SQLException;

/**
 * Servicio de lógica de negocio para autenticación y gestión de empleados
 *
 * @author EurekaBank
 */
public class AutenticacionService {

    private final EmpleadoDAO empleadoDAO;

    public AutenticacionService() {
        this.empleadoDAO = new EmpleadoDAO();
    }

    /**
     * Realiza el login de un empleado
     *
     * @param usuario Usuario del empleado
     * @param clave Contraseña del empleado
     * @return RespuestaDTO con el resultado de la operación
     */
    public RespuestaDTO login(String usuario, String clave) {
        try {
            // Validar datos de entrada
            if (!EmpleadoValidator.usuarioValido(usuario)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_USUARIO_VACIO);
                respuesta.setCodigoError("VAL001");
                return respuesta;
            }

            if (!EmpleadoValidator.claveValida(clave)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_CLAVE_CORTA);
                respuesta.setCodigoError("VAL002");
                return respuesta;
            }

            // Validar credenciales
            Empleado empleado = empleadoDAO.validarCredenciales(usuario, clave);

            if (empleado == null) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_CREDENCIALES_INVALIDAS);
                respuesta.setCodigoError("AUTH001");
                return respuesta;
            }

            // Ocultar la contraseña en la respuesta
            empleado.setClave(null);

            RespuestaDTO respuesta = new RespuestaDTO();
            respuesta.setExitoso(true);
            respuesta.setMensaje(MensajesConstants.LOGIN_EXITOSO);
            respuesta.setDatos(empleado);
            return respuesta;

        } catch (SQLException ex) {
            RespuestaDTO respuesta = new RespuestaDTO();
            respuesta.setExitoso(false);
            respuesta.setMensaje(MensajesConstants.ERROR_BASE_DATOS + ": " + ex.getMessage());
            respuesta.setCodigoError("DB001");
            return respuesta;
        }
    }

    /**
     * Registra un nuevo empleado en el sistema
     *
     * @param empleado Datos del empleado a registrar
     * @return RespuestaDTO con el resultado de la operación
     */
    public RespuestaDTO registrarEmpleado(Empleado empleado) {
        try {
            // Validar datos completos
            if (!EmpleadoValidator.datosCompletos(empleado.getNombre(),
                    empleado.getPaterno(), empleado.getMaterno())) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_DATOS_INCOMPLETOS);
                respuesta.setCodigoError("VAL003");
                return respuesta;
            }

            // Validar usuario
            if (!EmpleadoValidator.usuarioValido(empleado.getUsuario())) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_USUARIO_VACIO);
                respuesta.setCodigoError("VAL001");
                return respuesta;
            }

            // Validar clave
            if (!EmpleadoValidator.claveValida(empleado.getClave())) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_CLAVE_CORTA);
                respuesta.setCodigoError("VAL002");
                return respuesta;
            }

            // Verificar si el usuario ya existe
            Empleado empleadoExistente = empleadoDAO.obtenerPorUsuario(empleado.getUsuario());
            if (empleadoExistente != null) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_USUARIO_EXISTENTE);
                respuesta.setCodigoError("AUTH002");
                return respuesta;
            }

            // Generar código de empleado
            String codigoGenerado = empleadoDAO.generarCodigoEmpleado();
            empleado.setCodigo(codigoGenerado);

            // Registrar empleado
            boolean registrado = empleadoDAO.registrar(empleado);

            if (!registrado) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_OPERACION_FALLIDA);
                respuesta.setCodigoError("DB002");
                return respuesta;
            }

            // Ocultar contraseña en la respuesta
            empleado.setClave(null);

            RespuestaDTO respuesta = new RespuestaDTO();
            respuesta.setExitoso(true);
            respuesta.setMensaje(MensajesConstants.REGISTRO_EXITOSO);
            respuesta.setDatos(empleado);
            return respuesta;

        } catch (SQLException ex) {
            RespuestaDTO respuesta = new RespuestaDTO();
            respuesta.setExitoso(false);
            respuesta.setMensaje(MensajesConstants.ERROR_BASE_DATOS + ": " + ex.getMessage());
            respuesta.setCodigoError("DB001");
            return respuesta;
        }
    }

    /**
     * Cambia la contraseña de un empleado
     *
     * @param codigo Código del empleado
     * @param claveActual Contraseña actual
     * @param claveNueva Nueva contraseña
     * @return RespuestaDTO con el resultado de la operación
     */
    public RespuestaDTO cambiarClave(String codigo, String claveActual, String claveNueva) {
        try {
            // Validar código
            if (!EmpleadoValidator.codigoValido(codigo)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje("Código de empleado inválido");
                respuesta.setCodigoError("VAL004");
                return respuesta;
            }

            // Validar nueva clave
            if (!EmpleadoValidator.claveValida(claveNueva)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_CLAVE_CORTA);
                respuesta.setCodigoError("VAL002");
                return respuesta;
            }

            // Obtener empleado
            Empleado empleado = empleadoDAO.obtenerPorCodigo(codigo);
            if (empleado == null) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje("Empleado no encontrado");
                respuesta.setCodigoError("AUTH003");
                return respuesta;
            }

            // Validar clave actual
            if (!empleado.getClave().equals(claveActual)) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_CLAVE_INCORRECTA);
                respuesta.setCodigoError("AUTH004");
                return respuesta;
            }

            // Actualizar clave
            boolean actualizado = empleadoDAO.actualizarClave(codigo, claveNueva);

            if (!actualizado) {
                RespuestaDTO respuesta = new RespuestaDTO();
                respuesta.setExitoso(false);
                respuesta.setMensaje(MensajesConstants.ERROR_OPERACION_FALLIDA);
                respuesta.setCodigoError("DB002");
                return respuesta;
            }

            RespuestaDTO respuesta = new RespuestaDTO();
            respuesta.setExitoso(true);
            respuesta.setMensaje("Contraseña actualizada exitosamente");
            return respuesta;

        } catch (SQLException ex) {
            RespuestaDTO respuesta = new RespuestaDTO();
            respuesta.setExitoso(false);
            respuesta.setMensaje(MensajesConstants.ERROR_BASE_DATOS + ": " + ex.getMessage());
            respuesta.setCodigoError("DB001");
            return respuesta;
        }
    }
}
