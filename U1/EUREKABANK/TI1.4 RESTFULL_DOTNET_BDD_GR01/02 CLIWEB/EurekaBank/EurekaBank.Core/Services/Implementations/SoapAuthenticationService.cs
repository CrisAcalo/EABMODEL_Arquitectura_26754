// En: EurekaBank.Core/Services/Implementations/SoapAuthenticationService.cs
// Importamos los namespaces de los clientes SOAP
using DotNetSoapAuth;
using EurekaBank.Core.Managers;
using EurekaBank.Core.Models.Requests;
using EurekaBank.Core.Models.Responses;
using EurekaBank.Core.Services.Abstractions;
using JavaSoapAuth;
using Microsoft.Extensions.Configuration; // Corregido por NuGet
using System.ServiceModel;

namespace EurekaBank.Core.Services.Implementations
{
    public class SoapAuthenticationService : IAuthenticationService
    {
        private readonly IConfiguration _configuration;
        private ApiPlatform _currentTarget = ApiPlatform.Java;
        private static readonly BasicHttpBinding Binding = new BasicHttpBinding();

        public void SetTarget(ApiPlatform target) // Asegúrate de que sea public
        {
            _currentTarget = target;
        }
        public SoapAuthenticationService(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        public async Task<LoginResponse> LoginAsync(LoginRequest request)
        {
            try
            {
                if (_currentTarget == ApiPlatform.DotNet)
                {
                    return await CallDotNetLoginAsync(request);
                }
                else // ApiTarget.Java
                {
                    return await CallJavaLoginAsync(request);
                }
            }
            catch (FaultException fex)
            {
                return new LoginResponse { Exitoso = false, Mensaje = $"Error de SOAP: {fex.Message}" };
            }
            catch (Exception ex)
            {
                return new LoginResponse { Exitoso = false, Mensaje = $"Error de conexión: {ex.Message}" };
            }
        }

        // --- LÓGICA PARA EL SERVIDOR .NET ---

        private async Task<LoginResponse> CallDotNetLoginAsync(LoginRequest request)
        {
            var endpointUrl = _configuration["Hosts:Soap:DotNet:Authentication"];
            var client = new DotNetSoapAuth.ServicioAutenticacionClient(Binding, new EndpointAddress(endpointUrl));

            // 'soapResponse' será ahora del tipo 'RespuestaDTO'
            var soapResponse = await client.LoginAsync(request.Usuario, request.Clave);

            // Le pasamos el objeto 'RespuestaDTO' al mapeador
            return MapDotNetResponse(soapResponse);
        }

        // CORRECCIÓN: El parámetro es ahora del tipo 'RespuestaDTO'
        private LoginResponse MapDotNetResponse(DotNetSoapAuth.RespuestaDTO soapResult)
        {
            if (soapResult == null)
            {
                return new LoginResponse { Exitoso = false, Mensaje = "La respuesta del servicio SOAP de .NET fue nula." };
            }

            var unifiedResponse = new LoginResponse
            {
                Exitoso = soapResult.Exitoso,
                Mensaje = soapResult.Mensaje,
                CodigoError = soapResult.CodigoError
            };

            if (soapResult.Exitoso && soapResult.Datos != null)
            {
                // El tipo de 'Datos' podría ser simplemente 'object'.
                // Debemos hacer un cast al tipo de Empleado que WCF generó.
                // Asumo que se llama 'DotNetSoapAuth.Empleado' basado en tu XML anterior.
                var empleado = soapResult.Datos as DotNetSoapAuth.Empleado;
                if (empleado != null)
                {
                    unifiedResponse.Datos = new EmpleadoDto
                    {
                        Codigo = empleado.Codigo,
                        Paterno = empleado.Paterno,
                        Materno = empleado.Materno,
                        Nombre = empleado.Nombre,
                        Ciudad = empleado.Ciudad,
                        Direccion = empleado.Direccion,
                        Usuario = empleado.Usuario
                    };
                }
            }

            return unifiedResponse;
        }

        // --- LÓGICA PARA EL SERVIDOR JAVA ---

        private async Task<LoginResponse> CallJavaLoginAsync(LoginRequest request)
        {
            var endpointUrl = _configuration["Hosts:Soap:Java:Authentication"];
            var client = new JavaSoapAuth.ServicioAutenticacionClient(Binding, new EndpointAddress(endpointUrl));

            var soapResponse = await client.loginAsync(request.Usuario, request.Clave);

            return MapJavaResponse(soapResponse);
        }

        // CORRECCIÓN 4: Se usa el tipo de respuesta correcto (ej. loginResponse1)
        private LoginResponse MapJavaResponse(JavaSoapAuth.loginResponse1 soapResponse)
        {
            var result = soapResponse.@return;

            var unifiedResponse = new LoginResponse
            {
                Exitoso = result.exitoso,
                Mensaje = result.mensaje,
                CodigoError = result.codigoError
            };

            if (result.exitoso && result.datos != null)
            {
                var empleado = result.datos as JavaSoapAuth.empleado;
                if (empleado != null)
                {
                    unifiedResponse.Datos = new EmpleadoDto
                    {
                        Codigo = empleado.codigo,
                        Paterno = empleado.paterno,
                        Materno = empleado.materno,
                        Nombre = empleado.nombre,
                        Ciudad = empleado.ciudad,
                        Direccion = empleado.direccion,
                        Usuario = empleado.usuario
                    };
                }
            }

            return unifiedResponse;
        }
    }

    // Enum para controlar a qué API apuntamos
    public enum ApiTarget { Java, DotNet }
}