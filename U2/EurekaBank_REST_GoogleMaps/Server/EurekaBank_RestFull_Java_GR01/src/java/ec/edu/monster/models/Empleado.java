package ec.edu.monster.models;

import jakarta.json.bind.annotation.JsonbProperty;

/**
 * Representa un empleado del banco
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class Empleado {

    @JsonbProperty("Codigo")
    private String codigo;

    @JsonbProperty("Paterno")
    private String paterno;

    @JsonbProperty("Materno")
    private String materno;

    @JsonbProperty("Nombre")
    private String nombre;

    @JsonbProperty("Ciudad")
    private String ciudad;

    @JsonbProperty("Direccion")
    private String direccion;

    @JsonbProperty("Usuario")
    private String usuario;

    @JsonbProperty("Clave")
    private String clave;

    // Constructores
    public Empleado() {
    }

    public Empleado(String codigo, String usuario) {
        this.codigo = codigo;
        this.usuario = usuario;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * Retorna el nombre completo del empleado
     *
     * @return Nombre completo
     */
    @JsonbProperty("NombreCompleto")
    public String getNombreCompleto() {
        return nombre + " " + paterno + " " + materno;
    }
}
