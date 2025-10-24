/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package ec.edu.monster.ws;

import ec.edu.monster.models.ConversionResult;
import ec.edu.monster.services.TemperaturaBusinessService;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * Servicio web SOAP para conversiones de temperatura
 * @author Kewo
 */
@WebService(serviceName = "TemperaturaService")
public class TemperaturaService {

    // Instancia del servicio de negocio
    private final TemperaturaBusinessService businessService;
    
    // Constructor
    public TemperaturaService() {
        this.businessService = new TemperaturaBusinessService();
    }
    
    // ==================== Conversiones Celsius ====================
    
    @WebMethod(operationName = "CelsiusAFahrenheit")
    public ConversionResult celsiusAFahrenheit(@WebParam(name = "celsius") String celsius) {
        return businessService.convertirCelsiusAFahrenheit(celsius);
    }
    
    @WebMethod(operationName = "CelsiusAKelvin")
    public ConversionResult celsiusAKelvin(@WebParam(name = "celsius") String celsius) {
        return businessService.convertirCelsiusAKelvin(celsius);
    }
    
    // ==================== Conversiones Fahrenheit ====================
    
    @WebMethod(operationName = "FahrenheitACelsius")
    public ConversionResult fahrenheitACelsius(@WebParam(name = "fahrenheit") String fahrenheit) {
        return businessService.convertirFahrenheitACelsius(fahrenheit);
    }
    
    @WebMethod(operationName = "FahrenheitAKelvin")
    public ConversionResult fahrenheitAKelvin(@WebParam(name = "fahrenheit") String fahrenheit) {
        return businessService.convertirFahrenheitAKelvin(fahrenheit);
    }
    
    // ==================== Conversiones Kelvin ====================
    
    @WebMethod(operationName = "KelvinACelsius")
    public ConversionResult kelvinACelsius(@WebParam(name = "kelvin") String kelvin) {
        return businessService.convertirKelvinACelsius(kelvin);
    }
    
    @WebMethod(operationName = "KelvinAFahrenheit")
    public ConversionResult kelvinAFahrenheit(@WebParam(name = "kelvin") String kelvin) {
        return businessService.convertirKelvinAFahrenheit(kelvin);
    }
}
