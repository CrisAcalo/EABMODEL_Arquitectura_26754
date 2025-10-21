using System;
using System.Globalization;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.constants;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.models;

namespace ConUni_Soap_DotNet_GR01.ec.edu.monster.validators
{
    /// <summary>
    /// Validador para conversi�n de strings a n�meros
    /// </summary>
    public static class StringToNumberValidator
    {
        /// <summary>
        /// Convierte string a double con validaci�n completa
        /// </summary>
        /// <param name="valorString">Valor como string</param>
        /// <param name="unidad">Unidad para mensajes de error</param>
        /// <param name="valor">Valor convertido (output)</param>
        /// <returns>ConversionError si hay error, null si es v�lido</returns>
        public static ConversionError ValidarYConvertirADouble(string valorString, string unidad, out double valor)
        {
            valor = 0.0;

            // Validar string vac�o o null
            if (string.IsNullOrWhiteSpace(valorString))
            {
                return new ConversionError(
                    ErrorConstants.VALOR_VACIO,
                    $"El valor en {unidad} no puede estar vac�o",
                    ErrorConstants.TIPO_VALIDACION,
                    null,
                    unidad,
                    "Debe proporcionar un valor num�rico v�lido."
                );
            }

            // Limpiar el string (quitar espacios)
            valorString = valorString.Trim();

            // Intentar convertir a double con diferentes formatos
            if (!TryParseDouble(valorString, out valor))
            {
                return new ConversionError(
                    ErrorConstants.VALOR_NO_NUMERICO,
                    $"El valor '{valorString}' no es un n�mero v�lido para {unidad}",
                    ErrorConstants.TIPO_VALIDACION,
                    null,
                    unidad,
                    $"El valor '{valorString}' no puede ser convertido a un n�mero. Use formato decimal con punto (.) como separador."
                );
            }

            // Validar que no sea NaN o infinito
            var errorNumero = BaseValidator.ValidarNumeroValido(valor, unidad);
            if (errorNumero != null)
                return errorNumero;

            return null; // Conversi�n exitosa
        }

        /// <summary>
        /// Intenta parsear un string a double usando m�ltiples formatos
        /// </summary>
        private static bool TryParseDouble(string input, out double result)
        {
            result = 0.0;

            // Intentar con cultura invariante (punto como decimal)
            if (double.TryParse(input, NumberStyles.Float, CultureInfo.InvariantCulture, out result))
                return true;

            // Intentar con cultura actual (coma como decimal en espa�ol)
            if (double.TryParse(input, NumberStyles.Float, CultureInfo.CurrentCulture, out result))
                return true;

            // Intentar reemplazando coma por punto
            string inputWithDot = input.Replace(',', '.');
            if (double.TryParse(inputWithDot, NumberStyles.Float, CultureInfo.InvariantCulture, out result))
                return true;

            // Intentar reemplazando punto por coma
            string inputWithComma = input.Replace('.', ',');
            if (double.TryParse(inputWithComma, NumberStyles.Float, CultureInfo.CurrentCulture, out result))
                return true;

            return false;
        }
    }

    /// <summary>
    /// Clase base para validaciones comunes
    /// </summary>
    public static class BaseValidator
    {
        /// <summary>
        /// Valida que el n�mero sea v�lido (no NaN ni infinito)
        /// </summary>
        /// <param name="valor">Valor a validar</param>
        /// <param name="unidad">Nombre de la unidad para el mensaje de error</param>
        /// <returns>ConversionError si hay error, null si es v�lido</returns>
        public static ConversionError ValidarNumeroValido(double valor, string unidad)
        {
            if (double.IsNaN(valor))
            {
                return new ConversionError(
                    ErrorConstants.VALOR_NAN,
                    $"{ErrorConstants.MSG_VALOR_NAN} en {unidad}",
                    ErrorConstants.TIPO_VALIDACION,
                    valor,
                    unidad,
                    "El valor proporcionado no representa un n�mero v�lido."
                );
            }

            if (double.IsInfinity(valor))
            {
                return new ConversionError(
                    ErrorConstants.VALOR_INFINITO,
                    $"{ErrorConstants.MSG_VALOR_INFINITO} en {unidad}",
                    ErrorConstants.TIPO_VALIDACION,
                    valor,
                    unidad,
                    "El valor proporcionado es infinito, lo cual no es permitido."
                );
            }

            return null; // Sin errores
        }

        /// <summary>
        /// Valida que el valor sea positivo o cero
        /// </summary>
        /// <param name="valor">Valor a validar</param>
        /// <param name="unidad">Nombre de la unidad para el mensaje de error</param>
        /// <returns>ConversionError si hay error, null si es v�lido</returns>
        public static ConversionError ValidarValorPositivo(double valor, string unidad)
        {
            // Primero validar que sea un n�mero v�lido
            var errorNumero = ValidarNumeroValido(valor, unidad);
            if (errorNumero != null)
                return errorNumero;

            // Luego validar que sea positivo
            if (valor < 0)
            {
                return new ConversionError(
                    ErrorConstants.VALOR_NEGATIVO,
                    $"{ErrorConstants.MSG_VALOR_NEGATIVO} en {unidad}",
                    ErrorConstants.TIPO_VALIDACION,
                    valor,
                    unidad,
                    $"Las medidas de {unidad.ToLower()} deben ser valores positivos o cero."
                );
            }

            return null; // Sin errores
        }

        /// <summary>
        /// Valida string y convierte a double positivo
        /// </summary>
        /// <param name="valorString">Valor como string</param>
        /// <param name="unidad">Unidad para mensajes</param>
        /// <param name="valor">Valor convertido (output)</param>
        /// <returns>ConversionError si hay error, null si es v�lido</returns>
        public static ConversionError ValidarStringPositivo(string valorString, string unidad, out double valor)
        {
            // Primero convertir string a double
            var errorConversion = StringToNumberValidator.ValidarYConvertirADouble(valorString, unidad, out valor);
            if (errorConversion != null)
                return errorConversion;

            // Luego validar que sea positivo
            return ValidarValorPositivo(valor, unidad);
        }
    }

    /// <summary>
    /// Validador espec�fico para temperaturas
    /// </summary>
    public static class TemperaturaValidator
    {
        /// <summary>
        /// Valida string y convierte a temperatura Celsius v�lida
        /// </summary>
        /// <param name="celsiusString">Temperatura como string</param>
        /// <param name="celsius">Temperatura convertida (output)</param>
        /// <returns>ConversionError si hay error, null si es v�lido</returns>
        public static ConversionError ValidarStringTemperaturaCelsius(string celsiusString, out double celsius)
        {
            // Primero convertir string a double
            var errorConversion = StringToNumberValidator.ValidarYConvertirADouble(celsiusString, TemperaturaConstants.CELSIUS, out celsius);
            if (errorConversion != null)
                return errorConversion;

            // Luego validar l�mites de temperatura
            return ValidarTemperaturaCelsius(celsius);
        }

        /// <summary>
        /// Valida string y convierte a temperatura Fahrenheit v�lida
        /// </summary>
        /// <param name="fahrenheitString">Temperatura como string</param>
        /// <param name="fahrenheit">Temperatura convertida (output)</param>
        /// <returns>ConversionError si hay error, null si es v�lido</returns>
        public static ConversionError ValidarStringTemperaturaFahrenheit(string fahrenheitString, out double fahrenheit)
        {
            // Primero convertir string a double
            var errorConversion = StringToNumberValidator.ValidarYConvertirADouble(fahrenheitString, TemperaturaConstants.FAHRENHEIT, out fahrenheit);
            if (errorConversion != null)
                return errorConversion;

            // Luego validar l�mites de temperatura
            return ValidarTemperaturaFahrenheit(fahrenheit);
        }

        /// <summary>
        /// Valida string y convierte a temperatura Kelvin v�lida
        /// </summary>
        /// <param name="kelvinString">Temperatura como string</param>
        /// <param name="kelvin">Temperatura convertida (output)</param>
        /// <returns>ConversionError si hay error, null si es v�lido</returns>
        public static ConversionError ValidarStringTemperaturaKelvin(string kelvinString, out double kelvin)
        {
            // Primero convertir string a double
            var errorConversion = StringToNumberValidator.ValidarYConvertirADouble(kelvinString, TemperaturaConstants.KELVIN, out kelvin);
            if (errorConversion != null)
                return errorConversion;

            // Luego validar l�mites de temperatura
            return ValidarTemperaturaKelvin(kelvin);
        }

        /// <summary>
        /// Valida que la temperatura en Celsius sea v�lida
        /// </summary>
        /// <param name="celsius">Temperatura en Celsius</param>
        /// <returns>ConversionError si hay error, null si es v�lido</returns>
        public static ConversionError ValidarTemperaturaCelsius(double celsius)
        {
            // Primero validar que sea un n�mero v�lido
            var errorNumero = BaseValidator.ValidarNumeroValido(celsius, TemperaturaConstants.CELSIUS);
            if (errorNumero != null)
                return errorNumero;

            // Validar l�mite del cero absoluto
            if (celsius < TemperaturaConstants.CERO_ABSOLUTO_CELSIUS)
            {
                return new ConversionError(
                    ErrorConstants.TEMPERATURA_BAJO_CERO_ABSOLUTO,
                    $"{ErrorConstants.MSG_TEMPERATURA_INVALIDA} para Celsius",
                    ErrorConstants.TIPO_VALIDACION,
                    celsius,
                    TemperaturaConstants.CELSIUS,
                    $"La temperatura m�nima posible es {TemperaturaConstants.CERO_ABSOLUTO_CELSIUS}�C (cero absoluto)."
                );
            }

            return null; // Sin errores
        }

        /// <summary>
        /// Valida que la temperatura en Fahrenheit sea v�lida
        /// </summary>
        /// <param name="fahrenheit">Temperatura en Fahrenheit</param>
        /// <returns>ConversionError si hay error, null si es v�lido</returns>
        public static ConversionError ValidarTemperaturaFahrenheit(double fahrenheit)
        {
            // Primero validar que sea un n�mero v�lido
            var errorNumero = BaseValidator.ValidarNumeroValido(fahrenheit, TemperaturaConstants.FAHRENHEIT);
            if (errorNumero != null)
                return errorNumero;

            // Validar l�mite del cero absoluto
            if (fahrenheit < TemperaturaConstants.CERO_ABSOLUTO_FAHRENHEIT)
            {
                return new ConversionError(
                    ErrorConstants.TEMPERATURA_BAJO_CERO_ABSOLUTO,
                    $"{ErrorConstants.MSG_TEMPERATURA_INVALIDA} para Fahrenheit",
                    ErrorConstants.TIPO_VALIDACION,
                    fahrenheit,
                    TemperaturaConstants.FAHRENHEIT,
                    $"La temperatura m�nima posible es {TemperaturaConstants.CERO_ABSOLUTO_FAHRENHEIT}�F (cero absoluto)."
                );
            }

            return null; // Sin errores
        }

        /// <summary>
        /// Valida que la temperatura en Kelvin sea v�lida
        /// </summary>
        /// <param name="kelvin">Temperatura en Kelvin</param>
        /// <returns>ConversionError si hay error, null si es v�lido</returns>
        public static ConversionError ValidarTemperaturaKelvin(double kelvin)
        {
            // Primero validar que sea un n�mero v�lido
            var errorNumero = BaseValidator.ValidarNumeroValido(kelvin, TemperaturaConstants.KELVIN);
            if (errorNumero != null)
                return errorNumero;

            // Validar l�mite del cero absoluto
            if (kelvin < TemperaturaConstants.CERO_ABSOLUTO_KELVIN)
            {
                return new ConversionError(
                    ErrorConstants.TEMPERATURA_BAJO_CERO_ABSOLUTO,
                    $"{ErrorConstants.MSG_TEMPERATURA_INVALIDA} para Kelvin",
                    ErrorConstants.TIPO_VALIDACION,
                    kelvin,
                    TemperaturaConstants.KELVIN,
                    $"La temperatura m�nima posible es {TemperaturaConstants.CERO_ABSOLUTO_KELVIN}K (cero absoluto)."
                );
            }

            return null; // Sin errores
        }
    }
}