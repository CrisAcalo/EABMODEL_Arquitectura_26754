using System;
using System.Collections.Generic;
using System.ServiceModel;
using EurekaBank_Soap_DotNet_GR01.Models.DTOs;

namespace EurekaBank_Soap_DotNet_GR01.WS
{
    /// <summary>
    /// Contrato de servicio para reportes
    /// </summary>
    [ServiceContract]
    public interface IServicioReporte
    {
        /// <summary>
        /// Obtiene todos los movimientos de una cuenta ordenados por fecha descendente
        /// </summary>
        /// <param name="codigoCuenta">Código de la cuenta</param>
        /// <returns>Lista de movimientos con información detallada</returns>
        [OperationContract]
        List<MovimientoDetalleDTO> ObtenerMovimientos(string codigoCuenta);

        /// <summary>
        /// Obtiene los movimientos de una cuenta en un rango de fechas
        /// </summary>
        /// <param name="codigoCuenta">Código de la cuenta</param>
        /// <param name="fechaInicio">Fecha de inicio del rango</param>
        /// <param name="fechaFin">Fecha de fin del rango</param>
        /// <returns>Lista de movimientos con información detallada</returns>
        [OperationContract]
        List<MovimientoDetalleDTO> ObtenerMovimientosPorRango(string codigoCuenta, DateTime fechaInicio, DateTime fechaFin);
    }
}
