package ec.edu.monster.DTOs;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para calcular total de factura (sin generar)
 */
@Data
public class SolicitudCalculoDTO {
    private List<ItemFacturaDTO> items = new ArrayList<>();
}
