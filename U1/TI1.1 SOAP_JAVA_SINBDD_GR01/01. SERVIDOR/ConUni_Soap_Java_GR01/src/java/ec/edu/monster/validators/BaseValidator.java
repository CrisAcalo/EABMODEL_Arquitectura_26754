/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.validators;

import ec.edu.monster.constants.ErrorConstants;
import ec.edu.monster.models.ConversionError;

/**
 *
 * Clase base para las validaciones comunes
 * @author Kewo
 */
public final class BaseValidator {
    // Constructor privado para prevenir instanciación
    private BaseValidator() {
        throw new AssertionError("Esta clase no debe ser instanciada");
    }
    
    /**
     * Valida que el número sea válido (no NaN ni infinito)
     * @param valor Valor a validar
     * @param unidad Nombre de la unidad para el mensaje de error
     * @return ConversionError si hay error, null si es válido
     */
    public static ConversionError validarNumeroValido(double valor, String unidad) {
        if (Double.isNaN(valor)) {
            return new ConversionError(
                ErrorConstants.VALOR_NAN,
                String.format("%s en %s", ErrorConstants.MSG_VALOR_NAN, unidad),
                ErrorConstants.TIPO_VALIDACION,
                valor,
                unidad,
                "El valor proporcionado no representa un número válido."
            );
        }
        
        if (Double.isInfinite(valor)) {
            return new ConversionError(
                ErrorConstants.VALOR_INFINITO,
                String.format("%s en %s", ErrorConstants.MSG_VALOR_INFINITO, unidad),
                ErrorConstants.TIPO_VALIDACION,
                valor,
                unidad,
                "El valor proporcionado es infinito, lo cual no es permitido."
            );
        }
        
        return null; // Sin errores
    }
    
    /**
     * Valida que el valor sea positivo o cero
     * @param valor Valor a validar
     * @param unidad Nombre de la unidad para el mensaje de error
     * @return ConversionError si hay error, null si es válido
     */
    public static ConversionError validarValorPositivo(double valor, String unidad) {
        // Primero validar que sea un número válido
        ConversionError errorNumero = validarNumeroValido(valor, unidad);
        if (errorNumero != null) {
            return errorNumero;
        }
        
        // Luego validar que sea positivo
        if (valor < 0) {
            return new ConversionError(
                ErrorConstants.VALOR_NEGATIVO,
                String.format("%s en %s", ErrorConstants.MSG_VALOR_NEGATIVO, unidad),
                ErrorConstants.TIPO_VALIDACION,
                valor,
                unidad,
                String.format("Las medidas de %s deben ser valores positivos o cero.", unidad.toLowerCase())
            );
        }
        
        return null; // Sin errores
    }
    
    /**
     * Valida string y convierte a double positivo
     * @param valorString Valor como string
     * @param unidad Unidad para mensajes
     * @return ValidationResult con error (si hay) y valor convertido
     */
    public static ValidationResult validarStringPositivo(String valorString, String unidad) {
        // Primero convertir string a double
        ValidationResult resultado = StringToNumberValidator.validarYConvertirADouble(valorString, unidad);
        if (resultado.getError() != null) {
            return resultado;
        }
        
        // Luego validar que sea positivo
        ConversionError error = validarValorPositivo(resultado.getValor(), unidad);
        return new ValidationResult(error, resultado.getValor());
    }
}
