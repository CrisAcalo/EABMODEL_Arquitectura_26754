/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package ec.edu.monster.ws;

import ec.edu.monster.models.ConversionResult;
import ec.edu.monster.services.MasaBusinessService;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * Servicio web SOAP para conversiones de masa
 * @author Kewo
 */
@WebService(serviceName = "MasaService")
public class MasaService {

    // Instancia del servicio de negocio
    private final MasaBusinessService businessService;
    
    // Constructor
    public MasaService() {
        this.businessService = new MasaBusinessService();
    }
    
    // ==================== Conversiones Kilogramo <-> Quintal ====================
    
    @WebMethod(operationName = "KilogramoAQuintal")
    public ConversionResult kilogramoAQuintal(@WebParam(name = "kilogramos") String kilogramos) {
        return businessService.convertirKilogramoAQuintal(kilogramos);
    }
    
    @WebMethod(operationName = "QuintalAKilogramo")
    public ConversionResult quintalAKilogramo(@WebParam(name = "quintales") String quintales) {
        return businessService.convertirQuintalAKilogramo(quintales);
    }
    
    // ==================== Conversiones Kilogramo <-> Libra ====================
    
    @WebMethod(operationName = "KilogramoALibra")
    public ConversionResult kilogramoALibra(@WebParam(name = "kilogramos") String kilogramos) {
        return businessService.convertirKilogramoALibra(kilogramos);
    }
    
    @WebMethod(operationName = "LibraAKilogramo")
    public ConversionResult libraAKilogramo(@WebParam(name = "libras") String libras) {
        return businessService.convertirLibraAKilogramo(libras);
    }
    
    // ==================== Conversiones Quintal <-> Libra ====================
    
    @WebMethod(operationName = "QuintalALibra")
    public ConversionResult quintalALibra(@WebParam(name = "quintales") String quintales) {
        return businessService.convertirQuintalALibra(quintales);
    }
    
    @WebMethod(operationName = "LibraAQuintal")
    public ConversionResult libraAQuintal(@WebParam(name = "libras") String libras) {
        return businessService.convertirLibraAQuintal(libras);
    }
}
