package ec.edu.monster.services;

import ec.edu.monster.constants.MensajesConstants;
import ec.edu.monster.dal.EmpleadoDAO;
import ec.edu.monster.models.Empleado;
import ec.edu.monster.models.dto.RespuestaDTO;
import ec.edu.monster.validators.EmpleadoValidator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio de lógica de negocio para autenticación y gestión de empleados
 */
public class AutenticacionService {

    private static final Logger LOGGER = Logger.getLogger(AutenticacionService.class.getName());
    private final EmpleadoDAO empleadoDAO;

    public AutenticacionService() {
        this.empleadoDAO = new EmpleadoDAO();
    }

    /**
     * Realiza el login de un empleado
     */
    public RespuestaDTO login(String usuario, String clave) {
        try {
            // Validar datos de entrada
            EmpleadoValidator.validarUsuario(usuario);
            EmpleadoValidator.validarClave(clave);

            // Validar credenciales
            Empleado empleado = empleadoDAO.validarCredenciales(usuario, clave);

            if (empleado == null) {
                LOGGER.warning("Intento de login fallido para usuario: " + usuario);
                return RespuestaDTO.error(MensajesConstants.CREDENCIALES_INVALIDAS);
            }

            LOGGER.info("Login exitoso para usuario: " + usuario);
            return RespuestaDTO.exito(MensajesConstants.LOGIN_EXITOSO, empleado);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error en login", e);
            return RespuestaDTO.error(e.getMessage());
        }
    }

    /**
     * Registra un nuevo empleado
     */
    public RespuestaDTO registrarEmpleado(Empleado empleado) {
        try {
            // Validar datos del empleado
            EmpleadoValidator.validarDatosRegistro(empleado);

            // Verificar si el usuario ya existe (si se proporcionó)
            if (empleado.getUsuario() != null && !empleado.getUsuario().trim().isEmpty()) {
                Empleado empleadoExistente = empleadoDAO.obtenerPorUsuario(empleado.getUsuario());
                if (empleadoExistente != null) {
                    return RespuestaDTO.error(MensajesConstants.USUARIO_EXISTE);
                }
            }

            // Generar código de empleado si no se proporcionó
            if (empleado.getCodigo() == null || empleado.getCodigo().trim().isEmpty()) {
                String nuevoCodigo = empleadoDAO.generarCodigoEmpleado();
                empleado.setCodigo(nuevoCodigo);
            }

            // Registrar empleado
            empleadoDAO.registrar(empleado);

            LOGGER.info("Empleado registrado: " + empleado.getCodigo());
            return RespuestaDTO.exito(MensajesConstants.REGISTRO_EXITOSO, empleado);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar empleado", e);
            return RespuestaDTO.error(e.getMessage());
        }
    }

    /**
     * Cambia la clave de un empleado
     */
    public RespuestaDTO cambiarClave(String codigo, String claveActual, String claveNueva) {
        try {
            // Validar la nueva clave
            EmpleadoValidator.validarClave(claveNueva);

            // Obtener el empleado
            Empleado empleado = empleadoDAO.obtenerPorCodigo(codigo);
            EmpleadoValidator.validarExistencia(empleado);

            // Validar que la clave actual sea correcta
            // Intentamos login con el usuario del empleado
            Empleado empleadoConUsuario = empleadoDAO.obtenerPorUsuario(
                    empleadoDAO.obtenerPorCodigo(codigo).getUsuario());

            if (empleadoConUsuario != null) {
                Empleado validacion = empleadoDAO.validarCredenciales(
                        empleadoConUsuario.getUsuario(), claveActual);

                if (validacion == null) {
                    return RespuestaDTO.error(MensajesConstants.CLAVE_ACTUAL_INCORRECTA);
                }
            }

            // Actualizar la clave
            empleadoDAO.actualizarClave(codigo, claveNueva);

            LOGGER.info("Clave actualizada para empleado: " + codigo);
            return RespuestaDTO.exito(MensajesConstants.CLAVE_ACTUALIZADA);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cambiar clave", e);
            return RespuestaDTO.error(e.getMessage());
        }
    }
}
