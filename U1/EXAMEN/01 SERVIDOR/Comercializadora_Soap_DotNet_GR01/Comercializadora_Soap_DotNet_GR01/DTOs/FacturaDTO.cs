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
        [DataMember(IsRequired = false, Order = 1)]
        public string CedulaCliente { get; set; }

        [DataMember(IsRequired = false, Order = 2)]
        public string FormaPago { get; set; } // EFECTIVO o CREDITO

        [DataMember(IsRequired = false, Order = 3)]
        public List<ItemFacturaDTO> Items { get; set; }

        [DataMember(IsRequired = false, Order = 4)]
        public string NombreCliente { get; set; }

        [DataMember(IsRequired = false, Order = 5)]
        public string NumeroCredito { get; set; } // Solo para CREDITO - Obtenido desde BanQuito

        public SolicitudFacturaDTO()
        {
            Items = new List<ItemFacturaDTO>();
        }
    }

    [DataContract]
    public class ItemFacturaDTO
    {
        [DataMember(Order = 1)]
        public int Cantidad { get; set; }

        [DataMember(Order = 2)]
        public int ProductoId { get; set; }
    }
}