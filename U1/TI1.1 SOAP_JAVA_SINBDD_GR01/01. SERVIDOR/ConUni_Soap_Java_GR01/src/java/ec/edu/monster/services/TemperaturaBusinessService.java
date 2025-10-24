/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.services;

import ec.edu.monster.constants.ErrorConstants;
import ec.edu.monster.constants.TemperaturaConstants;
import ec.edu.monster.models.ConversionError;
import ec.edu.monster.models.ConversionResult;
import ec.edu.monster.models.UnidadConversion;
import ec.edu.monster.validators.TemperaturaValidator;
import ec.edu.monster.validators.ValidationResult;

/**
 *
 * Servicio de negocio para conversiones de temperatura
 * @author Kewo
 */
public class TemperaturaBusinessService {
    /**
     * Convierte Celsius a Fahrenheit y retorna resultado con manejo de errores
     * @param celsiusString Temperatura en Celsius como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirCelsiusAFahrenheit(String celsiusString) {
        // Validar y convertir string a double
        ValidationResult validacion = TemperaturaValidator.validarStringTemperaturaCelsius(celsiusString);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double celsius = validacion.getValor();
        
        try {
            // °F = (°C × 9/5) + 32
            double resultadoExacto = (celsius * TemperaturaConstants.CELSIUS_FAHRENHEIT_FACTOR) + 
                                    TemperaturaConstants.FAHRENHEIT_CELSIUS_OFFSET;
            
            UnidadConversion conversion = new UnidadConversion(
                celsius,
                resultadoExacto,
                TemperaturaConstants.CELSIUS,
                TemperaturaConstants.FAHRENHEIT,
                TemperaturaConstants.CELSIUS_FAHRENHEIT_FACTOR
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
                "Error en la conversión de Celsius a Fahrenheit",
                ErrorConstants.TIPO_CONVERSION,
                celsius,
                TemperaturaConstants.CELSIUS,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    /**
     * Convierte Fahrenheit a Celsius y retorna resultado con manejo de errores
     * @param fahrenheitString Temperatura en Fahrenheit como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirFahrenheitACelsius(String fahrenheitString) {
        // Validar y convertir string a double
        ValidationResult validacion = TemperaturaValidator.validarStringTemperaturaFahrenheit(fahrenheitString);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double fahrenheit = validacion.getValor();
        
        try {
            // °C = (°F - 32) × 5/9
            double resultadoExacto = (fahrenheit - TemperaturaConstants.FAHRENHEIT_CELSIUS_OFFSET) * 
                                    TemperaturaConstants.FAHRENHEIT_CELSIUS_FACTOR;
            
            UnidadConversion conversion = new UnidadConversion(
                fahrenheit,
                resultadoExacto,
                TemperaturaConstants.FAHRENHEIT,
                TemperaturaConstants.CELSIUS,
                TemperaturaConstants.FAHRENHEIT_CELSIUS_FACTOR
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
                "Error en la conversión de Fahrenheit a Celsius",
                ErrorConstants.TIPO_CONVERSION,
                fahrenheit,
                TemperaturaConstants.FAHRENHEIT,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    /**
     * Convierte Fahrenheit a Kelvin y retorna resultado con manejo de errores
     * @param fahrenheitString Temperatura en Fahrenheit como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirFahrenheitAKelvin(String fahrenheitString) {
        // Validar y convertir string a double
        ValidationResult validacion = TemperaturaValidator.validarStringTemperaturaFahrenheit(fahrenheitString);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double fahrenheit = validacion.getValor();
        
        try {
            // K = (°F - 32) × 5/9 + 273.15
            double resultadoExacto = (fahrenheit - TemperaturaConstants.FAHRENHEIT_CELSIUS_OFFSET) * 
                                    TemperaturaConstants.FAHRENHEIT_CELSIUS_FACTOR + 
                                    TemperaturaConstants.CELSIUS_KELVIN_OFFSET;
            
            UnidadConversion conversion = new UnidadConversion(
                fahrenheit,
                resultadoExacto,
                TemperaturaConstants.FAHRENHEIT,
                TemperaturaConstants.KELVIN,
                TemperaturaConstants.FAHRENHEIT_CELSIUS_FACTOR
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
                "Error en la conversión de Fahrenheit a Kelvin",
                ErrorConstants.TIPO_CONVERSION,
                fahrenheit,
                TemperaturaConstants.FAHRENHEIT,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    /**
     * Convierte Kelvin a Fahrenheit y retorna resultado con manejo de errores
     * @param kelvinString Temperatura en Kelvin como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirKelvinAFahrenheit(String kelvinString) {
        // Validar y convertir string a double
        ValidationResult validacion = TemperaturaValidator.validarStringTemperaturaKelvin(kelvinString);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double kelvin = validacion.getValor();
        
        try {
            // °F = (K - 273.15) × 9/5 + 32
            double resultadoExacto = (kelvin - TemperaturaConstants.CELSIUS_KELVIN_OFFSET) * 
                                    TemperaturaConstants.CELSIUS_FAHRENHEIT_FACTOR + 
                                    TemperaturaConstants.FAHRENHEIT_CELSIUS_OFFSET;
            
            UnidadConversion conversion = new UnidadConversion(
                kelvin,
                resultadoExacto,
                TemperaturaConstants.KELVIN,
                TemperaturaConstants.FAHRENHEIT,
                TemperaturaConstants.CELSIUS_FAHRENHEIT_FACTOR
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
                "Error en la conversión de Kelvin a Fahrenheit",
                ErrorConstants.TIPO_CONVERSION,
                kelvin,
                TemperaturaConstants.KELVIN,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    /**
     * Convierte Kelvin a Celsius y retorna resultado con manejo de errores
     * @param kelvinString Temperatura en Kelvin como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirKelvinACelsius(String kelvinString) {
        // Validar y convertir string a double
        ValidationResult validacion = TemperaturaValidator.validarStringTemperaturaKelvin(kelvinString);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double kelvin = validacion.getValor();
        
        try {
            // °C = K - 273.15
            double resultadoExacto = kelvin - TemperaturaConstants.CELSIUS_KELVIN_OFFSET;
            
            UnidadConversion conversion = new UnidadConversion(
                kelvin,
                resultadoExacto,
                TemperaturaConstants.KELVIN,
                TemperaturaConstants.CELSIUS,
                1.0
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
                "Error en la conversión de Kelvin a Celsius",
                ErrorConstants.TIPO_CONVERSION,
                kelvin,
                TemperaturaConstants.KELVIN,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    /**
     * Convierte Celsius a Kelvin y retorna resultado con manejo de errores
     * @param celsiusString Temperatura en Celsius como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirCelsiusAKelvin(String celsiusString) {
        // Validar y convertir string a double
        ValidationResult validacion = TemperaturaValidator.validarStringTemperaturaCelsius(celsiusString);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double celsius = validacion.getValor();
        
        try {
            // K = °C + 273.15
            double resultadoExacto = celsius + TemperaturaConstants.CELSIUS_KELVIN_OFFSET;
            
            UnidadConversion conversion = new UnidadConversion(
                celsius,
                resultadoExacto,
                TemperaturaConstants.CELSIUS,
                TemperaturaConstants.KELVIN,
                1.0
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
                "Error en la conversión de Celsius a Kelvin",
                ErrorConstants.TIPO_CONVERSION,
                celsius,
                TemperaturaConstants.CELSIUS,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
}
