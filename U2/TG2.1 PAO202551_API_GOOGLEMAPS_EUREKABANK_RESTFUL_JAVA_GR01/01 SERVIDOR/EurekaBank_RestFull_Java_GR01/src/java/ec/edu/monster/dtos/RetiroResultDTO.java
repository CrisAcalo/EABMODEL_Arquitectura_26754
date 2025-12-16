package ec.edu.monster.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;

/**
 * DTO para resultado de operación de retiro
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class RetiroResultDTO {

    @JsonbProperty("SaldoAnterior")
    private BigDecimal saldoAnterior;

    @JsonbProperty("SaldoNuevo")
    private BigDecimal saldoNuevo;

    @JsonbProperty("ImporteRetiro")
    private BigDecimal importeRetiro;

    @JsonbProperty("ITF")
    private BigDecimal itf;

    @JsonbProperty("CostoPorMovimiento")
    private BigDecimal costoPorMovimiento;

    @JsonbProperty("TotalDescontado")
    private BigDecimal totalDescontado;

    // Constructores
    public RetiroResultDTO() {
    }

    // Getters y Setters
    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public BigDecimal getSaldoNuevo() {
        return saldoNuevo;
    }

    public void setSaldoNuevo(BigDecimal saldoNuevo) {
        this.saldoNuevo = saldoNuevo;
    }

    public BigDecimal getImporteRetiro() {
        return importeRetiro;
    }

    public void setImporteRetiro(BigDecimal importeRetiro) {
        this.importeRetiro = importeRetiro;
    }

    public BigDecimal getItf() {
        return itf;
    }

    public void setItf(BigDecimal itf) {
        this.itf = itf;
    }

    public BigDecimal getCostoPorMovimiento() {
        return costoPorMovimiento;
    }

    public void setCostoPorMovimiento(BigDecimal costoPorMovimiento) {
        this.costoPorMovimiento = costoPorMovimiento;
    }

    public BigDecimal getTotalDescontado() {
        return totalDescontado;
    }

    public void setTotalDescontado(BigDecimal totalDescontado) {
        this.totalDescontado = totalDescontado;
    }
}
