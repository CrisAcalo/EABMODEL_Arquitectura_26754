package ec.edu.monster.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO para el resultado de una operación de transferencia
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TransferenciaResultDTO implements Serializable {

    private String cuentaOrigen;
    private String cuentaDestino;
    private BigDecimal importeTransferido;
    private BigDecimal saldoAnteriorOrigen;
    private BigDecimal saldoNuevoOrigen;
    private BigDecimal saldoAnteriorDestino;
    private BigDecimal saldoNuevoDestino;
    private int numeroMovimientoOrigen;
    private int numeroMovimientoDestino;

    // Constructor vacío
    public TransferenciaResultDTO() {
    }

    // Constructor completo
    public TransferenciaResultDTO(String cuentaOrigen, String cuentaDestino,
                                 BigDecimal importeTransferido,
                                 BigDecimal saldoAnteriorOrigen, BigDecimal saldoNuevoOrigen,
                                 BigDecimal saldoAnteriorDestino, BigDecimal saldoNuevoDestino,
                                 int numeroMovimientoOrigen, int numeroMovimientoDestino) {
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.importeTransferido = importeTransferido;
        this.saldoAnteriorOrigen = saldoAnteriorOrigen;
        this.saldoNuevoOrigen = saldoNuevoOrigen;
        this.saldoAnteriorDestino = saldoAnteriorDestino;
        this.saldoNuevoDestino = saldoNuevoDestino;
        this.numeroMovimientoOrigen = numeroMovimientoOrigen;
        this.numeroMovimientoDestino = numeroMovimientoDestino;
    }

    // Getters y Setters
    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public BigDecimal getImporteTransferido() {
        return importeTransferido;
    }

    public void setImporteTransferido(BigDecimal importeTransferido) {
        this.importeTransferido = importeTransferido;
    }

    public BigDecimal getSaldoAnteriorOrigen() {
        return saldoAnteriorOrigen;
    }

    public void setSaldoAnteriorOrigen(BigDecimal saldoAnteriorOrigen) {
        this.saldoAnteriorOrigen = saldoAnteriorOrigen;
    }

    public BigDecimal getSaldoNuevoOrigen() {
        return saldoNuevoOrigen;
    }

    public void setSaldoNuevoOrigen(BigDecimal saldoNuevoOrigen) {
        this.saldoNuevoOrigen = saldoNuevoOrigen;
    }

    public BigDecimal getSaldoAnteriorDestino() {
        return saldoAnteriorDestino;
    }

    public void setSaldoAnteriorDestino(BigDecimal saldoAnteriorDestino) {
        this.saldoAnteriorDestino = saldoAnteriorDestino;
    }

    public BigDecimal getSaldoNuevoDestino() {
        return saldoNuevoDestino;
    }

    public void setSaldoNuevoDestino(BigDecimal saldoNuevoDestino) {
        this.saldoNuevoDestino = saldoNuevoDestino;
    }

    public int getNumeroMovimientoOrigen() {
        return numeroMovimientoOrigen;
    }

    public void setNumeroMovimientoOrigen(int numeroMovimientoOrigen) {
        this.numeroMovimientoOrigen = numeroMovimientoOrigen;
    }

    public int getNumeroMovimientoDestino() {
        return numeroMovimientoDestino;
    }

    public void setNumeroMovimientoDestino(int numeroMovimientoDestino) {
        this.numeroMovimientoDestino = numeroMovimientoDestino;
    }
}
