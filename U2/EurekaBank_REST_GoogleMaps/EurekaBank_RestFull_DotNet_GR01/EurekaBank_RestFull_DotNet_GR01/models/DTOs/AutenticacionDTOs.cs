using System.ComponentModel.DataAnnotations;

namespace EurekaBank_RestFull_DotNet_GR01.Models.DTOs
{
    /// <summary>
    /// DTO para solicitud de login
    /// </summary>
    public class LoginRequest
    {
        [Required(ErrorMessage = "El usuario es requerido")]
        [StringLength(20, MinimumLength = 3, ErrorMessage = "El usuario debe tener entre 3 y 20 caracteres")]
        public string Usuario { get; set; }

        [Required(ErrorMessage = "La clave es requerida")]
        [StringLength(50, MinimumLength = 6, ErrorMessage = "La clave debe tener entre 6 y 50 caracteres")]
        public string Clave { get; set; }
    }

    /// <summary>
    /// DTO para solicitud de cambio de clave
    /// </summary>
    public class CambiarClaveRequest
    {
        [Required(ErrorMessage = "El código de empleado es requerido")]
        [StringLength(4, MinimumLength = 4, ErrorMessage = "El código debe tener exactamente 4 caracteres")]
        public string Codigo { get; set; }

        [Required(ErrorMessage = "La clave actual es requerida")]
        [StringLength(50, MinimumLength = 6, ErrorMessage = "La clave actual debe tener entre 6 y 50 caracteres")]
        public string ClaveActual { get; set; }

        [Required(ErrorMessage = "La clave nueva es requerida")]
        [StringLength(50, MinimumLength = 6, ErrorMessage = "La clave nueva debe tener entre 6 y 50 caracteres")]
        public string ClaveNueva { get; set; }
    }
}