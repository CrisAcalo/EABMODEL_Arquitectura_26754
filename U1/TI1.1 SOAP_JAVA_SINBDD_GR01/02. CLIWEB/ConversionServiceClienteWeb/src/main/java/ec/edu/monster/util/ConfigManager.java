/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * Gestor de configuracion para leer el archivo config.properties
 * @author Kewo
 */
public class ConfigManager {
    private static Properties config;
    private static String servidorURL;
    
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
                config.setProperty("servidor.ip", "localhost");
                config.setProperty("servidor.puerto", "8080");
                config.setProperty("servidor.contexto", "ConUni_Soap_Java_GR01");
                config.setProperty("servicio.longitud", "LongitudService");
                config.setProperty("servicio.masa", "MasaService");
                config.setProperty("servicio.temperatura", "TemperaturaService");
            } else {
                config.load(input);
                System.out.println("Configuración cargada desde config.properties");
            }
            
            // Construir URL base del servidor
            String ip = config.getProperty("servidor.ip");
            String puerto = config.getProperty("servidor.puerto");
            String contexto = config.getProperty("servidor.contexto");
            servidorURL = "http://" + ip + ":" + puerto + "/" + contexto;
            
            System.out.println("URL del servidor: " + servidorURL);
            
        } catch (IOException ex) {
            System.out.println("Error al cargar configuración: " + ex.getMessage());
            servidorURL = "http://localhost:8080/ConUni_Soap_Java_GR01";
        }
    }
    
    /**
     * Obtiene la URL base del servidor
     */
    public static String getServidorURL() {
        return servidorURL;
    }
    
    /**
     * Obtiene una propiedad específica
     */
    public static String getProperty(String key) {
        return config.getProperty(key);
    }
    
    /**
     * Obtiene la URL completa de un servicio
     */
    public static String getServiceURL(String serviceName) {
        return servidorURL + "/" + serviceName;
    }
    
    /**
     * Obtiene la URL del WSDL de un servicio
     */
    public static String getWSDLURL(String serviceName) {
        return servidorURL + "/" + serviceName + "?wsdl";
    }
}
