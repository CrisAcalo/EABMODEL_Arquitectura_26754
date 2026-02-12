package ec.edu.monster.ms_transacciones.dtos;

import ec.edu.monster.ms_transacciones.utils.LocalDateAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@XmlRootElement(name = "listarMovimientosRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class ListarMovimientosRequest {
    private String codigoCuenta;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaInicio;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaFin;
}
