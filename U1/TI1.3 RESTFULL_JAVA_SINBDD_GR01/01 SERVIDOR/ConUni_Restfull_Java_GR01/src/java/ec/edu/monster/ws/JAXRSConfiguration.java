/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.ws;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 *
 * @author jeffe
 */
@ApplicationPath("api")
public class JAXRSConfiguration extends Application {
    // No se necesita ningún código adicional aquí.
    // El servidor escaneará y registrará automáticamente las clases con @Path.
}
