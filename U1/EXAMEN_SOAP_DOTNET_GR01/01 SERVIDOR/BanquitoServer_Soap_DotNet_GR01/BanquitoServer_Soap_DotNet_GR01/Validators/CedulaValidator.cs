using System.Text.RegularExpressions;
using BanquitoServer_Soap_DotNet_GR01.Constants;

namespace BanquitoServer_Soap_DotNet_GR01.Validators
{
    /// <summary>
    /// Validador para cédulas ecuatorianas
    /// </summary>
    public static class CedulaValidator
    {
        /// <summary>
        /// Validar que la cédula tenga el formato correcto
        /// </summary>
        /// <param name="cedula">Cédula a validar</param>
        /// <param name="mensajeError">Mensaje de error si la validación falla</param>
        /// <returns>True si es válida, False si no</returns>
        public static bool Validar(string cedula, out string mensajeError)
        {
            mensajeError = string.Empty;

            // Validar que no sea nula o vacía
            if (string.IsNullOrWhiteSpace(cedula))
            {
                mensajeError = ErrorMessages.CedulaRequerida;
                return false;
            }

            // Validar formato (10 dígitos)
            if (!Regex.IsMatch(cedula, @"^\d{10}$"))
            {
                mensajeError = ErrorMessages.CedulaInvalida;
                return false;
            }

            return true;
        }
    }
}
