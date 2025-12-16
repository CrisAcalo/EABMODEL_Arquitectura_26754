package ec.edu.monster.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;

/**
 * DTO para resultado de operación de transferencia
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class TransferenciaResultDTO {

    @JsonbProperty("CuentaOrigen")
    private CuentaResumenDTO cuentaOrigen;

    @JsonbProperty("CuentaDestino")
    private CuentaResumenDTO cuentaDestino;

    @JsonbProperty("ImporteTransferido")
    private BigDecimal importeTransferido;

    @JsonbProperty("ITF")
    private BigDecimal itf;

    @JsonbProperty("CostoPorMovimiento")
    private BigDecimal costoPorMovimiento;

    @JsonbProperty("TotalDescontado")
    private BigDecimal totalDescontado;

    // Constructores
    public TransferenciaResultDTO() {
    }

    // Getters y Setters
    public CuentaResumenDTO getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(CuentaResumenDTO cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public CuentaResumenDTO getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(CuentaResumenDTO cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public BigDecimal getImporteTransferido() {
        return importeTransferido;
    }

    public void setImporteTransferido(BigDecimal importeTransferido) {
        this.importeTransferido = importeTransferido;
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
