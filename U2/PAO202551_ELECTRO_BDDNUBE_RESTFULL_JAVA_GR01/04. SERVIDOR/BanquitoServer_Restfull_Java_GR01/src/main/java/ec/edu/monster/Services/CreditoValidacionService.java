package ec.edu.monster.Services;

import ec.edu.monster.Constants.AppConfig;
import ec.edu.monster.Constants.ErrorMessages;
import ec.edu.monster.Models.Cliente;
import ec.edu.monster.Repositories.ClienteRepository;
import ec.edu.monster.Repositories.CreditoRepository;
import ec.edu.monster.Repositories.MovimientoRepository;
import ec.edu.monster.Utilities.CalculadoraFinanciera;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Servicio para validación de crédito y cálculo de monto máximo
 */
@Service
@RequiredArgsConstructor
public class CreditoValidacionService {

    private final ClienteRepository clienteRepository;
    private final MovimientoRepository movimientoRepository;
    private final CreditoRepository creditoRepository;
    private final AppConfig appConfig;

    /**
     * Validar si una persona es sujeto de crédito
     * Reglas:
     * 1. Es cliente del banco
     * 2. Tiene depósito en último mes
     * 3. Si casado, >= 25 años
     * 4. No tiene crédito activo
     */
    public ResultadoValidacion validarSujetoCredito(String cedula) {
        // Regla 1: Verificar si es cliente del banco
        Optional<Cliente> clienteOpt = clienteRepository.findByCedula(cedula);
        if (clienteOpt.isEmpty()) {
            return new ResultadoValidacion(false, ErrorMessages.CLIENTE_NO_ENCONTRADO, null);
        }

        Cliente cliente = clienteOpt.get();

        // Regla 2: Verificar que tenga al menos un depósito en el último mes
        LocalDateTime fechaInicio = LocalDateTime.now().minusMonths(1);
        if (!movimientoRepository.tieneDepositoEnUltimoMes(cedula, fechaInicio)) {
            return new ResultadoValidacion(false, ErrorMessages.SIN_DEPOSITOS_RECIENTES, null);
        }

        // Regla 3: Si es casado, debe tener al menos 25 años
        if (cliente.getEstadoCivil() != null &&
                cliente.getEstadoCivil().equalsIgnoreCase("casado")) {
            int edad = CalculadoraFinanciera.calcularEdad(cliente.getFechaNacimiento());
            if (edad < 25) {
                return new ResultadoValidacion(false,
                        String.format(ErrorMessages.EDAD_MINIMA_CASADO, edad), null);
            }
        }

        // Regla 4: No debe tener un crédito activo
        if (creditoRepository.tieneCreditoActivo(cedula)) {
            return new ResultadoValidacion(false, ErrorMessages.CREDITO_ACTIVO, null);
        }

        // Si pasó todas las validaciones
        return new ResultadoValidacion(true, ErrorMessages.CLIENTE_VALIDO, cliente);
    }

    /**
     * Calcular el monto máximo de crédito
     * Fórmula: ((Promedio Depósitos – Promedio Retiros) * 60%) * 9
     */
    public ResultadoMontoMaximo calcularMontoMaximo(String cedula) {
        // Primero validar si es sujeto de crédito
        ResultadoValidacion validacion = validarSujetoCredito(cedula);
        if (!validacion.isEsValido()) {
            return new ResultadoMontoMaximo(
                    BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, validacion.getMensaje());
        }

        // Calcular promedios de los últimos 3 meses
        LocalDateTime fechaInicio = LocalDateTime.now().minusMonths(3);
        LocalDateTime fechaFin = LocalDateTime.now();

        BigDecimal promedioDepositos = movimientoRepository
                .promedioDepositosPorPeriodo(cedula, fechaInicio, fechaFin);
        BigDecimal promedioRetiros = movimientoRepository
                .promedioRetirosPorPeriodo(cedula, fechaInicio, fechaFin);

        if (promedioDepositos == null)
            promedioDepositos = BigDecimal.ZERO;
        if (promedioRetiros == null)
            promedioRetiros = BigDecimal.ZERO;

        // Obtener configuración
        BigDecimal porcentaje = appConfig.getCreditoPorcentajeCapacidad();
        Integer multiplicador = appConfig.getCreditoMultiplicador();

        // Calcular monto máximo
        BigDecimal diferencia = promedioDepositos.subtract(promedioRetiros);
        BigDecimal montoMaximo = diferencia.max(BigDecimal.ZERO)
                .multiply(porcentaje)
                .multiply(BigDecimal.valueOf(multiplicador))
                .setScale(2, RoundingMode.HALF_UP);

        return new ResultadoMontoMaximo(
                montoMaximo,
                promedioDepositos.setScale(2, RoundingMode.HALF_UP),
                promedioRetiros.setScale(2, RoundingMode.HALF_UP),
                ErrorMessages.MONTO_MAXIMO_CALCULADO);
    }

    /**
     * Clase para resultado de validación
     */
    public static class ResultadoValidacion {
        private final boolean esValido;
        private final String mensaje;
        private final Cliente cliente;

        public ResultadoValidacion(boolean esValido, String mensaje, Cliente cliente) {
            this.esValido = esValido;
            this.mensaje = mensaje;
            this.cliente = cliente;
        }

        public boolean isEsValido() {
            return esValido;
        }

        public String getMensaje() {
            return mensaje;
        }

        public Cliente getCliente() {
            return cliente;
        }
    }

    /**
     * Clase para resultado de monto máximo
     */
    public static class ResultadoMontoMaximo {
        private final BigDecimal montoMaximo;
        private final BigDecimal promedioDepositos;
        private final BigDecimal promedioRetiros;
        private final String mensaje;

        public ResultadoMontoMaximo(BigDecimal montoMaximo, BigDecimal promedioDepositos,
                BigDecimal promedioRetiros, String mensaje) {
            this.montoMaximo = montoMaximo;
            this.promedioDepositos = promedioDepositos;
            this.promedioRetiros = promedioRetiros;
            this.mensaje = mensaje;
        }

        public BigDecimal getMontoMaximo() {
            return montoMaximo;
        }

        public BigDecimal getPromedioDepositos() {
            return promedioDepositos;
        }

        public BigDecimal getPromedioRetiros() {
            return promedioRetiros;
        }

        public String getMensaje() {
            return mensaje;
        }
    }
}
