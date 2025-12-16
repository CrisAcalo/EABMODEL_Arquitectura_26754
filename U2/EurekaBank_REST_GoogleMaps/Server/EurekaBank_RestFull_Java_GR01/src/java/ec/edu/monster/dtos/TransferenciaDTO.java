package ec.edu.monster.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;

/**
 * DTO para transferencias entre cuentas
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class TransferenciaDTO {

    @JsonbProperty("CuentaOrigen")
    private String cuentaOrigen;

    @JsonbProperty("CuentaDestino")
    private String cuentaDestino;

    @JsonbProperty("ClaveCuentaOrigen")
    private String claveCuentaOrigen;

    @JsonbProperty("Importe")
    private BigDecimal importe;

    @JsonbProperty("CodigoEmpleado")
    private String codigoEmpleado;

    // Constructores
    public TransferenciaDTO() {
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
