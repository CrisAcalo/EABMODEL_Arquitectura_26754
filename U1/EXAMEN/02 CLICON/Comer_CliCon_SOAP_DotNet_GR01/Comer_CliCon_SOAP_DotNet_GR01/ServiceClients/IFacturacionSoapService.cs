using System.ServiceModel;
using Comer_CliCon_SOAP_DotNet_GR01.Models;

namespace Comer_CliCon_SOAP_DotNet_GR01.ServiceClients
{
    [ServiceContract]
    public interface IFacturacionSoapService
    {
        [OperationContract]
        CalculoFacturaDTO CalcularTotalFactura(SolicitudFacturaDTO solicitud);

        [OperationContract]
        FacturaDTO GenerarFactura(SolicitudFacturaDTO solicitud);

        [OperationContract]
        List<FacturaDTO> ObtenerFacturasPorCliente(string cedula);

        [OperationContract]
        FacturaDTO ObtenerFacturaPorNumero(string numeroFactura);
    }
}
