package ec.edu.monster.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa un movimiento bancario
 */
public class Movimiento implements Serializable {

    private String codigoCuenta;
    private int numero;
    private LocalDate fecha;
    private String codigoEmpleado;
    private String codigoTipo;
    private BigDecimal importe;
    private String cuentaReferencia;

    // Constructor vacío
    public Movimiento() {
    }

    // Constructor completo
    public Movimiento(String codigoCuenta, int numero, LocalDate fecha,
                      String codigoEmpleado, String codigoTipo, BigDecimal importe,
                      String cuentaReferencia) {
        this.codigoCuenta = codigoCuenta;
        this.numero = numero;
        this.fecha = fecha;
        this.codigoEmpleado = codigoEmpleado;
        this.codigoTipo = codigoTipo;
        this.importe = importe;
        this.cuentaReferencia = cuentaReferencia;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
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
