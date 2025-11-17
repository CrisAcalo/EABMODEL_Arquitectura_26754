package ec.edu.monster.service;

import ec.edu.monster.config.AppConfig;
import ec.edu.monster.dao.ClienteDAO;
import ec.edu.monster.dao.CreditoDAO;
import ec.edu.monster.model.Cliente;
import ec.edu.monster.model.Credito;
import ec.edu.monster.model.CuotaAmortizacion;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio para otorgar créditos
 */
@Stateless
public class CreditoService {
    
    @Inject
    private ClienteDAO clienteDAO;
    
    @Inject
    private CreditoDAO creditoDAO;
    
    @Inject
    private CreditoValidacionService validacionService;
    
    @Inject
    private AppConfig config;
    
    /**
     * Otorgar un crédito y crear la tabla de amortización
     * 
     * @param cedula Cédula del cliente
     * @param precioElectrodomestico Precio del electrodoméstico (monto del crédito)
     * @param numeroCuotas Número de cuotas (entre 3 y 24)
     * @return Mapa con el resultado de la operación
     */
    @Transactional
    public Map<String, Object> otorgarCredito(String cedula, BigDecimal precioElectrodomestico, Integer numeroCuotas) {
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("exito", false);
        resultado.put("mensaje", "");
        
        // Validar número de cuotas
        int plazoMinimo = config.getPlazoMinimo();
        int plazoMaximo = config.getPlazoMaximo();
        
        if (numeroCuotas < plazoMinimo || numeroCuotas > plazoMaximo) {
            resultado.put("mensaje", "El número de cuotas debe estar entre " + plazoMinimo + " y " + plazoMaximo);
            return resultado;
        }
        
        // Validar si es sujeto de crédito
        Map<String, Object> validacion = validacionService.validarSujetoCredito(cedula);
        if (!(Boolean) validacion.get("esValido")) {
            resultado.put("mensaje", validacion.get("mensaje"));
            return resultado;
        }
        
        // Verificar monto máximo
        Map<String, Object> montoMaximoInfo = validacionService.calcularMontoMaximoCredito(cedula);
        BigDecimal montoMaximo = (BigDecimal) montoMaximoInfo.get("montoMaximo");
        
        if (precioElectrodomestico.compareTo(montoMaximo) > 0) {
            resultado.put("mensaje", "El monto del crédito ($" + precioElectrodomestico + 
                         ") supera el monto máximo autorizado ($" + montoMaximo + ")");
            resultado.put("montoMaximo", montoMaximo);
            return resultado;
        }
        
        // Obtener cliente
        Cliente cliente = (Cliente) validacion.get("cliente");
        
        // Crear el crédito
        BigDecimal tasaInteres = new BigDecimal(config.getTasaAnual());
        BigDecimal cuotaMensual = calcularCuotaFija(precioElectrodomestico, tasaInteres, numeroCuotas);
        
        String numeroCredito = generarNumeroCredito();
        
        Credito credito = new Credito(
            numeroCredito,
            precioElectrodomestico,
            tasaInteres,
            numeroCuotas,
            cuotaMensual,
            cliente
        );
        
        credito.setDescripcion("Crédito para compra de electrodoméstico");
        
        // Generar tabla de amortización
        List<CuotaAmortizacion> tablaAmortizacion = generarTablaAmortizacion(
            precioElectrodomestico, tasaInteres, numeroCuotas, cuotaMensual
        );
        
        // Asociar cuotas al crédito
        for (CuotaAmortizacion cuota : tablaAmortizacion) {
            credito.addCuotaAmortizacion(cuota);
        }
        
        // Guardar en la base de datos
        creditoDAO.save(credito);
        
        resultado.put("exito", true);
        resultado.put("mensaje", "Crédito otorgado exitosamente");
        resultado.put("credito", credito);
        resultado.put("numeroCredito", numeroCredito);
        resultado.put("tablaAmortizacion", tablaAmortizacion);
        
        return resultado;
    }
    
    /**
     * Calcular la cuota fija mensual
     * 
     * Fórmula: Cuota = Valor / (1 - ((1 + TasaPeriodo)^-NúmeroCuotas)) / TasaPeriodo
     */
    private BigDecimal calcularCuotaFija(BigDecimal montoCredito, BigDecimal tasaAnual, Integer numeroCuotas) {
        // Tasa mensual
        BigDecimal tasaMensual = tasaAnual.divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);
        
        // (1 + tasaMensual)
        BigDecimal unMasTasa = BigDecimal.ONE.add(tasaMensual);
        
        // (1 + tasaMensual)^-numeroCuotas
        double potencia = Math.pow(unMasTasa.doubleValue(), -numeroCuotas);
        BigDecimal potenciaBD = new BigDecimal(potencia).setScale(10, RoundingMode.HALF_UP);
        
        // 1 - potencia
        BigDecimal numerador = BigDecimal.ONE.subtract(potenciaBD);
        
        // numerador / tasaMensual
        BigDecimal denominador = numerador.divide(tasaMensual, 10, RoundingMode.HALF_UP);
        
        // Cuota = montoCredito / denominador
        BigDecimal cuota = montoCredito.divide(denominador, 2, RoundingMode.HALF_UP);
        
        return cuota;
    }
    
    /**
     * Generar la tabla de amortización
     */
    private List<CuotaAmortizacion> generarTablaAmortizacion(BigDecimal montoCredito, 
                                                              BigDecimal tasaAnual, 
                                                              Integer numeroCuotas,
                                                              BigDecimal cuotaFija) {
        List<CuotaAmortizacion> tabla = new ArrayList<>();
        
        BigDecimal tasaMensual = tasaAnual.divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);
        BigDecimal saldo = montoCredito;
        
        for (int i = 1; i <= numeroCuotas; i++) {
            // Calcular interés de la cuota
            BigDecimal interes = saldo.multiply(tasaMensual).setScale(2, RoundingMode.HALF_UP);
            
            // Calcular capital pagado
            BigDecimal capitalPagado = cuotaFija.subtract(interes).setScale(2, RoundingMode.HALF_UP);
            
            // Calcular nuevo saldo
            BigDecimal nuevoSaldo = saldo.subtract(capitalPagado).setScale(2, RoundingMode.HALF_UP);
            
            // Ajuste para la última cuota (por posibles diferencias de redondeo)
            if (i == numeroCuotas) {
                capitalPagado = saldo;
                cuotaFija = capitalPagado.add(interes);
                nuevoSaldo = BigDecimal.ZERO;
            }
            
            // Crear cuota de amortización
            CuotaAmortizacion cuota = new CuotaAmortizacion(i, cuotaFija, interes, capitalPagado, nuevoSaldo);
            tabla.add(cuota);
            
            // Actualizar saldo para la siguiente iteración
            saldo = nuevoSaldo;
        }
        
        return tabla;
    }
    
    /**
     * Generar un número de crédito único
     */
    private String generarNumeroCredito() {
        long timestamp = System.currentTimeMillis();
        return "CRE" + timestamp;
    }
    
    /**
     * Obtener la tabla de amortización de un crédito
     */
    public List<CuotaAmortizacion> obtenerTablaAmortizacion(String numeroCredito) {
        Credito credito = creditoDAO.findByNumeroCredito(numeroCredito);
        if (credito == null) {
            return new ArrayList<>();
        }
        return credito.getCuotasAmortizacion();
    }
}
