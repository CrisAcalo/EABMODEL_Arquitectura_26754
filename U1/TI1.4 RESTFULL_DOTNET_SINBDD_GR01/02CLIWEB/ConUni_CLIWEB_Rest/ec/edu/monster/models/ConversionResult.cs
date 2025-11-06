using System;

namespace ConUni_CLIWEB_Rest.ec.edu.monster.models
{
    /// <summary>
    /// Modelo para el resultado de una conversión REST
    /// </summary>
    public class ConversionResult
    {
        public bool Exitoso { get; set; }
        public ResultadoConversion Resultado { get; set; }
        public ErrorConversion Error { get; set; }

        public ConversionResult()
        {
            Exitoso = false;
            Resultado = null;
            Error = null;
        }
    }
}