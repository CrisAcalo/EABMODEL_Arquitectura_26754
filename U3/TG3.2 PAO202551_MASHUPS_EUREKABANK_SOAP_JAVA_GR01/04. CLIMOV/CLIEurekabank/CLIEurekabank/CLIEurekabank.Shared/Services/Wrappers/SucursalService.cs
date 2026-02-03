using System.Net.Http;
using System.Text;
using System.Xml.Linq;
using CLIEurekabank.Shared.Config;

namespace CLIEurekabank.Shared.Services.Wrappers
{
    public class SucursalService
    {
        private readonly HttpClient _httpClient;
        private readonly AppConfig _config;

        public SucursalService(AppConfig config)
        {
            _config = config;
            _httpClient = new HttpClient();
            _httpClient.DefaultRequestHeaders.Add("SOAPAction", "");
        }

        public async Task<List<SucursalDTO>> ListarSucursalesAsync()
        {
            try
            {
                var soapEnvelope = @"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:listarSucursales/>
   </soapenv:Body>
</soapenv:Envelope>";

                var content = new StringContent(soapEnvelope, Encoding.UTF8, "text/xml");
                var response = await _httpClient.PostAsync($"{_config.BaseUrl}/ServicioSucursal", content);
                var responseBody = await response.Content.ReadAsStringAsync();

                return ParseListarSucursalesResponse(responseBody);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error listando sucursales: {ex.Message}");
                return new List<SucursalDTO>();
            }
        }

        private List<SucursalDTO> ParseListarSucursalesResponse(string xml)
        {
            var sucursales = new List<SucursalDTO>();
            try
            {
                var doc = XDocument.Parse(xml);
                var returnElements = doc.Descendants()
                    .Where(e => e.Name.LocalName == "return");

                foreach (var element in returnElements)
                {
                    sucursales.Add(new SucursalDTO
                    {
                        Codigo = element.Element("codigo")?.Value ?? "",
                        Nombre = element.Element("nombre")?.Value ?? "",
                        Ciudad = element.Element("ciudad")?.Value ?? "",
                        Direccion = element.Element("direccion")?.Value ?? ""
                    });
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error parseando sucursales: {ex.Message}");
            }
            return sucursales;
        }
    }

    public class SucursalDTO
    {
        public string Codigo { get; set; } = "";
        public string Nombre { get; set; } = "";
        public string Ciudad { get; set; } = "";
        public string Direccion { get; set; } = "";
    }
}
