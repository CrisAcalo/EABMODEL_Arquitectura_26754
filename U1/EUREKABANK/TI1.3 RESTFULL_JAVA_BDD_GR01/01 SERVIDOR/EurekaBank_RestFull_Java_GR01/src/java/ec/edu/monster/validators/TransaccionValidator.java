package ec.edu.monster.validators;

import ec.edu.monster.constants.CostosConstants;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Validador para operaciones de transacciones bancarias
 *
 * @author EurekaBank
 */
public class TransaccionValidator {

    /**
     * Verifica si el importe es válido (mayor a 0 y con máximo 2 decimales)
     *
     * @param importe Importe a validar
     * @return true si el importe es válido
     */
    public static boolean importeValido(BigDecimal importe) {
        if (importe == null || importe.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        // Verificar que tenga máximo 2 decimales
        BigDecimal redondeado = importe.setScale(2, RoundingMode.HALF_UP);
        return importe.compareTo(redondeado) == 0;
    }

    /**
     * Verifica si debe aplicarse el costo por movimiento
     *
     * @param numeroMovimientos Número de movimientos actuales
     * @return true si debe aplicarse el costo
     */
    public static boolean debeAplicarCostoPorMovimiento(int numeroMovimientos) {
        return numeroMovimientos >= CostosConstants.OPERACIONES_GRATUITAS;
    }

    /**
     * Calcula el ITF sobre un importe
     *
     * @param importe Importe base
     * @return ITF calculado
     */
    public static BigDecimal calcularITF(BigDecimal importe) {
        return importe.multiply(CostosConstants.TASA_ITF)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Obtiene el costo por movimiento según la moneda
     *
     * @param codigoMoneda Código de la moneda
     * @return Costo por movimiento
     */
    public static BigDecimal obtenerCostoPorMovimiento(String codigoMoneda) {
        if (CostosConstants.MONEDA_SOLES.equals(codigoMoneda)) {
            return CostosConstants.COSTO_MOVIMIENTO_SOLES;
        } else if (CostosConstants.MONEDA_DOLARES.equals(codigoMoneda)) {
            return CostosConstants.COSTO_MOVIMIENTO_DOLARES;
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Calcula el costo total de una transacción (ITF + costo por movimiento si
     * aplica)
     *
     * @param importe Importe de la transacción
     * @param codigoMoneda Código de la moneda
     * @param numeroMovimientos Número de movimientos actuales
     * @return Costo total
     */
    public static BigDecimal calcularCostoTotal(BigDecimal importe, String codigoMoneda, int numeroMovimientos) {
        BigDecimal itf = calcularITF(importe);
        BigDecimal costoPorMovimiento = debeAplicarCostoPorMovimiento(numeroMovimientos)
                ? obtenerCostoPorMovimiento(codigoMoneda)
                : BigDecimal.ZERO;

        return itf.add(costoPorMovimiento);
    }

    // Constructor privado para evitar instanciación
    private TransaccionValidator() {
    }
}
