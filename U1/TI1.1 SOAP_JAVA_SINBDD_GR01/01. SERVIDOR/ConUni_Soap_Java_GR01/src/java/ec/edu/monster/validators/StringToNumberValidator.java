/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.validators;

import ec.edu.monster.constants.ErrorConstants;
import ec.edu.monster.models.ConversionError;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * Validador para conversión de strings a números
 * @author Kewo
 */
public final class StringToNumberValidator {
    // Constructor privado para prevenir instanciación
    private StringToNumberValidator() {
        throw new AssertionError("Esta clase no debe ser instanciada");
    }
    
    /**
     * Convierte string a double con validación completa
     * @param valorString Valor como string
     * @param unidad Unidad para mensajes de error
     * @return ValidationResult con error (si hay) y valor convertido
     */
    public static ValidationResult validarYConvertirADouble(String valorString, String unidad) {
        double valor = 0.0;
        
        // Validar string vacío o null
        if (valorString == null || valorString.trim().isEmpty()) {
            ConversionError error = new ConversionError(
                ErrorConstants.VALOR_VACIO,
                String.format("El valor en %s no puede estar vacío", unidad),
                ErrorConstants.TIPO_VALIDACION,
                null,
                unidad,
                "Debe proporcionar un valor numérico válido."
            );
            return new ValidationResult(error, valor);
        }
        
        // Limpiar el string (quitar espacios)
        valorString = valorString.trim();
        
        // Intentar convertir a double con diferentes formatos
        Double resultado = tryParseDouble(valorString);
        if (resultado == null) {
            ConversionError error = new ConversionError(
                ErrorConstants.VALOR_NO_NUMERICO,
                String.format("El valor '%s' no es un número válido para %s", valorString, unidad),
                ErrorConstants.TIPO_VALIDACION,
                null,
                unidad,
                String.format("El valor '%s' no puede ser convertido a un número. Use formato decimal con punto (.) como separador.", valorString)
            );
            return new ValidationResult(error, valor);
        }
        
        valor = resultado;
        
        // Validar que no sea NaN o infinito
        ConversionError errorNumero = BaseValidator.validarNumeroValido(valor, unidad);
        if (errorNumero != null) {
            return new ValidationResult(errorNumero, valor);
        }
        
        return new ValidationResult(null, valor); // Conversión exitosa
    }
    
    /**
     * Intenta parsear un string a double usando múltiples formatos
     * @param input String a parsear
     * @return Double parseado o null si falla
     */
    private static Double tryParseDouble(String input) {
        // Intentar con formato estándar (punto como decimal)
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            // Continuar con otros formatos
        }
        
        // Intentar con locale español (coma como decimal)
        try {
            DecimalFormat dfSpanish = new DecimalFormat();
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "ES"));
            dfSpanish.setDecimalFormatSymbols(symbols);
            return dfSpanish.parse(input).doubleValue();
        } catch (ParseException e) {
            // Continuar
        }
        
        // Intentar reemplazando coma por punto
        try {
            String inputWithDot = input.replace(',', '.');
            return Double.parseDouble(inputWithDot);
        } catch (NumberFormatException e) {
            // Continuar
        }
        
        // Intentar reemplazando punto por coma y parseando con locale español
        try {
            String inputWithComma = input.replace('.', ',');
            DecimalFormat dfSpanish = new DecimalFormat();
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "ES"));
            dfSpanish.setDecimalFormatSymbols(symbols);
            return dfSpanish.parse(inputWithComma).doubleValue();
        } catch (ParseException e) {
            // Falló todo
        }
        
        return null;
    }
}
