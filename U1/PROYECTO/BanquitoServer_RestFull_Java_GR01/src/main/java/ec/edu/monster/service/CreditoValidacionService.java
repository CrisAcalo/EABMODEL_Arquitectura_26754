package ec.edu.monster.service;

import ec.edu.monster.config.AppConfig;
import ec.edu.monster.dao.ClienteDAO;
import ec.edu.monster.dao.CreditoDAO;
import ec.edu.monster.dao.MovimientoDAO;
import ec.edu.monster.model.Cliente;
import ec.edu.monster.model.Movimiento;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio para validación de crédito
 */
@Stateless
public class CreditoValidacionService {
    
    @Inject
    private ClienteDAO clienteDAO;
    
    @Inject
    private MovimientoDAO movimientoDAO;
    
    @Inject
    private CreditoDAO creditoDAO;
    
    @Inject
    private AppConfig config;
    
    /**
     * Verificar si una persona es sujeto de crédito
     * 
     * Reglas:
     * 1. Verificar si el solicitante es cliente del banco
     * 2. Verificar que el cliente al menos posea una transacción de depósito en el último mes
     * 3. Verificar que el cliente no sea menor a 25 años cuando su estado civil sea Casado
     * 4. Verificar que el cliente actualmente no tenga un crédito activo en el banco
     */
    public Map<String, Object> validarSujetoCredito(String cedula) {
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("esValido", false);
        resultado.put("mensaje", "");
        
        // Regla 1: Verificar si es cliente del banco
        Cliente cliente = clienteDAO.findByCedula(cedula);
        if (cliente == null) {
            resultado.put("mensaje", "La persona no es cliente del banco");
            return resultado;
        }
        
        // Regla 2: Verificar que tenga al menos un depósito en el último mes
        if (!movimientoDAO.hasDepositoEnUltimoMes(cedula)) {
            resultado.put("mensaje", "El cliente no tiene depósitos en el último mes");
            return resultado;
        }
        
        // Regla 3: Si es casado, debe tener al menos 25 años
        if ("Casado".equalsIgnoreCase(cliente.getEstadoCivil())) {
            int edad = calcularEdad(cliente.getFechaNacimiento());
            if (edad < 25) {
                resultado.put("mensaje", "El cliente casado debe tener al menos 25 años. Edad actual: " + edad);
                return resultado;
            }
        }
        
        // Regla 4: No debe tener un crédito activo
        if (creditoDAO.tieneCreditoActivo(cedula)) {
            resultado.put("mensaje", "El cliente ya tiene un crédito activo");
            return resultado;
        }
        
        // Si pasó todas las validaciones
        resultado.put("esValido", true);
        resultado.put("mensaje", "El cliente es sujeto de crédito");
        resultado.put("cliente", cliente);
        
        return resultado;
    }
    
    /**
     * Calcular el monto máximo de crédito para un cliente
     * 
     * Fórmula: ((Promedio Depósitos – Promedio Retiros) * 60%) * 9
     */
    public Map<String, Object> calcularMontoMaximoCredito(String cedula) {
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("montoMaximo", BigDecimal.ZERO);
        resultado.put("mensaje", "");
        
        // Primero validar si es sujeto de crédito
        Map<String, Object> validacion = validarSujetoCredito(cedula);
        if (!(Boolean) validacion.get("esValido")) {
            resultado.put("mensaje", validacion.get("mensaje"));
            return resultado;
        }
        
        // Calcular promedios de los últimos 3 meses
        LocalDateTime fechaInicio = LocalDateTime.now().minusMonths(3);
        LocalDateTime fechaFin = LocalDateTime.now();
        
        // Obtener depósitos
        List<Movimiento> depositos = movimientoDAO.findDepositosByClienteAndPeriodo(cedula, fechaInicio, fechaFin);
        BigDecimal promedioDepositos = calcularPromedio(depositos);
        
        // Obtener retiros
        List<Movimiento> retiros = movimientoDAO.findRetirosByClienteAndPeriodo(cedula, fechaInicio, fechaFin);
        BigDecimal promedioRetiros = calcularPromedio(retiros);
        
        // Calcular monto máximo
        BigDecimal diferencia = promedioDepositos.subtract(promedioRetiros);
        BigDecimal porcentajeCapacidad = new BigDecimal(config.getPorcentajeCapacidad());
        int multiplicador = config.getMultiplicador();
        
        BigDecimal montoMaximo = diferencia.multiply(porcentajeCapacidad)
                                          .multiply(new BigDecimal(multiplicador))
                                          .setScale(2, RoundingMode.HALF_UP);
        
        // Asegurar que no sea negativo
        if (montoMaximo.compareTo(BigDecimal.ZERO) < 0) {
            montoMaximo = BigDecimal.ZERO;
        }
        
        resultado.put("montoMaximo", montoMaximo);
        resultado.put("promedioDepositos", promedioDepositos);
        resultado.put("promedioRetiros", promedioRetiros);
        resultado.put("mensaje", "Monto máximo calculado exitosamente");
        
        return resultado;
    }
    
    /**
     * Calcular el promedio de los montos de una lista de movimientos
     */
    private BigDecimal calcularPromedio(List<Movimiento> movimientos) {
        if (movimientos == null || movimientos.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal suma = movimientos.stream()
                .map(Movimiento::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return suma.divide(new BigDecimal(movimientos.size()), 2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calcular la edad de una persona
     */
    private int calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            return 0;
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}
