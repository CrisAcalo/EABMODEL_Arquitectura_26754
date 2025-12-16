package ec.edu.monster.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;

/**
 * DTO para realizar operaciones de depósito o retiro
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class TransaccionDTO {

    @JsonbProperty("CodigoCuenta")
    private String codigoCuenta;

    @JsonbProperty("ClaveCuenta")
    private String claveCuenta;

    @JsonbProperty("Importe")
    private BigDecimal importe;

    @JsonbProperty("CodigoEmpleado")
    private String codigoEmpleado;

    @JsonbProperty("CodigoTipoMovimiento")
    private String codigoTipoMovimiento;

    // Constructores
    public TransaccionDTO() {
    }

    // Getters y Setters
    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getClaveCuenta() {
        return claveCuenta;
    }

    public void setClaveCuenta(String claveCuenta) {
        this.claveCuenta = claveCuenta;
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

    public String getCodigoTipoMovimiento() {
        return codigoTipoMovimiento;
    }

    public void setCodigoTipoMovimiento(String codigoTipoMovimiento) {
        this.codigoTipoMovimiento = codigoTipoMovimiento;
    }
}
