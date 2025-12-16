package ec.edu.monster.models;

import jakarta.json.bind.annotation.JsonbProperty;

/**
 * Representa una moneda en el sistema
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class Moneda {

    @JsonbProperty("Codigo")
    private String codigo;

    @JsonbProperty("Descripcion")
    private String descripcion;

    // Constructores
    public Moneda() {
    }

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
