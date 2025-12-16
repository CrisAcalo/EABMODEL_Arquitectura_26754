package ec.edu.monster.models;

import jakarta.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa un movimiento bancario en una cuenta
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class Movimiento {

    @JsonbProperty("CodigoCuenta")
    private String codigoCuenta;

    @JsonbProperty("Numero")
    private int numero;

    @JsonbProperty("Fecha")
    private Date fecha;

    @JsonbProperty("CodigoEmpleado")
    private String codigoEmpleado;

    @JsonbProperty("CodigoTipo")
    private String codigoTipo;

    @JsonbProperty("Importe")
    private BigDecimal importe;

    @JsonbProperty("CuentaReferencia")
    private String cuentaReferencia;

    // Constructores
    public Movimiento() {
    }

    public Movimiento(String codigoCuenta, int numero) {
        this.codigoCuenta = codigoCuenta;
        this.numero = numero;
    }

    // Getters y Setters
    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipo(String codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getCuentaReferencia() {
        return cuentaReferencia;
    }

    public void setCuentaReferencia(String cuentaReferencia) {
        this.cuentaReferencia = cuentaReferencia;
    }
}
