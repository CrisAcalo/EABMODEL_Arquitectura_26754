using System;
using System.Configuration;
using BanquitoServer_Soap_DotNet_GR01.DataAccess;
using BanquitoServer_Soap_DotNet_GR01.DataAccess.Repositories;
using BanquitoServer_Soap_DotNet_GR01.Models;
using BanquitoServer_Soap_DotNet_GR01.Utilities;

namespace BanquitoServer_Soap_DotNet_GR01.BusinessLogic
{
    /// <summary>
    /// Servicio para validación de crédito y cálculo de monto máximo
    /// </summary>
    public class CreditoValidacionService
    {
        private readonly ClienteRepository _clienteRepository;
        private readonly MovimientoRepository _movimientoRepository;
        private readonly CreditoRepository _creditoRepository;

        public CreditoValidacionService()
        {
            var context = new BanquitoDbContext();
            _clienteRepository = new ClienteRepository(context);
            _movimientoRepository = new MovimientoRepository(context);
            _creditoRepository = new CreditoRepository(context);
        }

        /// <summary>
        /// Validar si una persona es sujeto de crédito
        /// Reglas:
        /// 1. Es cliente del banco
        /// 2. Tiene depósito en último mes
        /// 3. Si casado, >= 25 años
        /// 4. No tiene crédito activo
        /// </summary>
        public ResultadoValidacion ValidarSujetoCredito(string cedula)
        {
            // Regla 1: Verificar si es cliente del banco
            Cliente cliente = _clienteRepository.FindByCedula(cedula);
            if (cliente == null)
            {
                return new ResultadoValidacion
                {
                    EsValido = false,
                    Mensaje = "La persona no es cliente del banco",
                    Cliente = null
                };
            }

            // Regla 2: Verificar que tenga al menos un depósito en el último mes
            if (!_movimientoRepository.TieneDepositoEnUltimoMes(cedula))
            {
                return new ResultadoValidacion
                {
                    EsValido = false,
                    Mensaje = "El cliente no tiene depósitos en el último mes",
                    Cliente = null
                };
            }

            // Regla 3: Si es casado, debe tener al menos 25 años
            if (!string.IsNullOrEmpty(cliente.EstadoCivil) &&
                cliente.EstadoCivil.ToLower() == "casado")
            {
                int edad = CalculadoraFinanciera.CalcularEdad(cliente.FechaNacimiento);
                if (edad < 25)
                {
                    return new ResultadoValidacion
                    {
                        EsValido = false,
                        Mensaje = $"El cliente casado debe tener al menos 25 años. Edad actual: {edad}",
                        Cliente = null
                    };
                }
            }

            // Regla 4: No debe tener un crédito activo
            if (_creditoRepository.TieneCreditoActivo(cedula))
            {
                return new ResultadoValidacion
                {
                    EsValido = false,
                    Mensaje = "El cliente ya tiene un crédito activo",
                    Cliente = null
                };
            }

            // Si pasó todas las validaciones
            return new ResultadoValidacion
            {
                EsValido = true,
                Mensaje = "El cliente es sujeto de crédito",
                Cliente = cliente
            };
        }

        /// <summary>
        /// Calcular el monto máximo de crédito
        /// Fórmula: ((Promedio Depósitos – Promedio Retiros) * 60%) * 9
        /// </summary>
        public ResultadoMontoMaximo CalcularMontoMaximo(string cedula)
        {
            // Primero validar si es sujeto de crédito
            var validacion = ValidarSujetoCredito(cedula);
            if (!validacion.EsValido)
            {
                return new ResultadoMontoMaximo
                {
                    MontoMaximo = 0,
                    PromedioDepositos = 0,
                    PromedioRetiros = 0,
                    Mensaje = validacion.Mensaje
                };
            }

            // Calcular promedios de los últimos 3 meses
            DateTime fechaInicio = DateTime.Now.AddMonths(-3);
            DateTime fechaFin = DateTime.Now;

            decimal promedioDepositos = _movimientoRepository.PromedioDepositosPorPeriodo(cedula, fechaInicio, fechaFin);
            decimal promedioRetiros = _movimientoRepository.PromedioRetirosPorPeriodo(cedula, fechaInicio, fechaFin);

            // Obtener configuración
            decimal porcentaje = decimal.Parse(ConfigurationManager.AppSettings["CreditoPorcentajeCapacidad"] ?? "0.60");
            int multiplicador = int.Parse(ConfigurationManager.AppSettings["CreditoMultiplicador"] ?? "9");

            // Calcular monto máximo
            decimal diferencia = promedioDepositos - promedioRetiros;
            decimal montoMaximo = Math.Max(0, diferencia * porcentaje * multiplicador);
            montoMaximo = Math.Round(montoMaximo, 2);

            return new ResultadoMontoMaximo
            {
                MontoMaximo = montoMaximo,
                PromedioDepositos = Math.Round(promedioDepositos, 2),
                PromedioRetiros = Math.Round(promedioRetiros, 2),
                Mensaje = "Monto máximo calculado exitosamente"
            };
        }
    }

    /// <summary>
    /// Clase para resultado de validación
    /// </summary>
    public class ResultadoValidacion
    {
        public bool EsValido { get; set; }
        public string Mensaje { get; set; }
        public Cliente Cliente { get; set; }
    }

    /// <summary>
    /// Clase para resultado de monto máximo
    /// </summary>
    public class ResultadoMontoMaximo
    {
        public decimal MontoMaximo { get; set; }
        public decimal PromedioDepositos { get; set; }
        public decimal PromedioRetiros { get; set; }
        public string Mensaje { get; set; }
    }
}
