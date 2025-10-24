/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package ec.edu.monster.ws;

import ec.edu.monster.models.ConversionResult;
import ec.edu.monster.services.LongitudBusinessService;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Kewo
 */
@WebService(serviceName = "LongitudService")
public class LongitudService {

    // Instancia del servicio de negocio
    private final LongitudBusinessService businessService;
    
    // Constructor
    public LongitudService() {
        this.businessService = new LongitudBusinessService();
    }
    
    // ==================== Conversiones Milla <-> Metro ====================
    
    @WebMethod(operationName = "MillaAMetro")
    public ConversionResult millaAMetro(@WebParam(name = "millas") String millas) {
        return businessService.convertirMillaAMetro(millas);
    }
    
    @WebMethod(operationName = "MetroAMilla")
    public ConversionResult metroAMilla(@WebParam(name = "metros") String metros) {
        return businessService.convertirMetroAMilla(metros);
    }
    
    // ==================== Conversiones Milla <-> Pulgada ====================
    
    @WebMethod(operationName = "MillaAPulgada")
    public ConversionResult millaAPulgada(@WebParam(name = "millas") String millas) {
        return businessService.convertirMillaAPulgada(millas);
    }
    
    @WebMethod(operationName = "PulgadaAMilla")
    public ConversionResult pulgadaAMilla(@WebParam(name = "pulgadas") String pulgadas) {
        return businessService.convertirPulgadaAMilla(pulgadas);
    }
    
    // ==================== Conversiones Metro <-> Pulgada ====================
    
    @WebMethod(operationName = "MetroAPulgada")
    public ConversionResult metroAPulgada(@WebParam(name = "metros") String metros) {
        return businessService.convertirMetroAPulgada(metros);
    }
    
    @WebMethod(operationName = "PulgadaAMetro")
    public ConversionResult pulgadaAMetro(@WebParam(name = "pulgadas") String pulgadas) {
        return businessService.convertirPulgadaAMetro(pulgadas);
    }
}
