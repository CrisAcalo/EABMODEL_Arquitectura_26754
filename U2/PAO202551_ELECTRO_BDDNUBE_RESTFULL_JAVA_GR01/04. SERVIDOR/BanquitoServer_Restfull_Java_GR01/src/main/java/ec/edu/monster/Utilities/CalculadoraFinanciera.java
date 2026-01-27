package ec.edu.monster.Utilities;

import ec.edu.monster.Models.CuotaAmortizacion;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * Calculadora para operaciones financieras
 */
public final class CalculadoraFinanciera {

    private static final MathContext MC = new MathContext(10, RoundingMode.HALF_UP);

    private CalculadoraFinanciera() {
    }

    /**
     * Calcular la cuota fija mensual
     * Fórmula: Cuota = Valor / (1 - ((1 + TasaPeriodo)^-NúmeroCuotas)) /
     * TasaPeriodo
     */
    public static BigDecimal calcularCuotaFija(BigDecimal montoCredito, BigDecimal tasaAnual, int numeroCuotas) {
        // Tasa mensual
        BigDecimal tasaMensual = tasaAnual.divide(BigDecimal.valueOf(12), MC);

        // (1 + tasaMensual)
        BigDecimal unMasTasa = BigDecimal.ONE.add(tasaMensual);

        // (1 + tasaMensual)^-numeroCuotas
        double potencia = Math.pow(unMasTasa.doubleValue(), -numeroCuotas);

        // 1 - potencia
        BigDecimal numerador = BigDecimal.ONE.subtract(BigDecimal.valueOf(potencia));

        // numerador / tasaMensual
        BigDecimal denominador = numerador.divide(tasaMensual, MC);

        // Cuota = montoCredito / denominador
        BigDecimal cuota = montoCredito.divide(denominador, MC);

        return cuota.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Generar la tabla de amortización completa
     */
    public static List<CuotaAmortizacion> generarTablaAmortizacion(
            BigDecimal montoCredito,
            BigDecimal tasaAnual,
            int numeroCuotas,
            BigDecimal cuotaFija) {

        List<CuotaAmortizacion> tabla = new ArrayList<>();

        BigDecimal tasaMensual = tasaAnual.divide(BigDecimal.valueOf(12), MC);
        BigDecimal saldo = montoCredito;

        for (int i = 1; i <= numeroCuotas; i++) {
            // Calcular interés de la cuota
            BigDecimal interes = saldo.multiply(tasaMensual).setScale(2, RoundingMode.HALF_UP);

            // Calcular capital pagado
            BigDecimal capitalPagado = cuotaFija.subtract(interes).setScale(2, RoundingMode.HALF_UP);

            // Calcular nuevo saldo
            BigDecimal nuevoSaldo = saldo.subtract(capitalPagado).setScale(2, RoundingMode.HALF_UP);

            BigDecimal cuotaActual = cuotaFija;

            // Ajuste para la última cuota (por posibles diferencias de redondeo)
            if (i == numeroCuotas) {
                capitalPagado = saldo;
                cuotaActual = capitalPagado.add(interes);
                nuevoSaldo = BigDecimal.ZERO;
            }

            // Crear cuota de amortización
            CuotaAmortizacion cuota = new CuotaAmortizacion();
            cuota.setNumeroCuota(i);
            cuota.setValorCuota(cuotaActual);
            cuota.setInteres(interes);
            cuota.setCapitalPagado(capitalPagado);
            cuota.setSaldo(nuevoSaldo);

            tabla.add(cuota);

            // Actualizar saldo para la siguiente iteración
            saldo = nuevoSaldo;
        }

        return tabla;
    }

    /**
     * Calcular la edad de una persona
     */
    public static int calcularEdad(LocalDate fechaNacimiento) {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}
