package ec.edu.monster.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para mostrar detalles de movimientos en reportes
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MovimientoDetalleDTO implements Serializable {

    private String codigoCuenta;
    private int numeroMovimiento;
    private LocalDate fecha;
    private String tipoMovimiento;
    private String accion;
    private BigDecimal importe;
    private String empleadoNombre;
    private String cuentaReferencia;

    // Constructor vacío
    public MovimientoDetalleDTO() {
    }

    // Constructor completo
    public MovimientoDetalleDTO(String codigoCuenta, int numeroMovimiento, LocalDate fecha,
                                String tipoMovimiento, String accion, BigDecimal importe,
                                String empleadoNombre, String cuentaReferencia) {
        this.codigoCuenta = codigoCuenta;
        this.numeroMovimiento = numeroMovimiento;
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.accion = accion;
        this.importe = importe;
        this.empleadoNombre = empleadoNombre;
        this.cuentaReferencia = cuentaReferencia;
    }

    // Getters y Setters
    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public int getNumeroMovimiento() {
        return numeroMovimiento;
    }

    public void setNumeroMovimiento(int numeroMovimiento) {
        this.numeroMovimiento = numeroMovimiento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getEmpleadoNombre() {
        return empleadoNombre;
    }

    public void setEmpleadoNombre(String empleadoNombre) {
        this.empleadoNombre = empleadoNombre;
    }

    public String getCuentaReferencia() {
        return cuentaReferencia;
    }

    public void setCuentaReferencia(String cuentaReferencia) {
        this.cuentaReferencia = cuentaReferencia;
    }
}
