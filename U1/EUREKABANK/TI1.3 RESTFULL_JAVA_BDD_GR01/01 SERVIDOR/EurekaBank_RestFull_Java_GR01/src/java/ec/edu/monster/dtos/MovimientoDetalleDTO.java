package ec.edu.monster.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO para mostrar el detalle de movimientos con información completa
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class MovimientoDetalleDTO {

    @JsonbProperty("CodigoCuenta")
    private String codigoCuenta;

    @JsonbProperty("NumeroMovimiento")
    private int numeroMovimiento;

    @JsonbProperty("Fecha")
    private Date fecha;

    @JsonbProperty("TipoMovimiento")
    private String tipoMovimiento;

    @JsonbProperty("Accion")
    private String accion;

    @JsonbProperty("Importe")
    private BigDecimal importe;

    @JsonbProperty("EmpleadoNombre")
    private String empleadoNombre;

    @JsonbProperty("CuentaReferencia")
    private String cuentaReferencia;

    // Constructores
    public MovimientoDetalleDTO() {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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
