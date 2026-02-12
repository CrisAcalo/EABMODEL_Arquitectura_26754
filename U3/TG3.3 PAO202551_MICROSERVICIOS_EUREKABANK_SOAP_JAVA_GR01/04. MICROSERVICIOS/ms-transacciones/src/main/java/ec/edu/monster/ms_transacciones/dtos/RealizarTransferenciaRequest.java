package ec.edu.monster.ms_transacciones.dtos;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@XmlRootElement(name = "realizarTransferenciaRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class RealizarTransferenciaRequest {
    private String cuentaOrigen;
    private String cuentaDestino;
    private String claveCuentaOrigen;
    private BigDecimal importe;
    private String codigoEmpleado;
}
