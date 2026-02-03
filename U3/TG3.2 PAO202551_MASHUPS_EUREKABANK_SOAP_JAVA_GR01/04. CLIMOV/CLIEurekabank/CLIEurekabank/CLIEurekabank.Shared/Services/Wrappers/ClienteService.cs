using System.Net.Http;
using System.Text;
using System.Xml.Linq;
using CLIEurekabank.Shared.Config;

namespace CLIEurekabank.Shared.Services.Wrappers
{
    public class ClienteService
    {
        private readonly HttpClient _httpClient;
        private readonly AppConfig _config;

        public ClienteService(AppConfig config)
        {
            _config = config;
            _httpClient = new HttpClient();
            _httpClient.DefaultRequestHeaders.Add("SOAPAction", "");
        }

        public async Task<List<ClienteDTO>> ListarClientesAsync()
        {
            try
            {
                var soapEnvelope = @"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:listarClientes/>
   </soapenv:Body>
</soapenv:Envelope>";

                var content = new StringContent(soapEnvelope, Encoding.UTF8, "text/xml");
                var response = await _httpClient.PostAsync($"{_config.BaseUrl}/ServicioCliente", content);
                var responseBody = await response.Content.ReadAsStringAsync();

                return ParseClientesListResponse(responseBody);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error listando clientes: {ex.Message}");
                return new List<ClienteDTO>();
            }
        }

        public async Task<ClienteDTO?> ObtenerClienteAsync(string codigo)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:obtenerCliente>
         <codigo>{EscapeXml(codigo)}</codigo>
      </ws:obtenerCliente>
   </soapenv:Body>
</soapenv:Envelope>";

                var content = new StringContent(soapEnvelope, Encoding.UTF8, "text/xml");
                var response = await _httpClient.PostAsync($"{_config.BaseUrl}/ServicioCliente", content);
                var responseBody = await response.Content.ReadAsStringAsync();

                return ParseClienteResponse(responseBody);
            }
            catch
            {
                return null;
            }
        }

        public async Task<ClienteDTO?> ObtenerClientePorDNIAsync(string dni)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:obtenerClientePorDNI>
         <dni>{EscapeXml(dni)}</dni>
      </ws:obtenerClientePorDNI>
   </soapenv:Body>
</soapenv:Envelope>";

                var content = new StringContent(soapEnvelope, Encoding.UTF8, "text/xml");
                var response = await _httpClient.PostAsync($"{_config.BaseUrl}/ServicioCliente", content);
                var responseBody = await response.Content.ReadAsStringAsync();

                return ParseClienteResponse(responseBody);
            }
            catch
            {
                return null;
            }
        }

        public async Task<OperacionResultado> RegistrarClienteAsync(ClienteDTO cliente)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:registrarCliente>
         <cliente>
            <dni>{EscapeXml(cliente.Dni)}</dni>
            <paterno>{EscapeXml(cliente.Paterno)}</paterno>
            <materno>{EscapeXml(cliente.Materno)}</materno>
            <nombre>{EscapeXml(cliente.Nombre)}</nombre>
            <direccion>{EscapeXml(cliente.Direccion)}</direccion>
            <ciudad>{EscapeXml(cliente.Ciudad)}</ciudad>
            <telefono>{EscapeXml(cliente.Telefono)}</telefono>
            <email>{EscapeXml(cliente.Email)}</email>
         </cliente>
      </ws:registrarCliente>
   </soapenv:Body>
</soapenv:Envelope>";

                return await EnviarOperacion(soapEnvelope);
            }
            catch (Exception ex)
            {
                return new OperacionResultado { Success = false, Message = $"Error: {ex.Message}" };
            }
        }

        public async Task<OperacionResultado> ActualizarClienteAsync(ClienteDTO cliente)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:actualizarCliente>
         <cliente>
            <codigo>{EscapeXml(cliente.Codigo)}</codigo>
            <dni>{EscapeXml(cliente.Dni)}</dni>
            <paterno>{EscapeXml(cliente.Paterno)}</paterno>
            <materno>{EscapeXml(cliente.Materno)}</materno>
            <nombre>{EscapeXml(cliente.Nombre)}</nombre>
            <direccion>{EscapeXml(cliente.Direccion)}</direccion>
            <ciudad>{EscapeXml(cliente.Ciudad)}</ciudad>
            <telefono>{EscapeXml(cliente.Telefono)}</telefono>
            <email>{EscapeXml(cliente.Email)}</email>
         </cliente>
      </ws:actualizarCliente>
   </soapenv:Body>
</soapenv:Envelope>";

                return await EnviarOperacion(soapEnvelope);
            }
            catch (Exception ex)
            {
                return new OperacionResultado { Success = false, Message = $"Error: {ex.Message}" };
            }
        }

        public async Task<OperacionResultado> EliminarClienteAsync(string codigo)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:eliminarCliente>
         <codigo>{EscapeXml(codigo)}</codigo>
      </ws:eliminarCliente>
   </soapenv:Body>
</soapenv:Envelope>";

                return await EnviarOperacion(soapEnvelope);
            }
            catch (Exception ex)
            {
                return new OperacionResultado { Success = false, Message = $"Error: {ex.Message}" };
            }
        }

        private async Task<OperacionResultado> EnviarOperacion(string soapEnvelope)
        {
            var content = new StringContent(soapEnvelope, Encoding.UTF8, "text/xml");
            var response = await _httpClient.PostAsync($"{_config.BaseUrl}/ServicioCliente", content);
            var responseBody = await response.Content.ReadAsStringAsync();

            try
            {
                var doc = XDocument.Parse(responseBody);
                var returnElement = doc.Descendants().FirstOrDefault(e => e.Name.LocalName == "return");
                
                if (returnElement != null)
                {
                    var exitoso = returnElement.Element("exitoso")?.Value ?? "false";
                    var mensaje = returnElement.Element("mensaje")?.Value ?? "";
                    
                    bool success = exitoso.ToLower() == "true" || 
                                   mensaje.ToLower().Contains("exitosa") ||
                                   mensaje.ToLower().Contains("exito") ||
                                   mensaje.ToLower().Contains("correctamente");
                    
                    return new OperacionResultado { Success = success, Message = mensaje };
                }
                return new OperacionResultado { Success = false, Message = "Respuesta inválida" };
            }
            catch (Exception ex)
            {
                return new OperacionResultado { Success = false, Message = $"Error: {ex.Message}" };
            }
        }

        private ClienteDTO? ParseClienteResponse(string xml)
        {
            try
            {
                var doc = XDocument.Parse(xml);
                var returnElement = doc.Descendants().FirstOrDefault(e => e.Name.LocalName == "return");
                
                if (returnElement != null)
                {
                    var resultado = returnElement.Element("resultado");
                    if (resultado != null)
                    {
                        return ParseClienteElement(resultado);
                    }
                }
                return null;
            }
            catch
            {
                return null;
            }
        }

        private List<ClienteDTO> ParseClientesListResponse(string xml)
        {
            var clientes = new List<ClienteDTO>();
            try
            {
                var doc = XDocument.Parse(xml);
                var returnElements = doc.Descendants().Where(e => e.Name.LocalName == "return");
                
                foreach (var element in returnElements)
                {
                    var cliente = ParseClienteElement(element);
                    if (cliente != null)
                    {
                        clientes.Add(cliente);
                    }
                }
            }
            catch { }
            return clientes;
        }

        private ClienteDTO? ParseClienteElement(XElement element)
        {
            var codigo = element.Element("codigo")?.Value;
            if (string.IsNullOrEmpty(codigo)) return null;

            return new ClienteDTO
            {
                Codigo = codigo,
                Dni = element.Element("dni")?.Value ?? "",
                Paterno = element.Element("paterno")?.Value ?? "",
                Materno = element.Element("materno")?.Value ?? "",
                Nombre = element.Element("nombre")?.Value ?? "",
                Direccion = element.Element("direccion")?.Value ?? "",
                Ciudad = element.Element("ciudad")?.Value ?? "",
                Telefono = element.Element("telefono")?.Value ?? "",
                Email = element.Element("email")?.Value ?? ""
            };
        }

        private string EscapeXml(string value) => System.Security.SecurityElement.Escape(value) ?? value;
    }

    public class ClienteDTO
    {
        public string Codigo { get; set; } = "";
        public string Dni { get; set; } = "";
        public string Paterno { get; set; } = "";
        public string Materno { get; set; } = "";
        public string Nombre { get; set; } = "";
        public string Direccion { get; set; } = "";
        public string Ciudad { get; set; } = "";
        public string Telefono { get; set; } = "";
        public string Email { get; set; } = "";

        public string NombreCompleto => $"{Nombre} {Paterno} {Materno}".Trim();
    }

    public class OperacionResultado
    {
        public bool Success { get; set; }
        public string Message { get; set; } = "";
    }
}
