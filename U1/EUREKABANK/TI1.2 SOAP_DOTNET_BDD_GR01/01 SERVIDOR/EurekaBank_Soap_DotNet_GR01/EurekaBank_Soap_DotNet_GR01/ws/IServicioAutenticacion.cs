using System.ServiceModel;
using EurekaBank_Soap_DotNet_GR01.Models;
using EurekaBank_Soap_DotNet_GR01.Models.DTOs;

namespace EurekaBank_Soap_DotNet_GR01.WS
{
    /// <summary>
    /// Contrato de servicio para autenticación y gestión de empleados
    /// </summary>
    [ServiceContract]
    public interface IServicioAutenticacion
    {
        /// <summary>
        /// Realiza el login de un empleado
        /// </summary>
        /// <param name="usuario">Usuario del empleado</param>
        /// <param name="clave">Contraseña del empleado</param>
        /// <returns>RespuestaDTO con los datos del empleado si las credenciales son correctas</returns>
        [OperationContract]
        RespuestaDTO Login(string usuario, string clave);

        /// <summary>
        /// Registra un nuevo empleado en el sistema
        /// </summary>
        /// <param name="empleado">Datos del empleado a registrar</param>
        /// <returns>RespuestaDTO con el resultado de la operación</returns>
        [OperationContract]
        RespuestaDTO RegistrarEmpleado(Empleado empleado);

        /// <summary>
        /// Cambia la contraseña de un empleado
        /// </summary>
        /// <param name="codigo">Código del empleado</param>
        /// <param name="claveActual">Contraseña actual</param>
        /// <param name="claveNueva">Nueva contraseña</param>
        /// <returns>RespuestaDTO con el resultado de la operación</returns>
        [OperationContract]
        RespuestaDTO CambiarClave(string codigo, string claveActual, string claveNueva);
    }
}
