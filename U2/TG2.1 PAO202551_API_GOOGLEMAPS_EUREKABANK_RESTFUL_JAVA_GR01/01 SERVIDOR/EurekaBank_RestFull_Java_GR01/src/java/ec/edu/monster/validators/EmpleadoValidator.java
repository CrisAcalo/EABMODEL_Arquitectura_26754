package ec.edu.monster.validators;

/**
 * Validador para operaciones relacionadas con empleados
 *
 * @author EurekaBank
 */
public class EmpleadoValidator {

    /**
     * Verifica si el nombre de usuario es válido
     *
     * @param usuario Usuario a validar
     * @return true si el usuario es válido
     */
    public static boolean usuarioValido(String usuario) {
        return usuario != null && !usuario.trim().isEmpty() && usuario.length() >= 4;
    }

    /**
     * Verifica si la contraseña es válida (mínimo 6 caracteres)
     *
     * @param clave Contraseña a validar
     * @return true si la contraseña es válida
     */
    public static boolean claveValida(String clave) {
        return clave != null && !clave.trim().isEmpty() && clave.length() >= 6;
    }

    /**
     * Verifica si el código de empleado tiene el formato correcto
     *
     * @param codigo Código a validar
     * @return true si el código es válido
     */
    public static boolean codigoValido(String codigo) {
        return codigo != null && !codigo.trim().isEmpty() && codigo.length() == 4;
    }

    /**
     * Verifica si los datos básicos del empleado son válidos
     *
     * @param nombre Nombre del empleado
     * @param paterno Apellido paterno
     * @param materno Apellido materno
     * @return true si los datos son completos
     */
    public static boolean datosCompletos(String nombre, String paterno, String materno) {
        return nombre != null && !nombre.trim().isEmpty()
                && paterno != null && !paterno.trim().isEmpty()
                && materno != null && !materno.trim().isEmpty();
    }

    // Constructor privado para evitar instanciación
    private EmpleadoValidator() {
    }
}
