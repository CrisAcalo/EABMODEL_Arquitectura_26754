namespace BanquitoServer_Soap_DotNet_GR01.Constants
{
    /// <summary>
    /// Mensajes de error estandarizados del sistema
    /// </summary>
    public static class ErrorMessages
    {
        // Errores de validación de entrada
        public const string SolicitudNula = "Error: La solicitud de crédito no puede ser nula";
        public const string CedulaRequerida = "Error: La cédula es requerida";
        public const string CedulaInvalida = "Error: La cédula debe tener 10 dígitos numéricos";
        public const string PrecioInvalido = "Error: El precio del electrodoméstico debe ser mayor a cero";
        public const string NumeroCuotasInvalido = "Error: El número de cuotas debe ser mayor a cero";

        // Errores de validación de negocio
        public const string ClienteNoEncontrado = "La persona no es cliente del banco";
        public const string SinDepositosRecientes = "El cliente no tiene depósitos en el último mes";
        public const string EdadMinimaCasado = "El cliente casado debe tener al menos 25 años. Edad actual: {0}";
        public const string CreditoActivo = "El cliente ya tiene un crédito activo";
        public const string PlazoInvalido = "El número de cuotas debe estar entre {0} y {1}";
        public const string MontoSuperaMaximo = "El monto solicitado (${0}) supera el máximo autorizado (${1})";

        // Mensajes de éxito
        public const string CreditoOtorgado = "Crédito otorgado exitosamente";
        public const string MontoMaximoCalculado = "Monto máximo calculado exitosamente";
        public const string ClienteValido = "El cliente es sujeto de crédito";

        // Errores internos
        public const string ErrorInterno = "Error interno: {0}";
    }
}
