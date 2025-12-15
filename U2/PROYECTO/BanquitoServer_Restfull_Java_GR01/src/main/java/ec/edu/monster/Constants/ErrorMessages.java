package ec.edu.monster.Constants;

/**
 * Mensajes de error estandarizados del sistema
 */
public final class ErrorMessages {

    private ErrorMessages() {
    }

    // Errores de validación de entrada
    public static final String SOLICITUD_NULA = "Error: La solicitud de crédito no puede ser nula";
    public static final String CEDULA_REQUERIDA = "Error: La cédula es requerida";
    public static final String CEDULA_INVALIDA = "Error: La cédula debe tener 10 dígitos numéricos";
    public static final String PRECIO_INVALIDO = "Error: El precio del electrodoméstico debe ser mayor a cero";
    public static final String PRECIO_NO_NUMERICO = "Error: El precio del electrodoméstico debe ser un valor numérico, no se permite texto";
    public static final String NUMERO_CUOTAS_INVALIDO = "Error: El número de cuotas debe ser mayor a cero";
    public static final String NUMERO_CUOTAS_NO_NUMERICO = "Error: El número de cuotas debe ser un valor numérico entero, no se permite texto";

    // Errores de validación de negocio
    public static final String CLIENTE_NO_ENCONTRADO = "La persona no es cliente del banco";
    public static final String SIN_DEPOSITOS_RECIENTES = "El cliente no tiene depósitos en el último mes";
    public static final String EDAD_MINIMA_CASADO = "El cliente casado debe tener al menos 25 años. Edad actual: %d";
    public static final String CREDITO_ACTIVO = "El cliente ya tiene un crédito activo";
    public static final String PLAZO_INVALIDO = "El número de cuotas debe estar entre %d y %d";
    public static final String MONTO_SUPERA_MAXIMO = "El monto solicitado ($%.2f) supera el máximo autorizado ($%.2f)";

    // Mensajes de éxito
    public static final String CREDITO_OTORGADO = "Crédito otorgado exitosamente";
    public static final String MONTO_MAXIMO_CALCULADO = "Monto máximo calculado exitosamente";
    public static final String CLIENTE_VALIDO = "El cliente es sujeto de crédito";

    // Errores internos
    public static final String ERROR_INTERNO = "Error interno: %s";
}
