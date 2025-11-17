using System;
using System.Collections.Generic;
using Comercializadora_Soap_DotNet_GR01.DTOs;
using Comercializadora_Soap_DotNet_GR01.Services;

namespace Comercializadora_Soap_DotNet_GR01.WS
{
    public class FacturacionSoapService : IFacturacionSoapService
    {
        private readonly FacturacionService _facturacionService;

        public FacturacionSoapService()
        {
            _facturacionService = new FacturacionService();
        }

        public FacturaDTO GenerarFacturaEfectivo(SolicitudFacturaDTO solicitud)
        {
            try
            {
                return _facturacionService.GenerarFacturaEfectivo(solicitud);
            }
            catch (Exception ex)
            {
                throw new Exception($"Error en servicio SOAP GenerarFacturaEfectivo: {ex.Message}");
            }
        }

        public FacturaDTO GenerarFacturaCredito(SolicitudFacturaDTO solicitud)
        {
            try
            {
                return _facturacionService.GenerarFacturaCredito(solicitud);
            }
            catch (Exception ex)
            {
                throw new Exception($"Error en servicio SOAP GenerarFacturaCredito: {ex.Message}");
            }
        }

        public List<FacturaDTO> ObtenerFacturasPorCliente(string cedula)
        {
            try
            {
                return _facturacionService.ObtenerFacturasPorCliente(cedula);
            }
            catch (Exception ex)
            {
                throw new Exception($"Error en servicio SOAP ObtenerFacturasPorCliente: {ex.Message}");
            }
        }

        public FacturaDTO ObtenerFacturaPorNumero(string numeroFactura)
        {
            try
            {
                return _facturacionService.ObtenerFacturaPorNumero(numeroFactura);
            }
            catch (Exception ex)
            {
                throw new Exception($"Error en servicio SOAP ObtenerFacturaPorNumero: {ex.Message}");
            }
        }
    }
}