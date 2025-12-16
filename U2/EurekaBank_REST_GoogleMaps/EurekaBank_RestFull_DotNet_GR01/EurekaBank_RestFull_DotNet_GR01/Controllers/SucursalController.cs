using Microsoft.AspNetCore.Mvc;
using EurekaBank_RestFull_DotNet_GR01.DAL;
using EurekaBank_RestFull_DotNet_GR01.Models;
using EurekaBank_RestFull_DotNet_GR01.Models.DTOs;
using EurekaBank_RestFull_DotNet_GR01.Validators;
using EurekaBank_RestFull_DotNet_GR01.Helpers;
using EurekaBank_RestFull_DotNet_GR01.Constants;
using System.ComponentModel.DataAnnotations;
using System.Linq;

namespace EurekaBank_RestFull_DotNet_GR01.Controllers
{
    /// <summary>
    /// Controlador para gestionar operaciones relacionadas con sucursales
    /// </summary>
    [ApiController]
    [Route("api/[controller]")]
    public class SucursalController : ControllerBase
    {
        private readonly SucursalDAO sucursalDAO;

        public SucursalController()
        {
            sucursalDAO = new SucursalDAO();
        }

        /// <summary>
        /// Obtiene todas las sucursales con todos sus datos
        /// </summary>
        /// <returns>Lista de todas las sucursales</returns>
        [HttpGet]
        public ActionResult<RespuestaDTO> ObtenerTodas()
        {
            try
            {
                var sucursales = sucursalDAO.ListarTodas();
                
                // Convertir a DTO de detalle completo
                var sucursalesDetalle = sucursales.Select(s => new SucursalDetalleDTO
                {
                    Codigo = s.Codigo,
                    Nombre = s.Nombre,
                    Ciudad = s.Ciudad,
                    Direccion = s.Direccion,
                    ContadorCuentas = s.ContadorCuentas,
                    Latitud = s.Latitud,
                    Longitud = s.Longitud,
                    TieneCoordenadas = s.TieneCoordenadas
                }).ToList();
                
                return Ok(RespuestaHelper.CrearExito("Sucursales obtenidas correctamente", sucursalesDetalle));
            }
            catch (Exception ex)
            {
                return StatusCode(500, RespuestaHelper.CrearErrorServidor(ex));
            }
        }

        /// <summary>
        /// Obtiene una sucursal por su código
        /// </summary>
        /// <param name="codigo">Código de la sucursal</param>
        /// <returns>Datos de la sucursal</returns>
        [HttpGet("{codigo:int}")]
        public ActionResult<RespuestaDTO> ObtenerPorCodigo(int codigo)
        {
            try
            {
                if (codigo <= 0)
                {
                    return BadRequest(RespuestaHelper.CrearCodigoInvalido("sucursal"));
                }

                var sucursal = sucursalDAO.ObtenerPorCodigo(codigo);
                
                if (sucursal == null)
                {
                    return NotFound(RespuestaHelper.CrearRecursoNoEncontrado("Sucursal"));
                }

                // Convertir a DTO de detalle
                var sucursalDetalle = new SucursalDetalleDTO
                {
                    Codigo = sucursal.Codigo,
                    Nombre = sucursal.Nombre,
                    Ciudad = sucursal.Ciudad,
                    Direccion = sucursal.Direccion,
                    ContadorCuentas = sucursal.ContadorCuentas,
                    Latitud = sucursal.Latitud,
                    Longitud = sucursal.Longitud,
                    TieneCoordenadas = sucursal.TieneCoordenadas
                };

                return Ok(RespuestaHelper.CrearExito("Sucursal encontrada", sucursalDetalle));
            }
            catch (Exception ex)
            {
                return StatusCode(500, new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error interno del servidor: {ex.Message}",
                    CodigoError = "SRV001"
                });
            }
        }

        /// <summary>
        /// Crea una nueva sucursal
        /// </summary>
        /// <param name="sucursalDTO">Datos de la nueva sucursal</param>
        /// <returns>Resultado de la operación</returns>
        [HttpPost]
        public ActionResult<RespuestaDTO> CrearSucursal([FromBody] CrearSucursalDTO sucursalDTO)
        {
            try
            {
                // Validar modelo
                if (!ModelState.IsValid)
                {
                    var errores = ModelState.Values
                        .SelectMany(v => v.Errors)
                        .Select(e => e.ErrorMessage);
                    
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = $"Errores de validación: {string.Join(", ", errores)}",
                        CodigoError = "VAL001"
                    });
                }

                if (sucursalDTO == null)
                {
                    return BadRequest(RespuestaHelper.CrearDatosRequeridos("sucursal"));
                }

                // Crear objeto Sucursal desde DTO (sin código, se auto-genera)
                var sucursal = new Sucursal
                {
                    Codigo = 0, // Se auto-genera en la BD
                    Nombre = sucursalDTO.Nombre?.Trim(),
                    Ciudad = sucursalDTO.Ciudad?.Trim(),
                    Direccion = sucursalDTO.Direccion?.Trim(),
                    ContadorCuentas = 0, // Nueva sucursal inicia con 0 cuentas
                    Latitud = sucursalDTO.Latitud,
                    Longitud = sucursalDTO.Longitud
                };

                // Validar con SucursalValidator
                var (esValida, mensajeError) = SucursalValidator.ValidarParaCreacion(sucursal);
                if (!esValida)
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = mensajeError,
                        CodigoError = "VAL003"
                    });
                }

                // Insertar y obtener el código generado
                int nuevoCodigo = sucursalDAO.Insertar(sucursal);
                
                if (nuevoCodigo > 0)
                {
                    // Obtener la sucursal creada para devolver datos completos
                    var sucursalCreada = sucursalDAO.ObtenerPorCodigo(nuevoCodigo);
                    
                    // Crear DTO de respuesta
                    var sucursalDetalle = new SucursalDetalleDTO
                    {
                        Codigo = sucursalCreada.Codigo,
                        Nombre = sucursalCreada.Nombre,
                        Ciudad = sucursalCreada.Ciudad,
                        Direccion = sucursalCreada.Direccion,
                        ContadorCuentas = sucursalCreada.ContadorCuentas,
                        Latitud = sucursalCreada.Latitud,
                        Longitud = sucursalCreada.Longitud,
                        TieneCoordenadas = sucursalCreada.TieneCoordenadas
                    };

                    return CreatedAtAction(
                        nameof(ObtenerPorCodigo), 
                        new { codigo = nuevoCodigo }, 
                        new RespuestaDTO
                        {
                            Exitoso = true,
                            Mensaje = "Sucursal creada correctamente",
                            Datos = sucursalDetalle
                        });
                }
                else
                {
                    return StatusCode(500, new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Error al crear la sucursal",
                        CodigoError = "SUC004"
                    });
                }
            }
            catch (Exception ex)
            {
                return StatusCode(500, new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error interno del servidor: {ex.Message}",
                    CodigoError = "SRV001"
                });
            }
        }

        /// <summary>
        /// Actualiza parcialmente los datos de una sucursal (nombre, ciudad, dirección y/o coordenadas)
        /// </summary>
        /// <param name="codigo">Código de la sucursal</param>
        /// <param name="sucursalDTO">Datos a actualizar (solo los campos proporcionados serán modificados)</param>
        /// <returns>Resultado de la operación</returns>
        [HttpPatch("{codigo:int}")]
        public ActionResult<RespuestaDTO> ActualizarSucursal(int codigo, [FromBody] ActualizarSucursalDTO sucursalDTO)
        {
            try
            {
                if (codigo <= 0)
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "El código de sucursal debe ser mayor a cero",
                        CodigoError = "VAL001"
                    });
                }

                if (sucursalDTO == null)
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Los datos de actualización son requeridos",
                        CodigoError = "VAL002"
                    });
                }

                // Verificar que al menos un campo está presente para actualizar
                if (string.IsNullOrEmpty(sucursalDTO.Nombre) && 
                    string.IsNullOrEmpty(sucursalDTO.Ciudad) && 
                    sucursalDTO.Direccion == null && 
                    !sucursalDTO.Latitud.HasValue && 
                    !sucursalDTO.Longitud.HasValue)
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Debe proporcionar al menos un campo para actualizar",
                        CodigoError = "VAL003"
                    });
                }

                // Validar modelo solo con los campos proporcionados
                if (!ModelState.IsValid)
                {
                    var errores = ModelState.Values
                        .SelectMany(v => v.Errors)
                        .Select(e => e.ErrorMessage);
                    
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = $"Errores de validación: {string.Join(", ", errores)}",
                        CodigoError = "VAL001"
                    });
                }

                // Validar rangos de coordenadas si se proporcionan
                if (sucursalDTO.Latitud.HasValue && (sucursalDTO.Latitud < -90 || sucursalDTO.Latitud > 90))
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "La latitud debe estar entre -90 y 90 grados",
                        CodigoError = "VAL006"
                    });
                }

                if (sucursalDTO.Longitud.HasValue && (sucursalDTO.Longitud < -180 || sucursalDTO.Longitud > 180))
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "La longitud debe estar entre -180 y 180 grados",
                        CodigoError = "VAL007"
                    });
                }

                // Verificar que la sucursal existe
                var sucursalExistente = sucursalDAO.ObtenerPorCodigo(codigo);
                if (sucursalExistente == null)
                {
                    return NotFound(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Sucursal no encontrada",
                        CodigoError = "SUC001"
                    });
                }

                // Aplicar solo los cambios proporcionados (actualización parcial)
                var sucursalActualizada = new Sucursal
                {
                    Codigo = codigo,
                    Nombre = !string.IsNullOrEmpty(sucursalDTO.Nombre) ? sucursalDTO.Nombre.Trim() : sucursalExistente.Nombre,
                    Ciudad = !string.IsNullOrEmpty(sucursalDTO.Ciudad) ? sucursalDTO.Ciudad.Trim() : sucursalExistente.Ciudad,
                    Direccion = sucursalDTO.Direccion != null ? sucursalDTO.Direccion?.Trim() : sucursalExistente.Direccion,
                    ContadorCuentas = sucursalExistente.ContadorCuentas, // Mantener el contador actual
                    Latitud = sucursalDTO.Latitud ?? sucursalExistente.Latitud,
                    Longitud = sucursalDTO.Longitud ?? sucursalExistente.Longitud
                };

                // Validar el resultado final
                var (esValida, mensajeError) = SucursalValidator.ValidarParaActualizacion(sucursalActualizada);
                if (!esValida)
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = mensajeError,
                        CodigoError = "VAL004"
                    });
                }

                // Validar consistencia de coordenadas en el resultado final
                if (!SucursalValidator.CoordenadasValidas(sucursalActualizada.Latitud, sucursalActualizada.Longitud))
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Las coordenadas deben ser ambas nulas o ambas tener valor válido. Si desea actualizar solo una coordenada, debe proporcionar ambas.",
                        CodigoError = "VAL005"
                    });
                }

                // Validación adicional: Si se proporciona una coordenada pero no la otra, validar que la existente sea válida
                if (sucursalDTO.Latitud.HasValue && !sucursalDTO.Longitud.HasValue && !sucursalExistente.Longitud.HasValue)
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Para establecer una latitud, también debe proporcionar una longitud válida",
                        CodigoError = "VAL008"
                    });
                }

                if (sucursalDTO.Longitud.HasValue && !sucursalDTO.Latitud.HasValue && !sucursalExistente.Latitud.HasValue)
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Para establecer una longitud, también debe proporcionar una latitud válida",
                        CodigoError = "VAL009"
                    });
                }

                bool actualizado = sucursalDAO.Actualizar(sucursalActualizada);
                
                if (actualizado)
                {
                    // Crear DTO de respuesta
                    var sucursalDetalle = new SucursalDetalleDTO
                    {
                        Codigo = sucursalActualizada.Codigo,
                        Nombre = sucursalActualizada.Nombre,
                        Ciudad = sucursalActualizada.Ciudad,
                        Direccion = sucursalActualizada.Direccion,
                        ContadorCuentas = sucursalActualizada.ContadorCuentas,
                        Latitud = sucursalActualizada.Latitud,
                        Longitud = sucursalActualizada.Longitud,
                        TieneCoordenadas = sucursalActualizada.TieneCoordenadas
                    };

                    return Ok(new RespuestaDTO
                    {
                        Exitoso = true,
                        Mensaje = "Sucursal actualizada correctamente",
                        Datos = sucursalDetalle
                    });
                }
                else
                {
                    return StatusCode(500, new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Error al actualizar la sucursal",
                        CodigoError = "SUC005"
                    });
                }
            }
            catch (Exception ex)
            {
                return StatusCode(500, new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error interno del servidor: {ex.Message}",
                    CodigoError = "SRV001"
                });
            }
        }

        /// <summary>
        /// Elimina una sucursal
        /// </summary>
        /// <param name="codigo">Código de la sucursal a eliminar</param>
        /// <returns>Resultado de la operación</returns>
        [HttpDelete("{codigo:int}")]
        public ActionResult<RespuestaDTO> EliminarSucursal(int codigo)
        {
            try
            {
                if (codigo <= 0)
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "El código de sucursal debe ser mayor a cero",
                        CodigoError = "VAL001"
                    });
                }

                // Verificar que la sucursal existe
                var sucursalExistente = sucursalDAO.ObtenerPorCodigo(codigo);
                if (sucursalExistente == null)
                {
                    return NotFound(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Sucursal no encontrada",
                        CodigoError = "SUC001"
                    });
                }

                // Validar que la sucursal puede ser eliminada (no tiene cuentas)
                if (!SucursalValidator.PuedeSerEliminada(sucursalExistente))
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = $"No se puede eliminar la sucursal porque tiene {sucursalExistente.ContadorCuentas} cuentas asociadas",
                        CodigoError = "SUC007"
                    });
                }

                bool eliminado = sucursalDAO.Eliminar(codigo);
                
                if (eliminado)
                {
                    return Ok(new RespuestaDTO
                    {
                        Exitoso = true,
                        Mensaje = "Sucursal eliminada correctamente"
                    });
                }
                else
                {
                    return StatusCode(500, new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Error al eliminar la sucursal",
                        CodigoError = "SUC006"
                    });
                }
            }
            catch (Exception ex)
            {
                return StatusCode(500, new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error interno del servidor: {ex.Message}",
                    CodigoError = "SRV001"
                });
            }
        }
    }
}