package ec.edu.monster.validators;

import ec.edu.monster.constants.MensajesConstants;
import ec.edu.monster.models.Empleado;

/**
 * Validador para operaciones relacionadas con empleados
 */
public class EmpleadoValidator {

    /**
     * Valida que el empleado exista
     */
    public static void validarExistencia(Empleado empleado) throws Exception {
        if (empleado == null) {
            throw new Exception(MensajesConstants.EMPLEADO_NO_EXISTE);
        }
    }

    /**
     * Valida que el usuario no esté vacío
     */
    public static void validarUsuario(String usuario) throws Exception {
        if (usuario == null || usuario.trim().isEmpty()) {
            throw new Exception("El usuario no puede estar vacío");
        }

        if (usuario.length() < 4) {
            throw new Exception("El usuario debe tener al menos 4 caracteres");
        }
    }

    /**
     * Valida que la clave no esté vacía y cumpla requisitos mínimos
     */
    public static void validarClave(String clave) throws Exception {
        if (clave == null || clave.trim().isEmpty()) {
            throw new Exception("La clave no puede estar vacía");
        }

        if (clave.length() < 4) {
            throw new Exception("La clave debe tener al menos 4 caracteres");
        }
    }

    /**
     * Valida los datos completos de un empleado
     */
    public static void validarDatosEmpleado(Empleado empleado) throws Exception {
        if (empleado == null) {
            throw new Exception(MensajesConstants.DATOS_INCOMPLETOS);
        }

        if (empleado.getPaterno() == null || empleado.getPaterno().trim().isEmpty()) {
            throw new Exception("El apellido paterno es obligatorio");
        }

        if (empleado.getMaterno() == null || empleado.getMaterno().trim().isEmpty()) {
            throw new Exception("El apellido materno es obligatorio");
        }

        if (empleado.getNombre() == null || empleado.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre es obligatorio");
        }

        if (empleado.getCiudad() == null || empleado.getCiudad().trim().isEmpty()) {
            throw new Exception("La ciudad es obligatoria");
        }
    }

    /**
     * Valida los datos para registro de empleado con usuario
     */
    public static void validarDatosRegistro(Empleado empleado) throws Exception {
        validarDatosEmpleado(empleado);

        if (empleado.getUsuario() != null && !empleado.getUsuario().trim().isEmpty()) {
            validarUsuario(empleado.getUsuario());
            validarClave(empleado.getClave());
        }
    }

    // Constructor privado para evitar instanciación
    private EmpleadoValidator() {
        throw new AssertionError("No se puede instanciar esta clase");
    }
}
