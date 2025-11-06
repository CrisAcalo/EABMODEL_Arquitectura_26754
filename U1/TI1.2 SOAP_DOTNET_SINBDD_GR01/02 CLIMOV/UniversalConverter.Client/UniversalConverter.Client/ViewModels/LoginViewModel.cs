using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;

namespace UniversalConverter.Client.ViewModels
{
    public partial class LoginViewModel : ObservableObject
    {
        [ObservableProperty]
        [NotifyPropertyChangedFor(nameof(IsNotBusy))]
        private bool _isBusy;

        [ObservableProperty]
        private string _username;

        [ObservableProperty]
        private string _password;

        [ObservableProperty]
        private string _errorMessage;

        public bool IsNotBusy => !IsBusy;
        [RelayCommand]
        private async Task Login()
        {
            IsBusy = true;
            ErrorMessage = null;

            await Task.Delay(500);

            try
            {
                if (Username == "MONSTER" && Password == "MONSTER9")
                {
                    // ¡Éxito! Usamos la navegación de Shell.
                    // El prefijo "//" le dice al Shell que cree una nueva pila de navegación,
                    // eliminando la página de login del historial.
                    await Shell.Current.GoToAsync($"//{nameof(MainPage)}");
                }
                else
                {
                    ErrorMessage = "Usuario o contraseña incorrectos.";
                }
            }
            catch (Exception ex)
            {
                ErrorMessage = $"Ocurrió un error: {ex.Message}";
            }
            finally
            {
                IsBusy = false;
            }
        }
    }
}