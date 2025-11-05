// ViewModels/MainViewModel.cs
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using System.Collections.ObjectModel;
using UniversalConverter.Client.Models;
using UniversalConverter.Client.Services;

namespace UniversalConverter.Client.ViewModels
{
    // Heredamos de ObservableObject para poder notificar a la UI de los cambios en las propiedades.
    // Usamos 'partial' porque el Toolkit genera código por nosotros.
    public partial class MainViewModel : ObservableObject
    {
        // --- Inyección de Dependencias ---
        private readonly RestConversionService _restService;
        private readonly SoapConversionService _soapService;

        // --- Propiedades Observables (El estado de nuestra UI) ---

        [ObservableProperty]
        [NotifyPropertyChangedFor(nameof(IsNotBusy))] // Notifica que IsNotBusy también cambia
        private bool _isBusy; // Para mostrar un indicador de carga

        [ObservableProperty]
        private string _valorEntrada = "10"; // El valor que el usuario escribe

        [ObservableProperty]
        private ConversionResponse _conversionResult;

        // Opciones de configuración de la API
        [ObservableProperty]
        private string _selectedApiType = "SOAP";

        [ObservableProperty]
        private string _selectedApiPlatform = ".NET";

        // El tipo de conversión seleccionado (Longitud, Masa, etc.)
        [ObservableProperty]
        private ConversionType _selectedConversionType = ConversionType.Longitud;

        [ObservableProperty]
        private string _selectedUnitFrom;

        [ObservableProperty]
        private string _selectedUnitTo;

        // Colecciones para llenar los Pickers (desplegables)
        public ObservableCollection<string> ApiTypes { get; } = new ObservableCollection<string> { "REST", "SOAP" };
        public ObservableCollection<string> ApiPlatforms { get; } = new ObservableCollection<string> { "Java", ".NET" };
        public ObservableCollection<ConversionType> ConversionTypes { get; } = new ObservableCollection<ConversionType> { ConversionType.Longitud, ConversionType.Masa, ConversionType.Temperatura };

        [ObservableProperty]
        private ObservableCollection<string> _unitsFrom = new ObservableCollection<string>();

        [ObservableProperty]
        private ObservableCollection<string> _unitsTo = new ObservableCollection<string>();

        // Propiedad computada para habilitar/deshabilitar el botón de conversión
        public bool IsNotBusy => !IsBusy;

        // --- Constructor ---
        public MainViewModel(RestConversionService restService, SoapConversionService soapService)
        {
            _restService = restService;
            _soapService = soapService;

            // Inicializamos las listas de unidades al arrancar
            UpdateUnitLists();
        }

        // --- Lógica ---

        // Este método se ejecuta automáticamente cuando 'SelectedConversionType' cambia.
        // Es una 'convención' del CommunityToolkit.Mvvm
        partial void OnSelectedConversionTypeChanged(ConversionType value)
        {
            UpdateUnitLists();
        }

        private void UpdateUnitLists()
        {
            UnitsFrom.Clear();
            UnitsTo.Clear();

            List<string> units = new List<string>();
            switch (SelectedConversionType)
            {
                case ConversionType.Longitud:
                    units.AddRange(new[] { "Milla", "Metro", "Pulgada" });
                    break;
                case ConversionType.Masa:
                    units.AddRange(new[] { "Kilogramo", "Quintal", "Libra" });
                    break;
                case ConversionType.Temperatura:
                    units.AddRange(new[] { "Celsius", "Fahrenheit", "Kelvin" });
                    break;
            }

            foreach (var unit in units)
            {
                UnitsFrom.Add(unit);
                UnitsTo.Add(unit);
            }

            // Seleccionamos valores por defecto para evitar que estén vacíos
            SelectedUnitFrom = UnitsFrom.FirstOrDefault();
            SelectedUnitTo = UnitsTo.Skip(1).FirstOrDefault() ?? UnitsTo.FirstOrDefault();
        }

        // --- Comandos ---

        // Este atributo crea un 'ConvertCommand' que la UI puede llamar.
        // CanExecute previene que el botón se presione si 'IsNotBusy' es falso.
        [RelayCommand(CanExecute = nameof(IsNotBusy))]
        private async Task Convert()
        {
            // Limpiamos el resultado anterior antes de empezar
            ConversionResult = null;

            if (string.IsNullOrWhiteSpace(ValorEntrada) || SelectedUnitFrom == SelectedUnitTo)
            {
                // Creamos un objeto de respuesta de error para mostrar en la UI
                ConversionResult = new ConversionResponse
                {
                    Exitoso = false,
                    Error = new ErrorData { Mensaje = "Por favor, ingrese un valor y seleccione unidades diferentes." }
                };
                return;
            }

            IsBusy = true;

            try
            {
                IConversionService activeService;
                if (SelectedApiType == "REST")
                {
                    activeService = _restService;
                    _restService.SetTarget(SelectedApiPlatform == "Java" ? ApiTarget.Java : ApiTarget.DotNet);
                }
                else // SOAP
                {
                    activeService = _soapService;
                    _soapService.SetTarget(SelectedApiPlatform == "Java" ? ApiTarget.Java : ApiTarget.DotNet);
                }

                var request = new ConversionRequest
                {
                    Valor = ValorEntrada,
                    UnidadOrigen = SelectedUnitFrom,
                    UnidadDestino = SelectedUnitTo,
                    TipoConversion = SelectedConversionType
                };

                // Simplemente asignamos la respuesta completa a nuestra propiedad
                ConversionResult = await activeService.ConvertAsync(request);
            }
            catch (Exception ex)
            {
                // En caso de un error inesperado, también creamos un objeto de respuesta
                ConversionResult = new ConversionResponse
                {
                    Exitoso = false,
                    Error = new ErrorData { Mensaje = $"Ha ocurrido un error inesperado: {ex.Message}" }
                };
            }
            finally
            {
                IsBusy = false;
            }
        }
    }
}