package ec.edu.monster.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;

/**
 * DTO para resultado de operación de depósito
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class DepositoResultDTO {

    @JsonbProperty("NumeroMovimiento")
    private int numeroMovimiento;

    @JsonbProperty("SaldoAnterior")
    private BigDecimal saldoAnterior;

    @JsonbProperty("SaldoNuevo")
    private BigDecimal saldoNuevo;

    @JsonbProperty("Importe")
    private BigDecimal importe;

    // Constructores
    public DepositoResultDTO() {
    }

    public DepositoResultDTO(int numeroMovimiento, BigDecimal saldoAnterior, BigDecimal saldoNuevo, BigDecimal importe) {
        this.numeroMovimiento = numeroMovimiento;
        this.saldoAnterior = saldoAnterior;
        this.saldoNuevo = saldoNuevo;
        this.importe = importe;
    }

    // Getters y Setters
    public int getNumeroMovimiento() {
        return numeroMovimiento;
    }

    public void setNumeroMovimiento(int numeroMovimiento) {
        this.numeroMovimiento = numeroMovimiento;
    }

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

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }
}
