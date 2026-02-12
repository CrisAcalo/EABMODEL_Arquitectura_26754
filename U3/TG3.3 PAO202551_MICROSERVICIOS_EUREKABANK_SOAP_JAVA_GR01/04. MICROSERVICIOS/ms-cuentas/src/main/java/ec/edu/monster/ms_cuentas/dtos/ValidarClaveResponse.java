package ec.edu.monster.ms_cuentas.dtos;

import ec.edu.monster.ms_cuentas.dtos.RespuestaDTO;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "validarClaveResponse", namespace = "http://monster.edu.ec/ms-cuentas/ws")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ValidarClaveResponse {
    @XmlElement(name = "return")
    private RespuestaDTO respuesta;
}
