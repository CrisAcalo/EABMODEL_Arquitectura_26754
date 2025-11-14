package ec.edu.monster.controllers;

import java.util.Set;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configuración de la aplicación JAX-RS
 * Define la ruta base para todos los recursos REST
 *
 * @author EurekaBank
 */
@ApplicationPath("")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Registra todos los recursos REST de la aplicación
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ec.edu.monster.controllers.AutenticacionResource.class);
        resources.add(ec.edu.monster.controllers.ReporteResource.class);
        resources.add(ec.edu.monster.controllers.TransaccionResource.class);
    }
}
