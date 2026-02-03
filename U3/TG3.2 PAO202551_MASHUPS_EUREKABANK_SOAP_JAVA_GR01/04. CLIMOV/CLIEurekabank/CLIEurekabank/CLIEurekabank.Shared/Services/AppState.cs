namespace CLIEurekabank.Shared.Services
{
    /// <summary>
    /// Servicio Singleton para mantener el estado de la sesión del empleado
    /// </summary>
    public class AppState
    {
        public EmpleadoDTO? EmpleadoActual { get; private set; }
        
        public bool IsAuthenticated => EmpleadoActual != null;
        
        public string CodigoEmpleado => EmpleadoActual?.Codigo ?? "";
        public string NombreEmpleado => EmpleadoActual != null 
            ? $"{EmpleadoActual.Nombre} {EmpleadoActual.Paterno}".Trim() 
            : "";
        
        public event Action? OnAuthStateChanged;

        public void SetEmpleado(EmpleadoDTO? empleado)
        {
            EmpleadoActual = empleado;
            NotifyAuthStateChanged();
        }

        public void Logout()
        {
            EmpleadoActual = null;
            NotifyAuthStateChanged();
        }

        private void NotifyAuthStateChanged() => OnAuthStateChanged?.Invoke();
    }

    public class EmpleadoDTO
    {
        public string Codigo { get; set; } = "";
        public string Usuario { get; set; } = "";
        public string Nombre { get; set; } = "";
        public string Paterno { get; set; } = "";
        public string Materno { get; set; } = "";
        public string CodigoSucursal { get; set; } = "";
        public string CodigoVentanilla { get; set; } = "";
        
        public string NombreCompleto => $"{Nombre} {Paterno} {Materno}".Trim();
    }
}
