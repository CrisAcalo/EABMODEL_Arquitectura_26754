using System;
using System.Collections.Generic;
using System.Linq;
using BanquitoServer_Soap_DotNet_GR01.BusinessLogic;
using BanquitoServer_Soap_DotNet_GR01.Constants;
using BanquitoServer_Soap_DotNet_GR01.DTOs;
using BanquitoServer_Soap_DotNet_GR01.Validators;

namespace BanquitoServer_Soap_DotNet_GR01.WS
{
    /// <summary>
    /// Implementación del servicio SOAP de Crédito
    /// Expone los 4 Web Services requeridos
    /// </summary>
    public class CreditoSoapService : ICreditoSoapService
    {
        private readonly CreditoValidacionService _validacionService;
        private readonly CreditoService _creditoService;

        public CreditoSoapService()
        {
            _validacionService = new CreditoValidacionService();
            _creditoService = new CreditoService();
        }

        /// <summary>
        /// WS 1: Validar si una persona es sujeto de crédito
        /// GET /CreditoSoapService.svc
        /// Parámetro: cedula
        /// </summary>
        public ValidacionCreditoDTO ValidarSujetoCredito(string cedula)
        {
            try
            {
                // Validar formato de cédula
                if (!CedulaValidator.Validar(cedula, out string mensajeError))
                {
                    return new ValidacionCreditoDTO
                    {
                        EsValido = false,
                        Mensaje = mensajeError,
                        Cedula = cedula,
                        NombreCompleto = null
                    };
                }

                var resultado = _validacionService.ValidarSujetoCredito(cedula);

                return new ValidacionCreditoDTO
                {
                    EsValido = resultado.EsValido,
                    Mensaje = resultado.Mensaje,
                    Cedula = cedula,
                    NombreCompleto = resultado.Cliente?.NombreCompleto
                };
            }
            catch (Exception ex)
            {
                return new ValidacionCreditoDTO
                {
                    EsValido = false,
                    Mensaje = string.Format(ErrorMessages.ErrorInterno, ex.Message),
                    Cedula = cedula,
                    NombreCompleto = null
                };
            }
        }

        /// <summary>
        /// WS 2: Obtener el monto máximo de crédito
        /// GET /CreditoSoapService.svc
        /// Parámetro: cedula
        /// </summary>
        public MontoMaximoCreditoDTO ObtenerMontoMaximo(string cedula)
        {
            try
            {
                // Validar formato de cédula
                if (!CedulaValidator.Validar(cedula, out string mensajeError))
                {
                    return new MontoMaximoCreditoDTO
                    {
                        Cedula = cedula,
                        MontoMaximo = 0,
                        PromedioDepositos = 0,
                        PromedioRetiros = 0,
                        Mensaje = mensajeError
                    };
                }

                var resultado = _validacionService.CalcularMontoMaximo(cedula);

                return new MontoMaximoCreditoDTO
                {
                    Cedula = cedula,
                    MontoMaximo = resultado.MontoMaximo,
                    PromedioDepositos = resultado.PromedioDepositos,
                    PromedioRetiros = resultado.PromedioRetiros,
                    Mensaje = resultado.Mensaje
                };
            }
            catch (Exception ex)
            {
                return new MontoMaximoCreditoDTO
                {
                    Cedula = cedula,
                    MontoMaximo = 0,
                    PromedioDepositos = 0,
                    PromedioRetiros = 0,
                    Mensaje = string.Format(ErrorMessages.ErrorInterno, ex.Message)
                };
            }
        }

        /// <summary>
        /// WS 3: Otorgar un crédito y generar tabla de amortización
        /// POST /CreditoSoapService.svc
        /// Body: SolicitudCreditoDTO
        /// </summary>
        public RespuestaCreditoDTO OtorgarCredito(SolicitudCreditoDTO solicitud)
        {
            try
            {
                // Validar y convertir la solicitud
                if (!SolicitudCreditoValidator.ValidarYConvertir(solicitud, out decimal precio, out int cuotas, out string mensajeError))
                {
                    return new RespuestaCreditoDTO
                    {
                        Exito = false,
                        Mensaje = mensajeError,
                        Cedula = solicitud?.Cedula
                    };
                }

                var resultado = _creditoService.OtorgarCredito(
                    solicitud.Cedula,
                    precio,
                    cuotas);

                var response = new RespuestaCreditoDTO
                {
                    Exito = resultado.Exito,
                    Mensaje = resultado.Mensaje,
                    Cedula = solicitud.Cedula
                };

                if (resultado.Exito && resultado.Credito != null)
                {
                    response.NumeroCredito = resultado.Credito.NumeroCredito;
                    response.MontoCredito = resultado.Credito.MontoCredito;
                    response.NumeroCuotas = resultado.Credito.NumeroCuotas;
                    response.CuotaMensual = resultado.Credito.CuotaMensual;
                    response.TasaInteres = resultado.Credito.TasaInteres;

                    // Convertir tabla de amortización a DTO
                    response.TablaAmortizacion = resultado.TablaAmortizacion.Select(c => new CuotaAmortizacionDTO
                    {
                        NumeroCuota = c.NumeroCuota,
                        ValorCuota = c.ValorCuota,
                        Interes = c.Interes,
                        CapitalPagado = c.CapitalPagado,
                        Saldo = c.Saldo
                    }).ToList();
                }

                return response;
            }
            catch (Exception ex)
            {
                return new RespuestaCreditoDTO
                {
                    Exito = false,
                    Mensaje = string.Format(ErrorMessages.ErrorInterno, ex.Message),
                    Cedula = solicitud?.Cedula
                };
            }
        }

        /// <summary>
        /// WS 4: Obtener la tabla de amortización de un crédito
        /// GET /CreditoSoapService.svc
        /// Parámetro: numeroCredito
        /// </summary>
        public List<CuotaAmortizacionDTO> ObtenerTablaAmortizacion(string numeroCredito)
        {
            try
            {
                var tabla = _creditoService.ObtenerTablaAmortizacion(numeroCredito);

                return tabla.Select(c => new CuotaAmortizacionDTO
                {
                    NumeroCuota = c.NumeroCuota,
                    ValorCuota = c.ValorCuota,
                    Interes = c.Interes,
                    CapitalPagado = c.CapitalPagado,
                    Saldo = c.Saldo
                }).ToList();
            }
            catch (Exception ex)
            {
                // Retornar lista vacía en caso de error
                return new List<CuotaAmortizacionDTO>();
            }
        }
    }
}
