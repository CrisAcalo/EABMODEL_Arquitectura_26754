package ec.edu.monster.service;

import ec.edu.monster.dao.FacturaDAO;
import ec.edu.monster.dao.ProductoDAO;
import ec.edu.monster.dao.DetalleFacturaDAO;
import ec.edu.monster.dto.FacturaDTO;
import ec.edu.monster.dto.DetalleFacturaDTO;
import ec.edu.monster.dto.banquito.ValidacionCreditoDTO;
import ec.edu.monster.dto.banquito.MontoMaximoCreditoDTO;
import ec.edu.monster.dto.banquito.RespuestaCreditoDTO;
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
 * Incluye integración con BanQuito para gestión de créditos
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
    
    @Inject
    private BanquitoClientService banquitoClient;
    
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
     * Si es crédito, valida con BanQuito antes de crear
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
        
        BigDecimal descuento = facturaDTO.getDescuento() != null ? facturaDTO.getDescuento() : BigDecimal.ZERO;
        BigDecimal total = subtotal.subtract(descuento);
        
        // Si es crédito, validar con BanQuito
        String numeroCredito = null;
        if ("CREDITO".equals(facturaDTO.getFormaPago())) {
            LOGGER.info("=== VALIDANDO CRÉDITO CON BANQUITO ===");
            
            // 1. Validar que el cliente es sujeto de crédito
            ValidacionCreditoDTO validacion = banquitoClient.validarSujetoCredito(facturaDTO.getCedulaCliente());
            if (!validacion.getEsValido()) {
                throw new RuntimeException("Cliente no es sujeto de crédito: " + validacion.getMensaje());
            }
            LOGGER.info("Cliente validado: " + validacion.getNombreCompleto());
            
            // 2. Verificar monto máximo
            MontoMaximoCreditoDTO montoMaximo = banquitoClient.obtenerMontoMaximoCredito(facturaDTO.getCedulaCliente());
            if (total.compareTo(montoMaximo.getMontoMaximo()) > 0) {
                throw new RuntimeException(
                    String.format("El monto de la factura ($%.2f) excede el monto máximo de crédito ($%.2f)", 
                    total, montoMaximo.getMontoMaximo())
                );
            }
            LOGGER.info("Monto validado: $" + total + " <= $" + montoMaximo.getMontoMaximo());
            
            // 3. Otorgar crédito en BanQuito (se puede configurar el número de cuotas)
            Integer numeroCuotas = 12; // Por defecto 12 cuotas, se puede parametrizar
            RespuestaCreditoDTO respuestaCredito = banquitoClient.otorgarCredito(
                facturaDTO.getCedulaCliente(), 
                total, 
                numeroCuotas
            );
            
            if (!respuestaCredito.getExito()) {
                throw new RuntimeException("Error al otorgar crédito en BanQuito: " + respuestaCredito.getMensaje());
            }
            
            numeroCredito = respuestaCredito.getNumeroCredito();
            LOGGER.info("Crédito otorgado: " + numeroCredito);
        }
        
        // Crear factura
        Factura factura = new Factura();
        factura.setNumeroFactura(facturaDAO.generarNumeroFactura());
        factura.setCedulaCliente(facturaDTO.getCedulaCliente());
        factura.setNombreCliente(facturaDTO.getNombreCliente());
        factura.setFormaPago(facturaDTO.getFormaPago());
        factura.setNumeroCredito(numeroCredito); // Será null si es efectivo
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
