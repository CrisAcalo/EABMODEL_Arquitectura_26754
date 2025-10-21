using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.models;

namespace ConUni_Soap_DotNet_GR01.ec.edu.monster.ws
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "ILongitudService" in both code and config file together.
    [ServiceContract]
    public interface ILongitudService
    {
        // Conversiones Milla <-> Metro
        [OperationContract]
        ConversionResult MillaAMetro(string millas);

        [OperationContract]
        ConversionResult MetroAMilla(string metros);

        // Conversiones Milla <-> Pulgada
        [OperationContract]
        ConversionResult MillaAPulgada(string millas);

        [OperationContract]
        ConversionResult PulgadaAMilla(string pulgadas);

        // Conversiones Metro <-> Pulgada
        [OperationContract]
        ConversionResult MetroAPulgada(string metros);

        [OperationContract]
        ConversionResult PulgadaAMetro(string pulgadas);
    }
}
