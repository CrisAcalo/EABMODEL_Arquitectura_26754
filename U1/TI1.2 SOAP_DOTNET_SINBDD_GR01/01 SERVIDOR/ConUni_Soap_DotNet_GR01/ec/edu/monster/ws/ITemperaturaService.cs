using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.models;

namespace ConUni_Soap_DotNet_GR01.ec.edu.monster.ws
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "ITemperaturaService" in both code and config file together.
    [ServiceContract]
    public interface ITemperaturaService
    {
        // Conversiones Celsius <-> Fahrenheit
        [OperationContract]
        ConversionResult CelsiusAFahrenheit(string celsius);

        [OperationContract]
        ConversionResult FahrenheitACelsius(string fahrenheit);

        // Conversiones Fahrenheit <-> Kelvin
        [OperationContract]
        ConversionResult FahrenheitAKelvin(string fahrenheit);

        [OperationContract]
        ConversionResult KelvinAFahrenheit(string kelvin);

        // Conversiones Kelvin <-> Celsius
        [OperationContract]
        ConversionResult KelvinACelsius(string kelvin);

        [OperationContract]
        ConversionResult CelsiusAKelvin(string celsius);
    }
}
