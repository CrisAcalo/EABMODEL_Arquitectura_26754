/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.validators;

import ec.edu.monster.constants.ErrorConstants;
import ec.edu.monster.constants.TemperaturaConstants;
import ec.edu.monster.models.ConversionError;

/**
 *
 * Validador específico para temperaturas
 * @author Kewo
 */
public final class TemperaturaValidator {
    // Constructor privado para prevenir instanciación
    private TemperaturaValidator() {
        throw new AssertionError("Esta clase no debe ser instanciada");
    }
    
    /**
     * Valida string y convierte a temperatura Celsius válida
     * @param celsiusString Temperatura como string
     * @return ValidationResult con error (si hay) y temperatura convertida
     */
    public static ValidationResult validarStringTemperaturaCelsius(String celsiusString) {
        // Primero convertir string a double
        ValidationResult resultado = StringToNumberValidator.validarYConvertirADouble(
            celsiusString, 
            TemperaturaConstants.CELSIUS
        );
        if (resultado.getError() != null) {
            return resultado;
        }
        
        // Luego validar límites de temperatura
        ConversionError error = validarTemperaturaCelsius(resultado.getValor());
        return new ValidationResult(error, resultado.getValor());
    }
    
    /**
     * Valida string y convierte a temperatura Fahrenheit válida
     * @param fahrenheitString Temperatura como string
     * @return ValidationResult con error (si hay) y temperatura convertida
     */
    public static ValidationResult validarStringTemperaturaFahrenheit(String fahrenheitString) {
        // Primero convertir string a double
        ValidationResult resultado = StringToNumberValidator.validarYConvertirADouble(
            fahrenheitString, 
            TemperaturaConstants.FAHRENHEIT
        );
        if (resultado.getError() != null) {
            return resultado;
        }
        
        // Luego validar límites de temperatura
        ConversionError error = validarTemperaturaFahrenheit(resultado.getValor());
        return new ValidationResult(error, resultado.getValor());
    }
    
    /**
     * Valida string y convierte a temperatura Kelvin válida
     * @param kelvinString Temperatura como string
     * @return ValidationResult con error (si hay) y temperatura convertida
     */
    public static ValidationResult validarStringTemperaturaKelvin(String kelvinString) {
        // Primero convertir string a double
        ValidationResult resultado = StringToNumberValidator.validarYConvertirADouble(
            kelvinString, 
            TemperaturaConstants.KELVIN
        );
        if (resultado.getError() != null) {
            return resultado;
        }
        
        // Luego validar límites de temperatura
        ConversionError error = validarTemperaturaKelvin(resultado.getValor());
        return new ValidationResult(error, resultado.getValor());
    }
    
    /**
     * Valida que la temperatura en Celsius sea válida
     * @param celsius Temperatura en Celsius
     * @return ConversionError si hay error, null si es válido
     */
    public static ConversionError validarTemperaturaCelsius(double celsius) {
        // Primero validar que sea un número válido
        ConversionError errorNumero = BaseValidator.validarNumeroValido(
            celsius, 
            TemperaturaConstants.CELSIUS
        );
        if (errorNumero != null) {
            return errorNumero;
        }
        
        // Validar límite del cero absoluto
        if (celsius < TemperaturaConstants.CERO_ABSOLUTO_CELSIUS) {
            return new ConversionError(
                ErrorConstants.TEMPERATURA_BAJO_CERO_ABSOLUTO,
                String.format("%s para Celsius", ErrorConstants.MSG_TEMPERATURA_INVALIDA),
                ErrorConstants.TIPO_VALIDACION,
                celsius,
                TemperaturaConstants.CELSIUS,
                String.format("La temperatura mínima posible es %.2f°C (cero absoluto).", 
                    TemperaturaConstants.CERO_ABSOLUTO_CELSIUS)
            );
        }
        
        return null; // Sin errores
    }
    
    /**
     * Valida que la temperatura en Fahrenheit sea válida
     * @param fahrenheit Temperatura en Fahrenheit
     * @return ConversionError si hay error, null si es válido
     */
    public static ConversionError validarTemperaturaFahrenheit(double fahrenheit) {
        // Primero validar que sea un número válido
        ConversionError errorNumero = BaseValidator.validarNumeroValido(
            fahrenheit, 
            TemperaturaConstants.FAHRENHEIT
        );
        if (errorNumero != null) {
            return errorNumero;
        }
        
        // Validar límite del cero absoluto
        if (fahrenheit < TemperaturaConstants.CERO_ABSOLUTO_FAHRENHEIT) {
            return new ConversionError(
                ErrorConstants.TEMPERATURA_BAJO_CERO_ABSOLUTO,
                String.format("%s para Fahrenheit", ErrorConstants.MSG_TEMPERATURA_INVALIDA),
                ErrorConstants.TIPO_VALIDACION,
                fahrenheit,
                TemperaturaConstants.FAHRENHEIT,
                String.format("La temperatura mínima posible es %.2f°F (cero absoluto).", 
                    TemperaturaConstants.CERO_ABSOLUTO_FAHRENHEIT)
            );
        }
        
        return null; // Sin errores
    }
    
    /**
     * Valida que la temperatura en Kelvin sea válida
     * @param kelvin Temperatura en Kelvin
     * @return ConversionError si hay error, null si es válido
     */
    public static ConversionError validarTemperaturaKelvin(double kelvin) {
        // Primero validar que sea un número válido
        ConversionError errorNumero = BaseValidator.validarNumeroValido(
            kelvin, 
            TemperaturaConstants.KELVIN
        );
        if (errorNumero != null) {
            return errorNumero;
        }
        
        // Validar límite del cero absoluto
        if (kelvin < TemperaturaConstants.CERO_ABSOLUTO_KELVIN) {
            return new ConversionError(
                ErrorConstants.TEMPERATURA_BAJO_CERO_ABSOLUTO,
                String.format("%s para Kelvin", ErrorConstants.MSG_TEMPERATURA_INVALIDA),
                ErrorConstants.TIPO_VALIDACION,
                kelvin,
                TemperaturaConstants.KELVIN,
                String.format("La temperatura mínima posible es %.2fK (cero absoluto).", 
                    TemperaturaConstants.CERO_ABSOLUTO_KELVIN)
            );
        }
        
        return null; // Sin errores
    }
}
