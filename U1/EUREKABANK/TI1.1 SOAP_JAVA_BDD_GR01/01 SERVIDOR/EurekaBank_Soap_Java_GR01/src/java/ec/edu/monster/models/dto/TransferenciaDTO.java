package ec.edu.monster.models.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO para operaciones de transferencia entre cuentas
 */
public class TransferenciaDTO implements Serializable {

    private String cuentaOrigen;
    private String cuentaDestino;
    private String claveCuentaOrigen;
    private BigDecimal importe;
    private String codigoEmpleado;

    // Constructor vacío
    public TransferenciaDTO() {
    }

    // Constructor completo
    public TransferenciaDTO(String cuentaOrigen, String cuentaDestino,
                           String claveCuentaOrigen, BigDecimal importe,
                           String codigoEmpleado) {
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.claveCuentaOrigen = claveCuentaOrigen;
        this.importe = importe;
        this.codigoEmpleado = codigoEmpleado;
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

    public String getClaveCuentaOrigen() {
        return claveCuentaOrigen;
    }

    public void setClaveCuentaOrigen(String claveCuentaOrigen) {
        this.claveCuentaOrigen = claveCuentaOrigen;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }
}
