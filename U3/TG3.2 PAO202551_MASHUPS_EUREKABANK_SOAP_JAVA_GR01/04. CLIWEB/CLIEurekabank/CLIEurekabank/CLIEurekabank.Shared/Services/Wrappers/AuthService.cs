using System.Net.Http;
using System.Text;
using System.Xml.Linq;
using CLIEurekabank.Shared.Config;

namespace CLIEurekabank.Shared.Services.Wrappers
{
    public class AuthService
    {
        private readonly HttpClient _httpClient;
        private readonly AppConfig _config;

        public AuthService(AppConfig config)
        {
            _config = config;
            _httpClient = new HttpClient();
            _httpClient.DefaultRequestHeaders.Add("SOAPAction", "");
        }

        public async Task<(bool Success, string Message, EmpleadoDTO? Empleado)> LoginAsync(string usuario, string clave)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:login>
         <usuario>{EscapeXml(usuario)}</usuario>
         <clave>{EscapeXml(clave)}</clave>
      </ws:login>
   </soapenv:Body>
</soapenv:Envelope>";

                var content = new StringContent(soapEnvelope, Encoding.UTF8, "text/xml");
                var response = await _httpClient.PostAsync($"{_config.BaseUrl}/ServicioAutenticacion", content);
                var responseBody = await response.Content.ReadAsStringAsync();

                return ParseLoginResponse(responseBody);
            }
            catch (Exception ex)
            {
                return (false, $"Error de conexión: {ex.Message}", null);
            }
        }

        private (bool Success, string Message, EmpleadoDTO? Empleado) ParseLoginResponse(string xml)
        {
            try
            {
                var doc = XDocument.Parse(xml);
                var returnElement = doc.Descendants().FirstOrDefault(e => e.Name.LocalName == "return");

                if (returnElement != null)
                {
                    var exitoso = returnElement.Element("exitoso")?.Value ?? "false";
                    var mensaje = returnElement.Element("mensaje")?.Value ?? "";

                    bool isSuccess = exitoso.ToLower() == "true" || 
                                     mensaje.ToLower().Contains("exitosa") ||
                                     mensaje.ToLower().Contains("correctamente");

                    if (isSuccess)
                    {
                        var datosElement = returnElement.Element("datos");
                        EmpleadoDTO? empleado = null;
                        
                        if (datosElement != null)
                        {
                            empleado = new EmpleadoDTO
                            {
                                Codigo = datosElement.Element("codigo")?.Value ?? "",
                                Usuario = datosElement.Element("usuario")?.Value ?? "",
                                Nombre = datosElement.Element("nombre")?.Value ?? "",
                                Paterno = datosElement.Element("paterno")?.Value ?? "",
                                Materno = datosElement.Element("materno")?.Value ?? "",
                                CodigoSucursal = datosElement.Element("codigoSucursal")?.Value ?? "",
                                CodigoVentanilla = datosElement.Element("codigoVentanilla")?.Value ?? ""
                            };
                        }

                        return (true, mensaje, empleado);
                    }
                    
                    return (false, mensaje, null);
                }

                return (false, "Respuesta inválida del servidor", null);
            }
            catch (Exception ex)
            {
                return (false, $"Error parseando respuesta: {ex.Message}", null);
            }
        }

        private string EscapeXml(string value)
        {
            return System.Security.SecurityElement.Escape(value) ?? value;
        }
    }
}
