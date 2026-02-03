package ec.edu.monster.models;

import java.io.Serializable;

/**
 * Modelo para la entidad Ventanilla
 */
public class Ventanilla implements Serializable {

    private String codigo;
    private String nombre;
    private String codigoEmpleado;
    private String estado;

    public Ventanilla() {
    }

    public Ventanilla(String codigo, String nombre, String codigoEmpleado, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.codigoEmpleado = codigoEmpleado;
        this.estado = estado;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
