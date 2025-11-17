using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace Comercializadora_Soap_DotNet_GR01.DTOs
{
    [DataContract]
    public class FacturaDTO
    {
        [DataMember]
        public int FacturaId { get; set; }

        [DataMember]
        public string NumeroFactura { get; set; }

        [DataMember]
        public string CedulaCliente { get; set; }

        [DataMember]
        public string NombreCliente { get; set; }

        [DataMember]
        public string FormaPago { get; set; }

        [DataMember]
        public decimal Subtotal { get; set; }

        [DataMember]
        public decimal Descuento { get; set; }

        [DataMember]
        public decimal Total { get; set; }

        [DataMember]
        public string NumeroCredito { get; set; }

        [DataMember]
        public DateTime FechaEmision { get; set; }

        [DataMember]
        public List<DetalleFacturaDTO> Detalles { get; set; }
    }

    [DataContract]
    public class DetalleFacturaDTO
    {
        [DataMember]
        public int ProductoId { get; set; }

        [DataMember]
        public string NombreProducto { get; set; }

        [DataMember]
        public int Cantidad { get; set; }

        [DataMember]
        public decimal PrecioUnitario { get; set; }

        [DataMember]
        public decimal Subtotal { get; set; }
    }

    [DataContract]
    public class SolicitudFacturaDTO
    {
        [DataMember]
        public string CedulaCliente { get; set; }

        [DataMember]
        public string NombreCliente { get; set; }

        [DataMember]
        public string FormaPago { get; set; } // EFECTIVO o CREDITO

        [DataMember]
        public string NumeroCredito { get; set; } // Solo para CREDITO - Obtenido desde BanQuito

        [DataMember]
        public List<ItemFacturaDTO> Items { get; set; }
    }

    [DataContract]
    public class ItemFacturaDTO
    {
        [DataMember]
        public int ProductoId { get; set; }

        [DataMember]
        public int Cantidad { get; set; }
    }
}