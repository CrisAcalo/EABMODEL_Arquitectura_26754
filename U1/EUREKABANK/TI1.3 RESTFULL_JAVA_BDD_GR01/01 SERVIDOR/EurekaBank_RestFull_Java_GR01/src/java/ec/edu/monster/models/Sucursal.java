package ec.edu.monster.models;

import jakarta.json.bind.annotation.JsonbProperty;

/**
 * Representa una sucursal del banco
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class Sucursal {

    @JsonbProperty("Codigo")
    private String codigo;

    @JsonbProperty("Nombre")
    private String nombre;

    @JsonbProperty("Ciudad")
    private String ciudad;

    @JsonbProperty("Direccion")
    private String direccion;

    @JsonbProperty("ContadorCuentas")
    private int contadorCuentas;

    // Constructores
    public Sucursal() {
    }

    public Sucursal(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
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

    public int getContadorCuentas() {
        return contadorCuentas;
    }

    public void setContadorCuentas(int contadorCuentas) {
        this.contadorCuentas = contadorCuentas;
    }
}
