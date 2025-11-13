package ec.edu.monster.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa una cuenta bancaria
 */
public class Cuenta implements Serializable {

    private String codigo;
    private String codigoMoneda;
    private String codigoSucursal;
    private String codigoEmpleadoCreador;
    private String codigoCliente;
    private BigDecimal saldo;
    private LocalDate fechaCreacion;
    private String estado;
    private int contadorMovimientos;
    private String clave;

    // Constructor vacío
    public Cuenta() {
    }

    // Constructor completo
    public Cuenta(String codigo, String codigoMoneda, String codigoSucursal,
                  String codigoEmpleadoCreador, String codigoCliente, BigDecimal saldo,
                  LocalDate fechaCreacion, String estado, int contadorMovimientos, String clave) {
        this.codigo = codigo;
        this.codigoMoneda = codigoMoneda;
        this.codigoSucursal = codigoSucursal;
        this.codigoEmpleadoCreador = codigoEmpleadoCreador;
        this.codigoCliente = codigoCliente;
        this.saldo = saldo;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.contadorMovimientos = contadorMovimientos;
        this.clave = clave;
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

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
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
