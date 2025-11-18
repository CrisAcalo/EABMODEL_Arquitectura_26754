package ec.edu.monster.dto;

import java.math.BigDecimal;

/**
 * DTO para transferencia de datos de DetalleFactura
 */
public class DetalleFacturaDTO {
    
    private Integer detalleId;
    private Integer productoId;
    private String codigoProducto;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    
    public DetalleFacturaDTO() {
    }
    
    public DetalleFacturaDTO(Integer detalleId, Integer productoId, String codigoProducto, 
                             String nombreProducto, Integer cantidad, BigDecimal precioUnitario, 
                             BigDecimal subtotal) {
        this.detalleId = detalleId;
        this.productoId = productoId;
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }
    
    public Integer getDetalleId() {
        return detalleId;
    }
    
    public void setDetalleId(Integer detalleId) {
        this.detalleId = detalleId;
    }
    
    public Integer getProductoId() {
        return productoId;
    }
    
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }
    
    public String getCodigoProducto() {
        return codigoProducto;
    }
    
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
    public String getNombreProducto() {
        return nombreProducto;
    }
    
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }
    
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
