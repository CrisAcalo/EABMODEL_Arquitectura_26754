package ec.edu.monster.validators;

import ec.edu.monster.constants.EstadosConstants;
import ec.edu.monster.constants.MensajesConstants;
import ec.edu.monster.models.Cuenta;
import java.math.BigDecimal;

/**
 * Validador para operaciones relacionadas con cuentas
 */
public class CuentaValidator {

    /**
     * Valida que la cuenta exista
     */
    public static void validarExistencia(Cuenta cuenta) throws Exception {
        if (cuenta == null) {
            throw new Exception(MensajesConstants.CUENTA_NO_EXISTE);
        }
    }

    /**
     * Valida que la cuenta esté activa
     */
    public static void validarEstadoActivo(Cuenta cuenta) throws Exception {
        if (!EstadosConstants.ACTIVO.equals(cuenta.getEstado())) {
            throw new Exception(MensajesConstants.CUENTA_INACTIVA);
        }
    }

    /**
     * Valida la clave de la cuenta
     */
    public static void validarClave(Cuenta cuenta, String claveIngresada) throws Exception {
        if (!cuenta.getClave().equals(claveIngresada)) {
            throw new Exception(MensajesConstants.CLAVE_INCORRECTA);
        }
    }

    /**
     * Valida que haya saldo suficiente
     */
    public static void validarSaldoSuficiente(Cuenta cuenta, BigDecimal importeNecesario) throws Exception {
        if (cuenta.getSaldo().compareTo(importeNecesario) < 0) {
            throw new Exception(MensajesConstants.SALDO_INSUFICIENTE);
        }
    }

    /**
     * Valida que dos cuentas sean de la misma moneda
     */
    public static void validarMismaMoneda(Cuenta cuenta1, Cuenta cuenta2) throws Exception {
        if (!cuenta1.getCodigoMoneda().equals(cuenta2.getCodigoMoneda())) {
            throw new Exception(MensajesConstants.MONEDA_DIFERENTE);
        }
    }

    /**
     * Valida que las cuentas origen y destino no sean la misma
     */
    public static void validarCuentasDiferentes(String cuentaOrigen, String cuentaDestino) throws Exception {
        if (cuentaOrigen.equals(cuentaDestino)) {
            throw new Exception(MensajesConstants.MISMA_CUENTA);
        }
    }

    /**
     * Validación completa para depósito
     */
    public static void validarParaDeposito(Cuenta cuenta, String clave, BigDecimal importe) throws Exception {
        validarExistencia(cuenta);
        validarEstadoActivo(cuenta);
        validarClave(cuenta, clave);

        if (importe.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception(MensajesConstants.IMPORTE_INVALIDO);
        }
    }

    /**
     * Validación completa para retiro
     */
    public static void validarParaRetiro(Cuenta cuenta, String clave, BigDecimal importeTotal) throws Exception {
        validarExistencia(cuenta);
        validarEstadoActivo(cuenta);
        validarClave(cuenta, clave);
        validarSaldoSuficiente(cuenta, importeTotal);

        if (importeTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception(MensajesConstants.IMPORTE_INVALIDO);
        }
    }

    /**
     * Validación completa para transferencia
     */
    public static void validarParaTransferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino,
                                                String clave, BigDecimal importe) throws Exception {
        // Validar cuenta origen
        validarExistencia(cuentaOrigen);
        validarEstadoActivo(cuentaOrigen);
        validarClave(cuentaOrigen, clave);

        // Validar cuenta destino
        if (cuentaDestino == null) {
            throw new Exception(MensajesConstants.CUENTA_DESTINO_INVALIDA);
        }
        validarEstadoActivo(cuentaDestino);

        // Validar que sean cuentas diferentes
        validarCuentasDiferentes(cuentaOrigen.getCodigo(), cuentaDestino.getCodigo());

        // Validar misma moneda
        validarMismaMoneda(cuentaOrigen, cuentaDestino);

        // Validar saldo suficiente
        validarSaldoSuficiente(cuentaOrigen, importe);

        if (importe.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception(MensajesConstants.IMPORTE_INVALIDO);
        }
    }

    // Constructor privado para evitar instanciación
    private CuentaValidator() {
        throw new AssertionError("No se puede instanciar esta clase");
    }
}
