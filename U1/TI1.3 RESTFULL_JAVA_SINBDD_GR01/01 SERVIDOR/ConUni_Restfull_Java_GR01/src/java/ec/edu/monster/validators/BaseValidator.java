/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.validators;

import ec.edu.monster.constants.ErrorConstants;
import ec.edu.monster.models.ConversionErrorModel;

/**
 *
 * @author jeffe
 */
public class BaseValidator {
    private BaseValidator() {}

    public static void validarNumeroValido(double valor, String unidad) throws ValidationException {
        if (Double.isNaN(valor)) {
            throw new ValidationException(new ConversionErrorModel(
                ErrorConstants.VALOR_NAN,
                ErrorConstants.MSG_VALOR_NAN + " en " + unidad,
                ErrorConstants.TIPO_VALIDACION,
                valor,
                unidad,
                "El valor proporcionado no representa un número válido."
            ));
        }
        if (Double.isInfinite(valor)) {
            throw new ValidationException(new ConversionErrorModel(
                ErrorConstants.VALOR_INFINITO,
                ErrorConstants.MSG_VALOR_INFINITO + " en " + unidad,
                ErrorConstants.TIPO_VALIDACION,
                valor,
                unidad,
                "El valor proporcionado es infinito, lo cual no es permitido."
            ));
        }
    }

    public static void validarValorPositivo(double valor, String unidad) throws ValidationException {
        validarNumeroValido(valor, unidad);
        if (valor < 0) {
            throw new ValidationException(new ConversionErrorModel(
                ErrorConstants.VALOR_NEGATIVO,
                ErrorConstants.MSG_VALOR_NEGATIVO + " en " + unidad,
                ErrorConstants.TIPO_VALIDACION,
                valor,
                unidad,
                "Las medidas de " + unidad.toLowerCase() + " deben ser valores positivos o cero."
            ));
        }
    }
    
}
