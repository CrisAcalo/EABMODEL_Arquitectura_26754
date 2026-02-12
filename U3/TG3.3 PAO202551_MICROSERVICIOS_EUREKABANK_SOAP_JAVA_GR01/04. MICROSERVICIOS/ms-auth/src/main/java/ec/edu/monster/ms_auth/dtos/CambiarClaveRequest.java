package ec.edu.monster.ms_auth.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "cambiarClaveRequest", namespace = "http://monster.edu.ec/ms-auth/ws")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class CambiarClaveRequest {
    @XmlElement(required = true)
    private String codigo;
    @XmlElement(required = true)
    private String claveActual;
    @XmlElement(required = true)
    private String claveNueva;
}
