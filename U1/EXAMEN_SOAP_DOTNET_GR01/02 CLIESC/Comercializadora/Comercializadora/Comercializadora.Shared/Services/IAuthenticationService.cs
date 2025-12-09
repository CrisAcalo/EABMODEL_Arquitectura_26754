// En: Comercializadora.Shared/Services/IAuthenticationService.cs
namespace Comercializadora.Shared.Services
{
    public interface IAuthenticationService
    {
        bool IsAuthenticated { get; }
        event Action<bool> AuthenticationStateChanged;
        Task<bool> LoginAsync(string username, string password);
        Task LogoutAsync();
        string? CurrentUser { get; }
    }
}