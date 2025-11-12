using EurekaBank_Soap_DotNet_GR01.Models;
using EurekaBank_Soap_DotNet_GR01.Models.DTOs;
using EurekaBank_Soap_DotNet_GR01.Services;

namespace EurekaBank_Soap_DotNet_GR01.WS
{
    /// <summary>
    /// Implementación del servicio WCF de autenticación
    /// </summary>
    public class ServicioAutenticacion : IServicioAutenticacion
    {
        private readonly AutenticacionService autenticacionService;

        public ServicioAutenticacion()
        {
            autenticacionService = new AutenticacionService();
        }

        public RespuestaDTO Login(string usuario, string clave)
        {
            return autenticacionService.Login(usuario, clave);
        }

        public RespuestaDTO RegistrarEmpleado(Empleado empleado)
        {
            return autenticacionService.RegistrarEmpleado(empleado);
        }

        public RespuestaDTO CambiarClave(string codigo, string claveActual, string claveNueva)
        {
            return autenticacionService.CambiarClave(codigo, claveActual, claveNueva);
        }
    }
}
