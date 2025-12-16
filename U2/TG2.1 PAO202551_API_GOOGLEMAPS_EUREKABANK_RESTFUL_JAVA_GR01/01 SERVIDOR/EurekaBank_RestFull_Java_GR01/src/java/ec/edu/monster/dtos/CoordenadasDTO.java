package ec.edu.monster.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;

/**
 * DTO para coordenadas geográficas
 * 
 * @author EurekaBank
 */
public class CoordenadasDTO {

    @JsonbProperty("latitud")
    private BigDecimal latitud;

    @JsonbProperty("longitud")
    private BigDecimal longitud;

    public CoordenadasDTO() {
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
