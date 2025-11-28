using System;
using System.Collections.Generic;
using BanquitoServer_Soap_DotNet_GR01.Models;

namespace BanquitoServer_Soap_DotNet_GR01.Utilities
{
    /// <summary>
    /// Calculadora para operaciones financieras
    /// </summary>
    public static class CalculadoraFinanciera
    {
        /// <summary>
        /// Calcular la cuota fija mensual
        /// Fórmula: Cuota = Valor / (1 - ((1 + TasaPeriodo)^-NúmeroCuotas)) / TasaPeriodo
        /// </summary>
        public static decimal CalcularCuotaFija(decimal montoCredito, decimal tasaAnual, int numeroCuotas)
        {
            // Tasa mensual
            decimal tasaMensual = tasaAnual / 12;

            // (1 + tasaMensual)
            decimal unMasTasa = 1 + tasaMensual;

            // (1 + tasaMensual)^-numeroCuotas
            double potencia = Math.Pow((double)unMasTasa, -numeroCuotas);

            // 1 - potencia
            decimal numerador = 1 - (decimal)potencia;

            // numerador / tasaMensual
            decimal denominador = numerador / tasaMensual;

            // Cuota = montoCredito / denominador
            decimal cuota = montoCredito / denominador;

            return Math.Round(cuota, 2);
        }

        /// <summary>
        /// Generar la tabla de amortización completa
        /// </summary>
        public static List<CuotaAmortizacion> GenerarTablaAmortizacion(
            decimal montoCredito,
            decimal tasaAnual,
            int numeroCuotas,
            decimal cuotaFija)
        {
            List<CuotaAmortizacion> tabla = new List<CuotaAmortizacion>();

            decimal tasaMensual = tasaAnual / 12;
            decimal saldo = montoCredito;

            for (int i = 1; i <= numeroCuotas; i++)
            {
                // Calcular interés de la cuota
                decimal interes = Math.Round(saldo * tasaMensual, 2);

                // Calcular capital pagado
                decimal capitalPagado = Math.Round(cuotaFija - interes, 2);

                // Calcular nuevo saldo
                decimal nuevoSaldo = Math.Round(saldo - capitalPagado, 2);

                // Ajuste para la última cuota (por posibles diferencias de redondeo)
                if (i == numeroCuotas)
                {
                    capitalPagado = saldo;
                    cuotaFija = capitalPagado + interes;
                    nuevoSaldo = 0;
                }

                // Crear cuota de amortización
                CuotaAmortizacion cuota = new CuotaAmortizacion
                {
                    NumeroCuota = i,
                    ValorCuota = cuotaFija,
                    Interes = interes,
                    CapitalPagado = capitalPagado,
                    Saldo = nuevoSaldo
                };

                tabla.Add(cuota);

                // Actualizar saldo para la siguiente iteración
                saldo = nuevoSaldo;
            }

            return tabla;
        }

        /// <summary>
        /// Calcular la edad de una persona
        /// </summary>
        public static int CalcularEdad(DateTime fechaNacimiento)
        {
            DateTime hoy = DateTime.Today;
            int edad = hoy.Year - fechaNacimiento.Year;

            if (fechaNacimiento.Date > hoy.AddYears(-edad))
                edad--;

            return edad;
        }
    }
}
