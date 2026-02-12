package ec.edu.monster.ms_auth.dtos;

import ec.edu.monster.ms_auth.models.Empleado;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "actualizarEmpleadoRequest", namespace = "http://monster.edu.ec/ms-auth/ws")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ActualizarEmpleadoRequest {
    @XmlElement(required = true)
    private Empleado empleado;
}
