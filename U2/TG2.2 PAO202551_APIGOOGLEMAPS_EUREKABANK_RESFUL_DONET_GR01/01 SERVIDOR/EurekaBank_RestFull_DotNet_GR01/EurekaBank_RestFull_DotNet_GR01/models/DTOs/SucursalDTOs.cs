namespace EurekaBank_RestFull_DotNet_GR01.Models.DTOs
{
    /// <summary>
    /// DTO para crear una nueva sucursal
    /// </summary>
    public class CrearSucursalDTO
    {
        public string Nombre { get; set; }
        public string Ciudad { get; set; }
        public string Direccion { get; set; }
        public decimal? Latitud { get; set; }
        public decimal? Longitud { get; set; }
    }

    /// <summary>
    /// DTO para actualizar parcialmente una sucursal existente (PATCH)
    /// Todos los campos son opcionales, solo se actualizarán los que se proporcionen
    /// </summary>
    public class ActualizarSucursalDTO
    {
        public string? Nombre { get; set; }
        public string? Ciudad { get; set; }
        public string? Direccion { get; set; }
        public decimal? Latitud { get; set; }
        public decimal? Longitud { get; set; }
    }

    /// <summary>
    /// DTO para respuesta de sucursal con información completa
    /// </summary>
    public class SucursalDetalleDTO
    {
        public int Codigo { get; set; }
        public string Nombre { get; set; }
        public string Ciudad { get; set; }
        public string Direccion { get; set; }
        public int ContadorCuentas { get; set; }
        public decimal? Latitud { get; set; }
        public decimal? Longitud { get; set; }
        public bool TieneCoordenadas { get; set; }
        public string DireccionCompleta => string.IsNullOrEmpty(Direccion) ? Ciudad : $"{Direccion}, {Ciudad}";
    }

    /// <summary>
    /// DTO para listado resumido de sucursales
    /// </summary>
    public class SucursalResumenDTO
    {
        public int Codigo { get; set; }
        public string Nombre { get; set; }
        public string Ciudad { get; set; }
        public int ContadorCuentas { get; set; }
        public bool TieneCoordenadas { get; set; }
    }
}