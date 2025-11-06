using System.Windows;
using System.Windows.Input;

namespace ConUni_CliEsc_GR01
{
    /// <summary>
    /// Lógica de interacción para LoginWindow.xaml
    /// </summary>
    public partial class LoginWindow : Window
    {
        // Credenciales hardcodeadas
        private const string USUARIO_VALIDO = "MONSTER";
        private const string PASSWORD_VALIDO = "MONSTER9";

        public LoginWindow()
        {
            InitializeComponent();

            // Establecer el foco en el campo de usuario al cargar
            Loaded += (s, e) => txtUsuario.Focus();
        }

        private void BtnIngresar_Click(object sender, RoutedEventArgs e)
        {
            ValidarCredenciales();
        }

        private void TxtPassword_KeyDown(object sender, KeyEventArgs e)
        {
            // Permitir login con Enter
            if (e.Key == Key.Enter)
            {
                ValidarCredenciales();
            }
        }

        private void ValidarCredenciales()
        {
            // Ocultar mensaje de error previo
            pnlError.Visibility = Visibility.Collapsed;

            // Obtener valores ingresados
            string usuario = txtUsuario.Text.Trim();
            string password = txtPassword.Password;

            // Validar campos vacíos
            if (string.IsNullOrWhiteSpace(usuario) || string.IsNullOrWhiteSpace(password))
            {
                MostrarError("Por favor ingrese usuario y contraseña");
                return;
            }

            // Validar credenciales
            if (usuario == USUARIO_VALIDO && password == PASSWORD_VALIDO)
            {
                // Credenciales correctas - abrir MainWindow
                MainWindow mainWindow = new MainWindow();
                mainWindow.Show();

                // Cerrar ventana de login
                this.Close();
            }
            else
            {
                // Credenciales incorrectas
                MostrarError("Usuario o contraseña incorrectos");

                // Limpiar campos
                txtPassword.Clear();
                txtUsuario.Focus();
            }
        }

        private void MostrarError(string mensaje)
        {
            txtMensajeError.Text = mensaje;
            pnlError.Visibility = Visibility.Visible;
        }
    }
}
