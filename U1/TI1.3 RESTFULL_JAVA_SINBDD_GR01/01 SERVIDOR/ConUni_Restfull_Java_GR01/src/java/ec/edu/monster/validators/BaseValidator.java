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

    /**
     * Convierte string a double con validación completa
     * @param valorString Valor como string
     * @param unidad Unidad para mensajes de error
     * @return double convertido
     * @throws ValidationException si hay error en la conversión
     */
    public static double validarYConvertirADouble(String valorString, String unidad) throws ValidationException {
        // Validar string vacío o null
        if (valorString == null || valorString.trim().isEmpty()) {
            throw new ValidationException(new ConversionErrorModel(
                ErrorConstants.VALOR_VACIO,
                "El valor en " + unidad + " no puede estar vacío",
                ErrorConstants.TIPO_VALIDACION,
                null,
                unidad,
                "Debe proporcionar un valor numérico válido."
            ));
        }

        // Limpiar el string (quitar espacios)
        valorString = valorString.trim();

        // Intentar convertir a double con diferentes formatos
        double valor;
        try {
            valor = tryParseDouble(valorString);
        } catch (NumberFormatException e) {
            throw new ValidationException(new ConversionErrorModel(
                ErrorConstants.VALOR_NO_NUMERICO,
                "El valor '" + valorString + "' no es un número válido para " + unidad,
                ErrorConstants.TIPO_VALIDACION,
                null,
                unidad,
                "El valor '" + valorString + "' no puede ser convertido a un número. Use formato decimal con punto (.) como separador."
            ));
        }

        // Validar que no sea NaN o infinito
        validarNumeroValido(valor, unidad);

        return valor;
    }

    /**
     * Valida string y convierte a double positivo
     * @param valorString Valor como string
     * @param unidad Unidad para mensajes
     * @return double convertido y validado
     * @throws ValidationException si hay error
     */
    public static double validarStringPositivo(String valorString, String unidad) throws ValidationException {
        // Primero convertir string a double
        double valor = validarYConvertirADouble(valorString, unidad);

        // Luego validar que sea positivo
        validarValorPositivo(valor, unidad);

        return valor;
    }

    /**
     * Intenta parsear un string a double usando múltiples formatos
     * @param input String a convertir
     * @return double parseado
     * @throws NumberFormatException si no se puede convertir
     */
    private static double tryParseDouble(String input) throws NumberFormatException {
        // Intentar parseo directo
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e1) {
            // Intentar reemplazando coma por punto (formato europeo)
            try {
                String inputWithDot = input.replace(',', '.');
                return Double.parseDouble(inputWithDot);
            } catch (NumberFormatException e2) {
                // Si ambos fallan, lanzar excepción
                throw new NumberFormatException("Cannot parse: " + input);
            }
        }
    }

}
