package ec.edu.monster.ms_auth.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "listarVentanillasActivasRequest", namespace = "http://monster.edu.ec/ms-auth/ws")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ListarVentanillasActivasRequest {
}
