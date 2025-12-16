package ec.edu.monster.constants;

import java.math.BigDecimal;

/**
 * Constantes de costos y parámetros del sistema bancario
 *
 * @author EurekaBank
 */
public class CostosConstants {

    // ITF (Impuesto a las Transacciones Financieras)
    public static final BigDecimal TASA_ITF = new BigDecimal("0.0008"); // 0.08%

    // Operaciones gratuitas por cuenta
    public static final int OPERACIONES_GRATUITAS = 15;

    // Costos por movimiento según moneda
    public static final BigDecimal COSTO_MOVIMIENTO_SOLES = new BigDecimal("2.00");
    public static final BigDecimal COSTO_MOVIMIENTO_DOLARES = new BigDecimal("0.60");

    // Códigos de moneda
    public static final String MONEDA_SOLES = "01";
    public static final String MONEDA_DOLARES = "02";

    // Cargo de mantenimiento
    public static final BigDecimal MONTO_MINIMO_SOLES = new BigDecimal("3500.00");
    public static final BigDecimal MONTO_MINIMO_DOLARES = new BigDecimal("1200.00");
    public static final BigDecimal CARGO_MANTENIMIENTO_SOLES = new BigDecimal("7.00");
    public static final BigDecimal CARGO_MANTENIMIENTO_DOLARES = new BigDecimal("2.50");

    // Intereses mensuales
    public static final BigDecimal INTERES_MENSUAL_SOLES = new BigDecimal("0.70"); // 0.70%
    public static final BigDecimal INTERES_MENSUAL_DOLARES = new BigDecimal("0.60"); // 0.60%

    // Constructor privado para evitar instanciación
    private CostosConstants() {
    }
}
