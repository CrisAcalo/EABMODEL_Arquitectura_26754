/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.validators;

import ec.edu.monster.constants.ConversionConstants.Temperatura;
import ec.edu.monster.constants.ErrorConstants;
import ec.edu.monster.models.ConversionErrorModel;

/**
 *
 * @author jeffe
 */
public class TemperaturaValidator {
    private TemperaturaValidator() {}

    public static void validarTemperaturaCelsius(double celsius) throws ValidationException {
        BaseValidator.validarNumeroValido(celsius, Temperatura.CELSIUS);
        if (celsius < Temperatura.CERO_ABSOLUTO_CELSIUS) {
            throw new ValidationException(new ConversionErrorModel(
                ErrorConstants.TEMPERATURA_BAJO_CERO_ABSOLUTO,
                ErrorConstants.MSG_TEMPERATURA_INVALIDA + " para Celsius",
                ErrorConstants.TIPO_VALIDACION,
                celsius,
                Temperatura.CELSIUS,
                "La temperatura mínima posible es " + Temperatura.CERO_ABSOLUTO_CELSIUS + "°C (cero absoluto)."
            ));
        }
    }

    public static void validarTemperaturaFahrenheit(double fahrenheit) throws ValidationException {
        BaseValidator.validarNumeroValido(fahrenheit, Temperatura.FAHRENHEIT);
        if (fahrenheit < Temperatura.CERO_ABSOLUTO_FAHRENHEIT) {
             throw new ValidationException(new ConversionErrorModel(
                ErrorConstants.TEMPERATURA_BAJO_CERO_ABSOLUTO,
                ErrorConstants.MSG_TEMPERATURA_INVALIDA + " para Fahrenheit",
                ErrorConstants.TIPO_VALIDACION,
                fahrenheit,
                Temperatura.FAHRENHEIT,
                "La temperatura mínima posible es " + Temperatura.CERO_ABSOLUTO_FAHRENHEIT + "°F (cero absoluto)."
            ));
        }
    }

    public static void validarTemperaturaKelvin(double kelvin) throws ValidationException {
        BaseValidator.validarNumeroValido(kelvin, Temperatura.KELVIN);
        if (kelvin < Temperatura.CERO_ABSOLUTO_KELVIN) {
            throw new ValidationException(new ConversionErrorModel(
                ErrorConstants.TEMPERATURA_BAJO_CERO_ABSOLUTO,
                ErrorConstants.MSG_TEMPERATURA_INVALIDA + " para Kelvin",
                ErrorConstants.TIPO_VALIDACION,
                kelvin,
                Temperatura.KELVIN,
                "La temperatura mínima posible es " + Temperatura.CERO_ABSOLUTO_KELVIN + "K (cero absoluto)."
            ));
        }
    }
    
}
