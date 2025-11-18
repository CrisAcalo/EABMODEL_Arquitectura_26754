using System.ServiceModel;
using Comer_CliCon_SOAP_DotNet_GR01.Models;

namespace Comer_CliCon_SOAP_DotNet_GR01.ServiceClients
{
    public class CreditoServiceClient : IDisposable
    {
        private readonly ChannelFactory<ICreditoSoapService> _factory;
        private readonly ICreditoSoapService _client;
        private const string ServiceUrl = "http://localhost:8007/WS/CreditoSoapService.svc";

        public CreditoServiceClient()
        {
            var binding = new BasicHttpBinding
            {
                MaxReceivedMessageSize = 2147483647,
                MaxBufferSize = 2147483647
            };
            var endpoint = new EndpointAddress(ServiceUrl);
            _factory = new ChannelFactory<ICreditoSoapService>(binding, endpoint);
            _client = _factory.CreateChannel();
        }

        public ValidacionCreditoDTO ValidarSujetoCredito(string cedula) => _client.ValidarSujetoCredito(cedula);
        public MontoMaximoCreditoDTO ObtenerMontoMaximo(string cedula) => _client.ObtenerMontoMaximo(cedula);
        public RespuestaCreditoDTO OtorgarCredito(SolicitudCreditoDTO solicitud) => _client.OtorgarCredito(solicitud);
        public List<CuotaAmortizacionDTO> ObtenerTablaAmortizacion(string numeroCredito) => _client.ObtenerTablaAmortizacion(numeroCredito);

        public void Dispose()
        {
            try
            {
                if (_client is IClientChannel channel)
                {
                    if (channel.State == CommunicationState.Faulted)
                        channel.Abort();
                    else
                        channel.Close();
                }
                _factory?.Close();
            }
            catch
            {
                _factory?.Abort();
            }
        }
    }
}
