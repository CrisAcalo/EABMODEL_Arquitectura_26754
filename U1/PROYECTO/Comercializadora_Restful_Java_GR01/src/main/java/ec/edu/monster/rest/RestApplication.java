package ec.edu.monster.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configuración de la aplicación REST
 * Define el path base para todos los endpoints REST
 */
@ApplicationPath("/api")
public class RestApplication extends Application {
    // JAX-RS automáticamente descubrirá todos los recursos anotados con @Path
}
