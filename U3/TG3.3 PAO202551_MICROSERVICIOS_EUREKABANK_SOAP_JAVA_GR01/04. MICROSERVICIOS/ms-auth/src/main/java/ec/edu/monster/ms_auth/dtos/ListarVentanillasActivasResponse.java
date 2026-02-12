package ec.edu.monster.ms_auth.dtos;

import ec.edu.monster.ms_auth.models.Ventanilla;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@XmlRootElement(name = "listarVentanillasActivasResponse", namespace = "http://monster.edu.ec/ms-auth/ws")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListarVentanillasActivasResponse {
    @XmlElement(name = "ventanillas")
    private List<Ventanilla> ventanillas = new ArrayList<>();
}
