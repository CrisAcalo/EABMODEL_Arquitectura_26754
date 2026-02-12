package ec.edu.monster.ms_transacciones.dtos;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@XmlRootElement(name = "realizarDepositoRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class RealizarDepositoRequest {
    private String codigoCuenta;
    private String claveCuenta;
    private BigDecimal importe;
    private String codigoEmpleado;
}
