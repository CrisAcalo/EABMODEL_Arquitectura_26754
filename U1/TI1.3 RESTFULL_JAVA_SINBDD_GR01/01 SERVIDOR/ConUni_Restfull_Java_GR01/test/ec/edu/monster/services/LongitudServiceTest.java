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
 * Tests para LongitudService
 * Prueba todas las conversiones de longitud y casos de error
 *
 * @author jeffe
 */
public class LongitudServiceTest {

    private LongitudService instance;

    public LongitudServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        instance = new LongitudService();
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of convertirMillaAMetro method, of class LongitudService.
     * Prueba: 1 milla = 1609.34 metros
     */
    @Test
    public void testConvertirMillaAMetro() {
        System.out.println("convertirMillaAMetro - caso exitoso");
        String millas = "1.0";
        ConversionResultModel result = instance.convertirMillaAMetro(millas);

        assertTrue(result.isExitoso(), "La conversión debería ser exitosa");
        assertNotNull(result.getResultado(), "El resultado no debería ser null");
        assertNull(result.getError(), "No debería haber error");
        assertEquals(1609.34, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "1 milla = 1609.34 metros");
        assertEquals("Milla", result.getResultado().getUnidadOrigen());
        assertEquals("Metro", result.getResultado().getUnidadDestino());
        assertEquals("Longitud", result.getResultado().getTipoConversion());
    }

    /**
     * Test con valor negativo - debe fallar
     */
    @Test
    public void testConvertirMillaAMetroValorNegativo() {
        System.out.println("convertirMillaAMetro - valor negativo");
        String millas = "-5.0";
        ConversionResultModel result = instance.convertirMillaAMetro(millas);

        assertFalse(result.isExitoso(), "No debería aceptar valores negativos");
        assertNull(result.getResultado(), "No debería haber resultado");
        assertNotNull(result.getError(), "Debería retornar un error");
        assertEquals("VAL_001", result.getError().getCodigoError());
    }

    /**
     * Test of convertirMetroAMilla method, of class LongitudService.
     * Prueba: 1609.34 metros = 1 milla
     */
    @Test
    public void testConvertirMetroAMilla() {
        System.out.println("convertirMetroAMilla - caso exitoso");
        String metros = "1609.34";
        ConversionResultModel result = instance.convertirMetroAMilla(metros);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(1.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "1609.34 metros = 1 milla");
        assertEquals("Metro", result.getResultado().getUnidadOrigen());
        assertEquals("Milla", result.getResultado().getUnidadDestino());
    }

    /**
     * Test con valor negativo
     */
    @Test
    public void testConvertirMetroAMillaValorNegativo() {
        System.out.println("convertirMetroAMilla - valor negativo");
        String metros = "-100.0";
        ConversionResultModel result = instance.convertirMetroAMilla(metros);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
    }

    /**
     * Test of convertirMillaAPulgada method, of class LongitudService.
     * Prueba: 1 milla = 63360 pulgadas
     */
    @Test
    public void testConvertirMillaAPulgada() {
        System.out.println("convertirMillaAPulgada - caso exitoso");
        String millas = "1.0";
        ConversionResultModel result = instance.convertirMillaAPulgada(millas);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(63360.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "1 milla = 63360 pulgadas");
        assertEquals("Milla", result.getResultado().getUnidadOrigen());
        assertEquals("Pulgada", result.getResultado().getUnidadDestino());
    }

    /**
     * Test con valor negativo
     */
    @Test
    public void testConvertirMillaAPulgadaValorNegativo() {
        System.out.println("convertirMillaAPulgada - valor negativo");
        String millas = "-2.0";
        ConversionResultModel result = instance.convertirMillaAPulgada(millas);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
    }

    /**
     * Test of convertirPulgadaAMilla method, of class LongitudService.
     * Prueba: 63360 pulgadas = 1 milla
     */
    @Test
    public void testConvertirPulgadaAMilla() {
        System.out.println("convertirPulgadaAMilla - caso exitoso");
        String pulgadas = "63360.0";
        ConversionResultModel result = instance.convertirPulgadaAMilla(pulgadas);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(1.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "63360 pulgadas = 1 milla");
        assertEquals("Pulgada", result.getResultado().getUnidadOrigen());
        assertEquals("Milla", result.getResultado().getUnidadDestino());
    }

    /**
     * Test con valor negativo
     */
    @Test
    public void testConvertirPulgadaAMillaValorNegativo() {
        System.out.println("convertirPulgadaAMilla - valor negativo");
        String pulgadas = "-1000.0";
        ConversionResultModel result = instance.convertirPulgadaAMilla(pulgadas);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
    }

    /**
     * Test of convertirMetroAPulgada method, of class LongitudService.
     * Prueba: 1 metro = 39.3701 pulgadas
     */
    @Test
    public void testConvertirMetroAPulgada() {
        System.out.println("convertirMetroAPulgada - caso exitoso");
        String metros = "1.0";
        ConversionResultModel result = instance.convertirMetroAPulgada(metros);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(39.3701, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "1 metro = 39.3701 pulgadas");
        assertEquals("Metro", result.getResultado().getUnidadOrigen());
        assertEquals("Pulgada", result.getResultado().getUnidadDestino());
    }

    /**
     * Test con valor negativo
     */
    @Test
    public void testConvertirMetroAPulgadaValorNegativo() {
        System.out.println("convertirMetroAPulgada - valor negativo");
        String metros = "-50.0";
        ConversionResultModel result = instance.convertirMetroAPulgada(metros);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
    }

    /**
     * Test of convertirPulgadaAMetro method, of class LongitudService.
     * Prueba: 39.3701 pulgadas = 1 metro
     */
    @Test
    public void testConvertirPulgadaAMetro() {
        System.out.println("convertirPulgadaAMetro - caso exitoso");
        String pulgadas = "39.3701";
        ConversionResultModel result = instance.convertirPulgadaAMetro(pulgadas);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado());
        assertEquals(1.0, result.getResultado().getValorConvertidoExacto(), 0.01,
                     "39.3701 pulgadas = 1 metro");
        assertEquals("Pulgada", result.getResultado().getUnidadOrigen());
        assertEquals("Metro", result.getResultado().getUnidadDestino());
    }

    /**
     * Test con valor negativo
     */
    @Test
    public void testConvertirPulgadaAMetroValorNegativo() {
        System.out.println("convertirPulgadaAMetro - valor negativo");
        String pulgadas = "-200.0";
        ConversionResultModel result = instance.convertirPulgadaAMetro(pulgadas);

        assertFalse(result.isExitoso());
        assertNotNull(result.getError());
    }

    /**
     * Test adicional: Conversión de múltiples millas
     */
    @Test
    public void testConvertirMultiplesMillas() {
        System.out.println("convertir 5 millas a metros");
        String millas = "5.0";
        ConversionResultModel result = instance.convertirMillaAMetro(millas);

        assertTrue(result.isExitoso());
        assertEquals(8046.7, result.getResultado().getValorConvertidoExacto(), 0.1,
                     "5 millas = 8046.7 metros");
    }

    /**
     * Test adicional: Verificar redondeo a 2 decimales
     */
    @Test
    public void testVerificarRedondeo() {
        System.out.println("verificar redondeo a 2 decimales");
        String metros = "100.0";
        ConversionResultModel result = instance.convertirMetroAMilla(metros);

        assertTrue(result.isExitoso());
        assertNotNull(result.getResultado().getValorConvertidoRedondeado());
        // El valor redondeado debe tener como máximo 2 decimales
        double redondeado = result.getResultado().getValorConvertidoRedondeado();
        assertEquals(redondeado, Math.round(redondeado * 100.0) / 100.0, 0.001);
    }
}
