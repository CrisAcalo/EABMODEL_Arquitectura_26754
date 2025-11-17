package ec.edu.monster.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configuración de JAX-RS
 * Todos los recursos estarán disponibles bajo /api/*
 */
@ApplicationPath("/api")
public class RestConfig extends Application {
    // La clase puede estar vacía, solo necesita la anotación @ApplicationPath
}
