package ec.edu.monster.validators;

import ec.edu.monster.constants.EstadosConstants;
import ec.edu.monster.models.Cuenta;
import java.math.BigDecimal;

/**
 * Validador para operaciones relacionadas con cuentas
 *
 * @author EurekaBank
 */
public class CuentaValidator {

    /**
     * Verifica si una cuenta está activa
     *
     * @param cuenta Cuenta a validar
     * @return true si la cuenta está activa
     */
    public static boolean esActiva(Cuenta cuenta) {
        return cuenta != null && EstadosConstants.ACTIVO.equals(cuenta.getEstado());
    }

    /**
     * Verifica si el saldo es suficiente para una operación
     *
     * @param saldo Saldo actual
     * @param montoRequerido Monto requerido
     * @return true si el saldo es suficiente
     */
    public static boolean tieneSaldoSuficiente(BigDecimal saldo, BigDecimal montoRequerido) {
        return saldo.compareTo(montoRequerido) >= 0;
    }

    /**
     * Verifica si la clave proporcionada es correcta
     *
     * @param cuenta Cuenta a validar
     * @param clave Clave proporcionada
     * @return true si la clave es correcta
     */
    public static boolean claveCorrecta(Cuenta cuenta, String clave) {
        return cuenta != null && cuenta.getClave() != null
                && cuenta.getClave().trim().equals(clave.trim());
    }

    /**
     * Verifica si dos cuentas son de la misma moneda
     *
     * @param cuenta1 Primera cuenta
     * @param cuenta2 Segunda cuenta
     * @return true si son de la misma moneda
     */
    public static boolean mismaMoneda(Cuenta cuenta1, Cuenta cuenta2) {
        return cuenta1 != null && cuenta2 != null
                && cuenta1.getCodigoMoneda() != null
                && cuenta1.getCodigoMoneda().equals(cuenta2.getCodigoMoneda());
    }

    /**
     * Verifica si la cuenta existe
     *
     * @param cuenta Cuenta a validar
     * @return true si la cuenta existe
     */
    public static boolean existe(Cuenta cuenta) {
        return cuenta != null;
    }

    // Constructor privado para evitar instanciación
    private CuentaValidator() {
    }
}
