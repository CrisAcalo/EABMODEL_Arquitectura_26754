package ec.edu.monster.DTOs;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para solicitar generación de factura
 */
@Data
public class SolicitudFacturaDTO {
    private String cedulaCliente;
    private String nombreCliente;
    private String formaPago; // EFECTIVO o CREDITO
    private String numeroCredito; // Solo para CREDITO
    private List<ItemFacturaDTO> items = new ArrayList<>();
}
