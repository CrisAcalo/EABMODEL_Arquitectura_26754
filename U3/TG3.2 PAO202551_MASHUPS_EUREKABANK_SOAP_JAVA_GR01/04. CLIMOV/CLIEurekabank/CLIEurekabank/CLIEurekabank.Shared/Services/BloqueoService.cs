using System.Net.WebSockets;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace CLIEurekabank.Shared.Services
{
    public class BloqueoService : IAsyncDisposable
    {
        private ClientWebSocket _ws;
        private readonly string _wsUrl = "ws://localhost:8080/Servidor/ws/bloqueo";
        private CancellationTokenSource _cts;
        private bool _isConnected;
        
        // Evento para notificar a la UI sobre cambios de estado
        public event Action<EstadoCuentaMensaje> OnEstadoCuentaChanged;

        public bool IsConnected => _isConnected && _ws?.State == WebSocketState.Open;

        public async Task ConnectAsync()
        {
            if (_isConnected) return;

            _ws = new ClientWebSocket();
            _cts = new CancellationTokenSource();

            try
            {
                await _ws.ConnectAsync(new Uri(_wsUrl), _cts.Token);
                _isConnected = true;
                _ = ReceiveLoopAsync(); // Iniciar loop de recepción en background
                Console.WriteLine("Conectado al servicio de bloqueos.");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error conectando al WS de bloqueos: {ex.Message}");
                _isConnected = false;
            }
        }

        public async Task BloquearCuentaAsync(string cuenta, string ventanilla)
        {
            if (!IsConnected) return;

            var msg = new
            {
                accion = "BLOQUEAR",
                cuenta = cuenta,
                ventanilla = ventanilla
            };

            await SendJsonAsync(msg);
        }

        public async Task LiberarCuentaAsync(string cuenta, string ventanilla)
        {
            if (!IsConnected) return;

            var msg = new
            {
                accion = "LIBERAR",
                cuenta = cuenta,
                ventanilla = ventanilla
            };

            await SendJsonAsync(msg);
        }

        private async Task SendJsonAsync(object payload)
        {
            if (_ws == null || _ws.State != WebSocketState.Open) return;

            try
            {
                var json = JsonSerializer.Serialize(payload);
                var buffer = Encoding.UTF8.GetBytes(json);
                await _ws.SendAsync(new ArraySegment<byte>(buffer), WebSocketMessageType.Text, true, _cts.Token);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error enviando mensaje WS: {ex.Message}");
            }
        }

        private async Task ReceiveLoopAsync()
        {
            var buffer = new byte[1024 * 4];

            try
            {
                while (_ws.State == WebSocketState.Open && !_cts.IsCancellationRequested)
                {
                    var result = await _ws.ReceiveAsync(new ArraySegment<byte>(buffer), _cts.Token);

                    if (result.MessageType == WebSocketMessageType.Close)
                    {
                        try 
                        {
                            if (_ws.State == WebSocketState.Open || _ws.State == WebSocketState.CloseReceived)
                            {
                                await _ws.CloseAsync(WebSocketCloseStatus.NormalClosure, "Cierre normal", _cts.Token);
                            }
                        }
                        catch { /* Ignore close errors */ }
                        break;
                    }

                    var message = Encoding.UTF8.GetString(buffer, 0, result.Count);
                    ProcesarMensaje(message);
                }
            }
            catch (OperationCanceledException) { }
            catch (WebSocketException) { } // Ignore aborted/connection error during receive
            catch (Exception ex)
            {
                Console.WriteLine($"Error en loop de recepción WS: {ex.Message}");
            }
            finally
            {
                _isConnected = false;
            }
        }

        // Cache local de estados de cuenta
        private readonly System.Collections.Concurrent.ConcurrentDictionary<string, EstadoCuentaMensaje> _cacheBloqueos = new();

        public EstadoCuentaMensaje? GetEstadoCuenta(string cuenta)
        {
            if (_cacheBloqueos.TryGetValue(cuenta, out var estado))
            {
                return estado;
            }
            return null;
        }

        private void ProcesarMensaje(string json)
        {
            try
            {
                // Intentar deserializar como mensaje de estado
                var estado = JsonSerializer.Deserialize<EstadoCuentaMensaje>(json, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
                if (estado != null && estado.Tipo == "ESTADO_CUENTA")
                {
                    // Actualizar cache
                    if (estado.Estado == "BLOQUEADO")
                    {
                        _cacheBloqueos.AddOrUpdate(estado.Cuenta, estado, (k, v) => estado);
                    }
                    else if (estado.Estado == "LIBERADO" || estado.Estado == "LIBRE")
                    {
                        _cacheBloqueos.TryRemove(estado.Cuenta, out _);
                    }

                    OnEstadoCuentaChanged?.Invoke(estado);
                }
            }
            catch (Exception)
            {
                // Ignorar mensajes malformados
            }
        }

        public async ValueTask DisposeAsync()
        {
            try
            {
                _cts?.Cancel();
                
                if (_ws != null)
                {
                    if (_ws.State == WebSocketState.Open || 
                        _ws.State == WebSocketState.CloseReceived || 
                        _ws.State == WebSocketState.CloseSent)
                    {
                        await _ws.CloseAsync(WebSocketCloseStatus.NormalClosure, "Disposing", CancellationToken.None);
                    }
                    _ws.Dispose();
                }
            }
            catch (Exception)
            {
                // Silenciar errores durante dispose
            }
            finally
            {
                _cts?.Dispose();
            }
        }
    }

    public class EstadoCuentaMensaje
    {
        [JsonPropertyName("tipo")]
        public string Tipo { get; set; }

        [JsonPropertyName("cuenta")]
        public string Cuenta { get; set; }

        [JsonPropertyName("estado")]
        public string Estado { get; set; } // BLOQUEADO, LIBERADO

        [JsonPropertyName("ventanilla")]
        public string Ventanilla { get; set; }
    }
}
