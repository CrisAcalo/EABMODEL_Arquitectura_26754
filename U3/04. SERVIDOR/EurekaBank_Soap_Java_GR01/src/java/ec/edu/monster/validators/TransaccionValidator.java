package ec.edu.monster.validators;

import ec.edu.monster.constants.CostosConstants;
import ec.edu.monster.models.Cuenta;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Validador y calculador para operaciones de transacciones
 */
public class TransaccionValidator {

    /**
     * Calcula el ITF (Impuesto a las Transacciones Financieras)
     * ITF = Importe * 0.0008 (0.08%)
     */
    public static BigDecimal calcularITF(BigDecimal importe) {
        BigDecimal itf = importe.multiply(CostosConstants.TASA_ITF);
        return itf.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Determina si se debe aplicar cargo por movimiento adicional
     * Se cobra si la cuenta ha realizado más de 15 movimientos
     */
    public static boolean debeAplicarCargoMovimiento(Cuenta cuenta) {
        return cuenta.getContadorMovimientos() >= CostosConstants.OPERACIONES_GRATUITAS;
    }

    /**
     * Obtiene el importe del cargo por movimiento según la moneda
     */
    public static BigDecimal obtenerCargoMovimiento(String codigoMoneda) {
        if (CostosConstants.MONEDA_SOLES.equals(codigoMoneda)) {
            return CostosConstants.COSTO_MOVIMIENTO_SOLES;
        } else if (CostosConstants.MONEDA_DOLARES.equals(codigoMoneda)) {
            return CostosConstants.COSTO_MOVIMIENTO_DOLARES;
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calcula el cargo por movimiento si aplica
     */
    public static BigDecimal calcularCargoMovimiento(Cuenta cuenta) {
        if (debeAplicarCargoMovimiento(cuenta)) {
            return obtenerCargoMovimiento(cuenta.getCodigoMoneda());
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calcula el total a descontar en un retiro (importe + ITF + cargo)
     */
    public static BigDecimal calcularTotalRetiro(BigDecimal importeRetiro, Cuenta cuenta) {
        BigDecimal itf = calcularITF(importeRetiro);
        BigDecimal cargo = calcularCargoMovimiento(cuenta);

        return importeRetiro.add(itf).add(cargo);
    }

    /**
     * Obtiene el cargo por mantenimiento según la moneda
     */
    public static BigDecimal obtenerCargoMantenimiento(String codigoMoneda) {
        if (CostosConstants.MONEDA_SOLES.equals(codigoMoneda)) {
            return CostosConstants.CARGO_MANTENIMIENTO_SOLES;
        } else if (CostosConstants.MONEDA_DOLARES.equals(codigoMoneda)) {
            return CostosConstants.CARGO_MANTENIMIENTO_DOLARES;
        }
        return BigDecimal.ZERO;
    }

    /**
     * Obtiene el monto mínimo para evitar cargo de mantenimiento
     */
    public static BigDecimal obtenerMontoMinimo(String codigoMoneda) {
        if (CostosConstants.MONEDA_SOLES.equals(codigoMoneda)) {
            return CostosConstants.MONTO_MINIMO_SOLES;
        } else if (CostosConstants.MONEDA_DOLARES.equals(codigoMoneda)) {
            return CostosConstants.MONTO_MINIMO_DOLARES;
        }
        return BigDecimal.ZERO;
    }

    /**
     * Determina si se debe aplicar cargo por mantenimiento
     */
    public static boolean debeAplicarCargoMantenimiento(Cuenta cuenta) {
        BigDecimal montoMinimo = obtenerMontoMinimo(cuenta.getCodigoMoneda());
        return cuenta.getSaldo().compareTo(montoMinimo) < 0;
    }

    /**
     * Valida que el importe sea mayor a cero
     */
    public static void validarImporte(BigDecimal importe) throws Exception {
        if (importe == null || importe.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("El importe debe ser mayor a cero");
        }
    }

    // Constructor privado para evitar instanciación
    private TransaccionValidator() {
        throw new AssertionError("No se puede instanciar esta clase");
    }
}
