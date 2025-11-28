using System;
using System.Collections.Generic;
using BanquitoServer_Soap_DotNet_GR01.Constants;
using BanquitoServer_Soap_DotNet_GR01.DataAccess;
using BanquitoServer_Soap_DotNet_GR01.DataAccess.Repositories;
using BanquitoServer_Soap_DotNet_GR01.Models;
using BanquitoServer_Soap_DotNet_GR01.Utilities;

namespace BanquitoServer_Soap_DotNet_GR01.BusinessLogic
{
    /// <summary>
    /// Servicio para otorgar créditos y gestionar tabla de amortización
    /// </summary>
    public class CreditoService
    {
        private readonly CreditoRepository _creditoRepository;
        private readonly CreditoValidacionService _validacionService;

        public CreditoService()
        {
            var context = new BanquitoDbContext();
            _creditoRepository = new CreditoRepository(context);
            _validacionService = new CreditoValidacionService();
        }

        /// <summary>
        /// Otorgar un crédito y generar la tabla de amortización
        /// </summary>
        public ResultadoCredito OtorgarCredito(string cedula, decimal montoCredito, int numeroCuotas)
        {
            // Obtener configuración desde AppConfig
            int plazoMin = AppConfig.CreditoPlazoMinimo;
            int plazoMax = AppConfig.CreditoPlazoMaximo;
            decimal tasaAnual = AppConfig.CreditoTasaAnual;

            // Validar plazo
            if (numeroCuotas < plazoMin || numeroCuotas > plazoMax)
            {
                return new ResultadoCredito
                {
                    Exito = false,
                    Mensaje = string.Format(ErrorMessages.PlazoInvalido, plazoMin, plazoMax),
                    Credito = null,
                    TablaAmortizacion = null
                };
            }

            // Validar sujeto de crédito
            var validacion = _validacionService.ValidarSujetoCredito(cedula);
            if (!validacion.EsValido)
            {
                return new ResultadoCredito
                {
                    Exito = false,
                    Mensaje = validacion.Mensaje,
                    Credito = null,
                    TablaAmortizacion = null
                };
            }

            // Verificar monto máximo
            var montoMaximoInfo = _validacionService.CalcularMontoMaximo(cedula);
            if (montoCredito > montoMaximoInfo.MontoMaximo)
            {
                return new ResultadoCredito
                {
                    Exito = false,
                    Mensaje = string.Format(ErrorMessages.MontoSuperaMaximo, montoCredito, montoMaximoInfo.MontoMaximo),
                    Credito = null,
                    TablaAmortizacion = null
                };
            }

            // Calcular cuota mensual
            decimal cuotaMensual = CalculadoraFinanciera.CalcularCuotaFija(montoCredito, tasaAnual, numeroCuotas);

            // Crear el crédito
            Credito credito = new Credito
            {
                NumeroCredito = GenerarNumeroCredito(),
                MontoCredito = montoCredito,
                TasaInteres = tasaAnual,
                NumeroCuotas = numeroCuotas,
                CuotaMensual = cuotaMensual,
                FechaOtorgamiento = DateTime.Now,
                Estado = "ACTIVO",
                Descripcion = "Crédito para compra de electrodoméstico",
                ClienteId = validacion.Cliente.ClienteId
            };

            // Generar tabla de amortización
            List<CuotaAmortizacion> tablaAmortizacion = CalculadoraFinanciera.GenerarTablaAmortizacion(
                montoCredito, tasaAnual, numeroCuotas, cuotaMensual);

            // Asociar cuotas al crédito
            foreach (var cuota in tablaAmortizacion)
            {
                credito.CuotasAmortizacion.Add(cuota);
            }

            // Guardar en la base de datos
            _creditoRepository.Save(credito);

            return new ResultadoCredito
            {
                Exito = true,
                Mensaje = ErrorMessages.CreditoOtorgado,
                Credito = credito,
                TablaAmortizacion = tablaAmortizacion
            };
        }

        /// <summary>
        /// Obtener la tabla de amortización de un crédito
        /// </summary>
        public List<CuotaAmortizacion> ObtenerTablaAmortizacion(string numeroCredito)
        {
            Credito credito = _creditoRepository.FindByNumeroCredito(numeroCredito);

            if (credito == null)
                return new List<CuotaAmortizacion>();

            return new List<CuotaAmortizacion>(credito.CuotasAmortizacion);
        }

        /// <summary>
        /// Generar un número de crédito único
        /// </summary>
        private string GenerarNumeroCredito()
        {
            // Formato: CRE + yyyyMMddHHmmss = 17 caracteres (dentro del límite de 20)
            return $"CRE{DateTime.Now:yyyyMMddHHmmss}";
        }
    }

    /// <summary>
    /// Clase para resultado de otorgar crédito
    /// </summary>
    public class ResultadoCredito
    {
        public bool Exito { get; set; }
        public string Mensaje { get; set; }
        public Credito Credito { get; set; }
        public List<CuotaAmortizacion> TablaAmortizacion { get; set; }
    }
}
