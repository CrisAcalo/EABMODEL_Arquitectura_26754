using System;

namespace ConUni_CLIWEB_Rest.ec.edu.monster.models
{
    /// <summary>
    /// Modelo para errores de conversión
    /// </summary>
    public class ErrorConversion
    {
        public string CodigoError { get; set; }
        public string Mensaje { get; set; }
        public string TipoError { get; set; }
        public double? ValorProblematico { get; set; }
        public string Unidad { get; set; }
        public DateTime FechaError { get; set; }
        public string Detalles { get; set; }

        public ErrorConversion()
        {
            CodigoError = string.Empty;
            Mensaje = string.Empty;
            TipoError = string.Empty;
            Unidad = string.Empty;
            Detalles = string.Empty;
            FechaError = DateTime.Now;
        }
    }
}