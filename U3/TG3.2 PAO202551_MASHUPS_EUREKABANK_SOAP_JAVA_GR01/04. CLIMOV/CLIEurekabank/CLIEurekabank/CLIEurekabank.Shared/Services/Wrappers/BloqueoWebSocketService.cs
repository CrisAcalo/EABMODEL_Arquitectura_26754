using System.Net.WebSockets;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using CLIEurekabank.Shared.Config;

namespace CLIEurekabank.Shared.Services.Wrappers
{
    public class BloqueoWebSocketService : IAsyncDisposable
    {
        private ClientWebSocket? _ws;
        private CancellationTokenSource? _cts;
        private readonly AppConfig _config;
        
        public event Action<BloqueoMensaje>? OnBloqueoChanged;
        public bool IsConnected => _ws?.State == WebSocketState.Open;

        public BloqueoWebSocketService(AppConfig config)
        {
            _config = config;
        }

        public async Task ConnectAsync()
        {
            _ws = new ClientWebSocket();
            _cts = new CancellationTokenSource();
            
            try
            {
                var wsUrl = $"{_config.WsUrl}/ws/bloqueo";
                await _ws.ConnectAsync(new Uri(wsUrl), _cts.Token);
                _ = ReceiveLoopAsync();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error conectando WebSocket: {ex.Message}");
            }
        }

        public async Task BloquearCuentaAsync(string cuenta, string ventanilla)
        {
            var msg = new { accion = "BLOQUEAR", cuenta, ventanilla };
            await SendJsonAsync(msg);
        }

        public async Task LiberarCuentaAsync(string cuenta, string ventanilla)
        {
            var msg = new { accion = "LIBERAR", cuenta, ventanilla };
            await SendJsonAsync(msg);
        }

        private async Task SendJsonAsync(object data)
        {
            if (_ws == null || _ws.State != WebSocketState.Open || _cts == null) return;
            
            var json = JsonSerializer.Serialize(data);
            var buffer = Encoding.UTF8.GetBytes(json);
            await _ws.SendAsync(new ArraySegment<byte>(buffer), WebSocketMessageType.Text, true, _cts.Token);
        }

        private async Task ReceiveLoopAsync()
        {
            var buffer = new byte[1024 * 4];
            
            while (_ws != null && _ws.State == WebSocketState.Open && _cts != null && !_cts.IsCancellationRequested)
            {
                try
                {
                    var result = await _ws.ReceiveAsync(new ArraySegment<byte>(buffer), _cts.Token);
                    
                    if (result.MessageType == WebSocketMessageType.Close)
                    {
                        await _ws.CloseAsync(WebSocketCloseStatus.NormalClosure, "Cierre", CancellationToken.None);
                        break;
                    }
                    
                    var json = Encoding.UTF8.GetString(buffer, 0, result.Count);
                    var mensaje = JsonSerializer.Deserialize<BloqueoMensaje>(json);
                    if (mensaje != null)
                    {
                        OnBloqueoChanged?.Invoke(mensaje);
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Error WebSocket: {ex.Message}");
                    break;
                }
            }
        }

        public async ValueTask DisposeAsync()
        {
            _cts?.Cancel();
            if (_ws != null)
            {
                try
                {
                    if (_ws.State == WebSocketState.Open)
                    {
                        await _ws.CloseAsync(WebSocketCloseStatus.NormalClosure, "Disposed", CancellationToken.None);
                    }
                }
                catch { }
                _ws.Dispose();
            }
            _cts?.Dispose();
        }
    }

    public class BloqueoMensaje
    {
        [JsonPropertyName("tipo")]
        public string Tipo { get; set; } = string.Empty;

        [JsonPropertyName("cuenta")]
        public string Cuenta { get; set; } = string.Empty;

        [JsonPropertyName("estado")]
        public string Estado { get; set; } = string.Empty;

        [JsonPropertyName("ventanilla")]
        public string Ventanilla { get; set; } = string.Empty;
    }
}
