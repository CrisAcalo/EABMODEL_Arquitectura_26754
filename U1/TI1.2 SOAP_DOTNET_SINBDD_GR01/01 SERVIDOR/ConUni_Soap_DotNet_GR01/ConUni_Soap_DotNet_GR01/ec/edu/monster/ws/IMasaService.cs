using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.models;

namespace ConUni_Soap_DotNet_GR01.ec.edu.monster.ws
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IMasaService" in both code and config file together.
    [ServiceContract]
    public interface IMasaService
    {
        // Conversiones Kilogramo <-> Quintal
        [OperationContract]
        ConversionResult KilogramoAQuintal(string kilogramos);

        [OperationContract]
        ConversionResult QuintalAKilogramo(string quintales);

        // Conversiones Kilogramo <-> Libra
        [OperationContract]
        ConversionResult KilogramoALibra(string kilogramos);

        [OperationContract]
        ConversionResult LibraAKilogramo(string libras);

        // Conversiones Quintal <-> Libra
        [OperationContract]
        ConversionResult QuintalALibra(string quintales);

        [OperationContract]
        ConversionResult LibraAQuintal(string libras);
    }
}
