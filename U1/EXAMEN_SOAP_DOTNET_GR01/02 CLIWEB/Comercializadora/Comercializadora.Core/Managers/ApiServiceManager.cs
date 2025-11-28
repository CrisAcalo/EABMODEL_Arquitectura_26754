// En: Comercializadora.Core/Managers/ApiServiceManager.cs
using System.ComponentModel;
using System.Runtime.CompilerServices;

namespace Comercializadora.Core.Managers
{
    public class ApiServiceManager : INotifyPropertyChanged
    {
        private TechnologyProfile _currentProfile = TechnologyProfile.SoapDotNet;
        public TechnologyProfile CurrentProfile
        {
            get => _currentProfile;
            set
            {
                if (_currentProfile != value)
                {
                    _currentProfile = value;
                    OnPropertyChanged();
                }
            }
        }

        // Propiedades computadas para obtener el protocolo y la plataforma fácilmente
        public ApiProtocol CurrentProtocol =>
            CurrentProfile == TechnologyProfile.SoapDotNet ? ApiProtocol.Soap : ApiProtocol.Rest;

        public ApiPlatform CurrentPlatform =>
            CurrentProfile == TechnologyProfile.SoapDotNet ? ApiPlatform.DotNet : ApiPlatform.Java;


        // --- Implementación de INotifyPropertyChanged ---
        public event PropertyChangedEventHandler? PropertyChanged;
        protected void OnPropertyChanged([CallerMemberName] string? propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }

    // El único enum que la UI necesita conocer
    public enum TechnologyProfile
    {
        SoapDotNet,
        RestfulJava
    }

    // Estos enums ahora son internos para la capa de servicios
    public enum ApiPlatform { DotNet, Java }
    public enum ApiProtocol { Soap, Rest }
}