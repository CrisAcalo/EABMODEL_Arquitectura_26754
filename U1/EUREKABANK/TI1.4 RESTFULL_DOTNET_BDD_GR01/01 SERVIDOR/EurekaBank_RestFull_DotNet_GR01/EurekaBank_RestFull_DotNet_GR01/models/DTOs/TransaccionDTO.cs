using System;
using System.Runtime.Serialization;

namespace EurekaBank_RestFull_DotNet_GR01.Models.DTOs
{
    /// <summary>
    /// DTO para realizar operaciones de depósito o retiro
    /// </summary>
    [DataContract]
    public class TransaccionDTO
    {
        [DataMember]
        public string CodigoCuenta { get; set; }

        [DataMember]
        public string ClaveCuenta { get; set; }

        [DataMember]
        public decimal Importe { get; set; }

        [DataMember]
        public string CodigoEmpleado { get; set; }

        [DataMember]
        public string CodigoTipoMovimiento { get; set; }
    }
}
