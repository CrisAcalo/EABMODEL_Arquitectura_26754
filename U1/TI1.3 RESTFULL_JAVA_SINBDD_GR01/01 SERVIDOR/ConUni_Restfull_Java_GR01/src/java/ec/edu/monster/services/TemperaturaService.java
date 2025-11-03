/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.services;

import ec.edu.monster.constants.ConversionConstants.Temperatura;
import ec.edu.monster.models.ConversionResultModel;
import ec.edu.monster.models.UnidadConversionModel;
import ec.edu.monster.validators.TemperaturaValidator;
import ec.edu.monster.validators.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Servicio para conversiones de unidades de temperatura
 * Soporta conversiones entre Celsius, Fahrenheit y Kelvin
 *
 * Fórmulas utilizadas:
 * - Celsius a Fahrenheit: °F = (°C × 9/5) + 32
 * - Fahrenheit a Celsius: °C = (°F - 32) × 5/9
 * - Celsius a Kelvin: K = °C + 273.15
 * - Kelvin a Celsius: °C = K - 273.15
 * - Fahrenheit a Kelvin: K = (°F - 32) × 5/9 + 273.15
 * - Kelvin a Fahrenheit: °F = (K - 273.15) × 9/5 + 32
 *
 * @author jeffe
 */
@ApplicationScoped
public class TemperaturaService {

    /**
     * Convierte Celsius a Fahrenheit
     * Fórmula: °F = (°C × 9/5) + 32
     * @param celsius Valor en grados Celsius
     * @return ConversionResultModel con el resultado o error
     */
    public ConversionResultModel convertirCelsiusAFahrenheit(double celsius) {
        try {
            TemperaturaValidator.validarTemperaturaCelsius(celsius);
            double fahrenheit = (celsius * Temperatura.CELSIUS_FAHRENHEIT_FACTOR) + Temperatura.FAHRENHEIT_CELSIUS_OFFSET;
            // El factor usado es una combinación de la fórmula completa
            var resultado = new UnidadConversionModel(celsius, fahrenheit, Temperatura.CELSIUS, Temperatura.FAHRENHEIT, "Temperatura", Temperatura.CELSIUS_FAHRENHEIT_FACTOR);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    /**
     * Convierte Fahrenheit a Celsius
     * Fórmula: °C = (°F - 32) × 5/9
     * @param fahrenheit Valor en grados Fahrenheit
     * @return ConversionResultModel con el resultado o error
     */
    public ConversionResultModel convertirFahrenheitACelsius(double fahrenheit) {
        try {
            TemperaturaValidator.validarTemperaturaFahrenheit(fahrenheit);
            double celsius = (fahrenheit - Temperatura.FAHRENHEIT_CELSIUS_OFFSET) * Temperatura.FAHRENHEIT_CELSIUS_FACTOR;
            var resultado = new UnidadConversionModel(fahrenheit, celsius, Temperatura.FAHRENHEIT, Temperatura.CELSIUS, "Temperatura", Temperatura.FAHRENHEIT_CELSIUS_FACTOR);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    /**
     * Convierte Celsius a Kelvin
     * Fórmula: K = °C + 273.15
     * @param celsius Valor en grados Celsius
     * @return ConversionResultModel con el resultado o error
     */
    public ConversionResultModel convertirCelsiusAKelvin(double celsius) {
        try {
            TemperaturaValidator.validarTemperaturaCelsius(celsius);
            double kelvin = celsius + Temperatura.CELSIUS_KELVIN_OFFSET;
            var resultado = new UnidadConversionModel(celsius, kelvin, Temperatura.CELSIUS, Temperatura.KELVIN, "Temperatura", Temperatura.CELSIUS_KELVIN_OFFSET);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    /**
     * Convierte Kelvin a Celsius
     * Fórmula: °C = K - 273.15
     * @param kelvin Valor en Kelvin
     * @return ConversionResultModel con el resultado o error
     */
    public ConversionResultModel convertirKelvinACelsius(double kelvin) {
        try {
            TemperaturaValidator.validarTemperaturaKelvin(kelvin);
            double celsius = kelvin - Temperatura.CELSIUS_KELVIN_OFFSET;
            var resultado = new UnidadConversionModel(kelvin, celsius, Temperatura.KELVIN, Temperatura.CELSIUS, "Temperatura", -Temperatura.CELSIUS_KELVIN_OFFSET);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    /**
     * Convierte Fahrenheit a Kelvin
     * Fórmula: K = (°F - 32) × 5/9 + 273.15
     * @param fahrenheit Valor en grados Fahrenheit
     * @return ConversionResultModel con el resultado o error
     */
    public ConversionResultModel convertirFahrenheitAKelvin(double fahrenheit) {
        try {
            TemperaturaValidator.validarTemperaturaFahrenheit(fahrenheit);
            double kelvin = ((fahrenheit - Temperatura.FAHRENHEIT_CELSIUS_OFFSET) * Temperatura.FAHRENHEIT_CELSIUS_FACTOR) + Temperatura.CELSIUS_KELVIN_OFFSET;
            var resultado = new UnidadConversionModel(fahrenheit, kelvin, Temperatura.FAHRENHEIT, Temperatura.KELVIN, "Temperatura", Temperatura.FAHRENHEIT_CELSIUS_FACTOR);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    /**
     * Convierte Kelvin a Fahrenheit
     * Fórmula: °F = (K - 273.15) × 9/5 + 32
     * @param kelvin Valor en Kelvin
     * @return ConversionResultModel con el resultado o error
     */
    public ConversionResultModel convertirKelvinAFahrenheit(double kelvin) {
        try {
            TemperaturaValidator.validarTemperaturaKelvin(kelvin);
            double fahrenheit = ((kelvin - Temperatura.CELSIUS_KELVIN_OFFSET) * Temperatura.CELSIUS_FAHRENHEIT_FACTOR) + Temperatura.FAHRENHEIT_CELSIUS_OFFSET;
            var resultado = new UnidadConversionModel(kelvin, fahrenheit, Temperatura.KELVIN, Temperatura.FAHRENHEIT, "Temperatura", Temperatura.CELSIUS_FAHRENHEIT_FACTOR);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }
}
