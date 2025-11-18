using System.Collections.Generic;
using System.ServiceModel;
using Comer_CliCon_SOAP_DotNet_GR01.Models;

namespace Comer_CliCon_SOAP_DotNet_GR01.ServiceClients
{
    [ServiceContract]
    public interface ICreditoSoapService
    {
        [OperationContract]
        ValidacionCreditoDTO ValidarSujetoCredito(string cedula);

        [OperationContract]
        MontoMaximoCreditoDTO ObtenerMontoMaximo(string cedula);

        [OperationContract]
        RespuestaCreditoDTO OtorgarCredito(SolicitudCreditoDTO solicitud);

        [OperationContract]
        List<CuotaAmortizacionDTO> ObtenerTablaAmortizacion(string numeroCredito);
    }
}
