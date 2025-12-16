package ec.edu.monster.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;

/**
 * DTO para actualizar una sucursal existente
 * 
 * @author EurekaBank
 */
public class ActualizarSucursalDTO {

    @JsonbProperty("nombre")
    private String nombre;

    @JsonbProperty("ciudad")
    private String ciudad;

    @JsonbProperty("direccion")
    private String direccion;

    @JsonbProperty("latitud")
    private BigDecimal latitud;

    @JsonbProperty("longitud")
    private BigDecimal longitud;

    public ActualizarSucursalDTO() {
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

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }
}
