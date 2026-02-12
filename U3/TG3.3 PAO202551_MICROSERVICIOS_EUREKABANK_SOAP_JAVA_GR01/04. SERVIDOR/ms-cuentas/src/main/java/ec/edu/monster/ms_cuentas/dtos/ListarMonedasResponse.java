package ec.edu.monster.ms_cuentas.dtos;

import ec.edu.monster.ms_cuentas.models.Moneda;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import java.util.List;

@XmlRootElement(name = "listarMonedasResponse", namespace = "http://monster.edu.ec/ms-cuentas/ws")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ListarMonedasResponse {
    @XmlElement(name = "monedas")
    private List<Moneda> monedas;
}
