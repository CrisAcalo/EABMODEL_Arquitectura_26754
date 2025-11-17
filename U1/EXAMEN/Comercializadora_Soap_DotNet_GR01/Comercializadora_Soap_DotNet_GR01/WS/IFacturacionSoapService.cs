using System.Collections.Generic;
using System.ServiceModel;
using Comercializadora_Soap_DotNet_GR01.DTOs;

namespace Comercializadora_Soap_DotNet_GR01.WS
{
    [ServiceContract]
    public interface IFacturacionSoapService
    {
        [OperationContract]
        FacturaDTO GenerarFacturaEfectivo(SolicitudFacturaDTO solicitud);

        [OperationContract]
        FacturaDTO GenerarFacturaCredito(SolicitudFacturaDTO solicitud);

        [OperationContract]
        List<FacturaDTO> ObtenerFacturasPorCliente(string cedula);

        [OperationContract]
        FacturaDTO ObtenerFacturaPorNumero(string numeroFactura);
    }
}