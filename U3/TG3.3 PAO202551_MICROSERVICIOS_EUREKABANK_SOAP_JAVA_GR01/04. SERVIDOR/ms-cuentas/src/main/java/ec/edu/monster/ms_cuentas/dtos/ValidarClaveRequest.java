package ec.edu.monster.ms_cuentas.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "validarClaveRequest", namespace = "http://monster.edu.ec/ms-cuentas/ws")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ValidarClaveRequest {
    @XmlElement(required = true)
    private String codigoCuenta;
    @XmlElement(required = true)
    private String clave;
}
