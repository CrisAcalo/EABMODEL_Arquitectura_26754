using System.Windows;
using ConUni_CliEsc_GR01.ec.edu.monster.config;

namespace ConUni_CliEsc_GR01
{
    /// <summary>
    /// Interaction logic for App.xaml
    /// </summary>
    public partial class App : Application
    {
        protected override void OnStartup(StartupEventArgs e)
        {
            base.OnStartup(e);

            // Inicializar el ConfigurationManager al inicio de la aplicación
            ConfigurationManager.Initialize();
        }
    }

}
