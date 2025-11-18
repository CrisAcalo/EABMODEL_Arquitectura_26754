package ec.edu.monster.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para transferencia de datos de Factura
 */
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
    
    public FacturaDTO() {
    }
    
    public FacturaDTO(Integer facturaId, String numeroFactura, String cedulaCliente, 
                      String nombreCliente, String formaPago, BigDecimal subtotal, 
                      BigDecimal descuento, BigDecimal total, String numeroCredito, 
                      LocalDateTime fechaEmision, List<DetalleFacturaDTO> detalles) {
        this.facturaId = facturaId;
        this.numeroFactura = numeroFactura;
        this.cedulaCliente = cedulaCliente;
        this.nombreCliente = nombreCliente;
        this.formaPago = formaPago;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
        this.numeroCredito = numeroCredito;
        this.fechaEmision = fechaEmision;
        this.detalles = detalles;
    }
    
    public Integer getFacturaId() {
        return facturaId;
    }
    
    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }
    
    public String getNumeroFactura() {
        return numeroFactura;
    }
    
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }
    
    public String getCedulaCliente() {
        return cedulaCliente;
    }
    
    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    public String getFormaPago() {
        return formaPago;
    }
    
    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
    
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
    
    public BigDecimal getDescuento() {
        return descuento;
    }
    
    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public String getNumeroCredito() {
        return numeroCredito;
    }
    
    public void setNumeroCredito(String numeroCredito) {
        this.numeroCredito = numeroCredito;
    }
    
    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }
    
    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
    public List<DetalleFacturaDTO> getDetalles() {
        return detalles;
    }
    
    public void setDetalles(List<DetalleFacturaDTO> detalles) {
        this.detalles = detalles;
    }
}
