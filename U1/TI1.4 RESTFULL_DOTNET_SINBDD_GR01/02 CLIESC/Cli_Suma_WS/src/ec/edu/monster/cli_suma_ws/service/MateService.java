/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.cli_suma_ws.service;

/**
 *
 * @author jeffe
 */
public class MateService {

    public int sumar(int num1, int num2) {
        ec.edu.monster.ws.SumaWS_Service service = new ec.edu.monster.ws.SumaWS_Service();
        ec.edu.monster.ws.SumaWS port = service.getSumaWSPort();
        return port.sumar(num1, num2);
    }
    
    
    
}
