using System.Net.Http;
using System.Text;
using System.Xml.Linq;
using CLIEurekabank.Shared.Config;

namespace CLIEurekabank.Shared.Services.Wrappers
{
    public class CuentaService
    {
        private readonly HttpClient _httpClient;
        private readonly AppState _appState;
        private readonly AppConfig _config;

        public CuentaService(AppState appState, AppConfig config)
        {
            _config = config;
            _httpClient = new HttpClient();
            _httpClient.DefaultRequestHeaders.Add("SOAPAction", "");
            _appState = appState;
        }

        public async Task<CuentaDTO?> ObtenerCuentaAsync(string codigoCuenta)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:obtenerCuenta>
         <codigo>{EscapeXml(codigoCuenta)}</codigo>
      </ws:obtenerCuenta>
   </soapenv:Body>
</soapenv:Envelope>";

                var content = new StringContent(soapEnvelope, Encoding.UTF8, "text/xml");
                var response = await _httpClient.PostAsync($"{_config.BaseUrl}/ServicioCuenta", content);
                var responseBody = await response.Content.ReadAsStringAsync();

                return ParseCuentaResponse(responseBody);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error obteniendo cuenta: {ex.Message}");
                return null;
            }
        }

        public async Task<decimal> ObtenerSaldoAsync(string codigoCuenta)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:obtenerSaldo>
         <codigo>{EscapeXml(codigoCuenta)}</codigo>
      </ws:obtenerSaldo>
   </soapenv:Body>
</soapenv:Envelope>";

                var content = new StringContent(soapEnvelope, Encoding.UTF8, "text/xml");
                var response = await _httpClient.PostAsync($"{_config.BaseUrl}/ServicioCuenta", content);
                var responseBody = await response.Content.ReadAsStringAsync();

                var doc = XDocument.Parse(responseBody);
                var returnElement = doc.Descendants().FirstOrDefault(e => e.Name.LocalName == "return");
                
                if (returnElement != null && decimal.TryParse(returnElement.Value, out var saldo))
                {
                    return saldo;
                }
                return 0;
            }
            catch
            {
                return 0;
            }
        }

        public async Task<List<CuentaDTO>> ListarCuentasActivasAsync()
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:listarCuentasActivas/>
   </soapenv:Body>
</soapenv:Envelope>";

                var content = new StringContent(soapEnvelope, Encoding.UTF8, "text/xml");
                var response = await _httpClient.PostAsync($"{_config.BaseUrl}/ServicioCuenta", content);
                var responseBody = await response.Content.ReadAsStringAsync();

                return ParseCuentasListResponse(responseBody);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error listando cuentas: {ex.Message}");
                return new List<CuentaDTO>();
            }
        }

        public async Task<OperacionResultado> AbrirCuentaAsync(string codigoCliente, string codigoMoneda, string codigoSucursal, string clave, decimal saldoInicial = 0)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:abrirCuenta>
         <cuenta>
            <codigoCliente>{EscapeXml(codigoCliente)}</codigoCliente>
            <codigoMoneda>{EscapeXml(codigoMoneda)}</codigoMoneda>
            <codigoSucursal>{EscapeXml(codigoSucursal)}</codigoSucursal>
            <codigoEmpleadoCreador>{EscapeXml(_appState.CodigoEmpleado)}</codigoEmpleadoCreador>
            <clave>{EscapeXml(clave)}</clave>
            <saldo>{saldoInicial}</saldo>
         </cuenta>
      </ws:abrirCuenta>
   </soapenv:Body>
</soapenv:Envelope>";

                return await EnviarOperacion(soapEnvelope);
            }
            catch (Exception ex)
            {
                return new OperacionResultado { Success = false, Message = $"Error: {ex.Message}" };
            }
        }

        public async Task<OperacionResultado> CancelarCuentaAsync(string codigoCuenta)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:cancelarCuenta>
         <codigo>{EscapeXml(codigoCuenta)}</codigo>
      </ws:cancelarCuenta>
   </soapenv:Body>
</soapenv:Envelope>";

                return await EnviarOperacion(soapEnvelope);
            }
            catch (Exception ex)
            {
                return new OperacionResultado { Success = false, Message = $"Error: {ex.Message}" };
            }
        }

        public async Task<OperacionResultado> ActualizarCuentaAsync(CuentaDTO cuenta, string? nuevaClave = null)
        {
            try
            {
                var claveXml = !string.IsNullOrEmpty(nuevaClave) ? $"<clave>{EscapeXml(nuevaClave)}</clave>" : "";
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:actualizarCuenta>
         <cuenta>
            <codigo>{EscapeXml(cuenta.Codigo)}</codigo>
            <codigoCliente>{EscapeXml(cuenta.CodigoCliente)}</codigoCliente>
            <codigoMoneda>{EscapeXml(cuenta.CodigoMoneda)}</codigoMoneda>
            <codigoSucursal>{EscapeXml(cuenta.CodigoSucursal)}</codigoSucursal>
            <estado>{EscapeXml(cuenta.Estado)}</estado>
            {claveXml}
         </cuenta>
      </ws:actualizarCuenta>
   </soapenv:Body>
</soapenv:Envelope>";

                return await EnviarOperacion(soapEnvelope);
            }
            catch (Exception ex)
            {
                return new OperacionResultado { Success = false, Message = $"Error: {ex.Message}" };
            }
        }

        public async Task<OperacionResultado> ActivarCuentaAsync(string codigoCuenta)
        {
            try
            {
                var soapEnvelope = $@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:ws=""http://ws.monster.edu.ec/"">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:activarCuenta>
         <codigo>{EscapeXml(codigoCuenta)}</codigo>
      </ws:activarCuenta>
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
            var response = await _httpClient.PostAsync($"{_config.BaseUrl}/ServicioCuenta", content);
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

        private CuentaDTO? ParseCuentaResponse(string xml)
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
                        return ParseCuentaElement(resultado);
                    }
                }
                return null;
            }
            catch
            {
                return null;
            }
        }

        private List<CuentaDTO> ParseCuentasListResponse(string xml)
        {
            var cuentas = new List<CuentaDTO>();
            try
            {
                var doc = XDocument.Parse(xml);
                var returnElements = doc.Descendants().Where(e => e.Name.LocalName == "return");
                
                foreach (var element in returnElements)
                {
                    var cuenta = ParseCuentaElement(element);
                    if (cuenta != null)
                    {
                        cuentas.Add(cuenta);
                    }
                }
            }
            catch { }
            return cuentas;
        }

        private CuentaDTO? ParseCuentaElement(XElement element)
        {
            var estadoStr = element.Element("estado")?.Value ?? "";
            // Normalizar estado: Si es "ACTIVO" o comienza con "A", mapear a "A", de lo contrario "C"
            // El backend devuelve "ACTIVO" pero la UI espera "A"
            var estadoNormalizado = (estadoStr.Equals("ACTIVO", StringComparison.OrdinalIgnoreCase) || estadoStr.StartsWith("A", StringComparison.OrdinalIgnoreCase)) ? "A" : "C";

            // Parsear saldo usando InvariantCulture para asegurar que el punto sea decimal
            var saldoStr = element.Element("saldo")?.Value;
            var saldo = 0m;
            if (!string.IsNullOrEmpty(saldoStr))
            {
                decimal.TryParse(saldoStr, System.Globalization.NumberStyles.Any, System.Globalization.CultureInfo.InvariantCulture, out saldo);
            }

            return new CuentaDTO
            {
                Codigo = element.Element("codigo")?.Value ?? "",
                Saldo = saldo,
                Estado = estadoNormalizado,
                CodigoCliente = element.Element("codigoCliente")?.Value ?? "",
                CodigoMoneda = element.Element("codigoMoneda")?.Value ?? "",
                CodigoSucursal = element.Element("codigoSucursal")?.Value ?? ""
            };
        }

        private string EscapeXml(string value) => System.Security.SecurityElement.Escape(value) ?? value;
    }

    public class CuentaDTO
    {
        public string Codigo { get; set; } = "";
        public decimal Saldo { get; set; }
        public string Estado { get; set; } = "";
        public string CodigoCliente { get; set; } = "";
        public string CodigoMoneda { get; set; } = "";
        public string CodigoSucursal { get; set; } = "";
    }
}
