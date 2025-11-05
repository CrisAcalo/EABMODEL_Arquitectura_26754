// Services/SoapConversionService.cs
using UniversalConverter.Client.Models;
using System.ServiceModel;
using System.Threading.Tasks;

// Importamos TODOS los nuevos namespaces
using DotNetLongitud;
using DotNetMasa;
using DotNetTemperatura;
using JavaLongitud;
using JavaMasa;
using JavaTemperatura;
using System.Reflection;

namespace UniversalConverter.Client.Services
{
    public class SoapConversionService : IConversionService
    {
        private ApiTarget _currentTarget = ApiTarget.Java;
        private static readonly BasicHttpBinding Binding = new BasicHttpBinding();

        // --- Endpoints ---
        private const string DotNetLongitudUrl = "http://localhost:56686/ec/edu/monster/ws/LongitudService.svc";
        private const string DotNetMasaUrl = "http://localhost:56686/ec/edu/monster/ws/MasaService.svc";
        private const string DotNetTemperaturaUrl = "http://localhost:56686/ec/edu/monster/ws/TemperaturaService.svc";
        private const string JavaLongitudUrl = "http://localhost:8080/ConUni_Soap_Java_GR01/LongitudService";
        private const string JavaMasaUrl = "http://localhost:8080/ConUni_Soap_Java_GR01/MasaService";
        private const string JavaTemperaturaUrl = "http://localhost:8080/ConUni_Soap_Java_GR01/TemperaturaService";

        public void SetTarget(ApiTarget target)
        {
            _currentTarget = target;
        }

        public async Task<ConversionResponse> ConvertAsync(ConversionRequest request)
        {
            //if (!double.TryParse(request.Valor, out double valorNumerico))
            //{
            //    return new ConversionResponse { Exitoso = false, Error = new ErrorData { Mensaje = "El valor ingresado no es un número válido." } };
            //}

            try
            {
                if (_currentTarget == ApiTarget.DotNet)
                {
                    return await CallDotNetService(request);
                }
                else // ApiTarget.Java
                {
                    return await CallJavaService(request);
                }
            }
            catch (FaultException fex)
            {
                return new ConversionResponse { Exitoso = false, Error = new ErrorData { Mensaje = $"Error de SOAP: {fex.Message}" } };
            }
            catch (Exception ex)
            {
                return new ConversionResponse { Exitoso = false, Error = new ErrorData { Mensaje = $"Error de conexión: {ex.Message}" } };
            }
        }

        private async Task<ConversionResponse> CallDotNetService(ConversionRequest request)
        {
            string operationName = $"{request.UnidadOrigen}A{request.UnidadDestino}Async";
            object rawResponse = null;

            // Invocamos el método, que devuelve un Task<T> guardado en un object
            Task resultTask;

            switch (request.TipoConversion)
            {
                case ConversionType.Longitud:
                    var clientL = new DotNetLongitud.LongitudServiceClient(Binding, new EndpointAddress(DotNetLongitudUrl));
                    resultTask = (Task)clientL.GetType().GetMethod(operationName).Invoke(clientL, new object[] { request.Valor });
                    break;
                case ConversionType.Masa:
                    var clientM = new DotNetMasa.MasaServiceClient(Binding, new EndpointAddress(DotNetMasaUrl));
                    resultTask = (Task)clientM.GetType().GetMethod(operationName).Invoke(clientM, new object[] { request.Valor });
                    break;
                case ConversionType.Temperatura:
                    var clientT = new DotNetTemperatura.TemperaturaServiceClient(Binding, new EndpointAddress(DotNetTemperaturaUrl));
                    resultTask = (Task)clientT.GetType().GetMethod(operationName).Invoke(clientT, new object[] { request.Valor });
                    break;
                default:
                    throw new InvalidOperationException("Tipo de conversión no soportado");
            }

            // Await dinámicamente. El runtime sabe cómo esperar un Task.
            await resultTask.ConfigureAwait(false);

            // Una vez completado el Task, obtenemos su resultado usando reflexión.
            rawResponse = ((dynamic)resultTask).Result;
            return MapDotNetResponse(rawResponse);
        }

        private async Task<ConversionResponse> CallJavaService(ConversionRequest request)
        {
            // ===== CORRECCIÓN 1: Usar PascalCase para el nombre del método, igual que en .NET =====
            string operationName = $"{request.UnidadOrigen}A{request.UnidadDestino}Async";
            object rawResponse = null;

            Task resultTask;

            switch (request.TipoConversion)
            {
                case ConversionType.Longitud:
                    var clientL = new JavaLongitud.LongitudServiceClient(Binding, new EndpointAddress(JavaLongitudUrl));
                    // ===== CORRECCIÓN 2: Pasar el 'double value', no el 'string request.Valor' =====
                    resultTask = (Task)clientL.GetType().GetMethod(operationName).Invoke(clientL, new object[] { request.Valor });
                    break;
                case ConversionType.Masa:
                    var clientM = new JavaMasa.MasaServiceClient(Binding, new EndpointAddress(JavaMasaUrl));
                    // ===== CORRECCIÓN 2 =====
                    resultTask = (Task)clientM.GetType().GetMethod(operationName).Invoke(clientM, new object[] { request.Valor });
                    break;
                case ConversionType.Temperatura:
                    var clientT = new JavaTemperatura.TemperaturaServiceClient(Binding, new EndpointAddress(JavaTemperaturaUrl));
                    // ===== CORRECCIÓN 2 =====
                    resultTask = (Task)clientT.GetType().GetMethod(operationName).Invoke(clientT, new object[] { request.Valor });
                    break;
                default:
                    throw new InvalidOperationException("Tipo de conversión no soportado");
            }

            await resultTask.ConfigureAwait(false);
            rawResponse = ((dynamic)resultTask).Result;

            // El método de mapeo para Java ya es correcto y no necesita cambios.
            return MapJavaResponse(rawResponse);
        }
        // Reemplaza este método completo en Services/SoapConversionService.cs
        private ConversionResponse MapDotNetResponse(object soapResponse)
        {
            // El objeto 'soapResponse' YA ES el objeto '...Result' que necesitamos.
            // Añadimos una comprobación de seguridad por si la respuesta es nula.
            if (soapResponse == null)
            {
                return new ConversionResponse { Exitoso = false, Error = new ErrorData { Mensaje = "La respuesta del servicio SOAP de .NET fue nula." } };
            }

            // Ahora obtenemos las propiedades directamente de 'soapResponse'.
            var exitoso = (bool)soapResponse.GetType().GetProperty("Exitoso").GetValue(soapResponse);
            var resultado = soapResponse.GetType().GetProperty("Resultado").GetValue(soapResponse);
            var error = soapResponse.GetType().GetProperty("Error").GetValue(soapResponse);

            var unifiedResponse = new ConversionResponse { Exitoso = exitoso };

            if (exitoso && resultado != null)
            {
                unifiedResponse.Resultado = new ResultData
                {
                    ValorOriginal = (double)resultado.GetType().GetProperty("ValorOriginal").GetValue(resultado),
                    ValorConvertidoExacto = (double)resultado.GetType().GetProperty("ValorConvertidoExacto").GetValue(resultado),
                    ValorConvertidoRedondeado = (double)resultado.GetType().GetProperty("ValorConvertidoRedondeado").GetValue(resultado),
                    UnidadOrigen = (string)resultado.GetType().GetProperty("UnidadOrigen").GetValue(resultado),
                    UnidadDestino = (string)resultado.GetType().GetProperty("UnidadDestino").GetValue(resultado),
                    FactorConversion = (double)resultado.GetType().GetProperty("FactorConversion").GetValue(resultado),
                    FechaConversion = (DateTime)resultado.GetType().GetProperty("FechaConversion").GetValue(resultado)
                };
            }
            else if (error != null)
            {
                unifiedResponse.Error = new ErrorData
                {
                    CodigoError = (string)error.GetType().GetProperty("CodigoError").GetValue(error),
                    Mensaje = (string)error.GetType().GetProperty("Mensaje").GetValue(error),
                    Detalles = (string)error.GetType().GetProperty("Detalles").GetValue(error),
                    TipoError = (string)error.GetType().GetProperty("TipoError").GetValue(error),
                    Unidad = (string)error.GetType().GetProperty("Unidad").GetValue(error),
                    FechaError = (DateTime)error.GetType().GetProperty("FechaError").GetValue(error),
                    ValorProblematico = error.GetType().GetProperty("ValorProblematico").GetValue(error)
                };
            }
            return unifiedResponse;
        }
        // Reemplaza este método completo en Services/SoapConversionService.cs
        private ConversionResponse MapJavaResponse(object soapResponse)
        {
            if (soapResponse == null)
            {
                return new ConversionResponse { Exitoso = false, Error = new ErrorData { Mensaje = "La respuesta del servicio SOAP de Java fue nula." } };
            }

            BindingFlags flags = BindingFlags.Instance | BindingFlags.Public | BindingFlags.NonPublic;

            // ===== LA CORRECCIÓN FINAL: Buscamos el nombre real del campo, "return" =====
            var returnField = soapResponse.GetType().GetField("return", flags);

            if (returnField == null)
            {
                // Este mensaje ya no debería aparecer nunca más.
                return new ConversionResponse { Exitoso = false, Error = new ErrorData { Mensaje = "Error inesperado: No se pudo encontrar el campo 'return' incluso con reflexión avanzada." } };
            }

            var conversionResultObject = returnField.GetValue(soapResponse);
            if (conversionResultObject == null)
            {
                return new ConversionResponse { Exitoso = false, Error = new ErrorData { Mensaje = "El objeto de resultado ('return') en la respuesta de Java fue nulo." } };
            }

            var exitoso = (bool)conversionResultObject.GetType().GetProperty("Exitoso").GetValue(conversionResultObject);
            var resultado = conversionResultObject.GetType().GetProperty("Resultado").GetValue(conversionResultObject);
            var error = conversionResultObject.GetType().GetProperty("Error").GetValue(conversionResultObject);

            var unifiedResponse = new ConversionResponse { Exitoso = exitoso };

            if (exitoso && resultado != null)
            {
                unifiedResponse.Resultado = new ResultData
                {
                    ValorOriginal = (double)resultado.GetType().GetProperty("valorOriginal").GetValue(resultado),
                    ValorConvertidoExacto = (double)resultado.GetType().GetProperty("valorConvertidoExacto").GetValue(resultado),
                    ValorConvertidoRedondeado = (double)resultado.GetType().GetProperty("valorConvertidoRedondeado").GetValue(resultado),
                    UnidadOrigen = (string)resultado.GetType().GetProperty("unidadOrigen").GetValue(resultado),
                    UnidadDestino = (string)resultado.GetType().GetProperty("unidadDestino").GetValue(resultado),
                    FactorConversion = (double)resultado.GetType().GetProperty("factorConversion").GetValue(resultado),
                    FechaConversion = DateTime.Now
                };
            }
            else if (error != null)
            {
                unifiedResponse.Error = new ErrorData
                {
                    CodigoError = (string)error.GetType().GetProperty("CodigoError").GetValue(error),
                    Mensaje = (string)error.GetType().GetProperty("Mensaje").GetValue(error),
                    Detalles = (string)error.GetType().GetProperty("Detalles").GetValue(error),
                    TipoError = (string)error.GetType().GetProperty("TipoError").GetValue(error),
                    Unidad = (string)error.GetType().GetProperty("Unidad").GetValue(error),
                    ValorProblematico = error.GetType().GetProperty("ValorProblematico").GetValue(error)
                };
            }
            return unifiedResponse;
        }
    }
}