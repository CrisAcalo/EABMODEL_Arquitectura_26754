/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;
import javax.xml.namespace.QName;

public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static Properties config;
    private static String servidorURL;
    
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
                config.setProperty("servidor.puerto", "8080");
                config.setProperty("servidor.contexto", "ConUni_Soap_Java_GR01");
            } else {
                config.load(input);
                System.out.println("Configuración cargada desde config.properties");
            }
            
            // Construir URL base del servidor
            String ip = config.getProperty("servidor.ip");
            String puerto = config.getProperty("servidor.puerto");
            String contexto = config.getProperty("servidor.contexto");
            servidorURL = "http://" + ip + ":" + puerto + "/" + contexto;
            
        } catch (IOException ex) {
            System.out.println("Error al cargar configuración: " + ex.getMessage());
            servidorURL = "http://localhost:8080/ConUni_Soap_Java_GR01";
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
        System.out.println("│     CONVERSIONES DE LONGITUD            │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│ 1. Milla → Metro                        │");
        System.out.println("│ 2. Metro → Milla                        │");
        System.out.println("│ 3. Milla → Pulgada                      │");
        System.out.println("│ 4. Pulgada → Milla                      │");
        System.out.println("│ 5. Metro → Pulgada                      │");
        System.out.println("│ 6. Pulgada → Metro                      │");
        System.out.println("│ 0. Volver                               │");
        System.out.println("└─────────────────────────────────────────┘");
        
        int opcion = leerEntero("Seleccione una opción: ");
        if (opcion == 0) return;
        
        String valor = leerTexto("Ingrese el valor a convertir: ");
        
        try {
            // Crear URL dinámica del WSDL
            String serviceName = config.getProperty("servicio.longitud", "LongitudService");
            URL wsdlURL = new URL(servidorURL + "/" + serviceName + "?wsdl");
            QName qname = new QName("http://ws.monster.edu.ec/", serviceName);
            
            ec.edu.monster.longitud.LongitudService_Service service = 
                new ec.edu.monster.longitud.LongitudService_Service(wsdlURL, qname);
            ec.edu.monster.longitud.LongitudService port = service.getLongitudServicePort();
            
            ec.edu.monster.longitud.ConversionResult resultado = null;
            
            switch (opcion) {
                case 1: resultado = port.millaAMetro(valor); break;
                case 2: resultado = port.metroAMilla(valor); break;
                case 3: resultado = port.millaAPulgada(valor); break;
                case 4: resultado = port.pulgadaAMilla(valor); break;
                case 5: resultado = port.metroAPulgada(valor); break;
                case 6: resultado = port.pulgadaAMetro(valor); break;
                default:
                    System.out.println("\nOpción inválida.\n");
                    return;
            }
            
            mostrarResultadoLongitud(resultado);
            
        } catch (Exception e) {
            System.out.println("\nError al conectar con el servicio: " + e.getMessage());
            System.out.println("   Verifica que el servidor esté corriendo en: " + servidorURL + "\n");
        }
    }
    
    private static void menuConversionesMasa() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│       CONVERSIONES DE MASA              │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│ 1. Kilogramo → Quintal                  │");
        System.out.println("│ 2. Quintal → Kilogramo                  │");
        System.out.println("│ 3. Kilogramo → Libra                    │");
        System.out.println("│ 4. Libra → Kilogramo                    │");
        System.out.println("│ 5. Quintal → Libra                      │");
        System.out.println("│ 6. Libra → Quintal                      │");
        System.out.println("│ 0. Volver                               │");
        System.out.println("└─────────────────────────────────────────┘");
        
        int opcion = leerEntero("Seleccione una opción: ");
        if (opcion == 0) return;
        
        String valor = leerTexto("Ingrese el valor a convertir: ");
        
        try {
            String serviceName = config.getProperty("servicio.masa", "MasaService");
            URL wsdlURL = new URL(servidorURL + "/" + serviceName + "?wsdl");
            QName qname = new QName("http://ws.monster.edu.ec/", serviceName);
            
            ec.edu.monster.masa.MasaService_Service service = 
                new ec.edu.monster.masa.MasaService_Service(wsdlURL, qname);
            ec.edu.monster.masa.MasaService port = service.getMasaServicePort();
            
            ec.edu.monster.masa.ConversionResult resultado = null;
            
            switch (opcion) {
                case 1: resultado = port.kilogramoAQuintal(valor); break;
                case 2: resultado = port.quintalAKilogramo(valor); break;
                case 3: resultado = port.kilogramoALibra(valor); break;
                case 4: resultado = port.libraAKilogramo(valor); break;
                case 5: resultado = port.quintalALibra(valor); break;
                case 6: resultado = port.libraAQuintal(valor); break;
                default:
                    System.out.println("\n❌ Opción inválida.\n");
                    return;
            }
            
            mostrarResultadoMasa(resultado);
            
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
        
        try {
            String serviceName = config.getProperty("servicio.temperatura", "TemperaturaService");
            URL wsdlURL = new URL(servidorURL + "/" + serviceName + "?wsdl");
            QName qname = new QName("http://ws.monster.edu.ec/", serviceName);
            
            ec.edu.monster.temperatura.TemperaturaService_Service service = 
                new ec.edu.monster.temperatura.TemperaturaService_Service(wsdlURL, qname);
            ec.edu.monster.temperatura.TemperaturaService port = service.getTemperaturaServicePort();
            
            ec.edu.monster.temperatura.ConversionResult resultado = null;
            
            switch (opcion) {
                case 1: resultado = port.celsiusAFahrenheit(valor); break;
                case 2: resultado = port.celsiusAKelvin(valor); break;
                case 3: resultado = port.fahrenheitACelsius(valor); break;
                case 4: resultado = port.fahrenheitAKelvin(valor); break;
                case 5: resultado = port.kelvinACelsius(valor); break;
                case 6: resultado = port.kelvinAFahrenheit(valor); break;
                default:
                    System.out.println("\nOpción inválida.\n");
                    return;
            }
            
            mostrarResultadoTemperatura(resultado);
            
        } catch (Exception e) {
            System.out.println("\nError al conectar con el servicio: " + e.getMessage());
            System.out.println("   Verifica que el servidor esté corriendo en: " + servidorURL + "\n");
        }
    }
    
    private static void mostrarResultadoLongitud(ec.edu.monster.longitud.ConversionResult resultado) {
        System.out.println("\n" + "=".repeat(50));
        
        if (resultado.isExitoso()) {
            ec.edu.monster.longitud.UnidadConversion conv = resultado.getResultado();
            System.out.println("CONVERSIÓN EXITOSA");
            System.out.println("=".repeat(50));
            System.out.println("Valor Original:    " + conv.getValorOriginal() + " " + conv.getUnidadOrigen());
            System.out.println("Valor Convertido:  " + conv.getValorConvertidoRedondeado() + " " + conv.getUnidadDestino());
            System.out.println("Valor Exacto:      " + conv.getValorConvertidoExacto());
            System.out.println("Factor:            " + conv.getFactorConversion());
        } else {
            ec.edu.monster.longitud.ConversionError err = resultado.getError();
            System.out.println("ERROR EN LA CONVERSIÓN");
            System.out.println("=".repeat(50));
            System.out.println("Código:   " + err.getCodigoError());
            System.out.println("Tipo:     " + err.getTipoError());
            System.out.println("Mensaje:  " + err.getMensaje());
            if (err.getDetalles() != null) {
                System.out.println("Detalles: " + err.getDetalles());
            }
        }
        System.out.println("=".repeat(50) + "\n");
    }
    
    private static void mostrarResultadoMasa(ec.edu.monster.masa.ConversionResult resultado) {
        System.out.println("\n" + "=".repeat(50));
        
        if (resultado.isExitoso()) {
            ec.edu.monster.masa.UnidadConversion conv = resultado.getResultado();
            System.out.println("CONVERSIÓN EXITOSA");
            System.out.println("=".repeat(50));
            System.out.println("Valor Original:    " + conv.getValorOriginal() + " " + conv.getUnidadOrigen());
            System.out.println("Valor Convertido:  " + conv.getValorConvertidoRedondeado() + " " + conv.getUnidadDestino());
            System.out.println("Valor Exacto:      " + conv.getValorConvertidoExacto());
            System.out.println("Factor:            " + conv.getFactorConversion());
        } else {
            ec.edu.monster.masa.ConversionError err = resultado.getError();
            System.out.println("ERROR EN LA CONVERSIÓN");
            System.out.println("=".repeat(50));
            System.out.println("Código:   " + err.getCodigoError());
            System.out.println("Tipo:     " + err.getTipoError());
            System.out.println("Mensaje:  " + err.getMensaje());
            if (err.getDetalles() != null) {
                System.out.println("Detalles: " + err.getDetalles());
            }
        }
        System.out.println("=".repeat(50) + "\n");
    }
    
    private static void mostrarResultadoTemperatura(ec.edu.monster.temperatura.ConversionResult resultado) {
        System.out.println("\n" + "=".repeat(50));
        
        if (resultado.isExitoso()) {
            ec.edu.monster.temperatura.UnidadConversion conv = resultado.getResultado();
            System.out.println("CONVERSIÓN EXITOSA");
            System.out.println("=".repeat(50));
            System.out.println("Valor Original:    " + conv.getValorOriginal() + " " + conv.getUnidadOrigen());
            System.out.println("Valor Convertido:  " + conv.getValorConvertidoRedondeado() + " " + conv.getUnidadDestino());
            System.out.println("Valor Exacto:      " + conv.getValorConvertidoExacto());
            System.out.println("Factor:            " + conv.getFactorConversion());
        } else {
            ec.edu.monster.temperatura.ConversionError err = resultado.getError();
            System.out.println("ERROR EN LA CONVERSIÓN");
            System.out.println("=".repeat(50));
            System.out.println("Código:   " + err.getCodigoError());
            System.out.println("Tipo:     " + err.getTipoError());
            System.out.println("Mensaje:  " + err.getMensaje());
            if (err.getDetalles() != null) {
                System.out.println("Detalles: " + err.getDetalles());
            }
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