package ec.edu.monster.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Gestor de configuración para REST API
 * @author YourName
 */
public class ConfigManager {
    private static Properties config;
    private static String baseURL;
    
    static {
        cargarConfiguracion();
    }
    
    /**
     * Carga la configuración desde config.properties
     */
    private static void cargarConfiguracion() {
        config = new Properties();
        try (InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream("/config.properties")) {
            
            if (input == null) {
                System.out.println("No se encontró config.properties, usando valores por defecto");
                config.setProperty("servidor.ip", "192.168.0.10");
                config.setProperty("servidor.puerto", "8081");
                config.setProperty("servidor.contexto", "ConUni_Restfull_Java_GR01");
            } else {
                config.load(input);
                System.out.println("Configuración cargada desde config.properties");
            }
            
            // Construir URL base del servidor REST
            String ip = config.getProperty("servidor.ip");
            String puerto = config.getProperty("servidor.puerto");
            String contexto = config.getProperty("servidor.contexto");
            baseURL = "http://" + ip + ":" + puerto + "/" + contexto + "/api";
            
            System.out.println("URL del servidor REST: " + baseURL);
            
        } catch (IOException ex) {
            System.out.println("Error al cargar configuración: " + ex.getMessage());
            baseURL = "http://192.168.0.10:8081/ConUni_Restfull_Java_GR01/api";
        }
    }
    
    /**
     * Obtiene la URL base del servidor REST
     */
    public static String getBaseURL() {
        return baseURL;
    }
    
    /**
     * Obtiene una propiedad específica
     */
    public static String getProperty(String key) {
        return config.getProperty(key);
    }
    
    /**
     * Obtiene la URL completa de un endpoint REST
     */
    public static String getEndpointURL(String endpoint) {
        return baseURL + "/" + endpoint;
    }
}