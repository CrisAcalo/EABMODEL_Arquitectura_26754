package ec.edu.monster.ms_transacciones.dtos;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "realizarDepositoResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class RealizarDepositoResponse {
    @XmlElement(name = "return")
    private RespuestaDTO result;
}
