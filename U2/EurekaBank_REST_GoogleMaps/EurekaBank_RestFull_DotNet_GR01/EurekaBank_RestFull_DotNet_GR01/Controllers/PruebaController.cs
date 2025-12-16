using Microsoft.AspNetCore.Mvc;
using EurekaBank_RestFull_DotNet_GR01.Models.DTOs;
using EurekaBank_RestFull_DotNet_GR01.Helpers;
using System.ComponentModel.DataAnnotations;

namespace EurekaBank_RestFull_DotNet_GR01.Controllers
{
    /// <summary>
    /// Controlador de prueba para demostrar la estandarización de errores
    /// </summary>
    [ApiController]
    [Route("api/[controller]")]
    public class PruebaController : ControllerBase
    {
        /// <summary>
        /// Endpoint de prueba que requiere campos obligatorios
        /// Demuestra cómo se manejan automáticamente los errores de validación
        /// </summary>
        /// <param name="request">Datos de prueba con validaciones</param>
        /// <returns>Respuesta estandarizada</returns>
        [HttpPost("validacion")]
        public ActionResult<RespuestaDTO> PruebaValidacion([FromBody] PruebaRequest request)
        {
            // Si llegamos aquí, las validaciones pasaron correctamente
            return Ok(RespuestaHelper.CrearExito("Validaciones correctas", request));
        }

        /// <summary>
        /// Endpoint que simula un error de servidor
        /// </summary>
        /// <returns>Error estandarizado del servidor</returns>
        [HttpGet("error-servidor")]
        public ActionResult<RespuestaDTO> PruebaErrorServidor()
        {
            try
            {
                // Simular una excepción
                throw new InvalidOperationException("Esta es una excepción de prueba");
            }
            catch (Exception ex)
            {
                return StatusCode(500, RespuestaHelper.CrearErrorServidor(ex, true));
            }
        }

        /// <summary>
        /// Endpoint que simula recurso no encontrado
        /// </summary>
        /// <param name="id">ID del recurso</param>
        /// <returns>Error de recurso no encontrado</returns>
        [HttpGet("recurso/{id:int}")]
        public ActionResult<RespuestaDTO> PruebaRecursoNoEncontrado(int id)
        {
            if (id <= 0)
            {
                return BadRequest(RespuestaHelper.CrearCodigoInvalido("recurso"));
            }

            if (id == 999) // ID que simula "no encontrado"
            {
                return NotFound(RespuestaHelper.CrearRecursoNoEncontrado("Recurso de prueba"));
            }

            return Ok(RespuestaHelper.CrearExito("Recurso encontrado", new { Id = id, Nombre = $"Recurso {id}" }));
        }
    }

    /// <summary>
    /// DTO de prueba con validaciones
    /// </summary>
    public class PruebaRequest
    {
        [Required(ErrorMessage = "El nombre es obligatorio")]
        [StringLength(50, MinimumLength = 2, ErrorMessage = "El nombre debe tener entre 2 y 50 caracteres")]
        public string Nombre { get; set; }

        [Required(ErrorMessage = "El email es obligatorio")]
        [EmailAddress(ErrorMessage = "El formato del email no es válido")]
        public string Email { get; set; }

        [Range(18, 99, ErrorMessage = "La edad debe estar entre 18 y 99 años")]
        public int Edad { get; set; }

        [Phone(ErrorMessage = "El formato del teléfono no es válido")]
        public string Telefono { get; set; }
    }
}