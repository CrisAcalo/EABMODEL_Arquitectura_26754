package ec.edu.monster.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO para el resultado de una operación de retiro
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RetiroResultDTO implements Serializable {

    private String codigoCuenta;
    private BigDecimal importeRetirado;
    private BigDecimal importeITF;
    private BigDecimal importeCargo;
    private BigDecimal totalDescontado;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoNuevo;
    private int numeroMovimientoRetiro;
    private Integer numeroMovimientoITF;
    private Integer numeroMovimientoCargo;

    // Constructor vacío
    public RetiroResultDTO() {
    }

    // Constructor completo
    public RetiroResultDTO(String codigoCuenta, BigDecimal importeRetirado,
                          BigDecimal importeITF, BigDecimal importeCargo,
                          BigDecimal totalDescontado, BigDecimal saldoAnterior,
                          BigDecimal saldoNuevo, int numeroMovimientoRetiro,
                          Integer numeroMovimientoITF, Integer numeroMovimientoCargo) {
        this.codigoCuenta = codigoCuenta;
        this.importeRetirado = importeRetirado;
        this.importeITF = importeITF;
        this.importeCargo = importeCargo;
        this.totalDescontado = totalDescontado;
        this.saldoAnterior = saldoAnterior;
        this.saldoNuevo = saldoNuevo;
        this.numeroMovimientoRetiro = numeroMovimientoRetiro;
        this.numeroMovimientoITF = numeroMovimientoITF;
        this.numeroMovimientoCargo = numeroMovimientoCargo;
    }

    // Getters y Setters
    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public BigDecimal getImporteRetirado() {
        return importeRetirado;
    }

    public void setImporteRetirado(BigDecimal importeRetirado) {
        this.importeRetirado = importeRetirado;
    }

    public BigDecimal getImporteITF() {
        return importeITF;
    }

    public void setImporteITF(BigDecimal importeITF) {
        this.importeITF = importeITF;
    }

    public BigDecimal getImporteCargo() {
        return importeCargo;
    }

    public void setImporteCargo(BigDecimal importeCargo) {
        this.importeCargo = importeCargo;
    }

    public BigDecimal getTotalDescontado() {
        return totalDescontado;
    }

    public void setTotalDescontado(BigDecimal totalDescontado) {
        this.totalDescontado = totalDescontado;
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

    public int getNumeroMovimientoRetiro() {
        return numeroMovimientoRetiro;
    }

    public void setNumeroMovimientoRetiro(int numeroMovimientoRetiro) {
        this.numeroMovimientoRetiro = numeroMovimientoRetiro;
    }

    public Integer getNumeroMovimientoITF() {
        return numeroMovimientoITF;
    }

    public void setNumeroMovimientoITF(Integer numeroMovimientoITF) {
        this.numeroMovimientoITF = numeroMovimientoITF;
    }

    public Integer getNumeroMovimientoCargo() {
        return numeroMovimientoCargo;
    }

    public void setNumeroMovimientoCargo(Integer numeroMovimientoCargo) {
        this.numeroMovimientoCargo = numeroMovimientoCargo;
    }
}
