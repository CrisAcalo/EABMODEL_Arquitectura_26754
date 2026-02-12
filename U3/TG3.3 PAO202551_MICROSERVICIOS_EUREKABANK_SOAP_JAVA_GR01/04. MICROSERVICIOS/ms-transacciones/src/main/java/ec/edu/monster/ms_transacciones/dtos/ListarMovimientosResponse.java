package ec.edu.monster.ms_transacciones.dtos;

import ec.edu.monster.ms_transacciones.models.Movimiento;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@XmlRootElement(name = "listarMovimientosResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class ListarMovimientosResponse {
    @XmlElement(name = "movimiento")
    private List<Movimiento> movimientos;
}
