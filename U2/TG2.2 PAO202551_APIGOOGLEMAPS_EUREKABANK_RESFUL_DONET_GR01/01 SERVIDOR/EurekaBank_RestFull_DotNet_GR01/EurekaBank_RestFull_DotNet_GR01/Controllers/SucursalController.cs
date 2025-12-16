using Microsoft.AspNetCore.Mvc;
using EurekaBank_RestFull_DotNet_GR01.DAL;
using EurekaBank_RestFull_DotNet_GR01.Models;
using EurekaBank_RestFull_DotNet_GR01.Models.DTOs;
using EurekaBank_RestFull_DotNet_GR01.Validators;
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
        /// Valida los datos para crear una sucursal
        /// </summary>
        private (bool EsValida, string Mensaje, string CodigoError) ValidarCrearSucursalDTO(CrearSucursalDTO dto)
        {
            if (string.IsNullOrWhiteSpace(dto.Nombre))
                return (false, "El nombre de la sucursal es requerido", "VAL002");

            if (dto.Nombre.Trim().Length < 3 || dto.Nombre.Trim().Length > 50)
                return (false, "El nombre debe tener entre 3 y 50 caracteres", "VAL003");

            if (string.IsNullOrWhiteSpace(dto.Ciudad))
                return (false, "La ciudad es requerida", "VAL004");

            if (dto.Ciudad.Trim().Length < 2 || dto.Ciudad.Trim().Length > 30)
                return (false, "La ciudad debe tener entre 2 y 30 caracteres", "VAL005");

            if (!string.IsNullOrWhiteSpace(dto.Direccion) && dto.Direccion.Trim().Length > 50)
                return (false, "La dirección debe tener máximo 50 caracteres", "VAL006");

            if (dto.Latitud.HasValue && (dto.Latitud < -90 || dto.Latitud > 90))
                return (false, "La latitud debe estar entre -90 y 90 grados", "VAL007");

            if (dto.Longitud.HasValue && (dto.Longitud < -180 || dto.Longitud > 180))
                return (false, "La longitud debe estar entre -180 y 180 grados", "VAL008");

            // Validar que las coordenadas sean consistentes
            if (dto.Latitud.HasValue != dto.Longitud.HasValue)
                return (false, "Las coordenadas deben ser ambas nulas o ambas tener valor", "VAL009");

            return (true, string.Empty, string.Empty);
        }

        /// <summary>
        /// Valida los datos para actualizar una sucursal
        /// </summary>
        private (bool EsValida, string Mensaje, string CodigoError) ValidarActualizarSucursalDTO(ActualizarSucursalDTO dto)
        {
            if (!string.IsNullOrWhiteSpace(dto.Nombre))
            {
                if (dto.Nombre.Trim().Length < 3 || dto.Nombre.Trim().Length > 50)
                    return (false, "El nombre debe tener entre 3 y 50 caracteres", "VAL003");
            }

            if (!string.IsNullOrWhiteSpace(dto.Ciudad))
            {
                if (dto.Ciudad.Trim().Length < 2 || dto.Ciudad.Trim().Length > 30)
                    return (false, "La ciudad debe tener entre 2 y 30 caracteres", "VAL005");
            }

            if (dto.Direccion != null && !string.IsNullOrWhiteSpace(dto.Direccion) && dto.Direccion.Trim().Length > 50)
                return (false, "La dirección debe tener máximo 50 caracteres", "VAL006");

            if (dto.Latitud.HasValue && (dto.Latitud < -90 || dto.Latitud > 90))
                return (false, "La latitud debe estar entre -90 y 90 grados", "VAL007");

            if (dto.Longitud.HasValue && (dto.Longitud < -180 || dto.Longitud > 180))
                return (false, "La longitud debe estar entre -180 y 180 grados", "VAL008");

            return (true, string.Empty, string.Empty);
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
                
                return Ok(new RespuestaDTO
                {
                    Exitoso = true,
                    Mensaje = "Sucursales obtenidas correctamente",
                    Datos = sucursalesDetalle
                });
            }
            catch (Exception ex)
            {
                return StatusCode(500, new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error interno del servidor: {ex.Message}",
                    CodigoError = "SRV001",
                    Datos = null
                });
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
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "El código de sucursal debe ser mayor a cero",
                        CodigoError = "VAL001",
                        Datos = null
                    });
                }

                var sucursal = sucursalDAO.ObtenerPorCodigo(codigo);
                
                if (sucursal == null)
                {
                    return NotFound(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Sucursal no encontrada",
                        CodigoError = "SUC001",
                        Datos = null
                    });
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

                return Ok(new RespuestaDTO
                {
                    Exitoso = true,
                    Mensaje = "Sucursal encontrada",
                    Datos = sucursalDetalle
                });
            }
            catch (Exception ex)
            {
                return StatusCode(500, new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error interno del servidor: {ex.Message}",
                    CodigoError = "SRV001",
                    Datos = null
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
                if (sucursalDTO == null)
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Los datos de la sucursal son requeridos",
                        CodigoError = "VAL001",
                        Datos = null
                    });
                }

                // Validación manual personalizada
                var validacionPersonalizada = ValidarCrearSucursalDTO(sucursalDTO);
                if (!validacionPersonalizada.EsValida)
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = validacionPersonalizada.Mensaje,
                        CodigoError = validacionPersonalizada.CodigoError,
                        Datos = null
                    });
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
                        CodigoError = "VAL010",
                        Datos = null
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
                        CodigoError = "SUC004",
                        Datos = null
                    });
                }
            }
            catch (Exception ex)
            {
                return StatusCode(500, new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error interno del servidor: {ex.Message}",
                    CodigoError = "SRV001",
                    Datos = null
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
                        CodigoError = "VAL001",
                        Datos = null
                    });
                }

                if (sucursalDTO == null)
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Los datos de actualización son requeridos",
                        CodigoError = "VAL002",
                        Datos = null
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
                        CodigoError = "VAL003",
                        Datos = null
                    });
                }

                // Validación manual personalizada
                var validacionPersonalizada = ValidarActualizarSucursalDTO(sucursalDTO);
                if (!validacionPersonalizada.EsValida)
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = validacionPersonalizada.Mensaje,
                        CodigoError = validacionPersonalizada.CodigoError,
                        Datos = null
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
                        CodigoError = "SUC001",
                        Datos = null
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
                        CodigoError = "VAL004",
                        Datos = null
                    });
                }

                // Validar consistencia de coordenadas en el resultado final
                if (!SucursalValidator.CoordenadasValidas(sucursalActualizada.Latitud, sucursalActualizada.Longitud))
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Las coordenadas deben ser ambas nulas o ambas tener valor válido. Si desea actualizar solo una coordenada, debe proporcionar ambas.",
                        CodigoError = "VAL005",
                        Datos = null
                    });
                }

                // Validación adicional: Si se proporciona una coordenada pero no la otra, validar que la existente sea válida
                if (sucursalDTO.Latitud.HasValue && !sucursalDTO.Longitud.HasValue && !sucursalExistente.Longitud.HasValue)
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Para establecer una latitud, también debe proporcionar una longitud válida",
                        CodigoError = "VAL008",
                        Datos = null
                    });
                }

                if (sucursalDTO.Longitud.HasValue && !sucursalDTO.Latitud.HasValue && !sucursalExistente.Latitud.HasValue)
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Para establecer una longitud, también debe proporcionar una latitud válida",
                        CodigoError = "VAL009",
                        Datos = null
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
                        CodigoError = "SUC005",
                        Datos = null
                    });
                }
            }
            catch (Exception ex)
            {
                return StatusCode(500, new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error interno del servidor: {ex.Message}",
                    CodigoError = "SRV001",
                    Datos = null
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
                        CodigoError = "VAL001",
                        Datos = null
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
                        CodigoError = "SUC001",
                        Datos = null
                    });
                }

                // Validar que la sucursal puede ser eliminada (no tiene cuentas)
                if (!SucursalValidator.PuedeSerEliminada(sucursalExistente))
                {
                    return BadRequest(new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = $"No se puede eliminar la sucursal porque tiene {sucursalExistente.ContadorCuentas} cuentas asociadas",
                        CodigoError = "SUC007",
                        Datos = null
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
                        CodigoError = "SUC006",
                        Datos = null
                    });
                }
            }
            catch (Exception ex)
            {
                return StatusCode(500, new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error interno del servidor: {ex.Message}",
                    CodigoError = "SRV001",
                    Datos = null
                });
            }
        }
    }
}