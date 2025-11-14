namespace CliUniversalConsole.Models
{
    public class MovimientoDetalle
    {
        public string CodigoCuenta { get; set; } = "";
        public int Numero { get; set; }
        public DateTime Fecha { get; set; }
        public string TipoMovimiento { get; set; } = "";
        public string CodigoEmpleado { get; set; } = "";
        public string? CuentaReferencia { get; set; }
        public decimal Importe { get; set; }

        public void Print()
        {
            Console.ForegroundColor = ConsoleColor.Cyan;
            Console.WriteLine($"\n┌─────────────────────────────────────────┐");
            Console.WriteLine($"│ Movimiento #{Numero}                    ");
            Console.WriteLine($"└─────────────────────────────────────────┘");
            Console.ResetColor();
            
            Console.WriteLine($"Fecha:             {Fecha:dd/MM/yyyy HH:mm:ss}");
            Console.WriteLine($"Tipo:              {TipoMovimiento}");
            Console.WriteLine($"Importe:           S/ {Importe:N2}");
            Console.WriteLine($"Empleado:          {CodigoEmpleado}");
            
            if (!string.IsNullOrEmpty(CuentaReferencia))
            {
                Console.WriteLine($"Cuenta Ref:        {CuentaReferencia}");
            }
        }
    }
}
