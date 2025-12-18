package ec.edu.monster.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para respuesta de Factura
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDTO {
    private Integer facturaId;
    private String numeroFactura;
    private String cedulaCliente;
    private String nombreCliente;
    private String formaPago;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal total;
    private String numeroCredito;
    private LocalDateTime fechaEmision;
    private List<DetalleFacturaDTO> detalles;
}
