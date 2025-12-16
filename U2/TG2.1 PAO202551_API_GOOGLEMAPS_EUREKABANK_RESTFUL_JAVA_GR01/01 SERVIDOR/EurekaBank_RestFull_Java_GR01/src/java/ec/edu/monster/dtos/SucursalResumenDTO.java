package ec.edu.monster.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;

/**
 * DTO para resumen de lista de sucursales
 * 
 * @author EurekaBank
 */
public class SucursalResumenDTO {

    @JsonbProperty("codigo")
    private int codigo;

    @JsonbProperty("nombre")
    private String nombre;

    @JsonbProperty("ciudad")
    private String ciudad;

    @JsonbProperty("direccion")
    private String direccion;

    @JsonbProperty("contadorCuentas")
    private int contadorCuentas;

    @JsonbProperty("latitud")
    private BigDecimal latitud;

    @JsonbProperty("longitud")
    private BigDecimal longitud;

    @JsonbProperty("tieneCoordenadas")
    private boolean tieneCoordenadas;

    @JsonbProperty("direccionCompleta")
    private String direccionCompleta;

    public SucursalResumenDTO() {
    }

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

    public int getContadorCuentas() {
        return contadorCuentas;
    }

    public void setContadorCuentas(int contadorCuentas) {
        this.contadorCuentas = contadorCuentas;
    }

    public boolean isTieneCoordenadas() {
        return tieneCoordenadas;
    }

    public void setTieneCoordenadas(boolean tieneCoordenadas) {
        this.tieneCoordenadas = tieneCoordenadas;
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

    public String getDireccionCompleta() {
        return direccionCompleta;
    }

    public void setDireccionCompleta(String direccionCompleta) {
        this.direccionCompleta = direccionCompleta;
    }
}
