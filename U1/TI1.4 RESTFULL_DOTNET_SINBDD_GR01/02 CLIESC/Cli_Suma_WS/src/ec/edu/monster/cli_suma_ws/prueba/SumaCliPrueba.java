/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.cli_suma_ws.prueba;

import ec.edu.monster.cli_suma_ws.service.MateService;

/**
 *
 * @author jeffe
 */
public class SumaCliPrueba {
   
    public static void main (String [] args) {
        //datos
        int n1 = 80;
        int n2 = 30;
    
        //proceso
    
        MateService servicio = new MateService();
        int suma = servicio.sumar(n1, n2);
        //reporte
    
        System.out.println("n1: " + n1);
        System.out.println("n2: " + n2);
        System.out.println("suma: " + suma);
    }
    
}
