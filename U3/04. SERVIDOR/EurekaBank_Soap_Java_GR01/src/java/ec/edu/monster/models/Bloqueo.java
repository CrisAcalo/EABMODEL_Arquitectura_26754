package ec.edu.monster.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Modelo para bloqueo de cuentas (concurrencia de ventanillas)
 */
public class Bloqueo implements Serializable {

    private String codigoCuenta;
    private String codigoVentanilla;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaExpiracion;
    private String estado;

    public Bloqueo() {
    }

    public Bloqueo(String codigoCuenta, String codigoVentanilla, LocalDateTime fechaInicio,
            LocalDateTime fechaExpiracion, String estado) {
        this.codigoCuenta = codigoCuenta;
        this.codigoVentanilla = codigoVentanilla;
        this.fechaInicio = fechaInicio;
        this.fechaExpiracion = fechaExpiracion;
        this.estado = estado;
    }

    // Getters y Setters
    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getCodigoVentanilla() {
        return codigoVentanilla;
    }

    public void setCodigoVentanilla(String codigoVentanilla) {
        this.codigoVentanilla = codigoVentanilla;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Verifica si el bloqueo ha expirado
     */
    public boolean haExpirado() {
        return LocalDateTime.now().isAfter(fechaExpiracion);
    }

    /**
     * Verifica si el bloqueo está activo
     */
    public boolean estaActivo() {
        return "ACTIVO".equals(estado) && !haExpirado();
    }
}
