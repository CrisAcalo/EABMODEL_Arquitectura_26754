package ec.edu.monster.models;

import java.io.Serializable;

/**
 * Entidad que representa un tipo de movimiento bancario
 */
public class TipoMovimiento implements Serializable {

    private String codigo;
    private String descripcion;
    private String accion;
    private String estado;

    // Constructor vacío
    public TipoMovimiento() {
    }

    // Constructor completo
    public TipoMovimiento(String codigo, String descripcion, String accion, String estado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.accion = accion;
        this.estado = estado;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
