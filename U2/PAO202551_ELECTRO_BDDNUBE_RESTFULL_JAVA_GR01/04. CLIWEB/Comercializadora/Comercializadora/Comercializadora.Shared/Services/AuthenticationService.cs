// En: Comercializadora.Shared/Services/AuthenticationService.cs
using Microsoft.Extensions.Logging;

namespace Comercializadora.Shared.Services
{
    public class AuthenticationService : IAuthenticationService
    {
        private readonly ILogger<AuthenticationService>? _logger;
        private const string ValidUsername = "MONSTER";
        private const string ValidPassword = "MONSTER9";
        
        private bool _isAuthenticated = false;
        private string? _currentUser = null;

        public AuthenticationService(ILogger<AuthenticationService>? logger = null)
        {
            _logger = logger;
        }

        public bool IsAuthenticated => _isAuthenticated;
        public string? CurrentUser => _currentUser;
        
        public event Action<bool>? AuthenticationStateChanged;

        public async Task<bool> LoginAsync(string username, string password)
        {
            try
            {
                _logger?.LogInformation("Intento de login para usuario: {Username}", username);
                
                // Simular una pequeña demora para parecer más real
                await Task.Delay(500);
                
                // Validar credenciales (case-sensitive)
                if (string.Equals(username, ValidUsername, StringComparison.Ordinal) && 
                    string.Equals(password, ValidPassword, StringComparison.Ordinal))
                {
                    _isAuthenticated = true;
                    _currentUser = username;
                    
                    _logger?.LogInformation("? Login exitoso para usuario: {Username}", username);
                    
                    // Notificar cambio de estado
                    AuthenticationStateChanged?.Invoke(true);
                    
                    return true;
                }
                else
                {
                    _logger?.LogWarning("? Credenciales inválidas para usuario: {Username}", username);
                    return false;
                }
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error durante el proceso de login");
                return false;
            }
        }

        public async Task LogoutAsync()
        {
            try
            {
                _logger?.LogInformation("Cerrando sesión para usuario: {Username}", _currentUser);
                
                _isAuthenticated = false;
                var previousUser = _currentUser;
                _currentUser = null;
                
                // Simular demora
                await Task.Delay(200);
                
                _logger?.LogInformation("? Sesión cerrada para usuario: {Username}", previousUser);
                
                // Notificar cambio de estado
                AuthenticationStateChanged?.Invoke(false);
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error durante el proceso de logout");
            }
        }
    }
}