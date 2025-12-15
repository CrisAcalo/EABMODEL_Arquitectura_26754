package ec.edu.monster.Services;

import ec.edu.monster.Constants.AppConfig;
import ec.edu.monster.Constants.ErrorMessages;
import ec.edu.monster.Models.Credito;
import ec.edu.monster.Models.CuotaAmortizacion;
import ec.edu.monster.Repositories.CreditoRepository;
import ec.edu.monster.Utilities.CalculadoraFinanciera;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para otorgar créditos y gestionar tabla de amortización
 */
@Service
@RequiredArgsConstructor
public class CreditoService {

    private final CreditoRepository creditoRepository;
    private final CreditoValidacionService validacionService;
    private final AppConfig appConfig;

    /**
     * Otorgar un crédito y generar la tabla de amortización
     */
    @Transactional
    public ResultadoCredito otorgarCredito(String cedula, BigDecimal montoCredito, int numeroCuotas) {
        // Obtener configuración
        int plazoMin = appConfig.getCreditoPlazoMinimo();
        int plazoMax = appConfig.getCreditoPlazoMaximo();
        BigDecimal tasaAnual = appConfig.getCreditoTasaAnual();

        // Validar plazo
        if (numeroCuotas < plazoMin || numeroCuotas > plazoMax) {
            return new ResultadoCredito(false,
                    String.format(ErrorMessages.PLAZO_INVALIDO, plazoMin, plazoMax),
                    null, null);
        }

        // Validar sujeto de crédito
        CreditoValidacionService.ResultadoValidacion validacion = validacionService.validarSujetoCredito(cedula);
        if (!validacion.isEsValido()) {
            return new ResultadoCredito(false, validacion.getMensaje(), null, null);
        }

        // Verificar monto máximo
        CreditoValidacionService.ResultadoMontoMaximo montoMaximoInfo = validacionService.calcularMontoMaximo(cedula);
        if (montoCredito.compareTo(montoMaximoInfo.getMontoMaximo()) > 0) {
            return new ResultadoCredito(false,
                    String.format(ErrorMessages.MONTO_SUPERA_MAXIMO,
                            montoCredito.doubleValue(), montoMaximoInfo.getMontoMaximo().doubleValue()),
                    null, null);
        }

        // Calcular cuota mensual
        BigDecimal cuotaMensual = CalculadoraFinanciera.calcularCuotaFija(
                montoCredito, tasaAnual, numeroCuotas);

        // Crear el crédito
        Credito credito = new Credito();
        credito.setNumeroCredito(generarNumeroCredito());
        credito.setMontoCredito(montoCredito);
        credito.setTasaInteres(tasaAnual);
        credito.setNumeroCuotas(numeroCuotas);
        credito.setCuotaMensual(cuotaMensual);
        credito.setFechaOtorgamiento(LocalDateTime.now());
        credito.setEstado("ACTIVO");
        credito.setDescripcion("Crédito para compra de electrodoméstico");
        credito.setCliente(validacion.getCliente());

        // Generar tabla de amortización
        List<CuotaAmortizacion> tablaAmortizacion = CalculadoraFinanciera
                .generarTablaAmortizacion(montoCredito, tasaAnual, numeroCuotas, cuotaMensual);

        // Asociar cuotas al crédito
        for (CuotaAmortizacion cuota : tablaAmortizacion) {
            cuota.setCredito(credito);
            credito.getCuotasAmortizacion().add(cuota);
        }

        // Guardar en la base de datos
        creditoRepository.save(credito);

        return new ResultadoCredito(true, ErrorMessages.CREDITO_OTORGADO,
                credito, tablaAmortizacion);
    }

    /**
     * Obtener la tabla de amortización de un crédito
     */
    public List<CuotaAmortizacion> obtenerTablaAmortizacion(String numeroCredito) {
        Optional<Credito> creditoOpt = creditoRepository.findByNumeroCredito(numeroCredito);

        if (creditoOpt.isEmpty()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(creditoOpt.get().getCuotasAmortizacion());
    }

    /**
     * Generar un número de crédito único
     */
    private String generarNumeroCredito() {
        // Formato: CRE + yyyyMMddHHmmss = 17 caracteres (dentro del límite de 20)
        return "CRE" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    /**
     * Clase para resultado de otorgar crédito
     */
    public static class ResultadoCredito {
        private final boolean exito;
        private final String mensaje;
        private final Credito credito;
        private final List<CuotaAmortizacion> tablaAmortizacion;

        public ResultadoCredito(boolean exito, String mensaje,
                Credito credito, List<CuotaAmortizacion> tablaAmortizacion) {
            this.exito = exito;
            this.mensaje = mensaje;
            this.credito = credito;
            this.tablaAmortizacion = tablaAmortizacion;
        }

        public boolean isExito() {
            return exito;
        }

        public String getMensaje() {
            return mensaje;
        }

        public Credito getCredito() {
            return credito;
        }

        public List<CuotaAmortizacion> getTablaAmortizacion() {
            return tablaAmortizacion;
        }
    }
}
