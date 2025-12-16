using System.ComponentModel.DataAnnotations;

namespace EurekaBank_RestFull_DotNet_GR01.Models.DTOs
{
    /// <summary>
    /// DTO para crear una nueva sucursal
    /// </summary>
    public class CrearSucursalDTO
    {
        [Required(ErrorMessage = "El nombre de la sucursal es requerido")]
        [StringLength(50, MinimumLength = 3, ErrorMessage = "El nombre debe tener entre 3 y 50 caracteres")]
        public string Nombre { get; set; }

        [Required(ErrorMessage = "La ciudad es requerida")]
        [StringLength(30, MinimumLength = 2, ErrorMessage = "La ciudad debe tener entre 2 y 30 caracteres")]
        public string Ciudad { get; set; }

        [StringLength(50, ErrorMessage = "La dirección debe tener máximo 50 caracteres")]
        public string Direccion { get; set; }

        [Range(-90, 90, ErrorMessage = "La latitud debe estar entre -90 y 90 grados")]
        public decimal? Latitud { get; set; }

        [Range(-180, 180, ErrorMessage = "La longitud debe estar entre -180 y 180 grados")]
        public decimal? Longitud { get; set; }
    }

    /// <summary>
    /// DTO para actualizar parcialmente una sucursal existente (PATCH)
    /// Todos los campos son opcionales, solo se actualizarán los que se proporcionen
    /// </summary>
    public class ActualizarSucursalDTO
    {
        [StringLength(50, MinimumLength = 3, ErrorMessage = "El nombre debe tener entre 3 y 50 caracteres")]
        public string? Nombre { get; set; }

        [StringLength(30, MinimumLength = 2, ErrorMessage = "La ciudad debe tener entre 2 y 30 caracteres")]
        public string? Ciudad { get; set; }

        [StringLength(50, ErrorMessage = "La dirección debe tener máximo 50 caracteres")]
        public string? Direccion { get; set; }

        [Range(-90, 90, ErrorMessage = "La latitud debe estar entre -90 y 90 grados")]
        public decimal? Latitud { get; set; }

        [Range(-180, 180, ErrorMessage = "La longitud debe estar entre -180 y 180 grados")]
        public decimal? Longitud { get; set; }
    }

    /// <summary>
    /// DTO para actualizar coordenadas de una sucursal
    /// </summary>
    public class CoordenadasDTO
    {
        [Range(-90, 90, ErrorMessage = "La latitud debe estar entre -90 y 90 grados")]
        public decimal? Latitud { get; set; }

        [Range(-180, 180, ErrorMessage = "La longitud debe estar entre -180 y 180 grados")]
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