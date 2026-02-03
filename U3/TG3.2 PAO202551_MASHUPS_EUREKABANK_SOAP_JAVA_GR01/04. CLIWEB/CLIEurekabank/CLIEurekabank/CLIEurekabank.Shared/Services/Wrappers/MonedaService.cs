using System.Net.Http;
using System.Text;
using System.Xml.Linq;
using CLIEurekabank.Shared.Config;

namespace CLIEurekabank.Shared.Services.Wrappers
{
    public class MonedaService
    {
        private readonly HttpClient _httpClient;
        private readonly AppConfig _config;

        public MonedaService(AppConfig config)
        {
            _config = config;
            _httpClient = new HttpClient();
            _httpClient.DefaultRequestHeaders.Add("SOAPAction", "");
        }

        public async Task<List<MonedaDTO>> ListarMonedasAsync()
        {
            try
            {
                var soapEnvelope = @"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:listarMonedas/>
   </soapenv:Body>
</soapenv:Envelope>";

                var content = new StringContent(soapEnvelope, Encoding.UTF8, "text/xml");
                var response = await _httpClient.PostAsync($"{_config.BaseUrl}/ServicioMoneda", content);
                var responseBody = await response.Content.ReadAsStringAsync();

                return ParseListarMonedasResponse(responseBody);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error listando monedas: {ex.Message}");
                return new List<MonedaDTO>();
            }
        }

        private List<MonedaDTO> ParseListarMonedasResponse(string xml)
        {
            var monedas = new List<MonedaDTO>();
            try
            {
                var doc = XDocument.Parse(xml);
                var returnElements = doc.Descendants()
                    .Where(e => e.Name.LocalName == "return");

                foreach (var element in returnElements)
                {
                    monedas.Add(new MonedaDTO
                    {
                        Codigo = element.Element("codigo")?.Value ?? "",
                        Descripcion = element.Element("descripcion")?.Value ?? ""
                    });
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error parseando monedas: {ex.Message}");
            }
            return monedas;
        }
    }

    public class MonedaDTO
    {
        public string Codigo { get; set; } = "";
        public string Descripcion { get; set; } = "";
    }
}
