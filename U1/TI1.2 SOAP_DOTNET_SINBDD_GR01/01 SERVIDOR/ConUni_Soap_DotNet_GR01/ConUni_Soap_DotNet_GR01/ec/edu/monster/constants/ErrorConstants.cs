namespace ConUni_Soap_DotNet_GR01.ec.edu.monster.constants
{
    /// <summary>
    /// Constantes para c�digos de error del sistema
    /// </summary>
    public static class ErrorConstants
    {
        // C�digos de error de validaci�n
        public const string VALOR_NEGATIVO = "VAL_001";
        public const string VALOR_NAN = "VAL_002";
        public const string VALOR_INFINITO = "VAL_003";
        public const string TEMPERATURA_BAJO_CERO_ABSOLUTO = "VAL_004";
        public const string VALOR_VACIO = "VAL_005";
        public const string VALOR_NO_NUMERICO = "VAL_006";

        // C�digos de error de conversi�n
        public const string ERROR_CONVERSION_LONGITUD = "CONV_001";
        public const string ERROR_CONVERSION_MASA = "CONV_002";
        public const string ERROR_CONVERSION_TEMPERATURA = "CONV_003";

        // C�digos de error del sistema
        public const string ERROR_INTERNO = "SYS_001";
        public const string ERROR_SERVICIO_NO_DISPONIBLE = "SYS_002";

        // Tipos de error
        public const string TIPO_VALIDACION = "Validacion";
        public const string TIPO_CONVERSION = "Conversion";
        public const string TIPO_SISTEMA = "Sistema";

        // Mensajes base
        public const string MSG_VALOR_NEGATIVO = "El valor no puede ser negativo";
        public const string MSG_VALOR_NAN = "El valor no es un n�mero v�lido";
        public const string MSG_VALOR_INFINITO = "El valor no puede ser infinito";
        public const string MSG_TEMPERATURA_INVALIDA = "La temperatura est� por debajo del cero absoluto";
        public const string MSG_ERROR_INTERNO = "Error interno del sistema";
        public const string MSG_VALOR_VACIO = "El valor no puede estar vac�o";
        public const string MSG_VALOR_NO_NUMERICO = "El valor no es un n�mero v�lido";
    }
}