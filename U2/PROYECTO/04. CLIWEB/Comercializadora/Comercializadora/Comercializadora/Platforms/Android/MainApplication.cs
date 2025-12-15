using Android.App;
using Android.Runtime;

namespace Comercializadora
{
    [Application(UsesCleartextTraffic = true)]
    public class MainApplication : MauiApplication
    {
        public MainApplication(IntPtr handle, JniHandleOwnership ownership)
            : base(handle, ownership)
        {
        }

        protected override MauiApp CreateMauiApp() => MauiProgram.CreateMauiApp();
        
        public override void OnCreate()
        {
            base.OnCreate();
            
            // Configuraciones adicionales para Android
            try
            {
                // Configurar el sistema para permitir tráfico HTTP
                if (Android.OS.Build.VERSION.SdkInt >= Android.OS.BuildVersionCodes.P)
                {
                    // Para Android 9+ (API 28+), asegurar que el tráfico HTTP esté permitido
                    System.Net.ServicePointManager.ServerCertificateValidationCallback = 
                        (sender, certificate, chain, sslPolicyErrors) => true;
                }
                
                // Configuraciones de timeout para conexiones de red
                System.Net.ServicePointManager.DefaultConnectionLimit = 10;
                System.Net.ServicePointManager.Expect100Continue = false;
                System.Net.ServicePointManager.UseNagleAlgorithm = false;
                
                // Configurar TLS para compatibilidad
                System.Net.ServicePointManager.SecurityProtocol = 
                    System.Net.SecurityProtocolType.Tls12 | 
                    System.Net.SecurityProtocolType.Tls11 | 
                    System.Net.SecurityProtocolType.Tls;
                    
                System.Diagnostics.Debug.WriteLine("✅ Configuraciones de red Android aplicadas correctamente");
            }
            catch (System.Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"❌ Error en configuración de red Android: {ex.Message}");
            }
        }
    }
}
