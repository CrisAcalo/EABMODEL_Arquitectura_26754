package ec.edu.monster.ms_cuentas.dtos;

import ec.edu.monster.ms_cuentas.models.Cliente;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "registrarClienteRequest", namespace = "http://monster.edu.ec/ms-cuentas/ws")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class RegistrarClienteRequest {
    @XmlElement(name = "cliente", namespace = "http://monster.edu.ec/ms-cuentas/ws", required = true)
    private Cliente cliente;
}
