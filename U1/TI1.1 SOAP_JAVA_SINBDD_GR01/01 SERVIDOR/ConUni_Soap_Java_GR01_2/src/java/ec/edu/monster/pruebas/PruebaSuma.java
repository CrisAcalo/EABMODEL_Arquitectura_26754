/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.pruebas;

import ec.edu.monster.modelo.SumaService;

/**
 *
 * @author crist
 */
public class PruebaSuma {
    public static void main(String[] arg){
        int n1 = 4;
        int n2 = 2;
        
        SumaService service = new SumaService();
        
        service.sumar(n1, n2);
        
    }
}
