using System.Net.Http;
using System.Text;
using System.Xml.Linq;
using CLIEurekabank.Shared.Config;

namespace CLIEurekabank.Shared.Services.Wrappers
{
    public class ReporteService
    {
        private readonly HttpClient _httpClient;
        private readonly AppConfig _config;

        public ReporteService(AppConfig config)
        {
            _config = config;
            _httpClient = new HttpClient();
            _httpClient.DefaultRequestHeaders.Add("SOAPAction", "");
        }

        public async Task<List<MovimientoDTO>> ObtenerMovimientosAsync(string codigoCuenta)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:obtenerMovimientos>
         <codigoCuenta>{EscapeXml(codigoCuenta)}</codigoCuenta>
      </ws:obtenerMovimientos>
   </soapenv:Body>
</soapenv:Envelope>";

                var content = new StringContent(soapEnvelope, Encoding.UTF8, "text/xml");
                var response = await _httpClient.PostAsync($"{_config.BaseUrl}/ServicioReporte", content);
                var responseBody = await response.Content.ReadAsStringAsync();

                return ParseMovimientosResponse(responseBody);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error obteniendo movimientos: {ex.Message}");
                return new List<MovimientoDTO>();
            }
        }

        private List<MovimientoDTO> ParseMovimientosResponse(string xml)
        {
            var movimientos = new List<MovimientoDTO>();
            try
            {
                var doc = XDocument.Parse(xml);
                var returnElements = doc.Descendants().Where(e => e.Name.LocalName == "return");
                
                foreach (var element in returnElements)
                {
                    var mov = ParseMovimientoElement(element);
                    if (mov != null)
                    {
                        movimientos.Add(mov);
                    }
                }
            }
            catch { }
            return movimientos;
        }

        private MovimientoDTO? ParseMovimientoElement(XElement element)
        {
            var montoStr = element.Element("monto")?.Value ?? element.Element("importe")?.Value;
            decimal.TryParse(montoStr, System.Globalization.NumberStyles.Any, System.Globalization.CultureInfo.InvariantCulture, out var monto);

            var saldoStr = element.Element("saldoResultante")?.Value ?? element.Element("saldo")?.Value;
            decimal.TryParse(saldoStr, System.Globalization.NumberStyles.Any, System.Globalization.CultureInfo.InvariantCulture, out var saldo);

            return new MovimientoDTO
            {
                NumeroMovimiento = element.Element("numeroMovimiento")?.Value ?? 
                                   element.Element("numero")?.Value ?? "",
                Fecha = element.Element("fecha")?.Value ?? "",
                Tipo = element.Element("tipo")?.Value ?? 
                       element.Element("tipoMovimiento")?.Value ?? "",
                Monto = monto,
                SaldoResultante = saldo,
                Referencia = element.Element("referencia")?.Value ?? 
                             element.Element("descripcion")?.Value ?? ""
            };
        }

        private string EscapeXml(string value) => System.Security.SecurityElement.Escape(value) ?? value;
    }

    public class MovimientoDTO
    {
        public string NumeroMovimiento { get; set; } = "";
        public string Fecha { get; set; } = "";
        public string Tipo { get; set; } = "";
        public decimal Monto { get; set; }
        public decimal SaldoResultante { get; set; }
        public string Referencia { get; set; } = "";

        public string TipoDisplay => Tipo switch
        {
            "D" => "Depósito",
            "R" => "Retiro",
            "TI" => "Trans. Entrada",
            "TS" => "Trans. Salida",
            _ => Tipo
        };

        public bool EsIngreso => Tipo == "D" || Tipo == "TI" || 
                                 Tipo.ToUpper().Contains("DEPÓSITO") || 
                                 Tipo.ToUpper().Contains("DEPOSITO") ||
                                 Tipo.ToUpper().Contains("ENTRADA");
    }
}
