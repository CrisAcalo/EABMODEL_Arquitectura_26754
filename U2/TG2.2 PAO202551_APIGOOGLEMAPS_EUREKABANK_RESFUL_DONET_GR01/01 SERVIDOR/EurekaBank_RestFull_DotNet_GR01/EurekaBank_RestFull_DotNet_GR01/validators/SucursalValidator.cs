using System;
using System.Text.RegularExpressions;
using EurekaBank_RestFull_DotNet_GR01.Models;

namespace EurekaBank_RestFull_DotNet_GR01.Validators
{
    /// <summary>
    /// Validador para operaciones relacionadas con sucursales
    /// </summary>
    public static class SucursalValidator
    {
        /// <summary>
        /// Verifica si una sucursal existe
        /// </summary>
        public static bool Existe(Sucursal sucursal)
        {
            return sucursal != null;
        }

        /// <summary>
        /// Valida que el código de sucursal sea válido (para códigos auto-generados)
        /// </summary>
        public static bool CodigoValido(int codigo)
        {
            return codigo > 0;
        }

        /// <summary>
        /// Valida que el nombre de la sucursal sea válido
        /// </summary>
        public static bool NombreValido(string nombre)
        {
            if (string.IsNullOrWhiteSpace(nombre))
                return false;

            return nombre.Trim().Length >= 3 && nombre.Trim().Length <= 50;
        }

        /// <summary>
        /// Valida que la ciudad sea válida
        /// </summary>
        public static bool CiudadValida(string ciudad)
        {
            if (string.IsNullOrWhiteSpace(ciudad))
                return false;

            return ciudad.Trim().Length >= 2 && ciudad.Trim().Length <= 30;
        }

        /// <summary>
        /// Valida que la dirección sea válida (opcional)
        /// </summary>
        public static bool DireccionValida(string direccion)
        {
            if (string.IsNullOrWhiteSpace(direccion))
                return true; // La dirección es opcional

            return direccion.Trim().Length <= 50;
        }

        /// <summary>
        /// Valida que las coordenadas de latitud sean válidas
        /// </summary>
        public static bool LatitudValida(decimal? latitud)
        {
            if (!latitud.HasValue)
                return true; // Las coordenadas son opcionales

            return latitud >= -90 && latitud <= 90;
        }

        /// <summary>
        /// Valida que las coordenadas de longitud sean válidas
        /// </summary>
        public static bool LongitudValida(decimal? longitud)
        {
            if (!longitud.HasValue)
                return true; // Las coordenadas son opcionales

            return longitud >= -180 && longitud <= 180;
        }

        /// <summary>
        /// Valida que las coordenadas en conjunto sean válidas
        /// </summary>
        public static bool CoordenadasValidas(decimal? latitud, decimal? longitud)
        {
            // Ambas deben ser nulas o ambas deben tener valor
            if (latitud.HasValue != longitud.HasValue)
                return false;

            return LatitudValida(latitud) && LongitudValida(longitud);
        }

        /// <summary>
        /// Valida que el contador de cuentas sea válido
        /// </summary>
        public static bool ContadorCuentasValido(int contadorCuentas)
        {
            return contadorCuentas >= 0;
        }

        /// <summary>
        /// Valida todos los datos de una sucursal para creación
        /// </summary>
        public static (bool EsValida, string MensajeError) ValidarParaCreacion(Sucursal sucursal)
        {
            if (sucursal == null)
                return (false, "La sucursal no puede ser nula");

            if (!NombreValido(sucursal.Nombre))
                return (false, "El nombre debe tener entre 3 y 50 caracteres");

            if (!CiudadValida(sucursal.Ciudad))
                return (false, "La ciudad debe tener entre 2 y 30 caracteres");

            if (!DireccionValida(sucursal.Direccion))
                return (false, "La dirección debe tener máximo 50 caracteres");

            if (!ContadorCuentasValido(sucursal.ContadorCuentas))
                return (false, "El contador de cuentas debe ser mayor o igual a 0");

            if (!CoordenadasValidas(sucursal.Latitud, sucursal.Longitud))
                return (false, "Las coordenadas no son válidas");

            return (true, string.Empty);
        }

        /// <summary>
        /// Valida todos los datos de una sucursal para actualización
        /// </summary>
        public static (bool EsValida, string MensajeError) ValidarParaActualizacion(Sucursal sucursal)
        {
            if (sucursal == null)
                return (false, "La sucursal no puede ser nula");

            if (!NombreValido(sucursal.Nombre))
                return (false, "El nombre debe tener entre 3 y 50 caracteres");

            if (!CiudadValida(sucursal.Ciudad))
                return (false, "La ciudad debe tener entre 2 y 30 caracteres");

            if (!DireccionValida(sucursal.Direccion))
                return (false, "La dirección debe tener máximo 50 caracteres");

            if (!ContadorCuentasValido(sucursal.ContadorCuentas))
                return (false, "El contador de cuentas debe ser mayor o igual a 0");

            if (!CoordenadasValidas(sucursal.Latitud, sucursal.Longitud))
                return (false, "Las coordenadas no son válidas");

            return (true, string.Empty);
        }

        /// <summary>
        /// Verifica si una sucursal puede ser eliminada (no tiene cuentas asociadas)
        /// </summary>
        public static bool PuedeSerEliminada(Sucursal sucursal)
        {
            return sucursal != null && sucursal.ContadorCuentas == 0;
        }

        /// <summary>
        /// Verifica si dos sucursales son la misma (mismo código)
        /// </summary>
        public static bool SonLaMisma(Sucursal sucursal1, Sucursal sucursal2)
        {
            return sucursal1 != null && 
                   sucursal2 != null && 
                   sucursal1.Codigo == sucursal2.Codigo;
        }
    }
}