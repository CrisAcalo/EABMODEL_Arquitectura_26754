namespace CLIEurekabank.Shared.Config
{
    public class AppConfig
    {
        // Default URL for Development
        public string BaseUrl { get; set; } = "http://192.168.100.31:8080/Servidor";

        public string WsUrl => BaseUrl.Replace("http", "ws");
    }
}
