using System.Collections.Generic;
using System.ServiceModel;
using Comercializadora_Soap_DotNet_GR01.DTOs;

namespace Comercializadora_Soap_DotNet_GR01.WS
{
    [ServiceContract]
    public interface IFacturacionSoapService
    {
        /// <summary>
        /// Calcula el total de una factura SIN generarla
        /// Útil para conocer el monto ANTES de solicitar crédito
        /// </summary>
        [OperationContract]
        CalculoFacturaDTO CalcularTotalFactura(SolicitudCalculoDTO solicitud);

        [OperationContract]
        FacturaDTO GenerarFactura(SolicitudFacturaDTO solicitud);

        [OperationContract]
        List<FacturaDTO> ObtenerFacturasPorCliente(string cedula);

        [OperationContract]
        FacturaDTO ObtenerFacturaPorNumero(string numeroFactura);
    }
}