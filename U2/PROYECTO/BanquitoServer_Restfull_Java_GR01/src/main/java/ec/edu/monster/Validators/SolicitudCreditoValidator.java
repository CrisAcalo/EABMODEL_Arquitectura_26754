package ec.edu.monster.Validators;

import ec.edu.monster.Constants.ErrorMessages;
import ec.edu.monster.DTOs.SolicitudCreditoDTO;
import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Validador para SolicitudCreditoDTO
 */
public final class SolicitudCreditoValidator {

    private static final Pattern CEDULA_PATTERN = Pattern.compile("^\\d{10}$");

    private SolicitudCreditoValidator() {
    }

    /**
     * Validar una solicitud de crédito y convertir valores
     */
    public static ValidationResult validarYConvertir(SolicitudCreditoDTO solicitud) {
        if (solicitud == null) {
            return new ValidationResult(false, ErrorMessages.SOLICITUD_NULA, null, null);
        }

        // Validar cédula no vacía
        if (solicitud.getCedula() == null || solicitud.getCedula().isBlank()) {
            return new ValidationResult(false, ErrorMessages.CEDULA_REQUERIDA, null, null);
        }

        // Validar formato de cédula (10 dígitos)
        if (!CEDULA_PATTERN.matcher(solicitud.getCedula()).matches()) {
            return new ValidationResult(false, ErrorMessages.CEDULA_INVALIDA, null, null);
        }

        // Validar y convertir precio del electrodoméstico
        if (solicitud.getPrecioElectrodomestico() == null || solicitud.getPrecioElectrodomestico().isBlank()) {
            return new ValidationResult(false, ErrorMessages.PRECIO_INVALIDO, null, null);
        }

        BigDecimal precio;
        try {
            precio = new BigDecimal(solicitud.getPrecioElectrodomestico());
        } catch (NumberFormatException e) {
            return new ValidationResult(false, ErrorMessages.PRECIO_NO_NUMERICO, null, null);
        }

        if (precio.compareTo(BigDecimal.ZERO) <= 0) {
            return new ValidationResult(false, ErrorMessages.PRECIO_INVALIDO, null, null);
        }

        // Validar y convertir número de cuotas
        if (solicitud.getNumeroCuotas() == null || solicitud.getNumeroCuotas().isBlank()) {
            return new ValidationResult(false, ErrorMessages.NUMERO_CUOTAS_INVALIDO, null, null);
        }

        Integer cuotas;
        try {
            cuotas = Integer.parseInt(solicitud.getNumeroCuotas());
        } catch (NumberFormatException e) {
            return new ValidationResult(false, ErrorMessages.NUMERO_CUOTAS_NO_NUMERICO, null, null);
        }

        if (cuotas <= 0) {
            return new ValidationResult(false, ErrorMessages.NUMERO_CUOTAS_INVALIDO, null, null);
        }

        return new ValidationResult(true, null, precio, cuotas);
    }

    /**
     * Clase para resultado de validación con valores convertidos
     */
    public static class ValidationResult {
        private final boolean valido;
        private final String mensajeError;
        private final BigDecimal precio;
        private final Integer cuotas;

        public ValidationResult(boolean valido, String mensajeError, BigDecimal precio, Integer cuotas) {
            this.valido = valido;
            this.mensajeError = mensajeError;
            this.precio = precio;
            this.cuotas = cuotas;
        }

        public boolean isValido() {
            return valido;
        }

        public String getMensajeError() {
            return mensajeError;
        }

        public BigDecimal getPrecio() {
            return precio;
        }

        public Integer getCuotas() {
            return cuotas;
        }
    }
}
