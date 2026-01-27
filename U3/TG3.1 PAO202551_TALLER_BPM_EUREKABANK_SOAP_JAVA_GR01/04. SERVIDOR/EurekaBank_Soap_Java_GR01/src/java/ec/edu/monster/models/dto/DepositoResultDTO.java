package ec.edu.monster.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO para el resultado de una operación de depósito
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DepositoResultDTO implements Serializable {

    private String codigoCuenta;
    private BigDecimal importeDepositado;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoNuevo;
    private int numeroMovimiento;

    // Constructor vacío
    public DepositoResultDTO() {
    }

    // Constructor completo
    public DepositoResultDTO(String codigoCuenta, BigDecimal importeDepositado,
                            BigDecimal saldoAnterior, BigDecimal saldoNuevo,
                            int numeroMovimiento) {
        this.codigoCuenta = codigoCuenta;
        this.importeDepositado = importeDepositado;
        this.saldoAnterior = saldoAnterior;
        this.saldoNuevo = saldoNuevo;
        this.numeroMovimiento = numeroMovimiento;
    }

    // Getters y Setters
    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public BigDecimal getImporteDepositado() {
        return importeDepositado;
    }

    public void setImporteDepositado(BigDecimal importeDepositado) {
        this.importeDepositado = importeDepositado;
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

    public int getNumeroMovimiento() {
        return numeroMovimiento;
    }

    public void setNumeroMovimiento(int numeroMovimiento) {
        this.numeroMovimiento = numeroMovimiento;
    }
}
