package ec.edu.monster.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO para operaciones de depósito y retiro
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TransaccionDTO implements Serializable {

    private String codigoCuenta;
    private String claveCuenta;
    private BigDecimal importe;
    private String codigoEmpleado;
    private String codigoTipoMovimiento;

    // Constructor vacío
    public TransaccionDTO() {
    }

    // Constructor completo
    public TransaccionDTO(String codigoCuenta, String claveCuenta, BigDecimal importe,
                          String codigoEmpleado, String codigoTipoMovimiento) {
        this.codigoCuenta = codigoCuenta;
        this.claveCuenta = claveCuenta;
        this.importe = importe;
        this.codigoEmpleado = codigoEmpleado;
        this.codigoTipoMovimiento = codigoTipoMovimiento;
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
