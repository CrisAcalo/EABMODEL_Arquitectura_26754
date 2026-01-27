namespace Comer_CliCon_SOAP_DotNet_GR01.Models
{
    public class ProductoDTO
    {
        public int ProductoId { get; set; }
        public string Codigo { get; set; } = string.Empty;
        public string Nombre { get; set; } = string.Empty;
        public string Descripcion { get; set; } = string.Empty;
        public decimal Precio { get; set; }
        public int Stock { get; set; }
        public string Categoria { get; set; } = string.Empty;
        public string ImagenUrl { get; set; } = string.Empty;
        public DateTime FechaRegistro { get; set; }
        public string Estado { get; set; } = string.Empty;
    }

    public class CrearProductoDTO
    {
        public string Codigo { get; set; } = string.Empty;
        public string Nombre { get; set; } = string.Empty;
        public string Descripcion { get; set; } = string.Empty;
        public decimal Precio { get; set; }
        public int Stock { get; set; }
        public string Categoria { get; set; } = string.Empty;
    }

    public class ActualizarProductoDTO
    {
        public string? Codigo { get; set; }
        public string? Nombre { get; set; }
        public string? Descripcion { get; set; }
        public decimal? Precio { get; set; }
        public int? Stock { get; set; }
        public string? Categoria { get; set; }
    }

    public class RespuestaDTO
    {
        public bool Exito { get; set; }
        public string Mensaje { get; set; } = string.Empty;
        public object? Datos { get; set; }
    }
}
