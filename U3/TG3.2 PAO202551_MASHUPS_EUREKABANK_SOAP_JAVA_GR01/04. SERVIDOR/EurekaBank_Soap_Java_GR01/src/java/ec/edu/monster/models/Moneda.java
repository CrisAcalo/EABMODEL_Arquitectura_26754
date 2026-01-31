package ec.edu.monster.models;

import java.io.Serializable;

/**
 * Entidad que representa un tipo de moneda
 */
public class Moneda implements Serializable {

    private String codigo;
    private String descripcion;

    // Constructor vacío
    public Moneda() {
    }

    // Constructor completo
    public Moneda(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
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
}
