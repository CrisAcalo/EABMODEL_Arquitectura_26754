package ec.edu.monster.constants;

import java.math.BigDecimal;

/**
 * Constantes para costos y cargos bancarios
 */
public class CostosConstants {

    // Impuesto a las Transacciones Financieras (0.08%)
    public static final BigDecimal TASA_ITF = new BigDecimal("0.0008");

    // Número de operaciones gratuitas
    public static final int OPERACIONES_GRATUITAS = 15;

    // Costo por movimiento adicional
    public static final BigDecimal COSTO_MOVIMIENTO_SOLES = new BigDecimal("2.00");
    public static final BigDecimal COSTO_MOVIMIENTO_DOLARES = new BigDecimal("0.60");

    // Códigos de moneda
    public static final String MONEDA_SOLES = "01";
    public static final String MONEDA_DOLARES = "02";

    // Montos mínimos para mantenimiento
    public static final BigDecimal MONTO_MINIMO_SOLES = new BigDecimal("3500.00");
    public static final BigDecimal MONTO_MINIMO_DOLARES = new BigDecimal("1200.00");

    // Cargo por mantenimiento
    public static final BigDecimal CARGO_MANTENIMIENTO_SOLES = new BigDecimal("7.00");
    public static final BigDecimal CARGO_MANTENIMIENTO_DOLARES = new BigDecimal("2.50");

    // Interés mensual (porcentaje)
    public static final BigDecimal INTERES_MENSUAL_SOLES = new BigDecimal("0.70");
    public static final BigDecimal INTERES_MENSUAL_DOLARES = new BigDecimal("0.60");

    // Constructor privado para evitar instanciación
    private CostosConstants() {
        throw new AssertionError("No se puede instanciar esta clase");
    }
}
