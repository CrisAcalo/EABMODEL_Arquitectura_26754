package ec.edu.monster.models;

import jakarta.json.bind.annotation.JsonbProperty;

/**
 * Representa un tipo de movimiento bancario
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class TipoMovimiento {

    @JsonbProperty("Codigo")
    private String codigo;

    @JsonbProperty("Descripcion")
    private String descripcion;

    @JsonbProperty("Accion")
    private String accion; // INGRESO o SALIDA

    @JsonbProperty("Estado")
    private String estado;

    // Constructores
    public TipoMovimiento() {
    }

    public TipoMovimiento(String codigo, String descripcion, String accion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.accion = accion;
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
