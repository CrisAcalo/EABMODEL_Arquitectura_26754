using System;
using System.Configuration;
using System.ServiceModel;
using System.Threading;

// Usar ALIAS para evitar ambigüedad entre los servicios
using LongitudService = ClienteConsolaConversion.LongitudServiceReference;
using MasaService = ClienteConsolaConversion.MasaServiceReference;
using TemperaturaService = ClienteConsolaConversion.TemperaturaServiceReference;

namespace ClienteConsolaConversion
{
    class Program
    {
        // Credenciales hardcodeadas
        private const string USUARIO_VALIDO = "MONSTER";
        private const string CONTRASENA_VALIDA = "MONSTER9";
        private const int MAX_INTENTOS = 3;

        // Configuración del servidor
        private static string servidorIP;
        private static string servidorPuerto;
        private static string servidorRuta;
        private static string servidorURL;

        static void Main(string[] args)
        {
            Console.OutputEncoding = System.Text.Encoding.UTF8;

            // Cargar configuración
            CargarConfiguracion();

            // Sistema de Login
            if (!RealizarLogin())
            {
                Console.WriteLine("\nAcceso denegado. Número máximo de intentos alcanzado.");
                Console.WriteLine("El sistema se cerrará por seguridad.\n");
                Console.WriteLine("Presione cualquier tecla para salir...");
                Console.ReadKey();
                return;
            }

            Console.WriteLine("\n==============================================");
            Console.WriteLine("  CLIENTE CONSOLA - SERVICIOS DE CONVERSION  ");
            Console.WriteLine("==============================================");
            Console.WriteLine($"Servidor: {servidorURL}");
            Console.WriteLine();

            bool continuar = true;

            while (continuar)
            {
                MostrarMenuPrincipal();
                int opcion = LeerEntero("Seleccione una opción: ");

                switch (opcion)
                {
                    case 1:
                        MenuConversionesLongitud();
                        break;
                    case 2:
                        MenuConversionesMasa();
                        break;
                    case 3:
                        MenuConversionesTemperatura();
                        break;
                    case 0:
                        continuar = false;
                        Console.WriteLine("\n¡Hasta luego!");
                        break;
                    default:
                        Console.WriteLine("\nOpción inválida. Intente nuevamente.\n");
                        break;
                }
            }

            Console.WriteLine("\nPresione cualquier tecla para salir...");
            Console.ReadKey();
        }

        #region Configuración
        private static void CargarConfiguracion()
        {
            try
            {
                servidorIP = ConfigurationManager.AppSettings["servidor.ip"] ?? "localhost";
                servidorPuerto = ConfigurationManager.AppSettings["servidor.puerto"] ?? "56686";
                servidorRuta = ConfigurationManager.AppSettings["servidor.ruta"] ?? "ec/edu/monster/ws";

                servidorURL = $"http://{servidorIP}:{servidorPuerto}/{servidorRuta}";

                Console.WriteLine("✓ Configuración cargada desde App.config");
                Console.WriteLine($"  Servidor: {servidorIP}:{servidorPuerto}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"⚠ Error al cargar configuración: {ex.Message}");
                Console.WriteLine("  Usando valores por defecto...");
                servidorURL = "http://localhost:56686/ec/edu/monster/ws";
            }
        }

        private static string ObtenerURLServicio(string nombreServicio)
        {
            string servicio = ConfigurationManager.AppSettings[$"servicio.{nombreServicio}"];
            return $"{servidorURL}/{servicio}";
        }
        #endregion

        #region Login
        private static bool RealizarLogin()
        {
            Console.WriteLine("\n╔═══════════════════════════════════════════╗");
            Console.WriteLine("║     SISTEMA DE AUTENTICACIÓN              ║");
            Console.WriteLine("║     SERVICIOS DE CONVERSIÓN               ║");
            Console.WriteLine("╚═══════════════════════════════════════════╝\n");

            int intentos = 0;

            while (intentos < MAX_INTENTOS)
            {
                Console.WriteLine($"Intento {intentos + 1} de {MAX_INTENTOS}");
                Console.WriteLine("─────────────────────────────────────────────");

                Console.Write("Usuario: ");
                string usuario = Console.ReadLine();

                Console.Write("Contraseña: ");
                string contrasena = LeerContrasena();

                if (ValidarCredenciales(usuario, contrasena))
                {
                    Console.WriteLine("\n¡Autenticación exitosa!");
                    Console.WriteLine($"Bienvenido, {usuario}\n");
                    Thread.Sleep(1000);
                    return true;
                }
                else
                {
                    intentos++;
                    int intentosRestantes = MAX_INTENTOS - intentos;

                    if (intentosRestantes > 0)
                    {
                        Console.WriteLine("\nUsuario o contraseña incorrectos.");
                        Console.WriteLine($"Te quedan {intentosRestantes} intento(s).\n");
                    }
                    else
                    {
                        Console.WriteLine("\nUsuario o contraseña incorrectos.");
                    }
                }
            }

            return false;
        }

        private static bool ValidarCredenciales(string usuario, string contrasena)
        {
            return USUARIO_VALIDO.Equals(usuario) && CONTRASENA_VALIDA.Equals(contrasena);
        }

        private static string LeerContrasena()
        {
            string contrasena = "";
            ConsoleKeyInfo tecla;

            do
            {
                tecla = Console.ReadKey(true);

                if (tecla.Key != ConsoleKey.Backspace && tecla.Key != ConsoleKey.Enter)
                {
                    contrasena += tecla.KeyChar;
                    Console.Write("*");
                }
                else if (tecla.Key == ConsoleKey.Backspace && contrasena.Length > 0)
                {
                    contrasena = contrasena.Substring(0, contrasena.Length - 1);
                    Console.Write("\b \b");
                }
            }
            while (tecla.Key != ConsoleKey.Enter);

            Console.WriteLine();
            return contrasena;
        }
        #endregion

        #region Menus
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

        private static void MenuConversionesLongitud()
        {
            Console.WriteLine("\n┌─────────────────────────────────────────┐");
            Console.WriteLine("│     CONVERSIONES DE LONGITUD            │");
            Console.WriteLine("├─────────────────────────────────────────┤");
            Console.WriteLine("│ 1. Milla → Metro                        │");
            Console.WriteLine("│ 2. Metro → Milla                        │");
            Console.WriteLine("│ 3. Milla → Pulgada                      │");
            Console.WriteLine("│ 4. Pulgada → Milla                      │");
            Console.WriteLine("│ 5. Metro → Pulgada                      │");
            Console.WriteLine("│ 6. Pulgada → Metro                      │");
            Console.WriteLine("│ 0. Volver                               │");
            Console.WriteLine("└─────────────────────────────────────────┘");

            int opcion = LeerEntero("Seleccione una opción: ");
            if (opcion == 0) return;

            Console.Write("Ingrese el valor a convertir: ");
            string valor = Console.ReadLine();

            try
            {
                string endpointUrl = ObtenerURLServicio("longitud");
                var binding = new BasicHttpBinding();
                var endpoint = new EndpointAddress(endpointUrl);

                using (var client = new LongitudService.LongitudServiceClient(binding, endpoint))
                {
                    LongitudService.ConversionResult resultado = null;

                    switch (opcion)
                    {
                        case 1: resultado = client.MillaAMetro(valor); break;
                        case 2: resultado = client.MetroAMilla(valor); break;
                        case 3: resultado = client.MillaAPulgada(valor); break;
                        case 4: resultado = client.PulgadaAMilla(valor); break;
                        case 5: resultado = client.MetroAPulgada(valor); break;
                        case 6: resultado = client.PulgadaAMetro(valor); break;
                        default:
                            Console.WriteLine("\nOpción inválida.\n");
                            return;
                    }

                    MostrarResultadoLongitud(resultado);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"\n❌ Error al conectar con el servicio: {ex.Message}");
                Console.WriteLine($"   Verifica que el servidor esté corriendo en: {servidorURL}\n");
            }
        }

        private static void MenuConversionesMasa()
        {
            Console.WriteLine("\n┌─────────────────────────────────────────┐");
            Console.WriteLine("│       CONVERSIONES DE MASA              │");
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
            string valor = Console.ReadLine();

            try
            {
                string endpointUrl = ObtenerURLServicio("masa");
                var binding = new BasicHttpBinding();
                var endpoint = new EndpointAddress(endpointUrl);

                using (var client = new MasaService.MasaServiceClient(binding, endpoint))
                {
                    MasaService.ConversionResult resultado = null;

                    switch (opcion)
                    {
                        case 1: resultado = client.KilogramoAQuintal(valor); break;
                        case 2: resultado = client.QuintalAKilogramo(valor); break;
                        case 3: resultado = client.KilogramoALibra(valor); break;
                        case 4: resultado = client.LibraAKilogramo(valor); break;
                        case 5: resultado = client.QuintalALibra(valor); break;
                        case 6: resultado = client.LibraAQuintal(valor); break;
                        default:
                            Console.WriteLine("\n⌘ Opción inválida.\n");
                            return;
                    }

                    MostrarResultadoMasa(resultado);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"\n❌ Error al conectar con el servicio: {ex.Message}");
                Console.WriteLine($"   Verifica que el servidor esté corriendo en: {servidorURL}\n");
            }
        }

        private static void MenuConversionesTemperatura()
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
            string valor = Console.ReadLine();

            try
            {
                string endpointUrl = ObtenerURLServicio("temperatura");
                var binding = new BasicHttpBinding();
                var endpoint = new EndpointAddress(endpointUrl);

                using (var client = new TemperaturaService.TemperaturaServiceClient(binding, endpoint))
                {
                    TemperaturaService.ConversionResult resultado = null;

                    switch (opcion)
                    {
                        case 1: resultado = client.CelsiusAFahrenheit(valor); break;
                        case 2: resultado = client.CelsiusAKelvin(valor); break;
                        case 3: resultado = client.FahrenheitACelsius(valor); break;
                        case 4: resultado = client.FahrenheitAKelvin(valor); break;
                        case 5: resultado = client.KelvinACelsius(valor); break;
                        case 6: resultado = client.KelvinAFahrenheit(valor); break;
                        default:
                            Console.WriteLine("\nOpción inválida.\n");
                            return;
                    }

                    MostrarResultadoTemperatura(resultado);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"\n❌ Error al conectar con el servicio: {ex.Message}");
                Console.WriteLine($"   Verifica que el servidor esté corriendo en: {servidorURL}\n");
            }
        }
        #endregion

        #region Mostrar Resultados
        private static void MostrarResultadoLongitud(LongitudService.ConversionResult resultado)
        {
            Console.WriteLine("\n" + new string('=', 50));

            if (resultado.Exitoso)
            {
                var conv = resultado.Resultado;
                Console.WriteLine("✓ CONVERSIÓN EXITOSA");
                Console.WriteLine(new string('=', 50));
                Console.WriteLine($"Valor Original:    {conv.ValorOriginal} {conv.UnidadOrigen}");
                Console.WriteLine($"Valor Convertido:  {conv.ValorConvertidoRedondeado} {conv.UnidadDestino}");
                Console.WriteLine($"Valor Exacto:      {conv.ValorConvertidoExacto}");
                Console.WriteLine($"Factor:            {conv.FactorConversion}");
            }
            else
            {
                var err = resultado.Error;
                Console.WriteLine("❌ ERROR EN LA CONVERSIÓN");
                Console.WriteLine(new string('=', 50));
                Console.WriteLine($"Código:   {err.CodigoError}");
                Console.WriteLine($"Tipo:     {err.TipoError}");
                Console.WriteLine($"Mensaje:  {err.Mensaje}");
                if (!string.IsNullOrEmpty(err.Detalles))
                {
                    Console.WriteLine($"Detalles: {err.Detalles}");
                }
            }
            Console.WriteLine(new string('=', 50) + "\n");
        }

        private static void MostrarResultadoMasa(MasaService.ConversionResult resultado)
        {
            Console.WriteLine("\n" + new string('=', 50));

            if (resultado.Exitoso)
            {
                var conv = resultado.Resultado;
                Console.WriteLine("✓ CONVERSIÓN EXITOSA");
                Console.WriteLine(new string('=', 50));
                Console.WriteLine($"Valor Original:    {conv.ValorOriginal} {conv.UnidadOrigen}");
                Console.WriteLine($"Valor Convertido:  {conv.ValorConvertidoRedondeado} {conv.UnidadDestino}");
                Console.WriteLine($"Valor Exacto:      {conv.ValorConvertidoExacto}");
                Console.WriteLine($"Factor:            {conv.FactorConversion}");
            }
            else
            {
                var err = resultado.Error;
                Console.WriteLine("❌ ERROR EN LA CONVERSIÓN");
                Console.WriteLine(new string('=', 50));
                Console.WriteLine($"Código:   {err.CodigoError}");
                Console.WriteLine($"Tipo:     {err.TipoError}");
                Console.WriteLine($"Mensaje:  {err.Mensaje}");
                if (!string.IsNullOrEmpty(err.Detalles))
                {
                    Console.WriteLine($"Detalles: {err.Detalles}");
                }
            }
            Console.WriteLine(new string('=', 50) + "\n");
        }

        private static void MostrarResultadoTemperatura(TemperaturaService.ConversionResult resultado)
        {
            Console.WriteLine("\n" + new string('=', 50));

            if (resultado.Exitoso)
            {
                var conv = resultado.Resultado;
                Console.WriteLine("✓ CONVERSIÓN EXITOSA");
                Console.WriteLine(new string('=', 50));
                Console.WriteLine($"Valor Original:    {conv.ValorOriginal} {conv.UnidadOrigen}");
                Console.WriteLine($"Valor Convertido:  {conv.ValorConvertidoRedondeado} {conv.UnidadDestino}");
                Console.WriteLine($"Valor Exacto:      {conv.ValorConvertidoExacto}");
                Console.WriteLine($"Factor:            {conv.FactorConversion}");
            }
            else
            {
                var err = resultado.Error;
                Console.WriteLine("❌ ERROR EN LA CONVERSIÓN");
                Console.WriteLine(new string('=', 50));
                Console.WriteLine($"Código:   {err.CodigoError}");
                Console.WriteLine($"Tipo:     {err.TipoError}");
                Console.WriteLine($"Mensaje:  {err.Mensaje}");
                if (!string.IsNullOrEmpty(err.Detalles))
                {
                    Console.WriteLine($"Detalles: {err.Detalles}");
                }
            }
            Console.WriteLine(new string('=', 50) + "\n");
        }
        #endregion

        #region Utilidades
        private static int LeerEntero(string mensaje)
        {
            Console.Write(mensaje);
            int valor;
            while (!int.TryParse(Console.ReadLine(), out valor))
            {
                Console.Write("Debe ingresar un número entero. Intente nuevamente: ");
            }
            return valor;
        }
        #endregion
    }
}