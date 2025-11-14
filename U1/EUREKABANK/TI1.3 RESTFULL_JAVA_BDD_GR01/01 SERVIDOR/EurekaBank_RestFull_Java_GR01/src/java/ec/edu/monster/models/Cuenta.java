package ec.edu.monster.models;

import jakarta.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa una cuenta bancaria
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class Cuenta {

    @JsonbProperty("Codigo")
    private String codigo;

    @JsonbProperty("CodigoMoneda")
    private String codigoMoneda;

    @JsonbProperty("CodigoSucursal")
    private String codigoSucursal;

    @JsonbProperty("CodigoEmpleadoCreador")
    private String codigoEmpleadoCreador;

    @JsonbProperty("CodigoCliente")
    private String codigoCliente;

    @JsonbProperty("Saldo")
    private BigDecimal saldo;

    @JsonbProperty("FechaCreacion")
    private Date fechaCreacion;

    @JsonbProperty("Estado")
    private String estado;

    @JsonbProperty("ContadorMovimientos")
    private int contadorMovimientos;

    @JsonbProperty("Clave")
    private String clave;

    // Constructores
    public Cuenta() {
    }

    public Cuenta(String codigo) {
        this.codigo = codigo;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public String getCodigoEmpleadoCreador() {
        return codigoEmpleadoCreador;
    }

    public void setCodigoEmpleadoCreador(String codigoEmpleadoCreador) {
        this.codigoEmpleadoCreador = codigoEmpleadoCreador;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getContadorMovimientos() {
        return contadorMovimientos;
    }

    public void setContadorMovimientos(int contadorMovimientos) {
        this.contadorMovimientos = contadorMovimientos;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
