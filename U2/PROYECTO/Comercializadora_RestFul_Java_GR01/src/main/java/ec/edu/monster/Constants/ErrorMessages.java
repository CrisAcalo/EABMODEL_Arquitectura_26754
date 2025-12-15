package ec.edu.monster.Constants;

/**
 * Mensajes de error estandarizados del sistema
 */
public final class ErrorMessages {

    private ErrorMessages() {
    }

    // ========== Errores de Producto ==========
    public static final String PRODUCTO_NO_ENCONTRADO = "Producto no encontrado";
    public static final String CODIGO_REQUERIDO = "El código del producto es requerido";
    public static final String CODIGO_DUPLICADO = "Ya existe un producto con el código %s";
    public static final String NOMBRE_REQUERIDO = "El nombre del producto es requerido";
    public static final String PRECIO_INVALIDO = "El precio debe ser mayor a cero";
    public static final String PRECIO_NO_NUMERICO = "El precio debe ser un valor numérico, no se permite texto";
    public static final String STOCK_INSUFICIENTE = "Stock insuficiente para el producto '%s'. Stock disponible: %d, solicitado: %d";

    // ========== Errores de Factura ==========
    public static final String SOLICITUD_NULA = "La solicitud es nula";
    public static final String ITEMS_REQUERIDOS = "La solicitud no contiene productos. Debe enviar al menos un producto.";
    public static final String CEDULA_REQUERIDA = "La cédula del cliente es requerida";
    public static final String NOMBRE_CLIENTE_REQUERIDO = "El nombre del cliente es requerido";
    public static final String FORMA_PAGO_REQUERIDA = "La forma de pago es requerida. Valores permitidos: EFECTIVO, CREDITO";
    public static final String FORMA_PAGO_INVALIDA = "Forma de pago inválida. Valores permitidos: EFECTIVO, CREDITO";
    public static final String NUMERO_CREDITO_REQUERIDO = "El número de crédito es requerido para pagos a CREDITO. Debe obtenerlo desde el servicio de BanQuito.";
    public static final String FACTURA_NO_ENCONTRADA = "Factura no encontrada";

    // ========== Errores de Validación General ==========
    public static final String CANTIDAD_INVALIDA = "La cantidad debe ser mayor a cero";
    public static final String PRODUCTO_ID_REQUERIDO = "El ID del producto es requerido";

    // ========== Mensajes de Éxito ==========
    public static final String PRODUCTO_CREADO = "Producto creado exitosamente con ID: %d";
    public static final String PRODUCTO_ACTUALIZADO = "Producto actualizado exitosamente";
    public static final String PRODUCTO_ELIMINADO = "Producto eliminado exitosamente";
    public static final String CALCULO_EXITOSO = "Cálculo realizado exitosamente";
    public static final String FACTURA_GENERADA = "Factura generada exitosamente";

    // ========== Errores Internos ==========
    public static final String ERROR_INTERNO = "Error interno: %s";
    public static final String ERROR_GENERAR_FACTURA = "Error al generar factura: %s";
}
