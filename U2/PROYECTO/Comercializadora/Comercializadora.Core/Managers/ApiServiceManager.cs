// En: Comercializadora.Core/Managers/ApiServiceManager.cs
using System.ComponentModel;
using System.Runtime.CompilerServices;

namespace Comercializadora.Core.Managers
{
    // Mantenemos la clase por compatibilidad con UI pero forzamos REST Java
    public class ApiServiceManager : INotifyPropertyChanged
    {
        private ApiPlatform _currentPlatform = ApiPlatform.Java;
        public ApiPlatform CurrentPlatform => _currentPlatform;

        // --- Implementación de INotifyPropertyChanged ---
        public event PropertyChangedEventHandler? PropertyChanged;
        protected void OnPropertyChanged([CallerMemberName] string? propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }

    public enum ApiPlatform { Java, DotNet }
}