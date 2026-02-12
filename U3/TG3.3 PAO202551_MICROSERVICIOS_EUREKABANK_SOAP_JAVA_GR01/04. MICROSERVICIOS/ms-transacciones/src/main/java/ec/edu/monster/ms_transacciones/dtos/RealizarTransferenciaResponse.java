package ec.edu.monster.ms_transacciones.dtos;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "realizarTransferenciaResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class RealizarTransferenciaResponse {
    @XmlElement(name = "return")
    private RespuestaDTO result;
}
