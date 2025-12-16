using EurekaBank_RestFull_DotNet_GR01.Models.DTOs;
using EurekaBank_RestFull_DotNet_GR01.Constants;
using Microsoft.AspNetCore.Mvc.ModelBinding;
using System.Linq;

namespace EurekaBank_RestFull_DotNet_GR01.Helpers
{
    /// <summary>
    /// Helper para crear respuestas estandarizadas
    /// </summary>
    public static class RespuestaHelper
    {
        /// <summary>
        /// Crea una respuesta de error estandarizada
        /// </summary>
        /// <param name="codigoError">Código de error</param>
        /// <param name="mensaje">Mensaje descriptivo</param>
        /// <param name="datos">Datos adicionales (opcional)</param>
        /// <returns>RespuestaDTO con error</returns>
        public static RespuestaDTO CrearError(string codigoError, string mensaje, object datos = null)
        {
            return new RespuestaDTO
            {
                Exitoso = false,
                CodigoError = codigoError,
                Mensaje = mensaje,
                Datos = datos
            };
        }

        /// <summary>
        /// Crea una respuesta de éxito estandarizada
        /// </summary>
        /// <param name="mensaje">Mensaje descriptivo</param>
        /// <param name="datos">Datos de respuesta (opcional)</param>
        /// <returns>RespuestaDTO exitosa</returns>
        public static RespuestaDTO CrearExito(string mensaje, object datos = null)
        {
            return new RespuestaDTO
            {
                Exitoso = true,
                Mensaje = mensaje,
                Datos = datos
            };
        }

        /// <summary>
        /// Crea una respuesta de error de validación a partir del ModelState
        /// </summary>
        /// <param name="modelState">Estado del modelo con errores</param>
        /// <returns>RespuestaDTO con errores de validación</returns>
        public static RespuestaDTO CrearErrorValidacion(ModelStateDictionary modelState)
        {
            var errores = modelState
                .Where(ms => ms.Value.Errors.Count > 0)
                .ToDictionary(
                    kvp => kvp.Key,
                    kvp => kvp.Value.Errors.Select(e => e.ErrorMessage).ToArray()
                );

            var mensajeCompleto = string.Join("; ", 
                errores.SelectMany(e => e.Value.Select(msg => $"{e.Key}: {msg}"))
            );

            return new RespuestaDTO
            {
                Exitoso = false,
                CodigoError = CodigosErrorConstants.VALIDACION_CAMPOS_REQUERIDOS,
                Mensaje = $"{MensajesConstants.ERROR_CAMPOS_REQUERIDOS}: {mensajeCompleto}",
                Datos = errores
            };
        }

        /// <summary>
        /// Crea una respuesta de error de validación con lista de errores personalizada
        /// </summary>
        /// <param name="errores">Lista de errores</param>
        /// <returns>RespuestaDTO con errores de validación</returns>
        public static RespuestaDTO CrearErrorValidacion(IEnumerable<string> errores)
        {
            return CrearError(
                CodigosErrorConstants.VALIDACION_CAMPOS_REQUERIDOS,
                $"{MensajesConstants.ERROR_MODELO_INVALIDO}: {string.Join(", ", errores)}"
            );
        }

        /// <summary>
        /// Crea una respuesta de recurso no encontrado
        /// </summary>
        /// <param name="recurso">Nombre del recurso</param>
        /// <returns>RespuestaDTO de recurso no encontrado</returns>
        public static RespuestaDTO CrearRecursoNoEncontrado(string recurso = null)
        {
            var mensaje = string.IsNullOrEmpty(recurso) 
                ? MensajesConstants.ERROR_RECURSO_NO_ENCONTRADO
                : $"{recurso} no encontrado";

            return CrearError(CodigosErrorConstants.RECURSO_NO_ENCONTRADO, mensaje);
        }

        /// <summary>
        /// Crea una respuesta de error del servidor
        /// </summary>
        /// <param name="excepcion">Excepción ocurrida</param>
        /// <param name="incluirDetalle">Si incluir el detalle de la excepción</param>
        /// <returns>RespuestaDTO de error del servidor</returns>
        public static RespuestaDTO CrearErrorServidor(Exception excepcion = null, bool incluirDetalle = false)
        {
            var mensaje = MensajesConstants.ERROR_SERVIDOR_INTERNO;
            
            if (incluirDetalle && excepcion != null)
            {
                mensaje += $": {excepcion.Message}";
            }

            return CrearError(CodigosErrorConstants.SERVIDOR_ERROR_INTERNO, mensaje);
        }

        /// <summary>
        /// Crea una respuesta de error de base de datos
        /// </summary>
        /// <param name="excepcion">Excepción de base de datos</param>
        /// <returns>RespuestaDTO de error de BD</returns>
        public static RespuestaDTO CrearErrorBaseDatos(Exception excepcion = null)
        {
            var mensaje = MensajesConstants.ERROR_BASE_DATOS;
            
            if (excepcion != null)
            {
                mensaje += $": {excepcion.Message}";
            }

            return CrearError(CodigosErrorConstants.SERVIDOR_BASE_DATOS, mensaje);
        }

        /// <summary>
        /// Crea una respuesta de datos nulos o requeridos
        /// </summary>
        /// <param name="campo">Campo que es nulo o requerido</param>
        /// <returns>RespuestaDTO de datos requeridos</returns>
        public static RespuestaDTO CrearDatosRequeridos(string campo = null)
        {
            var mensaje = string.IsNullOrEmpty(campo)
                ? MensajesConstants.ERROR_DATOS_NULOS
                : $"El campo '{campo}' es requerido";

            return CrearError(CodigosErrorConstants.VALIDACION_DATOS_NULOS, mensaje);
        }

        /// <summary>
        /// Crea una respuesta de código inválido
        /// </summary>
        /// <param name="tipoRecurso">Tipo de recurso (ej: sucursal, empleado)</param>
        /// <returns>RespuestaDTO de código inválido</returns>
        public static RespuestaDTO CrearCodigoInvalido(string tipoRecurso = "recurso")
        {
            return CrearError(
                CodigosErrorConstants.VALIDACION_FORMATO_INVALIDO,
                $"El código de {tipoRecurso} debe ser mayor a cero"
            );
        }

        /// <summary>
        /// Crea una respuesta de rango inválido
        /// </summary>
        /// <param name="campo">Campo con rango inválido</param>
        /// <param name="min">Valor mínimo</param>
        /// <param name="max">Valor máximo</param>
        /// <returns>RespuestaDTO de rango inválido</returns>
        public static RespuestaDTO CrearRangoInvalido(string campo, object min, object max)
        {
            return CrearError(
                CodigosErrorConstants.VALIDACION_RANGO_INVALIDO,
                $"{campo} debe estar entre {min} y {max}"
            );
        }
    }
}