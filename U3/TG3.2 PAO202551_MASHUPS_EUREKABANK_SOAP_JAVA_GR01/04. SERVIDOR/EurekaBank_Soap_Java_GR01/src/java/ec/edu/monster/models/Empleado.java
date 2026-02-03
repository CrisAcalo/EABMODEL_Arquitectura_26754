package ec.edu.monster.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Entidad que representa un empleado
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Empleado implements Serializable {

    private String codigo;
    private String paterno;
    private String materno;
    private String nombre;
    private String ciudad;
    private String direccion;
    private String usuario;
    private String clave;

    // Constructor vacío
    public Empleado() {
    }

    // Constructor completo
    public Empleado(String codigo, String paterno, String materno, String nombre,
                    String ciudad, String direccion, String usuario, String clave) {
        this.codigo = codigo;
        this.paterno = paterno;
        this.materno = materno;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.usuario = usuario;
        this.clave = clave;
    }

    // Propiedad calculada: Nombre Completo
    public String getNombreCompleto() {
        return paterno + " " + materno + " " + nombre;
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
}
