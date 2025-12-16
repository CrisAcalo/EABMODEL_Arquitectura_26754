namespace EurekaBank_RestFull_DotNet_GR01.Constants
{
    /// <summary>
    /// Mensajes estándar para respuestas del sistema
    /// </summary>
    public static class MensajesConstants
    {
        // Mensajes de éxito
        public const string DEPOSITO_EXITOSO = "Depósito realizado exitosamente";
        public const string RETIRO_EXITOSO = "Retiro realizado exitosamente";
        public const string TRANSFERENCIA_EXITOSA = "Transferencia realizada exitosamente";
        public const string LOGIN_EXITOSO = "Autenticación exitosa";
        public const string REGISTRO_EXITOSO = "Empleado registrado exitosamente";
        public const string SUCURSAL_CREADA = "Sucursal creada correctamente";
        public const string SUCURSAL_ACTUALIZADA = "Sucursal actualizada correctamente";
        public const string SUCURSAL_ELIMINADA = "Sucursal eliminada correctamente";
        public const string CLAVE_CAMBIADA = "Contraseña cambiada exitosamente";
        
        // Errores de validación
        public const string ERROR_CAMPOS_REQUERIDOS = "Uno o más campos requeridos están vacíos";
        public const string ERROR_MODELO_INVALIDO = "Los datos proporcionados no son válidos";
        public const string ERROR_FORMATO_INVALIDO = "El formato de los datos no es válido";
        public const string ERROR_DATOS_NULOS = "Los datos proporcionados no pueden ser nulos";
        public const string ERROR_CAMPOS_MINIMOS = "Debe proporcionar al menos un campo para actualizar";
        public const string ERROR_RANGO_INVALIDO = "El valor está fuera del rango permitido";
        public const string ERROR_LONGITUD_INVALIDA = "La longitud del campo no es válida";
        
        // Errores de cuenta
        public const string ERROR_CUENTA_NO_EXISTE = "La cuenta no existe";
        public const string ERROR_CUENTA_INACTIVA = "La cuenta no está activa";
        public const string ERROR_CLAVE_INCORRECTA = "Clave incorrecta";
        public const string ERROR_SALDO_INSUFICIENTE = "Saldo insuficiente para realizar la operación";
        
        // Errores de transacciones
        public const string ERROR_IMPORTE_INVALIDO = "El importe debe ser mayor a cero";
        public const string ERROR_IMPORTE_DECIMALES = "El importe no puede tener más de 2 decimales";
        public const string ERROR_MONEDA_DIFERENTE = "Las cuentas deben ser de la misma moneda";
        
        // Errores de autenticación
        public const string ERROR_CREDENCIALES_INVALIDAS = "Usuario o contraseña incorrectos";
        public const string ERROR_USUARIO_EXISTENTE = "El usuario ya existe";
        public const string ERROR_USUARIO_VACIO = "El usuario no puede estar vacío";
        public const string ERROR_CLAVE_CORTA = "La contraseña debe tener al menos 6 caracteres";
        public const string ERROR_USUARIO_NO_ENCONTRADO = "Usuario no encontrado";
        
        // Errores de sucursales
        public const string ERROR_SUCURSAL_NO_ENCONTRADA = "Sucursal no encontrada";
        public const string ERROR_SUCURSAL_TIENE_CUENTAS = "No se puede eliminar la sucursal porque tiene cuentas asociadas";
        public const string ERROR_CODIGO_SUCURSAL_INVALIDO = "El código de sucursal debe ser mayor a cero";
        public const string ERROR_COORDENADAS_INCONSISTENTES = "Las coordenadas deben ser ambas nulas o ambas tener valor válido";
        
        // Errores de recursos
        public const string ERROR_RECURSO_NO_ENCONTRADO = "El recurso solicitado no fue encontrado";
        public const string ERROR_RECURSO_YA_EXISTE = "El recurso ya existe";
        public const string ERROR_RECURSO_EN_USO = "El recurso está siendo utilizado y no puede ser eliminado";
        
        // Errores generales del sistema
        public const string ERROR_DATOS_INCOMPLETOS = "Faltan datos obligatorios";
        public const string ERROR_OPERACION_FALLIDA = "La operación no pudo completarse";
        public const string ERROR_BASE_DATOS = "Error al conectar con la base de datos";
        public const string ERROR_SERVIDOR_INTERNO = "Error interno del servidor";
        public const string ERROR_TIMEOUT = "La operación excedió el tiempo límite";
    }
}
