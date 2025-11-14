package ec.edu.monster.constants;

/**
 * Mensajes estándar para respuestas del sistema
 *
 * @author EurekaBank
 */
public class MensajesConstants {

    // Mensajes de éxito
    public static final String DEPOSITO_EXITOSO = "Depósito realizado exitosamente";
    public static final String RETIRO_EXITOSO = "Retiro realizado exitosamente";
    public static final String TRANSFERENCIA_EXITOSA = "Transferencia realizada exitosamente";
    public static final String LOGIN_EXITOSO = "Autenticación exitosa";
    public static final String REGISTRO_EXITOSO = "Empleado registrado exitosamente";

    // Errores de cuenta
    public static final String ERROR_CUENTA_NO_EXISTE = "La cuenta no existe";
    public static final String ERROR_CUENTA_INACTIVA = "La cuenta no está activa";
    public static final String ERROR_CLAVE_INCORRECTA = "Clave incorrecta";
    public static final String ERROR_SALDO_INSUFICIENTE = "Saldo insuficiente para realizar la operación";

    // Errores de validación
    public static final String ERROR_IMPORTE_INVALIDO = "El importe debe ser mayor a cero";
    public static final String ERROR_IMPORTE_DECIMALES = "El importe no puede tener más de 2 decimales";
    public static final String ERROR_MONEDA_DIFERENTE = "Las cuentas deben ser de la misma moneda";

    // Errores de autenticación
    public static final String ERROR_CREDENCIALES_INVALIDAS = "Usuario o contraseña incorrectos";
    public static final String ERROR_USUARIO_EXISTENTE = "El usuario ya existe";
    public static final String ERROR_USUARIO_VACIO = "El usuario no puede estar vacío";
    public static final String ERROR_CLAVE_CORTA = "La contraseña debe tener al menos 6 caracteres";

    // Errores generales
    public static final String ERROR_DATOS_INCOMPLETOS = "Faltan datos obligatorios";
    public static final String ERROR_OPERACION_FALLIDA = "La operación no pudo completarse";
    public static final String ERROR_BASE_DATOS = "Error al conectar con la base de datos";

    // Constructor privado para evitar instanciación
    private MensajesConstants() {
    }
}
