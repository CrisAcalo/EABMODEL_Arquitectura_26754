package ec.edu.monster.models;

import jakarta.json.bind.annotation.JsonbProperty;

/**
 * Representa un cliente del banco
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class Cliente {

    @JsonbProperty("Codigo")
    private String codigo;

    @JsonbProperty("Paterno")
    private String paterno;

    @JsonbProperty("Materno")
    private String materno;

    @JsonbProperty("Nombre")
    private String nombre;

    @JsonbProperty("Dni")
    private String dni;

    @JsonbProperty("Ciudad")
    private String ciudad;

    @JsonbProperty("Direccion")
    private String direccion;

    @JsonbProperty("Telefono")
    private String telefono;

    @JsonbProperty("Email")
    private String email;

    // Constructores
    public Cliente() {
    }

    public Cliente(String codigo, String paterno, String materno, String nombre) {
        this.codigo = codigo;
        this.paterno = paterno;
        this.materno = materno;
        this.nombre = nombre;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna el nombre completo del cliente
     *
     * @return Nombre completo
     */
    @JsonbProperty("NombreCompleto")
    public String getNombreCompleto() {
        return nombre + " " + paterno + " " + materno;
    }
}
