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
    private int codigo;

    @JsonbProperty("Nombre")
    private String nombre;

    @JsonbProperty("Ciudad")
    private String ciudad;

    @JsonbProperty("Direccion")
    private String direccion;

    @JsonbProperty("ContadorCuentas")
    private int contadorCuentas;

    @JsonbProperty("Latitud")
    private java.math.BigDecimal latitud;

    @JsonbProperty("Longitud")
    private java.math.BigDecimal longitud;

    // Constructores
    public Sucursal() {
    }

    public Sucursal(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
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

    public java.math.BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(java.math.BigDecimal latitud) {
        this.latitud = latitud;
    }

    public java.math.BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(java.math.BigDecimal longitud) {
        this.longitud = longitud;
    }
}
