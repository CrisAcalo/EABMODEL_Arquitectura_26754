using System;
using System.Collections.Generic;
using System.Linq;
using EurekaBank_RestFull_DotNet_GR01.DAL;
using EurekaBank_RestFull_DotNet_GR01.Models;
using EurekaBank_RestFull_DotNet_GR01.Models.DTOs;
using EurekaBank_RestFull_DotNet_GR01.Validators;

namespace EurekaBank_RestFull_DotNet_GR01.Services
{
    /// <summary>
    /// Servicio de lógica de negocio para sucursales
    /// </summary>
    public class SucursalService
    {
        private readonly SucursalDAO sucursalDAO;

        public SucursalService()
        {
            sucursalDAO = new SucursalDAO();
        }

        /// <summary>
        /// Obtiene todas las sucursales como DTO de resumen
        /// </summary>
        public RespuestaDTO ObtenerTodasLasSucursales()
        {
            try
            {
                var sucursales = sucursalDAO.ListarTodas();
                
                var sucursalesResumen = sucursales.Select(s => new SucursalResumenDTO
                {
                    Codigo = s.Codigo,
                    Nombre = s.Nombre,
                    Ciudad = s.Ciudad,
                    ContadorCuentas = s.ContadorCuentas,
                    TieneCoordenadas = s.TieneCoordenadas
                }).ToList();

                return new RespuestaDTO
                {
                    Exitoso = true,
                    Mensaje = "Sucursales obtenidas correctamente",
                    Datos = sucursalesResumen
                };
            }
            catch (Exception ex)
            {
                return new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error al obtener sucursales: {ex.Message}",
                    CodigoError = "SRV001"
                };
            }
        }

        /// <summary>
        /// Obtiene una sucursal por código
        /// </summary>
        public RespuestaDTO ObtenerSucursalPorCodigo(int codigo)
        {
            try
            {
                if (codigo <= 0)
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "El código de sucursal debe ser mayor a cero",
                        CodigoError = "VAL001"
                    };
                }

                var sucursal = sucursalDAO.ObtenerPorCodigo(codigo);
                
                if (!SucursalValidator.Existe(sucursal))
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Sucursal no encontrada",
                        CodigoError = "SUC001"
                    };
                }

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

                return new RespuestaDTO
                {
                    Exitoso = true,
                    Mensaje = "Sucursal encontrada",
                    Datos = sucursalDetalle
                };
            }
            catch (Exception ex)
            {
                return new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error al obtener sucursal: {ex.Message}",
                    CodigoError = "SRV001"
                };
            }
        }

        /// <summary>
        /// Crea una nueva sucursal
        /// </summary>
        public RespuestaDTO CrearSucursal(CrearSucursalDTO sucursalDTO)
        {
            try
            {
                if (sucursalDTO == null)
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Los datos de la sucursal son requeridos",
                        CodigoError = "VAL002"
                    };
                }

                // Crear entidad Sucursal (código se auto-genera)
                var sucursal = new Sucursal
                {
                    Codigo = 0, // Auto-generado en BD
                    Nombre = sucursalDTO.Nombre?.Trim(),
                    Ciudad = sucursalDTO.Ciudad?.Trim(),
                    Direccion = sucursalDTO.Direccion?.Trim(),
                    ContadorCuentas = 0,
                    Latitud = sucursalDTO.Latitud,
                    Longitud = sucursalDTO.Longitud
                };

                // Validar
                var (esValida, mensajeError) = SucursalValidator.ValidarParaCreacion(sucursal);
                if (!esValida)
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = mensajeError,
                        CodigoError = "VAL003"
                    };
                }

                // Insertar y obtener código generado
                int nuevoCodigo = sucursalDAO.Insertar(sucursal);
                if (nuevoCodigo <= 0)
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Error al crear la sucursal",
                        CodigoError = "SUC004"
                    };
                }

                // Obtener sucursal creada para respuesta completa
                var sucursalCreada = sucursalDAO.ObtenerPorCodigo(nuevoCodigo);

                // Respuesta con DTO
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

                return new RespuestaDTO
                {
                    Exitoso = true,
                    Mensaje = "Sucursal creada correctamente",
                    Datos = sucursalDetalle
                };
            }
            catch (Exception ex)
            {
                return new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error al crear sucursal: {ex.Message}",
                    CodigoError = "SRV001"
                };
            }
        }

        /// <summary>
        /// Actualiza una sucursal existente
        /// </summary>
        public RespuestaDTO ActualizarSucursal(int codigo, ActualizarSucursalDTO sucursalDTO)
        {
            try
            {
                if (codigo <= 0 || sucursalDTO == null)
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "El código debe ser mayor a cero y los datos de la sucursal son requeridos",
                        CodigoError = "VAL001"
                    };
                }

                // Verificar que existe
                var sucursalExistente = sucursalDAO.ObtenerPorCodigo(codigo);
                if (!SucursalValidator.Existe(sucursalExistente))
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Sucursal no encontrada",
                        CodigoError = "SUC001"
                    };
                }

                // Crear entidad actualizada
                var sucursalActualizada = new Sucursal
                {
                    Codigo = codigo,
                    Nombre = sucursalDTO.Nombre?.Trim(),
                    Ciudad = sucursalDTO.Ciudad?.Trim(),
                    Direccion = sucursalDTO.Direccion?.Trim(),
                    ContadorCuentas = sucursalExistente.ContadorCuentas,
                    Latitud = sucursalDTO.Latitud,
                    Longitud = sucursalDTO.Longitud
                };

                // Validar
                var (esValida, mensajeError) = SucursalValidator.ValidarParaActualizacion(sucursalActualizada);
                if (!esValida)
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = mensajeError,
                        CodigoError = "VAL003"
                    };
                }

                // Actualizar
                bool actualizado = sucursalDAO.Actualizar(sucursalActualizada);
                if (!actualizado)
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Error al actualizar la sucursal",
                        CodigoError = "SUC005"
                    };
                }

                // Respuesta con DTO
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

                return new RespuestaDTO
                {
                    Exitoso = true,
                    Mensaje = "Sucursal actualizada correctamente",
                    Datos = sucursalDetalle
                };
            }
            catch (Exception ex)
            {
                return new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error al actualizar sucursal: {ex.Message}",
                    CodigoError = "SRV001"
                };
            }
        }

        /// <summary>
        /// Elimina una sucursal
        /// </summary>
        public RespuestaDTO EliminarSucursal(int codigo)
        {
            try
            {
                if (codigo <= 0)
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "El código de sucursal debe ser mayor a cero",
                        CodigoError = "VAL001"
                    };
                }

                // Verificar que existe
                var sucursalExistente = sucursalDAO.ObtenerPorCodigo(codigo);
                if (!SucursalValidator.Existe(sucursalExistente))
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Sucursal no encontrada",
                        CodigoError = "SUC001"
                    };
                }

                // Validar que puede ser eliminada
                if (!SucursalValidator.PuedeSerEliminada(sucursalExistente))
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = $"No se puede eliminar la sucursal porque tiene {sucursalExistente.ContadorCuentas} cuentas asociadas",
                        CodigoError = "SUC007"
                    };
                }

                // Eliminar
                bool eliminado = sucursalDAO.Eliminar(codigo);
                if (!eliminado)
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Error al eliminar la sucursal",
                        CodigoError = "SUC006"
                    };
                }

                return new RespuestaDTO
                {
                    Exitoso = true,
                    Mensaje = "Sucursal eliminada correctamente"
                };
            }
            catch (Exception ex)
            {
                return new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error al eliminar sucursal: {ex.Message}",
                    CodigoError = "SRV001"
                };
            }
        }

        /// <summary>
        /// Actualiza coordenadas de una sucursal
        /// </summary>
        public RespuestaDTO ActualizarCoordenadas(int codigo, CoordenadasDTO coordenadas)
        {
            try
            {
                if (codigo <= 0 || coordenadas == null)
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "El código debe ser mayor a cero y las coordenadas son requeridos",
                        CodigoError = "VAL001"
                    };
                }

                // Verificar que existe
                var sucursalExistente = sucursalDAO.ObtenerPorCodigo(codigo);
                if (!SucursalValidator.Existe(sucursalExistente))
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Sucursal no encontrada",
                        CodigoError = "SUC001"
                    };
                }

                // Validar coordenadas
                if (!SucursalValidator.CoordenadasValidas(coordenadas.Latitud, coordenadas.Longitud))
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Las coordenadas proporcionadas no son válidas",
                        CodigoError = "VAL003"
                    };
                }

                // Actualizar coordenadas
                bool actualizado = sucursalDAO.ActualizarCoordenadas(codigo, coordenadas.Latitud, coordenadas.Longitud);
                if (!actualizado)
                {
                    return new RespuestaDTO
                    {
                        Exitoso = false,
                        Mensaje = "Error al actualizar las coordenadas",
                        CodigoError = "SUC002"
                    };
                }

                return new RespuestaDTO
                {
                    Exitoso = true,
                    Mensaje = "Coordenadas actualizadas correctamente"
                };
            }
            catch (Exception ex)
            {
                return new RespuestaDTO
                {
                    Exitoso = false,
                    Mensaje = $"Error al actualizar coordenadas: {ex.Message}",
                    CodigoError = "SRV001"
                };
            }
        }
    }
}