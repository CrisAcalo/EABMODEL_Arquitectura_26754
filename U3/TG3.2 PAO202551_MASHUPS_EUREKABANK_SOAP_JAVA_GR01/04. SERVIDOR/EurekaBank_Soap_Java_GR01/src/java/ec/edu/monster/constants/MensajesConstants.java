package ec.edu.monster.constants;

/**
 * Constantes para mensajes del sistema
 */
public class MensajesConstants {

    // Mensajes de éxito
    public static final String LOGIN_EXITOSO = "Autenticación exitosa";
    public static final String REGISTRO_EXITOSO = "Empleado registrado correctamente";
    public static final String CLAVE_ACTUALIZADA = "Clave actualizada correctamente";
    public static final String DEPOSITO_EXITOSO = "Depósito realizado correctamente";
    public static final String RETIRO_EXITOSO = "Retiro realizado correctamente";
    public static final String TRANSFERENCIA_EXITOSA = "Transferencia realizada correctamente";

    // Mensajes de error - Autenticación
    public static final String CREDENCIALES_INVALIDAS = "Usuario o clave incorrectos";
    public static final String USUARIO_INACTIVO = "Usuario inactivo";
    public static final String USUARIO_EXISTE = "El usuario ya existe";
    public static final String CLAVE_ACTUAL_INCORRECTA = "La clave actual es incorrecta";

    // Mensajes de error - Cuenta
    public static final String CUENTA_NO_EXISTE = "La cuenta no existe";
    public static final String CUENTA_INACTIVA = "La cuenta está inactiva";
    public static final String CLAVE_INCORRECTA = "Clave de cuenta incorrecta";
    public static final String SALDO_INSUFICIENTE = "Saldo insuficiente";
    public static final String MONEDA_DIFERENTE = "Las cuentas deben ser de la misma moneda";
    public static final String CUENTA_DESTINO_INVALIDA = "Cuenta destino inválida";
    public static final String MISMA_CUENTA = "No se puede transferir a la misma cuenta";

    // Mensajes de error - Validación
    public static final String IMPORTE_INVALIDO = "El importe debe ser mayor a cero";
    public static final String DATOS_INCOMPLETOS = "Datos incompletos";
    public static final String EMPLEADO_NO_EXISTE = "El empleado no existe";

    // Mensajes de error - Sistema
    public static final String ERROR_TRANSACCION = "Error al procesar la transacción";
    public static final String ERROR_BASE_DATOS = "Error al acceder a la base de datos";
    public static final String ERROR_INESPERADO = "Error inesperado del sistema";

    // Constructor privado para evitar instanciación
    private MensajesConstants() {
        throw new AssertionError("No se puede instanciar esta clase");
    }
}
