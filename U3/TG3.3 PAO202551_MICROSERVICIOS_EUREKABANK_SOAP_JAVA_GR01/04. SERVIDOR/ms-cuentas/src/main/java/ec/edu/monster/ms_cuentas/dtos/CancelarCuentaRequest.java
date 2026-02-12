package ec.edu.monster.ms_cuentas.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "cancelarCuentaRequest", namespace = "http://monster.edu.ec/ms-cuentas/ws")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class CancelarCuentaRequest {
    @XmlElement(required = true)
    private String codigoCuenta;
}
