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
 * Tests para MasaService
 * Prueba todas las conversiones de masa y casos de error
 *
 * @author jeffe
 */
public class MasaServiceTest {

    private MasaService instance;

    public MasaServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        instance = new MasaService();
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of convertirKilogramoAQuintal method, of class MasaService.
     * Prueba: 100 kg = 1 quintal
     */
    @Test
    public void testConvertirKilogramoAQuintal() {
        System.out.println("convertirKilogramoAQuintal - caso exitoso");
        double kilogramos = 100.0;
        ConversionResultModel result = instance.convertirKilogramoAQuintal(kilogramos);

        assertTrue(result.isExitoso(), "La conversión debería ser exitosa");
        assertNotNull(result.getResultado(), "El resultado no debería ser null");
        assertNull(result.getError(), "No debería haber error");
        assertEquals(1.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "100 kg = 1 quintal");
        assertEquals("Kilogramo", result.getResultado().getUnidadOrigen());
        assertEquals("Quintal", result.getResultado().getUnidadDestino());
        assertEquals("Masa", result.getResultado().getTipoConversion());
    }

    /**
     * Test con valor negativo
     */
    @Test
    public void testConvertirKilogramoAQuintalValorNegativo() {
        System.out.println("convertirKilogramoAQuintal - valor negativo");
        double kilogramos = -50.0;
        ConversionResultModel result = instance.convertirKilogramoAQuintal(kilogramos);

        assertFalse(result.isExitoso(), "No debería aceptar valores negativos");
        assertNull(result.getResultado(), "No debería haber resultado");
        assertNotNull(result.getError(), "Debería retornar un error");
        assertEquals("VAL002", result.getError().getCodigoError());
    }

    /**
     * Test of convertirQuintalAKilogramo method, of class MasaService.
     * Prueba: 1 quintal = 100 kg
     */
    @Test
    public void testConvertirQuintalAKilogramo() {
        System.out.println("convertirQuintalAKilogramo - caso exitoso");
        double quintales = 1.0;
        ConversionResultModel result = instance.convertirQuintalAKilogramo(quintales);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(100.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "1 quintal = 100 kg");
        assertEquals("Quintal", result.getResultado().getUnidadOrigen());
        assertEquals("Kilogramo", result.getResultado().getUnidadDestino());
    }

    /**
     * Test con valor negativo
     */
    @Test
    public void testConvertirQuintalAKilogramoValorNegativo() {
        System.out.println("convertirQuintalAKilogramo - valor negativo");
        double quintales = -5.0;
        ConversionResultModel result = instance.convertirQuintalAKilogramo(quintales);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
    }

    /**
     * Test of convertirKilogramoALibra method, of class MasaService.
     * Prueba: 1 kg = 2.20462 libras
     */
    @Test
    public void testConvertirKilogramoALibra() {
        System.out.println("convertirKilogramoALibra - caso exitoso");
        double kilogramos = 1.0;
        ConversionResultModel result = instance.convertirKilogramoALibra(kilogramos);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(2.20462, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "1 kg = 2.20462 libras");
        assertEquals("Kilogramo", result.getResultado().getUnidadOrigen());
        assertEquals("Libra", result.getResultado().getUnidadDestino());
    }

    /**
     * Test con valor negativo
     */
    @Test
    public void testConvertirKilogramoALibraValorNegativo() {
        System.out.println("convertirKilogramoALibra - valor negativo");
        double kilogramos = -10.0;
        ConversionResultModel result = instance.convertirKilogramoALibra(kilogramos);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
    }

    /**
     * Test of convertirLibraAKilogramo method, of class MasaService.
     * Prueba: 2.20462 libras = 1 kg
     */
    @Test
    public void testConvertirLibraAKilogramo() {
        System.out.println("convertirLibraAKilogramo - caso exitoso");
        double libras = 2.20462;
        ConversionResultModel result = instance.convertirLibraAKilogramo(libras);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(1.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "2.20462 libras = 1 kg");
        assertEquals("Libra", result.getResultado().getUnidadOrigen());
        assertEquals("Kilogramo", result.getResultado().getUnidadDestino());
    }

    /**
     * Test con valor negativo
     */
    @Test
    public void testConvertirLibraAKilogramoValorNegativo() {
        System.out.println("convertirLibraAKilogramo - valor negativo");
        double libras = -100.0;
        ConversionResultModel result = instance.convertirLibraAKilogramo(libras);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
    }

    /**
     * Test of convertirQuintalALibra method, of class MasaService.
     * Prueba: 1 quintal = 220.462 libras
     */
    @Test
    public void testConvertirQuintalALibra() {
        System.out.println("convertirQuintalALibra - caso exitoso");
        double quintales = 1.0;
        ConversionResultModel result = instance.convertirQuintalALibra(quintales);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(220.462, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "1 quintal = 220.462 libras");
        assertEquals("Quintal", result.getResultado().getUnidadOrigen());
        assertEquals("Libra", result.getResultado().getUnidadDestino());
    }

    /**
     * Test con valor negativo
     */
    @Test
    public void testConvertirQuintalALibraValorNegativo() {
        System.out.println("convertirQuintalALibra - valor negativo");
        double quintales = -2.0;
        ConversionResultModel result = instance.convertirQuintalALibra(quintales);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
    }

    /**
     * Test of convertirLibraAQuintal method, of class MasaService.
     * Prueba: 220.462 libras = 1 quintal
     */
    @Test
    public void testConvertirLibraAQuintal() {
        System.out.println("convertirLibraAQuintal - caso exitoso");
        double libras = 220.462;
        ConversionResultModel result = instance.convertirLibraAQuintal(libras);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(1.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "220.462 libras = 1 quintal");
        assertEquals("Libra", result.getResultado().getUnidadOrigen());
        assertEquals("Quintal", result.getResultado().getUnidadDestino());
    }

    /**
     * Test con valor negativo
     */
    @Test
    public void testConvertirLibraAQuintalValorNegativo() {
        System.out.println("convertirLibraAQuintal - valor negativo");
        double libras = -500.0;
        ConversionResultModel result = instance.convertirLibraAQuintal(libras);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
    }

    /**
     * Test adicional: Conversión de múltiples kilogramos
     */
    @Test
    public void testConvertirMultiplesKilogramos() {
        System.out.println("convertir 500 kg a quintales");
        double kilogramos = 500.0;
        ConversionResultModel result = instance.convertirKilogramoAQuintal(kilogramos);

        assertTrue(result.isExitoso());
        assertEquals(5.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "500 kg = 5 quintales");
    }

    /**
     * Test adicional: Conversión de peso grande
     */
    @Test
    public void testConvertirPesoGrande() {
        System.out.println("convertir 1000 kg a libras");
        double kilogramos = 1000.0;
        ConversionResultModel result = instance.convertirKilogramoALibra(kilogramos);

        assertTrue(result.isExitoso());
        assertEquals(2204.62, result.getResultado().getValorConvertidoExacto(), 0.1,
                     "1000 kg = 2204.62 libras");
    }

    /**
     * Test adicional: Verificar factor de conversión
     */
    @Test
    public void testVerificarFactorConversion() {
        System.out.println("verificar factor de conversión kg a libra");
        double kilogramos = 10.0;
        ConversionResultModel result = instance.convertirKilogramoALibra(kilogramos);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado().getFactorConversion());
        assertEquals(2.20462, result.getResultado().getFactorConversion(), 0.00001,
                     "El factor de conversión debe ser 2.20462");
    }
}
