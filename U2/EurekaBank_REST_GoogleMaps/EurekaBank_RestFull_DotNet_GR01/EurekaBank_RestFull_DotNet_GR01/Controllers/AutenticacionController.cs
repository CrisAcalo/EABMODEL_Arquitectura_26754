using EurekaBank_RestFull_DotNet_GR01.Models;
using EurekaBank_RestFull_DotNet_GR01.Models.DTOs;
using EurekaBank_RestFull_DotNet_GR01.Services;
using EurekaBank_RestFull_DotNet_GR01.Helpers;
using EurekaBank_RestFull_DotNet_GR01.Constants;
using Microsoft.AspNetCore.Mvc;
using System.ComponentModel.DataAnnotations;

namespace EurekaBank_RestFull_DotNet_GR01.Controllers
{
    /// <summary>
    /// API Controller para operaciones de autenticación
    /// Replica la funcionalidad del servicio SOAP ServicioAutenticacion
    /// </summary>
    [ApiController]
    [Route("api/[controller]")]
    public class AutenticacionController : ControllerBase
    {
        private readonly AutenticacionService _autenticacionService;

        public AutenticacionController()
        {
            _autenticacionService = new AutenticacionService();
        }

        /// <summary>
        /// Autentica un usuario empleado
        /// </summary>
        /// <param name="request">Objeto con usuario y clave</param>
        /// <returns>RespuestaDTO con datos del empleado si es exitoso</returns>
        [HttpPost("login")]
        public ActionResult<RespuestaDTO> Login([FromBody] LoginRequest request)
        {
            try
            {
                // Validación de datos nulos
                if (request == null)
                {
                    return BadRequest(RespuestaHelper.CrearDatosRequeridos("request"));
                }

                // Validaciones de campos específicos
                if (string.IsNullOrWhiteSpace(request.Usuario))
                {
                    return BadRequest(RespuestaHelper.CrearDatosRequeridos("Usuario"));
                }

                if (string.IsNullOrWhiteSpace(request.Clave))
                {
                    return BadRequest(RespuestaHelper.CrearDatosRequeridos("Clave"));
                }

                var resultado = _autenticacionService.Login(request.Usuario, request.Clave);

                if (resultado.Exitoso)
                {
                    return Ok(resultado);
                }
                return BadRequest(resultado);
            }
            catch (Exception ex)
            {
                return StatusCode(500, RespuestaHelper.CrearErrorServidor(ex));
            }
        }

        /// <summary>
        /// Registra un nuevo empleado en el sistema
        /// </summary>
        /// <param name="empleado">Datos del empleado a registrar</param>
        /// <returns>RespuestaDTO indicando el resultado de la operación</returns>
        [HttpPost("registrar")]
        public ActionResult<RespuestaDTO> RegistrarEmpleado([FromBody] Empleado empleado)
        {
            try
            {
                // Validación de datos nulos
                if (empleado == null)
                {
                    return BadRequest(RespuestaHelper.CrearDatosRequeridos("empleado"));
                }

                var resultado = _autenticacionService.RegistrarEmpleado(empleado);

                if (resultado.Exitoso)
                {
                    return CreatedAtAction(nameof(Login), resultado);
                }
                return BadRequest(resultado);
            }
            catch (Exception ex)
            {
                return StatusCode(500, RespuestaHelper.CrearErrorServidor(ex));
            }
        }

        /// <summary>
        /// Cambia la clave de un empleado
        /// </summary>
        /// <param name="request">Objeto con código, clave actual y nueva</param>
        /// <returns>RespuestaDTO indicando el resultado de la operación</returns>
        [HttpPatch("cambiar-clave")]
        public ActionResult<RespuestaDTO> CambiarClave([FromBody] CambiarClaveRequest request)
        {
            try
            {
                // Validación de datos nulos
                if (request == null)
                {
                    return BadRequest(RespuestaHelper.CrearDatosRequeridos("request"));
                }

                // Validaciones de campos específicos
                if (string.IsNullOrWhiteSpace(request.Codigo))
                {
                    return BadRequest(RespuestaHelper.CrearDatosRequeridos("Codigo"));
                }

                if (string.IsNullOrWhiteSpace(request.ClaveActual))
                {
                    return BadRequest(RespuestaHelper.CrearDatosRequeridos("ClaveActual"));
                }

                if (string.IsNullOrWhiteSpace(request.ClaveNueva))
                {
                    return BadRequest(RespuestaHelper.CrearDatosRequeridos("ClaveNueva"));
                }

                var resultado = _autenticacionService.CambiarClave(
                    request.Codigo,
                    request.ClaveActual,
                    request.ClaveNueva
                );

                if (resultado.Exitoso)
                {
                    return Ok(resultado);
                }
                return BadRequest(resultado);
            }
            catch (Exception ex)
            {
                return StatusCode(500, RespuestaHelper.CrearErrorServidor(ex));
            }
        }
    }

    // DTOs para requests
    public class LoginRequest
    {
        [Required(ErrorMessage = "El usuario es requerido")]
        [StringLength(20, MinimumLength = 3, ErrorMessage = "El usuario debe tener entre 3 y 20 caracteres")]
        public string Usuario { get; set; }

        [Required(ErrorMessage = "La clave es requerida")]
        [StringLength(50, MinimumLength = 6, ErrorMessage = "La clave debe tener entre 6 y 50 caracteres")]
        public string Clave { get; set; }
    }

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
