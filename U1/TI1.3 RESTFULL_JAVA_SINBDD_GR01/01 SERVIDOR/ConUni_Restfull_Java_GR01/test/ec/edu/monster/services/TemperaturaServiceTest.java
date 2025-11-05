/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ec.edu.monster.services;

import ec.edu.monster.models.ConversionResultModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para TemperaturaService
 * Prueba todas las conversiones de temperatura y casos de error
 * Incluye validaciones de cero absoluto
 *
 * @author jeffe
 */
public class TemperaturaServiceTest {

    private TemperaturaService instance;

    public TemperaturaServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        instance = new TemperaturaService();
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of convertirCelsiusAFahrenheit method, of class TemperaturaService.
     * Prueba: 0°C = 32°F (punto de congelación del agua)
     */
    @Test
    public void testConvertirCelsiusAFahrenheit() {
        System.out.println("convertirCelsiusAFahrenheit - caso exitoso");
        String celsius = "0.0";
        ConversionResultModel result = instance.convertirCelsiusAFahrenheit(celsius);

        assertTrue(result.isExitoso(), "La conversión debería ser exitosa");
        assertNotNull(result.getResultado(), "El resultado no debería ser null");
        assertNull(result.getError(), "No debería haber error");
        assertEquals(32.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "0°C = 32°F");
        assertEquals("Celsius", result.getResultado().getUnidadOrigen());
        assertEquals("Fahrenheit", result.getResultado().getUnidadDestino());
        assertEquals("Temperatura", result.getResultado().getTipoConversion());
    }

    /**
     * Test: 100°C = 212°F (punto de ebullición del agua)
     */
    @Test
    public void testConvertirCelsiusAFahrenheitEbullicion() {
        System.out.println("convertirCelsiusAFahrenheit - punto de ebullición");
        String celsius = "100.0";
        ConversionResultModel result = instance.convertirCelsiusAFahrenheit(celsius);

        assertTrue(result.isExitoso());
        assertEquals(212.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "100°C = 212°F");
    }

    /**
     * Test con temperatura bajo cero absoluto
     */
    @Test
    public void testConvertirCelsiusAFahrenheitCeroAbsoluto() {
        System.out.println("convertirCelsiusAFahrenheit - bajo cero absoluto");
        String celsius = "-300.0"; // Bajo -273.15°C
        ConversionResultModel result = instance.convertirCelsiusAFahrenheit(celsius);

        assertFalse(result.isExitoso(), "No debería aceptar temperaturas bajo cero absoluto");
        assertNull(result.getResultado(), "No debería haber resultado");
        assertNotNull(result.getError(), "Debería retornar un error");
        assertEquals("TEMP001", result.getError().getCodigoError());
    }

    /**
     * Test of convertirFahrenheitACelsius method, of class TemperaturaService.
     * Prueba: 32°F = 0°C
     */
    @Test
    public void testConvertirFahrenheitACelsius() {
        System.out.println("convertirFahrenheitACelsius - caso exitoso");
        String fahrenheit = "32.0";
        ConversionResultModel result = instance.convertirFahrenheitACelsius(fahrenheit);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(0.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "32°F = 0°C");
        assertEquals("Fahrenheit", result.getResultado().getUnidadOrigen());
        assertEquals("Celsius", result.getResultado().getUnidadDestino());
    }

    /**
     * Test: 212°F = 100°C
     */
    @Test
    public void testConvertirFahrenheitACelsiusEbullicion() {
        System.out.println("convertirFahrenheitACelsius - punto de ebullición");
        String fahrenheit = "212.0";
        ConversionResultModel result = instance.convertirFahrenheitACelsius(fahrenheit);

        assertTrue(result.isExitoso());
        assertEquals(100.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "212°F = 100°C");
    }

    /**
     * Test con temperatura bajo cero absoluto en Fahrenheit
     */
    @Test
    public void testConvertirFahrenheitACelsiusCeroAbsoluto() {
        System.out.println("convertirFahrenheitACelsius - bajo cero absoluto");
        String fahrenheit = "-500.0"; // Bajo -459.67°F
        ConversionResultModel result = instance.convertirFahrenheitACelsius(fahrenheit);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
        assertEquals("TEMP002", result.getError().getCodigoError());
    }

    /**
     * Test of convertirCelsiusAKelvin method, of class TemperaturaService.
     * Prueba: 0°C = 273.15K
     */
    @Test
    public void testConvertirCelsiusAKelvin() {
        System.out.println("convertirCelsiusAKelvin - caso exitoso");
        String celsius = "0.0";
        ConversionResultModel result = instance.convertirCelsiusAKelvin(celsius);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(273.15, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "0°C = 273.15K");
        assertEquals("Celsius", result.getResultado().getUnidadOrigen());
        assertEquals("Kelvin", result.getResultado().getUnidadDestino());
    }

    /**
     * Test: -273.15°C = 0K (cero absoluto)
     */
    @Test
    public void testConvertirCelsiusAKelvinCeroAbsoluto() {
        System.out.println("convertirCelsiusAKelvin - cero absoluto exacto");
        String celsius = "-273.15";
        ConversionResultModel result = instance.convertirCelsiusAKelvin(celsius);

        assertTrue(result.isExitoso(), "Debe aceptar exactamente -273.15°C");
        assertEquals(0.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "-273.15°C = 0K");
    }

    /**
     * Test con temperatura bajo cero absoluto
     */
    @Test
    public void testConvertirCelsiusAKelvinBajoCeroAbsoluto() {
        System.out.println("convertirCelsiusAKelvin - bajo cero absoluto");
        String celsius = "-274.0";
        ConversionResultModel result = instance.convertirCelsiusAKelvin(celsius);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
    }

    /**
     * Test of convertirKelvinACelsius method, of class TemperaturaService.
     * Prueba: 273.15K = 0°C
     */
    @Test
    public void testConvertirKelvinACelsius() {
        System.out.println("convertirKelvinACelsius - caso exitoso");
        String kelvin = "273.15";
        ConversionResultModel result = instance.convertirKelvinACelsius(kelvin);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(0.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "273.15K = 0°C");
        assertEquals("Kelvin", result.getResultado().getUnidadOrigen());
        assertEquals("Celsius", result.getResultado().getUnidadDestino());
    }

    /**
     * Test: 0K = -273.15°C (cero absoluto)
     */
    @Test
    public void testConvertirKelvinACelsiusCeroAbsoluto() {
        System.out.println("convertirKelvinACelsius - cero absoluto");
        String kelvin = "0.0";
        ConversionResultModel result = instance.convertirKelvinACelsius(kelvin);

        assertTrue(result.isExitoso(), "Debe aceptar 0K");
        assertEquals(-273.15, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "0K = -273.15°C");
    }

    /**
     * Test con Kelvin negativo (imposible)
     */
    @Test
    public void testConvertirKelvinACelsiusNegativo() {
        System.out.println("convertirKelvinACelsius - Kelvin negativo");
        String kelvin = "-10.0";
        ConversionResultModel result = instance.convertirKelvinACelsius(kelvin);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
        assertEquals("TEMP003", result.getError().getCodigoError());
    }

    /**
     * Test of convertirFahrenheitAKelvin method, of class TemperaturaService.
     * Prueba: 32°F = 273.15K
     */
    @Test
    public void testConvertirFahrenheitAKelvin() {
        System.out.println("convertirFahrenheitAKelvin - caso exitoso");
        String fahrenheit = "32.0";
        ConversionResultModel result = instance.convertirFahrenheitAKelvin(fahrenheit);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(273.15, result.getResultado().getValorConvertidoExacto(), 0.1,
                     "32°F = 273.15K");
        assertEquals("Fahrenheit", result.getResultado().getUnidadOrigen());
        assertEquals("Kelvin", result.getResultado().getUnidadDestino());
    }

    /**
     * Test con temperatura bajo cero absoluto
     */
    @Test
    public void testConvertirFahrenheitAKelvinCeroAbsoluto() {
        System.out.println("convertirFahrenheitAKelvin - bajo cero absoluto");
        String fahrenheit = "-500.0";
        ConversionResultModel result = instance.convertirFahrenheitAKelvin(fahrenheit);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
    }

    /**
     * Test of convertirKelvinAFahrenheit method, of class TemperaturaService.
     * Prueba: 273.15K = 32°F
     */
    @Test
    public void testConvertirKelvinAFahrenheit() {
        System.out.println("convertirKelvinAFahrenheit - caso exitoso");
        String kelvin = "273.15";
        ConversionResultModel result = instance.convertirKelvinAFahrenheit(kelvin);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(32.0, result.getResultado().getValorConvertidoExacto(), 0.1,
                     "273.15K = 32°F");
        assertEquals("Kelvin", result.getResultado().getUnidadOrigen());
        assertEquals("Fahrenheit", result.getResultado().getUnidadDestino());
    }

    /**
     * Test con Kelvin negativo
     */
    @Test
    public void testConvertirKelvinAFahrenheitNegativo() {
        System.out.println("convertirKelvinAFahrenheit - Kelvin negativo");
        String kelvin = "-5.0";
        ConversionResultModel result = instance.convertirKelvinAFahrenheit(kelvin);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
    }

    /**
     * Test adicional: Temperatura ambiente (25°C)
     */
    @Test
    public void testTemperaturaAmbiente() {
        System.out.println("convertir 25°C a Fahrenheit");
        String celsius = "25.0";
        ConversionResultModel result = instance.convertirCelsiusAFahrenheit(celsius);

        assertTrue(result.isExitoso());
        assertEquals(77.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "25°C = 77°F");
    }

    /**
     * Test adicional: Temperatura corporal (37°C)
     */
    @Test
    public void testTemperaturaCorporal() {
        System.out.println("convertir temperatura corporal");
        String celsius = "37.0";
        ConversionResultModel result = instance.convertirCelsiusAFahrenheit(celsius);

        assertTrue(result.isExitoso());
        assertEquals(98.6, result.getResultado().getValorConvertidoExacto(), 0.1,
                     "37°C = 98.6°F (temperatura corporal)");
    }
    /**
     * Test adicional: Verificar fechas de conversión
     */
    @Test
    public void testVerificarFechaConversion() {
        System.out.println("verificar fecha de conversión");
        String celsius = "20.0";
        ConversionResultModel result = instance.convertirCelsiusAFahrenheit(celsius);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado().getFechaConversion(),
                     "Debe incluir fecha y hora de conversión");
    }
}
