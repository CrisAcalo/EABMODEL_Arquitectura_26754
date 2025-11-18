package ec.edu.monster.service;

import ec.edu.monster.dao.FacturaDAO;
import ec.edu.monster.dao.ProductoDAO;
import ec.edu.monster.dao.DetalleFacturaDAO;
import ec.edu.monster.dto.FacturaDTO;
import ec.edu.monster.dto.DetalleFacturaDTO;
import ec.edu.monster.model.Factura;
import ec.edu.monster.model.Producto;
import ec.edu.monster.model.DetalleFactura;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.logging.Logger;

/**
 * Servicio para la lógica de negocio de Facturación
 * El sistema es independiente - para pagos a CREDITO, el cliente debe
 * obtener el numeroCredito directamente desde BanQuito antes de crear la factura
 */
@Stateless
public class FacturacionService {

    private static final Logger LOGGER = Logger.getLogger(FacturacionService.class.getName());

    @Inject
    private FacturaDAO facturaDAO;

    @Inject
    private ProductoDAO productoDAO;

    @Inject
    private DetalleFacturaDAO detalleFacturaDAO;
    
    /**
     * Convertir DetalleFactura a DTO
     */
    private DetalleFacturaDTO convertirDetalleADTO(DetalleFactura detalle) {
        DetalleFacturaDTO dto = new DetalleFacturaDTO();
        dto.setDetalleId(detalle.getDetalleId());
        dto.setProductoId(detalle.getProducto().getProductoId());
        dto.setCodigoProducto(detalle.getProducto().getCodigo());
        dto.setNombreProducto(detalle.getProducto().getNombre());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }
    
    /**
     * Convertir Factura a DTO
     */
    private FacturaDTO convertirFacturaADTO(Factura factura) {
        if (factura == null) return null;
        
        FacturaDTO dto = new FacturaDTO();
        dto.setFacturaId(factura.getFacturaId());
        dto.setNumeroFactura(factura.getNumeroFactura());
        dto.setCedulaCliente(factura.getCedulaCliente());
        dto.setNombreCliente(factura.getNombreCliente());
        dto.setFormaPago(factura.getFormaPago());
        dto.setSubtotal(factura.getSubtotal());
        dto.setDescuento(factura.getDescuento());
        dto.setTotal(factura.getTotal());
        dto.setNumeroCredito(factura.getNumeroCredito());
        dto.setFechaEmision(factura.getFechaEmision());
        
        // Convertir detalles
        if (factura.getDetalles() != null && !factura.getDetalles().isEmpty()) {
            List<DetalleFacturaDTO> detallesDTO = factura.getDetalles().stream()
                    .map(this::convertirDetalleADTO)
                    .collect(Collectors.toList());
            dto.setDetalles(detallesDTO);
        }
        
        return dto;
    }
    
    /**
     * Obtener todas las facturas
     */
    public List<FacturaDTO> obtenerTodas() {
        return facturaDAO.findAll().stream()
                .map(this::convertirFacturaADTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtener factura por ID
     */
    public FacturaDTO obtenerPorId(Integer id) {
        Factura factura = facturaDAO.findByIdWithDetalles(id);
        return convertirFacturaADTO(factura);
    }
    
    /**
     * Obtener factura por número
     */
    public FacturaDTO obtenerPorNumero(String numeroFactura) {
        Factura factura = facturaDAO.findByNumeroFactura(numeroFactura);
        if (factura != null) {
            // Cargar detalles manualmente
            factura = facturaDAO.findByIdWithDetalles(factura.getFacturaId());
        }
        return convertirFacturaADTO(factura);
    }
    
    /**
     * Obtener facturas por cédula de cliente
     */
    public List<FacturaDTO> obtenerPorCliente(String cedula) {
        return facturaDAO.findByCedulaCliente(cedula).stream()
                .map(this::convertirFacturaADTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Crear nueva factura
     *
     * FORMAS DE PAGO:
     * - EFECTIVO: Se aplica automáticamente un 33% de descuento sobre el subtotal
     * - CREDITO: Sin descuento. El cliente debe proporcionar un numeroCredito válido
     *            obtenido previamente desde el sistema BanQuito
     */
    @Transactional
    public FacturaDTO crearFactura(FacturaDTO facturaDTO) {
        // Validar que hay detalles
        if (facturaDTO.getDetalles() == null || facturaDTO.getDetalles().isEmpty()) {
            throw new RuntimeException("La factura debe tener al menos un detalle");
        }
        
        // Calcular totales primero
        BigDecimal subtotal = BigDecimal.ZERO;
        List<DetalleFactura> detalles = new ArrayList<>();
        
        // Procesar cada detalle y calcular subtotal
        for (DetalleFacturaDTO detalleDTO : facturaDTO.getDetalles()) {
            Producto producto = productoDAO.findById(detalleDTO.getProductoId());
            
            if (producto == null) {
                throw new RuntimeException("Producto no encontrado: " + detalleDTO.getProductoId());
            }
            
            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para producto: " + producto.getNombre());
            }
            
            BigDecimal subtotalDetalle = producto.getPrecio().multiply(new BigDecimal(detalleDTO.getCantidad()));
            subtotal = subtotal.add(subtotalDetalle);
        }

        // Calcular descuento automáticamente según forma de pago
        BigDecimal descuento;
        if ("EFECTIVO".equals(facturaDTO.getFormaPago())) {
            // EFECTIVO: 33% de descuento sobre el subtotal
            descuento = subtotal.multiply(new BigDecimal("0.33"));
            LOGGER.info("Pago en EFECTIVO - Descuento 33% aplicado: $" + descuento);
        } else if ("CREDITO".equals(facturaDTO.getFormaPago())) {
            // CREDITO: Sin descuento, pero requiere numeroCredito
            descuento = BigDecimal.ZERO;

            // Validar que el cliente proporcione el número de crédito
            if (facturaDTO.getNumeroCredito() == null || facturaDTO.getNumeroCredito().trim().isEmpty()) {
                throw new RuntimeException(
                    "El número de crédito es requerido para pagos a CREDITO. " +
                    "Debe obtenerlo desde el servicio de BanQuito antes de crear la factura."
                );
            }

            LOGGER.info("Pago a CREDITO - Sin descuento - Número de crédito: " + facturaDTO.getNumeroCredito());
        } else {
            throw new RuntimeException("Forma de pago no válida. Debe ser EFECTIVO o CREDITO");
        }

        BigDecimal total = subtotal.subtract(descuento);
        
        // Crear factura
        Factura factura = new Factura();
        factura.setNumeroFactura(facturaDAO.generarNumeroFactura());
        factura.setCedulaCliente(facturaDTO.getCedulaCliente());
        factura.setNombreCliente(facturaDTO.getNombreCliente());
        factura.setFormaPago(facturaDTO.getFormaPago());
        factura.setNumeroCredito(facturaDTO.getNumeroCredito()); // null para EFECTIVO, requerido para CREDITO
        factura.setSubtotal(subtotal);
        factura.setDescuento(descuento);
        factura.setTotal(total);
        
        // Crear detalles
        for (DetalleFacturaDTO detalleDTO : facturaDTO.getDetalles()) {
            Producto producto = productoDAO.findById(detalleDTO.getProductoId());
            
            // Crear detalle
            DetalleFactura detalle = new DetalleFactura();
            detalle.setFactura(factura);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio().multiply(new BigDecimal(detalleDTO.getCantidad())));
            
            detalles.add(detalle);
            
            // Reducir stock
            productoDAO.reducirStock(producto.getProductoId(), detalleDTO.getCantidad());
        }
        
        factura.setDetalles(detalles);
        
        // Guardar factura (cascade guardará los detalles)
        Factura guardada = facturaDAO.save(factura);
        
        LOGGER.info("Factura creada exitosamente: " + guardada.getNumeroFactura());

        // Convertir factura guardada a DTO y retornar
        return convertirFacturaADTO(facturaDAO.findByIdWithDetalles(guardada.getFacturaId()));
    }
    
    /**
     * Obtener facturas a crédito
     */
    public List<FacturaDTO> obtenerFacturasCredito() {
        return facturaDAO.findFacturasCredito().stream()
                .map(this::convertirFacturaADTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Buscar factura por número de crédito
     */
    public FacturaDTO obtenerPorNumeroCredito(String numeroCredito) {
        Factura factura = facturaDAO.findByNumeroCredito(numeroCredito);
        if (factura != null) {
            factura = facturaDAO.findByIdWithDetalles(factura.getFacturaId());
        }
        return convertirFacturaADTO(factura);
    }
}
