package ec.edu.monster.ms_transacciones.constants;

import java.math.BigDecimal;

public class CostosConstants {
    public static final BigDecimal TASA_ITF = new BigDecimal("0.0008");
    public static final int OPERACIONES_GRATUITAS = 15;
    public static final BigDecimal COSTO_MOVIMIENTO_SOLES = new BigDecimal("2.00");
    public static final BigDecimal COSTO_MOVIMIENTO_DOLARES = new BigDecimal("0.60");
    public static final String MONEDA_SOLES = "01";
    public static final String MONEDA_DOLARES = "02";
    public static final BigDecimal MONTO_MINIMO_SOLES = new BigDecimal("3500.00");
    public static final BigDecimal MONTO_MINIMO_DOLARES = new BigDecimal("1200.00");
    public static final BigDecimal CARGO_MANTENIMIENTO_SOLES = new BigDecimal("7.00");
    public static final BigDecimal CARGO_MANTENIMIENTO_DOLARES = new BigDecimal("2.50");

    private CostosConstants() {
    }
}
