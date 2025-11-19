using System.ServiceModel;
using Comer_CliCon_SOAP_DotNet_GR01.Models;

namespace Comer_CliCon_SOAP_DotNet_GR01.ServiceClients
{
    public class FacturacionServiceClient : IDisposable
    {
        private readonly ChannelFactory<IFacturacionSoapService> _factory;
        private readonly IFacturacionSoapService _client;
        private const string ServiceUrl = "http://localhost:8006/WS/FacturacionSoapService.svc";

        public FacturacionServiceClient()
        {
            var binding = new BasicHttpBinding
            {
                MaxReceivedMessageSize = 2147483647,
                MaxBufferSize = 2147483647
            };
            var endpoint = new EndpointAddress(ServiceUrl);
            _factory = new ChannelFactory<IFacturacionSoapService>(binding, endpoint);
            _client = _factory.CreateChannel();
        }

        public CalculoFacturaDTO CalcularTotalFactura(SolicitudCalculoDTO solicitud) => _client.CalcularTotalFactura(solicitud);
        public FacturaDTO GenerarFactura(SolicitudFacturaDTO solicitud) => _client.GenerarFactura(solicitud);
        public List<FacturaDTO> ObtenerFacturasPorCliente(string cedula) => _client.ObtenerFacturasPorCliente(cedula);
        public FacturaDTO ObtenerFacturaPorNumero(string numeroFactura) => _client.ObtenerFacturaPorNumero(numeroFactura);

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
