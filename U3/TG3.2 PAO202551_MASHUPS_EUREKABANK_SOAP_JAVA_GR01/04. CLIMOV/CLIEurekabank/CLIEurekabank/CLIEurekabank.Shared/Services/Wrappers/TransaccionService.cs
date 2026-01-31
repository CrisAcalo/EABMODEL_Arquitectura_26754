using System.Net.Http;
using System.Text;
using System.Xml.Linq;
using CLIEurekabank.Shared.Config;

namespace CLIEurekabank.Shared.Services.Wrappers
{
    public class TransaccionService
    {
        private readonly HttpClient _httpClient;
        private readonly AppConfig _config;

        public TransaccionService(AppConfig config)
        {
            _config = config;
            _httpClient = new HttpClient();
            _httpClient.DefaultRequestHeaders.Add("SOAPAction", "");
        }

        public async Task<TransaccionResultado> RealizarDepositoAsync(string codigoCuenta, decimal monto, string claveCuenta, string codigoEmpleado)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:realizarDeposito>
         <datos>
            <codigoCuenta>{EscapeXml(codigoCuenta)}</codigoCuenta>
            <importe>{monto}</importe>
            <claveCuenta>{EscapeXml(claveCuenta)}</claveCuenta>
            <codigoEmpleado>{EscapeXml(codigoEmpleado)}</codigoEmpleado>
         </datos>
      </ws:realizarDeposito>
   </soapenv:Body>
</soapenv:Envelope>";

                return await EnviarTransaccion(soapEnvelope);
            }
            catch (Exception ex)
            {
                return new TransaccionResultado { Success = false, Message = $"Error: {ex.Message}" };
            }
        }

        public async Task<TransaccionResultado> RealizarRetiroAsync(string codigoCuenta, decimal monto, string claveCuenta, string codigoEmpleado)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:realizarRetiro>
         <datos>
            <codigoCuenta>{EscapeXml(codigoCuenta)}</codigoCuenta>
            <importe>{monto}</importe>
            <claveCuenta>{EscapeXml(claveCuenta)}</claveCuenta>
            <codigoEmpleado>{EscapeXml(codigoEmpleado)}</codigoEmpleado>
         </datos>
      </ws:realizarRetiro>
   </soapenv:Body>
</soapenv:Envelope>";

                return await EnviarTransaccion(soapEnvelope);
            }
            catch (Exception ex)
            {
                return new TransaccionResultado { Success = false, Message = $"Error: {ex.Message}" };
            }
        }

        public async Task<TransaccionResultado> RealizarTransferenciaAsync(
            string cuentaOrigen, string cuentaDestino, decimal monto, 
            string claveCuenta, string codigoEmpleado)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:realizarTransferencia>
         <datos>
            <cuentaOrigen>{EscapeXml(cuentaOrigen)}</cuentaOrigen>
            <cuentaDestino>{EscapeXml(cuentaDestino)}</cuentaDestino>
            <importe>{monto}</importe>
            <claveCuentaOrigen>{EscapeXml(claveCuenta)}</claveCuentaOrigen>
            <codigoEmpleado>{EscapeXml(codigoEmpleado)}</codigoEmpleado>
         </datos>
      </ws:realizarTransferencia>
   </soapenv:Body>
</soapenv:Envelope>";

                return await EnviarTransaccion(soapEnvelope);
            }
            catch (Exception ex)
            {
                return new TransaccionResultado { Success = false, Message = $"Error: {ex.Message}" };
            }
        }

        private async Task<TransaccionResultado> EnviarTransaccion(string soapEnvelope)
        {
            var content = new StringContent(soapEnvelope, Encoding.UTF8, "text/xml");
            var response = await _httpClient.PostAsync($"{_config.BaseUrl}/ServicioTransaccion", content);
            var responseBody = await response.Content.ReadAsStringAsync();

            return ParseRespuesta(responseBody);
        }

        private TransaccionResultado ParseRespuesta(string xml)
        {
            try
            {
                var doc = XDocument.Parse(xml);
                var returnElement = doc.Descendants().FirstOrDefault(e => e.Name.LocalName == "return");
                
                if (returnElement != null)
                {
                    var exitoso = returnElement.Element("exitoso")?.Value ?? "false";
                    var mensaje = returnElement.Element("mensaje")?.Value ?? "";
                    
                    bool success = exitoso.ToLower() == "true" || 
                                   mensaje.ToLower().Contains("exitosa") ||
                                   mensaje.ToLower().Contains("exito") ||
                                   mensaje.ToLower().Contains("correctamente");
                    
                    return new TransaccionResultado { Success = success, Message = mensaje };
                }
                return new TransaccionResultado { Success = false, Message = "Respuesta inválida" };
            }
            catch (Exception ex)
            {
                return new TransaccionResultado { Success = false, Message = $"Error: {ex.Message}" };
            }
        }

        private string EscapeXml(string value) => System.Security.SecurityElement.Escape(value) ?? value;
    }

    public class TransaccionResultado
    {
        public bool Success { get; set; }
        public string Message { get; set; } = "";
    }
}
