package ec.edu.monster.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO para item de factura (producto + cantidad)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemFacturaDTO {
    private Integer productoId;
    private Integer cantidad;
}
