using System.Net.Http;
using System.Text;
using System.Xml.Linq;
using CLIEurekabank.Shared.Config;

namespace CLIEurekabank.Shared.Services.Wrappers
{
    public class VentanillaService
    {
        private readonly HttpClient _httpClient;
        private readonly AppConfig _config;

        public VentanillaService(AppConfig config)
        {
            _config = config;
            _httpClient = new HttpClient();
            _httpClient.DefaultRequestHeaders.Add("SOAPAction", "");
        }

        public async Task<List<VentanillaDTO>> ListarVentanillasActivasAsync()
        {
            try
            {
                var soapEnvelope = @"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:listarVentanillasActivas/>
   </soapenv:Body>
</soapenv:Envelope>";

                var content = new StringContent(soapEnvelope, Encoding.UTF8, "text/xml");
                var response = await _httpClient.PostAsync($"{_config.BaseUrl}/ServicioVentanilla", content);
                var responseBody = await response.Content.ReadAsStringAsync();

                return ParseListarVentanillasResponse(responseBody);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error listando ventanillas activas: {ex.Message}");
                return new List<VentanillaDTO>();
            }
        }

        private List<VentanillaDTO> ParseListarVentanillasResponse(string xml)
        {
            var ventanillas = new List<VentanillaDTO>();
            try
            {
                var doc = XDocument.Parse(xml);
                // Buscar elementos 'return' independientemente del namespace
                var returnElements = doc.Descendants()
                    .Where(e => e.Name.LocalName == "return");

                foreach (var element in returnElements)
                {
                    ventanillas.Add(new VentanillaDTO
                    {
                        Codigo = element.Element("codigo")?.Value ?? "",
                        Nombre = element.Element("nombre")?.Value ?? "",
                        CodigoEmpleado = element.Element("codigoEmpleado")?.Value ?? "",
                        Estado = element.Element("estado")?.Value ?? ""
                    });
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error parseando ventanillas: {ex.Message}");
            }
            return ventanillas;
        }
    }

    public class VentanillaDTO
    {
        public string Codigo { get; set; } = "";
        public string Nombre { get; set; } = "";
        public string CodigoEmpleado { get; set; } = "";
        public string Estado { get; set; } = "";
    }
}
