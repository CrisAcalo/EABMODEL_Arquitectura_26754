package ec.edu.monster.models.dto;

import ec.edu.monster.models.Cuenta;
import java.io.Serializable;
import java.util.List;

/**
 * DTO para el estado de cuenta con información y movimientos recientes
 */
public class EstadoCuentaDTO implements Serializable {

    private Cuenta cuenta;
    private List<MovimientoDetalleDTO> ultimosMovimientos;

    // Constructor vacío
    public EstadoCuentaDTO() {
    }

    // Constructor completo
    public EstadoCuentaDTO(Cuenta cuenta, List<MovimientoDetalleDTO> ultimosMovimientos) {
        this.cuenta = cuenta;
        this.ultimosMovimientos = ultimosMovimientos;
    }

    // Getters y Setters
    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public List<MovimientoDetalleDTO> getUltimosMovimientos() {
        return ultimosMovimientos;
    }

    public void setUltimosMovimientos(List<MovimientoDetalleDTO> ultimosMovimientos) {
        this.ultimosMovimientos = ultimosMovimientos;
    }
}
