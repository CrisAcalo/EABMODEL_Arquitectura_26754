using EurekaBank.Core.Models.DTOs;
using EurekaBank.Core.Models.Requests;
using EurekaBank.Core.Models.Responses;

namespace EurekaBank.Core.Services.Abstractions
{
    public interface ISucursalService
    {
        /// <summary>
        /// Obtiene todas las sucursales disponibles
        /// </summary>
        /// <returns>Lista de sucursales</returns>
        Task<SucursalesListResponse> ObtenerSucursalesAsync();

        /// <summary>
        /// Obtiene una sucursal específica por su código
        /// </summary>
        /// <param name="codigo">Código de la sucursal</param>
        /// <returns>Datos de la sucursal</returns>
        Task<SucursalResponse> ObtenerSucursalAsync(int codigo);

        /// <summary>
        /// Crea una nueva sucursal
        /// </summary>
        /// <param name="request">Datos de la sucursal a crear</param>
        /// <returns>Sucursal creada</returns>
        Task<SucursalResponse> CrearSucursalAsync(CreateSucursalRequest request);

        /// <summary>
        /// Actualiza los datos de una sucursal existente
        /// </summary>
        /// <param name="codigo">Código de la sucursal</param>
        /// <param name="request">Datos a actualizar</param>
        /// <returns>Sucursal actualizada</returns>
        Task<SucursalResponse> ActualizarSucursalAsync(int codigo, UpdateSucursalRequest request);

        /// <summary>
        /// Elimina una sucursal
        /// </summary>
        /// <param name="codigo">Código de la sucursal a eliminar</param>
        /// <returns>Resultado de la operación</returns>
        Task<DeleteSucursalResponse> EliminarSucursalAsync(int codigo);
    }
}