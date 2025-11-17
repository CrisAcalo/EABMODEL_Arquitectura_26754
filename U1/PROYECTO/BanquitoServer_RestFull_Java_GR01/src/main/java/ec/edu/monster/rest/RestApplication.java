package ec.edu.monster.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configuración de la aplicación REST
 * Define el path base para todos los servicios REST: /api
 * 
 * @author Javi
 */
@ApplicationPath("/api")
public class RestApplication extends Application {
    // No es necesario sobreescribir métodos
    // Jakarta EE automáticamente descubre y registra los recursos REST
}
