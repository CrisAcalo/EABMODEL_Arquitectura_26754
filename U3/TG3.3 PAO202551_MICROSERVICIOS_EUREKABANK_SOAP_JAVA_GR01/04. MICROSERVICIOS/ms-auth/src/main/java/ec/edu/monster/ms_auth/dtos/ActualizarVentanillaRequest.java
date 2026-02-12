package ec.edu.monster.ms_auth.dtos;

import ec.edu.monster.ms_auth.models.Ventanilla;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "actualizarVentanillaRequest", namespace = "http://monster.edu.ec/ms-auth/ws")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ActualizarVentanillaRequest {
    @XmlElement(required = true)
    private Ventanilla ventanilla;
}
