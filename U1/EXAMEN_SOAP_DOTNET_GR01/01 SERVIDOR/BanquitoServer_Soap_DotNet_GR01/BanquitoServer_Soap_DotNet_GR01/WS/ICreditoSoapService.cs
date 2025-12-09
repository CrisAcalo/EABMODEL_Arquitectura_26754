using System.Collections.Generic;
using System.ServiceModel;
using BanquitoServer_Soap_DotNet_GR01.DTOs;

namespace BanquitoServer_Soap_DotNet_GR01.WS
{
    /// <summary>
    /// Contrato del servicio SOAP de Crédito
    /// Define los 4 Web Services requeridos
    /// </summary>
    [ServiceContract]
    public interface ICreditoSoapService
    {
        /// <summary>
        /// WS 1: Validar si una persona es sujeto de crédito
        /// </summary>
        [OperationContract]
        ValidacionCreditoDTO ValidarSujetoCredito(string cedula);

        /// <summary>
        /// WS 2: Obtener el monto máximo de crédito para una persona
        /// </summary>
        [OperationContract]
        MontoMaximoCreditoDTO ObtenerMontoMaximo(string cedula);

        /// <summary>
        /// WS 3: Otorgar un crédito y generar tabla de amortización
        /// </summary>
        [OperationContract]
        RespuestaCreditoDTO OtorgarCredito(SolicitudCreditoDTO solicitud);

        /// <summary>
        /// WS 4: Obtener la tabla de amortización de un crédito
        /// </summary>
        [OperationContract]
        List<CuotaAmortizacionDTO> ObtenerTablaAmortizacion(string numeroCredito);
    }
}
