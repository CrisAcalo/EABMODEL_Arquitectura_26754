package ec.edu.monster.ms_cuentas.dtos;

import ec.edu.monster.ms_cuentas.models.Cuenta;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import java.util.List;

@XmlRootElement(name = "listarCuentasActivasResponse", namespace = "http://monster.edu.ec/ms-cuentas/ws")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ListarCuentasActivasResponse {
    @XmlElement(name = "cuentas")
    private List<Cuenta> cuentas;
}
