package ec.edu.monster;

import ec.edu.monster.client.RestClient;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

/**
 * Cliente REST de consola - Servicios de Conversión
 * @author YourName
 */
public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static Properties config;
    private static String servidorURL;
    private static RestClient restClient;
    
    // Credenciales hardcodeadas
    private static final String USUARIO_VALIDO = "Monster";
    private static final String CONTRASENA_VALIDA = "Monster9";
    private static final int MAX_INTENTOS = 3;
    
    public static void main(String[] args) {
        
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            System.err.println("Error al configurar UTF-8: " + e.getMessage());
        }
        
        // Cargar configuración al iniciar
        cargarConfiguracion();
        
        // Sistema de Login
        if (!realizarLogin()) {
            System.out.println("\nAcceso denegado. Número máximo de intentos alcanzado.");
            System.out.println("El sistema se cerrará por seguridad.\n");
            scanner.close();
            return; // Terminar el programa
        }
        
        System.out.println("\n==============================================");
        System.out.println("  CLIENTE CONSOLA - SERVICIOS DE CONVERSION  ");
        System.out.println("==============================================");
        System.out.println("Servidor: " + servidorURL);
        System.out.println();
        
        boolean continuar = true;
        
        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    menuConversionesLongitud();
                    break;
                case 2:
                    menuConversionesMasa();
                    break;
                case 3:
                    menuConversionesTemperatura();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("\n¡Hasta luego!");
                    break;
                default:
                    System.out.println("\nOpción inválida. Intente nuevamente.\n");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Sistema de autenticación con credenciales hardcodeadas
     * @return true si el login es exitoso, false si se superan los intentos máximos
     */
    private static boolean realizarLogin() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║     SISTEMA DE AUTENTICACIÓN              ║");
        System.out.println("║     SERVICIOS DE CONVERSIÓN               ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        int intentos = 0;
        
        while (intentos < MAX_INTENTOS) {
            System.out.println("Intento " + (intentos + 1) + " de " + MAX_INTENTOS);
            System.out.println("─────────────────────────────────────────────");
            
            String usuario = leerTexto("Usuario: ");
            String contrasena = leerTexto("Contraseña: ");
            
            if (validarCredenciales(usuario, contrasena)) {
                System.out.println("\n¡Autenticación exitosa!");
                System.out.println("Bienvenido, " + usuario + "\n");
                pausa(1000); // Pequeña pausa para mejor UX
                return true;
            } else {
                intentos++;
                int intentosRestantes = MAX_INTENTOS - intentos;
                
                if (intentosRestantes > 0) {
                    System.out.println("\nUsuario o contraseña incorrectos.");
                    System.out.println("Te quedan " + intentosRestantes + " intento(s).\n");
                } else {
                    System.out.println("\nUsuario o contraseña incorrectos.");
                }
            }
        }
        
        return false; // Se superaron los intentos máximos
    }
    
    /**
     * Valida las credenciales ingresadas contra las hardcodeadas
     */
    private static boolean validarCredenciales(String usuario, String contrasena) {
        return USUARIO_VALIDO.equals(usuario) && CONTRASENA_VALIDA.equals(contrasena);
    }
    
    /**
     * Pausa la ejecución por los milisegundos especificados
     */
    private static void pausa(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Carga la configuración desde config.properties
     */
    private static void cargarConfiguracion() {
        config = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
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
            servidorURL = "http://" + ip + ":" + puerto + "/" + contexto + "/api";
            
            // Inicializar cliente REST
            restClient = new RestClient(servidorURL);
            
        } catch (IOException ex) {
            System.out.println("Error al cargar configuración: " + ex.getMessage());
            servidorURL = "http://192.168.0.10:8080/ConUni_Restfull_Java_GR01/api";
            restClient = new RestClient(servidorURL);
        }
    }
    
    private static void mostrarMenuPrincipal() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│           MENÚ PRINCIPAL                │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│ 1. Conversiones de Longitud             │");
        System.out.println("│ 2. Conversiones de Masa                 │");
        System.out.println("│ 3. Conversiones de Temperatura          │");
        System.out.println("│ 0. Salir                                │");
        System.out.println("└─────────────────────────────────────────┘");
    }
    
    private static void menuConversionesLongitud() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│    CONVERSIONES DE LONGITUD             │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│ 1. Metro → Milla                        │");
        System.out.println("│ 2. Milla → Metro                        │");
        System.out.println("│ 3. Metro → Pulgada                      │");
        System.out.println("│ 4. Pulgada → Metro                      │");
        System.out.println("│ 5. Milla → Pulgada                      │");
        System.out.println("│ 6. Pulgada → Milla                      │");
        System.out.println("│ 0. Volver                               │");
        System.out.println("└─────────────────────────────────────────┘");
        
        int opcion = leerEntero("Seleccione una opción: ");
        if (opcion == 0) return;
        
        String valor = leerTexto("Ingrese el valor a convertir: ");
        
        String unidadOrigen = "";
        String unidadDestino = "";
        
        switch (opcion) {
            case 1: unidadOrigen = "Metro"; unidadDestino = "Milla"; break;
            case 2: unidadOrigen = "Milla"; unidadDestino = "Metro"; break;
            case 3: unidadOrigen = "Metro"; unidadDestino = "Pulgada"; break;
            case 4: unidadOrigen = "Pulgada"; unidadDestino = "Metro"; break;
            case 5: unidadOrigen = "Milla"; unidadDestino = "Pulgada"; break;
            case 6: unidadOrigen = "Pulgada"; unidadDestino = "Milla"; break;
            default:
                System.out.println("\n❌ Opción inválida.\n");
                return;
        }
        
        try {
            RestClient.ConversionResult resultado = restClient.convertirLongitud(valor, unidadOrigen, unidadDestino);
            mostrarResultado(resultado);
        } catch (Exception e) {
            System.out.println("\nError al conectar con el servicio: " + e.getMessage());
            System.out.println("   Verifica que el servidor esté corriendo en: " + servidorURL + "\n");
        }
    }
    
    private static void menuConversionesMasa() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│    CONVERSIONES DE MASA                 │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│ 1. Kilogramo → Libra                    │");
        System.out.println("│ 2. Libra → Kilogramo                    │");
        System.out.println("│ 3. Kilogramo → Onza                     │");
        System.out.println("│ 4. Onza → Kilogramo                     │");
        System.out.println("│ 5. Kilogramo → Tonelada                 │");
        System.out.println("│ 6. Tonelada → Kilogramo                 │");
        System.out.println("│ 7. Libra → Onza                         │");
        System.out.println("│ 8. Onza → Libra                         │");
        System.out.println("│ 9. Libra → Tonelada                     │");
        System.out.println("│ 10. Tonelada → Libra                    │");
        System.out.println("│ 0. Volver                               │");
        System.out.println("└─────────────────────────────────────────┘");
        
        int opcion = leerEntero("Seleccione una opción: ");
        if (opcion == 0) return;
        
        String valor = leerTexto("Ingrese el valor a convertir: ");
        
        String unidadOrigen = "";
        String unidadDestino = "";
        
        switch (opcion) {
            case 1: unidadOrigen = "Kilogramo"; unidadDestino = "Libra"; break;
            case 2: unidadOrigen = "Libra"; unidadDestino = "Kilogramo"; break;
            case 3: unidadOrigen = "Kilogramo"; unidadDestino = "Onza"; break;
            case 4: unidadOrigen = "Onza"; unidadDestino = "Kilogramo"; break;
            case 5: unidadOrigen = "Kilogramo"; unidadDestino = "Tonelada"; break;
            case 6: unidadOrigen = "Tonelada"; unidadDestino = "Kilogramo"; break;
            case 7: unidadOrigen = "Libra"; unidadDestino = "Onza"; break;
            case 8: unidadOrigen = "Onza"; unidadDestino = "Libra"; break;
            case 9: unidadOrigen = "Libra"; unidadDestino = "Tonelada"; break;
            case 10: unidadOrigen = "Tonelada"; unidadDestino = "Libra"; break;
            default:
                System.out.println("\n❌ Opción inválida.\n");
                return;
        }
        
        try {
            RestClient.ConversionResult resultado = restClient.convertirMasa(valor, unidadOrigen, unidadDestino);
            mostrarResultado(resultado);
        } catch (Exception e) {
            System.out.println("\nError al conectar con el servicio: " + e.getMessage());
            System.out.println("   Verifica que el servidor esté corriendo en: " + servidorURL + "\n");
        }
    }
    
    private static void menuConversionesTemperatura() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│    CONVERSIONES DE TEMPERATURA          │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│ 1. Celsius → Fahrenheit                 │");
        System.out.println("│ 2. Celsius → Kelvin                     │");
        System.out.println("│ 3. Fahrenheit → Celsius                 │");
        System.out.println("│ 4. Fahrenheit → Kelvin                  │");
        System.out.println("│ 5. Kelvin → Celsius                     │");
        System.out.println("│ 6. Kelvin → Fahrenheit                  │");
        System.out.println("│ 0. Volver                               │");
        System.out.println("└─────────────────────────────────────────┘");
        
        int opcion = leerEntero("Seleccione una opción: ");
        if (opcion == 0) return;
        
        String valor = leerTexto("Ingrese el valor a convertir: ");
        
        String unidadOrigen = "";
        String unidadDestino = "";
        
        switch (opcion) {
            case 1: unidadOrigen = "Celsius"; unidadDestino = "Fahrenheit"; break;
            case 2: unidadOrigen = "Celsius"; unidadDestino = "Kelvin"; break;
            case 3: unidadOrigen = "Fahrenheit"; unidadDestino = "Celsius"; break;
            case 4: unidadOrigen = "Fahrenheit"; unidadDestino = "Kelvin"; break;
            case 5: unidadOrigen = "Kelvin"; unidadDestino = "Celsius"; break;
            case 6: unidadOrigen = "Kelvin"; unidadDestino = "Fahrenheit"; break;
            default:
                System.out.println("\nOpción inválida.\n");
                return;
        }
        
        try {
            RestClient.ConversionResult resultado = restClient.convertirTemperatura(valor, unidadOrigen, unidadDestino);
            mostrarResultado(resultado);
        } catch (Exception e) {
            System.out.println("\nError al conectar con el servicio: " + e.getMessage());
            System.out.println("   Verifica que el servidor esté corriendo en: " + servidorURL + "\n");
        }
    }
    
    private static void mostrarResultado(RestClient.ConversionResult resultado) {
        System.out.println("\n" + "=".repeat(50));
        
        if (resultado.isExitoso()) {
            RestClient.ResultadoConversion conv = resultado.getResultado();
            System.out.println("CONVERSIÓN EXITOSA");
            System.out.println("=".repeat(50));
            System.out.println("Valor Original:    " + conv.getValorOriginal() + " " + conv.getUnidadOrigen());
            System.out.println("Valor Convertido:  " + conv.getValorConvertidoRedondeado() + " " + conv.getUnidadDestino());
            System.out.println("Valor Exacto:      " + conv.getValorConvertidoExacto());
            System.out.println("Factor:            " + conv.getFactorConversion());
        } else {
            System.out.println("ERROR EN LA CONVERSIÓN");
            System.out.println("=".repeat(50));
            System.out.println("Mensaje:  " + resultado.getMensajeError());
        }
        System.out.println("=".repeat(50) + "\n");
    }
    
    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Debe ingresar un número entero. Intente nuevamente: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
    
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}