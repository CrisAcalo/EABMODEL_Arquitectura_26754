using System;
using System.Threading.Tasks;
using ConUni_Client_DotNet.Config;
using ConUni_Client_DotNet.Services;
using ConUni_Client_DotNet.Models;

namespace ConUni_Client_DotNet
{
    class Program
    {
        private static AppConfiguration _config;
        private static RestClient _restClient;

        static async Task Main(string[] args)
        {
            // Configurar consola para UTF-8
            Console.OutputEncoding = System.Text.Encoding.UTF8;

            // Cargar configuración
            Console.WriteLine("\n╔════════════════════════════════════════════╗");
            Console.WriteLine("║  CLIENTE CONSOLA - SERVICIOS DE CONVERSION ║");
            Console.WriteLine("╚════════════════════════════════════════════╝\n");

            _config = AppConfiguration.LoadConfiguration();
            string serverUrl = _config.GetBaseUrl();
            Console.WriteLine($"📡 Servidor configurado: {serverUrl}\n");

            // Sistema de Login
            if (!RealizarLogin())
            {
                Console.WriteLine("\n❌ Acceso denegado. Número máximo de intentos alcanzado.");
                Console.WriteLine("   El sistema se cerrará por seguridad.\n");
                return;
            }

            // Inicializar cliente REST
            _restClient = new RestClient(serverUrl);

            // Menú principal
            Console.WriteLine("\n==============================================");
            Console.WriteLine("  CLIENTE CONSOLA - SERVICIOS DE CONVERSIÓN  ");
            Console.WriteLine("==============================================");
            Console.WriteLine($"Servidor: {serverUrl}");
            Console.WriteLine();

            bool continuar = true;
            while (continuar)
            {
                MostrarMenuPrincipal();
                int opcion = LeerEntero("Seleccione una opción: ");

                switch (opcion)
                {
                    case 1:
                        await MenuConversionesLongitud();
                        break;
                    case 2:
                        await MenuConversionesMasa();
                        break;
                    case 3:
                        await MenuConversionesTemperatura();
                        break;
                    case 0:
                        continuar = false;
                        Console.WriteLine("\n👋 ¡Hasta luego!");
                        break;
                    default:
                        Console.WriteLine("\n❌ Opción inválida. Intente nuevamente.\n");
                        break;
                }
            }

            // Limpiar recursos
            _restClient?.Dispose();
        }

        #region Sistema de Autenticación

        private static bool RealizarLogin()
        {
            Console.WriteLine("╔════════════════════════════════════════════╗");
            Console.WriteLine("║     SISTEMA DE AUTENTICACIÓN              ║");
            Console.WriteLine("║     SERVICIOS DE CONVERSIÓN               ║");
            Console.WriteLine("╚════════════════════════════════════════════╝\n");

            int maxIntentos = _config?.Authentication.MaxIntentos ?? 3;
            int intentos = 0;

            while (intentos < maxIntentos)
            {
                Console.WriteLine($"Intento {intentos + 1} de {maxIntentos}");
                Console.WriteLine("─────────────────────────────────────────────");

                Console.Write("Usuario: ");
                string usuario = Console.ReadLine();

                Console.Write("Contraseña: ");
                string contrasena = LeerContrasena();

                if (ValidarCredenciales(usuario, contrasena))
                {
                    Console.WriteLine("\n✓ ¡Autenticación exitosa!");
                    Console.WriteLine($"  Bienvenido, {usuario}\n");
                    System.Threading.Thread.Sleep(1000);
                    return true;
                }
                else
                {
                    intentos++;
                    int intentosRestantes = maxIntentos - intentos;

                    if (intentosRestantes > 0)
                    {
                        Console.WriteLine("\n❌ Usuario o contraseña incorrectos.");
                        Console.WriteLine($"   Te quedan {intentosRestantes} intento(s).\n");
                    }
                    else
                    {
                        Console.WriteLine("\n❌ Usuario o contraseña incorrectos.");
                    }
                }
            }

            return false;
        }

        private static bool ValidarCredenciales(string usuario, string contrasena)
        {
            string usuarioValido = _config?.Authentication.Usuario ?? "Monster";
            string contrasenaValida = _config?.Authentication.Contrasena ?? "Monster9";

            return usuario == usuarioValido && contrasena == contrasenaValida;
        }

        private static string LeerContrasena()
        {
            string contrasena = "";
            ConsoleKeyInfo key;

            do
            {
                key = Console.ReadKey(true);

                if (key.Key != ConsoleKey.Backspace && key.Key != ConsoleKey.Enter)
                {
                    contrasena += key.KeyChar;
                    Console.Write("*");
                }
                else if (key.Key == ConsoleKey.Backspace && contrasena.Length > 0)
                {
                    contrasena = contrasena.Substring(0, contrasena.Length - 1);
                    Console.Write("\b \b");
                }
            }
            while (key.Key != ConsoleKey.Enter);

            Console.WriteLine();
            return contrasena;
        }

        #endregion

        #region Menús

        private static void MostrarMenuPrincipal()
        {
            Console.WriteLine("┌─────────────────────────────────────────┐");
            Console.WriteLine("│           MENÚ PRINCIPAL                │");
            Console.WriteLine("├─────────────────────────────────────────┤");
            Console.WriteLine("│ 1. Conversiones de Longitud             │");
            Console.WriteLine("│ 2. Conversiones de Masa                 │");
            Console.WriteLine("│ 3. Conversiones de Temperatura          │");
            Console.WriteLine("│ 0. Salir                                │");
            Console.WriteLine("└─────────────────────────────────────────┘");
        }

        private static async Task MenuConversionesLongitud()
        {
            Console.WriteLine("\n┌─────────────────────────────────────────┐");
            Console.WriteLine("│    CONVERSIONES DE LONGITUD             │");
            Console.WriteLine("├─────────────────────────────────────────┤");
            Console.WriteLine("│ 1. Metro → Milla                        │");
            Console.WriteLine("│ 2. Milla → Metro                        │");
            Console.WriteLine("│ 3. Metro → Pulgada                      │");
            Console.WriteLine("│ 4. Pulgada → Metro                      │");
            Console.WriteLine("│ 5. Milla → Pulgada                      │");
            Console.WriteLine("│ 6. Pulgada → Milla                      │");
            Console.WriteLine("│ 0. Volver                               │");
            Console.WriteLine("└─────────────────────────────────────────┘");

            int opcion = LeerEntero("Seleccione una opción: ");
            if (opcion == 0) return;

            Console.Write("Ingrese el valor a convertir: ");
            string valor = Console.ReadLine() ?? "";

            string unidadOrigen = "";
            string unidadDestino = "";

            switch (opcion)
            {
                case 1: unidadOrigen = "Metro"; unidadDestino = "Milla"; break;
                case 2: unidadOrigen = "Milla"; unidadDestino = "Metro"; break;
                case 3: unidadOrigen = "Metro"; unidadDestino = "Pulgada"; break;
                case 4: unidadOrigen = "Pulgada"; unidadDestino = "Metro"; break;
                case 5: unidadOrigen = "Milla"; unidadDestino = "Pulgada"; break;
                case 6: unidadOrigen = "Pulgada"; unidadDestino = "Milla"; break;
                default:
                    Console.WriteLine("\n❌ Opción inválida.\n");
                    return;
            }

            if (_restClient != null)
            {
                var resultado = await _restClient.ConvertirLongitud(valor, unidadOrigen, unidadDestino);
                MostrarResultado(resultado);
            }
        }

        private static async Task MenuConversionesMasa()
        {
            Console.WriteLine("\n┌─────────────────────────────────────────┐");
            Console.WriteLine("│    CONVERSIONES DE MASA                 │");
            Console.WriteLine("├─────────────────────────────────────────┤");
            Console.WriteLine("│ 1. Kilogramo → Quintal                  │");
            Console.WriteLine("│ 2. Quintal → Kilogramo                  │");
            Console.WriteLine("│ 3. Kilogramo → Libra                    │");
            Console.WriteLine("│ 4. Libra → Kilogramo                    │");
            Console.WriteLine("│ 5. Quintal → Libra                      │");
            Console.WriteLine("│ 6. Libra → Quintal                      │");
            Console.WriteLine("│ 0. Volver                               │");
            Console.WriteLine("└─────────────────────────────────────────┘");

            int opcion = LeerEntero("Seleccione una opción: ");
            if (opcion == 0) return;

            Console.Write("Ingrese el valor a convertir: ");
            string valor = Console.ReadLine() ?? "";

            string unidadOrigen = "";
            string unidadDestino = "";

            switch (opcion)
            {
                case 1: unidadOrigen = "Kilogramo"; unidadDestino = "Quintal"; break;
                case 2: unidadOrigen = "Quintal"; unidadDestino = "Kilogramo"; break;
                case 3: unidadOrigen = "Kilogramo"; unidadDestino = "Libra"; break;
                case 4: unidadOrigen = "Libra"; unidadDestino = "Kilogramo"; break;
                case 5: unidadOrigen = "Quintal"; unidadDestino = "Libra"; break;
                case 6: unidadOrigen = "Libra"; unidadDestino = "Quintal"; break;
                default:
                    Console.WriteLine("\n❌ Opción inválida.\n");
                    return;
            }

            if (_restClient != null)
            {
                var resultado = await _restClient.ConvertirMasa(valor, unidadOrigen, unidadDestino);
                MostrarResultado(resultado);
            }
        }

        private static async Task MenuConversionesTemperatura()
        {
            Console.WriteLine("\n┌─────────────────────────────────────────┐");
            Console.WriteLine("│    CONVERSIONES DE TEMPERATURA          │");
            Console.WriteLine("├─────────────────────────────────────────┤");
            Console.WriteLine("│ 1. Celsius → Fahrenheit                 │");
            Console.WriteLine("│ 2. Celsius → Kelvin                     │");
            Console.WriteLine("│ 3. Fahrenheit → Celsius                 │");
            Console.WriteLine("│ 4. Fahrenheit → Kelvin                  │");
            Console.WriteLine("│ 5. Kelvin → Celsius                     │");
            Console.WriteLine("│ 6. Kelvin → Fahrenheit                  │");
            Console.WriteLine("│ 0. Volver                               │");
            Console.WriteLine("└─────────────────────────────────────────┘");

            int opcion = LeerEntero("Seleccione una opción: ");
            if (opcion == 0) return;

            Console.Write("Ingrese el valor a convertir: ");
            string valor = Console.ReadLine() ?? "";

            string unidadOrigen = "";
            string unidadDestino = "";

            switch (opcion)
            {
                case 1: unidadOrigen = "Celsius"; unidadDestino = "Fahrenheit"; break;
                case 2: unidadOrigen = "Celsius"; unidadDestino = "Kelvin"; break;
                case 3: unidadOrigen = "Fahrenheit"; unidadDestino = "Celsius"; break;
                case 4: unidadOrigen = "Fahrenheit"; unidadDestino = "Kelvin"; break;
                case 5: unidadOrigen = "Kelvin"; unidadDestino = "Celsius"; break;
                case 6: unidadOrigen = "Kelvin"; unidadDestino = "Fahrenheit"; break;
                default:
                    Console.WriteLine("\n❌ Opción inválida.\n");
                    return;
            }

            if (_restClient != null)
            {
                var resultado = await _restClient.ConvertirTemperatura(valor, unidadOrigen, unidadDestino);
                MostrarResultado(resultado);
            }
        }

        #endregion

        #region Utilidades

        private static void MostrarResultado(ConversionResult resultado)
        {
            Console.WriteLine("\n" + new string('=', 50));

            if (resultado.Exitoso && resultado.Resultado != null)
            {
                var conv = resultado.Resultado;
                Console.WriteLine("✓ CONVERSIÓN EXITOSA");
                Console.WriteLine(new string('=', 50));
                Console.WriteLine($"Valor Original:    {conv.ValorOriginal} {conv.UnidadOrigen}");
                Console.WriteLine($"Valor Convertido:  {conv.ValorConvertidoRedondeado} {conv.UnidadDestino}");
                Console.WriteLine($"Valor Exacto:      {conv.ValorConvertidoExacto}");
                Console.WriteLine($"Factor:            {conv.FactorConversion}");
                Console.WriteLine($"Tipo:              {conv.TipoConversion}");
                Console.WriteLine($"Fecha:             {conv.FechaConversion:yyyy-MM-dd HH:mm:ss}");
            }
            else if (resultado.Error != null)
            {
                var error = resultado.Error;
                Console.WriteLine("❌ ERROR EN LA CONVERSIÓN");
                Console.WriteLine(new string('=', 50));
                Console.WriteLine($"Código:   {error.CodigoError}");
                Console.WriteLine($"Tipo:     {error.TipoError}");
                Console.WriteLine($"Mensaje:  {error.Mensaje}");

                if (error.ValorProblematico.HasValue)
                    Console.WriteLine($"Valor:    {error.ValorProblematico} {error.Unidad}");

                if (!string.IsNullOrEmpty(error.Detalles))
                    Console.WriteLine($"Detalles: {error.Detalles}");
            }

            Console.WriteLine(new string('=', 50) + "\n");
        }

        private static int LeerEntero(string mensaje)
        {
            while (true)
            {
                Console.Write(mensaje);
                string input = Console.ReadLine();

                if (int.TryParse(input, out int valor))
                {
                    return valor;
                }

                Console.WriteLine("❌ Debe ingresar un número entero. Intente nuevamente.");
            }
        }

        #endregion
    }
}