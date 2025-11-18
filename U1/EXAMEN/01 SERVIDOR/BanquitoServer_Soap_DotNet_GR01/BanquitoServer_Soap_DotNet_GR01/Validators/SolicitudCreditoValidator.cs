using System;
using System.Globalization;
using System.Text.RegularExpressions;
using BanquitoServer_Soap_DotNet_GR01.Constants;
using BanquitoServer_Soap_DotNet_GR01.DTOs;

namespace BanquitoServer_Soap_DotNet_GR01.Validators
{
    /// <summary>
    /// Validador para SolicitudCreditoDTO
    /// </summary>
    public static class SolicitudCreditoValidator
    {
        /// <summary>
        /// Validar una solicitud de crédito y convertir valores
        /// </summary>
        /// <param name="solicitud">Solicitud a validar</param>
        /// <param name="precio">Precio convertido (out)</param>
        /// <param name="cuotas">Número de cuotas convertido (out)</param>
        /// <param name="mensajeError">Mensaje de error si la validación falla</param>
        /// <returns>True si es válida, False si no</returns>
        public static bool ValidarYConvertir(SolicitudCreditoDTO solicitud, out decimal precio, out int cuotas, out string mensajeError)
        {
            precio = 0;
            cuotas = 0;
            mensajeError = string.Empty;

            // Validar que la solicitud no sea nula
            if (solicitud == null)
            {
                mensajeError = ErrorMessages.SolicitudNula;
                return false;
            }

            // Validar cédula no vacía
            if (string.IsNullOrWhiteSpace(solicitud.Cedula))
            {
                mensajeError = ErrorMessages.CedulaRequerida;
                return false;
            }

            // Validar formato de cédula (10 dígitos)
            if (!Regex.IsMatch(solicitud.Cedula, @"^\d{10}$"))
            {
                mensajeError = ErrorMessages.CedulaInvalida;
                return false;
            }

            // Validar y convertir precio del electrodoméstico
            if (string.IsNullOrWhiteSpace(solicitud.PrecioElectrodomestico))
            {
                mensajeError = ErrorMessages.PrecioInvalido;
                return false;
            }

            if (!decimal.TryParse(solicitud.PrecioElectrodomestico, NumberStyles.Any, CultureInfo.InvariantCulture, out precio))
            {
                mensajeError = ErrorMessages.PrecioInvalido;
                return false;
            }

            if (precio <= 0)
            {
                mensajeError = ErrorMessages.PrecioInvalido;
                return false;
            }

            // Validar y convertir número de cuotas
            if (string.IsNullOrWhiteSpace(solicitud.NumeroCuotas))
            {
                mensajeError = ErrorMessages.NumeroCuotasInvalido;
                return false;
            }

            if (!int.TryParse(solicitud.NumeroCuotas, out cuotas))
            {
                mensajeError = ErrorMessages.NumeroCuotasInvalido;
                return false;
            }

            if (cuotas <= 0)
            {
                mensajeError = ErrorMessages.NumeroCuotasInvalido;
                return false;
            }

            return true;
        }
    }
}
