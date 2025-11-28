using System.ServiceModel;
using Comer_CliCon_SOAP_DotNet_GR01.Models;

namespace Comer_CliCon_SOAP_DotNet_GR01.ServiceClients
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
