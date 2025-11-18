namespace Comer_CliCon_SOAP_DotNet_GR01.Utils
{
    public static class ConsoleHelper
    {
        public static void MostrarTitulo(string titulo)
        {
            Console.Clear();
            Console.ForegroundColor = ConsoleColor.Cyan;
            Console.WriteLine("═══════════════════════════════════════════════════════════");
            Console.WriteLine($"  {titulo}");
            Console.WriteLine("═══════════════════════════════════════════════════════════");
            Console.ResetColor();
            Console.WriteLine();
        }

        public static void MostrarExito(string mensaje)
        {
            Console.ForegroundColor = ConsoleColor.Green;
            Console.WriteLine($"✓ {mensaje}");
            Console.ResetColor();
        }

        public static void MostrarError(string mensaje)
        {
            Console.ForegroundColor = ConsoleColor.Red;
            Console.WriteLine($"✗ ERROR: {mensaje}");
            Console.ResetColor();
        }

        public static void MostrarAdvertencia(string mensaje)
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine($"⚠ {mensaje}");
            Console.ResetColor();
        }

        public static void MostrarInfo(string mensaje)
        {
            Console.ForegroundColor = ConsoleColor.White;
            Console.WriteLine($"ℹ {mensaje}");
            Console.ResetColor();
        }

        public static string LeerTexto(string pregunta, bool obligatorio = true)
        {
            Console.Write($"{pregunta}: ");
            string? valor = Console.ReadLine();

            while (obligatorio && string.IsNullOrWhiteSpace(valor))
            {
                MostrarError("Este campo es obligatorio");
                Console.Write($"{pregunta}: ");
                valor = Console.ReadLine();
            }

            return valor ?? string.Empty;
        }

        public static int LeerEntero(string pregunta, int minimo = int.MinValue, int maximo = int.MaxValue)
        {
            while (true)
            {
                Console.Write($"{pregunta}: ");
                if (int.TryParse(Console.ReadLine(), out int valor) && valor >= minimo && valor <= maximo)
                    return valor;

                MostrarError($"Ingrese un número válido entre {minimo} y {maximo}");
            }
        }

        public static decimal LeerDecimal(string pregunta, decimal minimo = decimal.MinValue, decimal maximo = decimal.MaxValue)
        {
            while (true)
            {
                Console.Write($"{pregunta}: ");
                if (decimal.TryParse(Console.ReadLine(), out decimal valor) && valor >= minimo && valor <= maximo)
                    return valor;

                MostrarError($"Ingrese un número decimal válido entre {minimo} y {maximo}");
            }
        }

        public static void PausarConsola()
        {
            Console.WriteLine();
            Console.ForegroundColor = ConsoleColor.Gray;
            Console.Write("Presione cualquier tecla para continuar...");
            Console.ResetColor();
            Console.ReadKey();
        }

        public static void MostrarLinea()
        {
            Console.WriteLine("───────────────────────────────────────────────────────────");
        }
    }
}
