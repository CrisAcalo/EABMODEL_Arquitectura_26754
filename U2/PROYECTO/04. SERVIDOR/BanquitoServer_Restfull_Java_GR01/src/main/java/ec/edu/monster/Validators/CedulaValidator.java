package ec.edu.monster.Validators;

import ec.edu.monster.Constants.ErrorMessages;
import java.util.regex.Pattern;

/**
 * Validador de cédula
 */
public final class CedulaValidator {

    private static final Pattern CEDULA_PATTERN = Pattern.compile("^\\d{10}$");

    private CedulaValidator() {
    }

    /**
     * Validar formato de cédula
     * 
     * @param cedula Cédula a validar
     * @return Resultado de validación con mensaje de error si aplica
     */
    public static ValidationResult validar(String cedula) {
        if (cedula == null || cedula.isBlank()) {
            return new ValidationResult(false, ErrorMessages.CEDULA_REQUERIDA);
        }

        if (!CEDULA_PATTERN.matcher(cedula).matches()) {
            return new ValidationResult(false, ErrorMessages.CEDULA_INVALIDA);
        }

        return new ValidationResult(true, null);
    }

    /**
     * Clase interna para resultado de validación
     */
    public static class ValidationResult {
        private final boolean valido;
        private final String mensajeError;

        public ValidationResult(boolean valido, String mensajeError) {
            this.valido = valido;
            this.mensajeError = mensajeError;
        }

        public boolean isValido() {
            return valido;
        }

        public String getMensajeError() {
            return mensajeError;
        }
    }
}
