namespace EurekaBank_RestFull_DotNet_GR01.Constants
{
    /// <summary>
    /// Códigos de error estandarizados para toda la aplicación
    /// </summary>
    public static class CodigosErrorConstants
    {
        // Validación (VAL001-VAL099)
        public const string VALIDACION_CAMPOS_REQUERIDOS = "VAL001";
        public const string VALIDACION_FORMATO_INVALIDO = "VAL002";
        public const string VALIDACION_RANGO_INVALIDO = "VAL003";
        public const string VALIDACION_LONGITUD_INVALIDA = "VAL004";
        public const string VALIDACION_DATOS_NULOS = "VAL005";
        public const string VALIDACION_COORDENADAS_INCONSISTENTES = "VAL006";
        public const string VALIDACION_CAMPOS_MINIMOS = "VAL007";
        public const string VALIDACION_MODELO_INVALIDO = "VAL008";

        // Autenticación (AUTH001-AUTH099)
        public const string AUTH_CREDENCIALES_INVALIDAS = "AUTH001";
        public const string AUTH_USUARIO_EXISTENTE = "AUTH002";
        public const string AUTH_USUARIO_NO_ENCONTRADO = "AUTH003";
        public const string AUTH_CLAVE_INCORRECTA = "AUTH004";
        public const string AUTH_ACCESO_DENEGADO = "AUTH005";

        // Recursos (REC001-REC099)
        public const string RECURSO_NO_ENCONTRADO = "REC001";
        public const string RECURSO_YA_EXISTE = "REC002";
        public const string RECURSO_EN_USO = "REC003";
        public const string RECURSO_INACTIVO = "REC004";

        // Sucursales (SUC001-SUC099)
        public const string SUCURSAL_NO_ENCONTRADA = "SUC001";
        public const string SUCURSAL_TIENE_CUENTAS = "SUC002";
        public const string SUCURSAL_ERROR_ACTUALIZACION = "SUC003";
        public const string SUCURSAL_ERROR_ELIMINACION = "SUC004";

        // Cuentas (CTA001-CTA099)
        public const string CUENTA_NO_EXISTE = "CTA001";
        public const string CUENTA_INACTIVA = "CTA002";
        public const string CUENTA_SALDO_INSUFICIENTE = "CTA003";
        public const string CUENTA_CLAVE_INCORRECTA = "CTA004";

        // Transacciones (TXN001-TXN099)
        public const string TRANSACCION_MONEDA_DIFERENTE = "TXN001";
        public const string TRANSACCION_IMPORTE_INVALIDO = "TXN002";
        public const string TRANSACCION_ERROR_PROCESAMIENTO = "TXN003";

        // Servidor (SRV001-SRV099)
        public const string SERVIDOR_ERROR_INTERNO = "SRV001";
        public const string SERVIDOR_BASE_DATOS = "SRV002";
        public const string SERVIDOR_TIMEOUT = "SRV003";
        public const string SERVIDOR_NO_DISPONIBLE = "SRV004";
    }
}