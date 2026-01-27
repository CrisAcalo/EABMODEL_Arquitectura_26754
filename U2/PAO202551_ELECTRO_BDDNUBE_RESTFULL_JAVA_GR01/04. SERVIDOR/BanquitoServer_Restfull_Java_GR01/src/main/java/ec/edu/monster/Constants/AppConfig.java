package ec.edu.monster.Constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

/**
 * Clase para centralizar la lectura de configuración desde application.yaml
 */
@Component
public class AppConfig {

    @Value("${credito.tasa-anual:0.16}")
    private BigDecimal creditoTasaAnual;

    @Value("${credito.plazo-minimo:3}")
    private Integer creditoPlazoMinimo;

    @Value("${credito.plazo-maximo:24}")
    private Integer creditoPlazoMaximo;

    @Value("${credito.porcentaje-capacidad:0.60}")
    private BigDecimal creditoPorcentajeCapacidad;

    @Value("${credito.multiplicador:9}")
    private Integer creditoMultiplicador;

    public BigDecimal getCreditoTasaAnual() {
        return creditoTasaAnual;
    }

    public Integer getCreditoPlazoMinimo() {
        return creditoPlazoMinimo;
    }

    public Integer getCreditoPlazoMaximo() {
        return creditoPlazoMaximo;
    }

    public BigDecimal getCreditoPorcentajeCapacidad() {
        return creditoPorcentajeCapacidad;
    }

    public Integer getCreditoMultiplicador() {
        return creditoMultiplicador;
    }
}
