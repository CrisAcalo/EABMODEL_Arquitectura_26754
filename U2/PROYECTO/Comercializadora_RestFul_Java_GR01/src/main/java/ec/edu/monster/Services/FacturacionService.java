package ec.edu.monster.Services;

import ec.edu.monster.Constants.AppConfig;
import ec.edu.monster.Constants.ErrorMessages;
import ec.edu.monster.DTOs.*;
import ec.edu.monster.Models.DetalleFactura;
import ec.edu.monster.Models.Factura;
import ec.edu.monster.Models.Producto;
import ec.edu.monster.Repositories.FacturaRepository;
import ec.edu.monster.Repositories.ProductoRepository;
import ec.edu.monster.Validators.FacturaValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para facturación
 */
@Service
@Transactional
public class FacturacionService {

    private final ProductoRepository productoRepository;
    private final FacturaRepository facturaRepository;
    private final AppConfig appConfig;

    public FacturacionService(ProductoRepository productoRepository,
            FacturaRepository facturaRepository,
            AppConfig appConfig) {
        this.productoRepository = productoRepository;
        this.facturaRepository = facturaRepository;
        this.appConfig = appConfig;
    }

    /**
     * Calcular total de factura SIN generarla
     * Útil para conocer el monto antes de solicitar crédito en BanQuito
     */
    @Transactional(readOnly = true)
    public CalculoFacturaDTO calcularTotalFactura(SolicitudCalculoDTO solicitud) {
        // Validar usando FacturaValidator
        Optional<String> error = FacturaValidator.validarSolicitudCalculo(solicitud);
        if (error.isPresent()) {
            return errorCalculo(error.get());
        }

        try {
            BigDecimal total = BigDecimal.ZERO;
            List<DetalleCalculoDTO> detalles = new ArrayList<>();

            for (ItemFacturaDTO item : solicitud.getItems()) {
                var productoOpt = productoRepository.findById(item.getProductoId());
                if (productoOpt.isEmpty()) {
                    return errorCalculo(String.format(
                            ErrorMessages.PRODUCTO_NO_ENCONTRADO + " con ID %d", item.getProductoId()));
                }

                Producto producto = productoOpt.get();

                if (producto.getStock() < item.getCantidad()) {
                    return errorCalculo(String.format(
                            ErrorMessages.STOCK_INSUFICIENTE,
                            producto.getNombre(), producto.getStock(), item.getCantidad()));
                }

                BigDecimal subtotalItem = producto.getPrecio()
                        .multiply(BigDecimal.valueOf(item.getCantidad()));
                total = total.add(subtotalItem);

                detalles.add(new DetalleCalculoDTO(
                        item.getProductoId(),
                        producto.getNombre(),
                        item.getCantidad(),
                        producto.getPrecio(),
                        subtotalItem));
            }

            return new CalculoFacturaDTO(true, ErrorMessages.CALCULO_EXITOSO, total, detalles);

        } catch (Exception ex) {
            return errorCalculo(String.format(ErrorMessages.ERROR_INTERNO, ex.getMessage()));
        }
    }

    /**
     * Generar factura
     * EFECTIVO: 33% descuento
     * CREDITO: Sin descuento (requiere NumeroCredito de BanQuito)
     */
    public FacturaDTO generarFactura(SolicitudFacturaDTO solicitud) {
        // Validar usando FacturaValidator
        Optional<String> error = FacturaValidator.validarSolicitudFactura(solicitud);
        if (error.isPresent()) {
            throw new IllegalArgumentException(error.get());
        }

        String formaPago = solicitud.getFormaPago().toUpperCase();

        // Crear factura
        Factura factura = new Factura();
        factura.setNumeroFactura(generarNumeroFactura());
        factura.setCedulaCliente(solicitud.getCedulaCliente());
        factura.setNombreCliente(solicitud.getNombreCliente());
        factura.setFormaPago(formaPago);
        factura.setNumeroCredito(AppConfig.esCredito(formaPago) ? solicitud.getNumeroCredito() : null);

        BigDecimal subtotal = BigDecimal.ZERO;

        // Procesar items
        for (ItemFacturaDTO item : solicitud.getItems()) {
            var productoOpt = productoRepository.findById(item.getProductoId());
            if (productoOpt.isEmpty()) {
                throw new IllegalArgumentException(
                        String.format(ErrorMessages.PRODUCTO_NO_ENCONTRADO + " con ID %d", item.getProductoId()));
            }

            Producto producto = productoOpt.get();

            if (producto.getStock() < item.getCantidad()) {
                throw new IllegalArgumentException(String.format(
                        ErrorMessages.STOCK_INSUFICIENTE,
                        producto.getNombre(), producto.getStock(), item.getCantidad()));
            }

            BigDecimal subtotalItem = producto.getPrecio()
                    .multiply(BigDecimal.valueOf(item.getCantidad()));
            subtotal = subtotal.add(subtotalItem);

            DetalleFactura detalle = new DetalleFactura();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(subtotalItem);

            factura.addDetalle(detalle);

            // Reducir stock
            productoRepository.reducirStock(item.getProductoId(), item.getCantidad());
        }

        // Calcular totales según forma de pago
        factura.setSubtotal(subtotal);
        if (AppConfig.esEfectivo(formaPago)) {
            // EFECTIVO: 33% de descuento
            BigDecimal descuento = subtotal.multiply(appConfig.getDescuentoEfectivo())
                    .setScale(2, RoundingMode.HALF_UP);
            factura.setDescuento(descuento);
            factura.setTotal(subtotal.subtract(descuento));
        } else {
            // CREDITO: Sin descuento
            factura.setDescuento(BigDecimal.ZERO);
            factura.setTotal(subtotal);
        }

        // Guardar factura
        Factura facturaGuardada = facturaRepository.save(factura);

        return mapearFacturaADTO(facturaGuardada);
    }

    /**
     * Obtener facturas por cédula de cliente
     */
    @Transactional(readOnly = true)
    public List<FacturaDTO> obtenerFacturasPorCliente(String cedula) {
        return facturaRepository.findByCedulaClienteOrderByFechaEmisionDesc(cedula)
                .stream()
                .map(this::mapearFacturaADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener factura por número
     */
    @Transactional(readOnly = true)
    public FacturaDTO obtenerFacturaPorNumero(String numeroFactura) {
        return facturaRepository.findByNumeroFactura(numeroFactura)
                .map(this::mapearFacturaADTO)
                .orElse(null);
    }

    // Generar número de factura único
    private String generarNumeroFactura() {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern(AppConfig.FORMATO_FECHA));
        Integer maxId = facturaRepository.findMaxFacturaId();
        int siguiente = (maxId != null ? maxId : 0) + 1;
        return String.format("%s-%s-%03d", AppConfig.PREFIJO_FACTURA, fecha, siguiente);
    }

    // Mapear Factura a DTO
    private FacturaDTO mapearFacturaADTO(Factura factura) {
        List<DetalleFacturaDTO> detallesDTO = factura.getDetalles().stream()
                .map(d -> new DetalleFacturaDTO(
                        d.getProducto().getProductoId(),
                        d.getProducto().getNombre(),
                        d.getCantidad(),
                        d.getPrecioUnitario(),
                        d.getSubtotal()))
                .collect(Collectors.toList());

        return new FacturaDTO(
                factura.getFacturaId(),
                factura.getNumeroFactura(),
                factura.getCedulaCliente(),
                factura.getNombreCliente(),
                factura.getFormaPago(),
                factura.getSubtotal(),
                factura.getDescuento(),
                factura.getTotal(),
                factura.getNumeroCredito(),
                factura.getFechaEmision(),
                detallesDTO);
    }

    // Helper para error de cálculo
    private CalculoFacturaDTO errorCalculo(String mensaje) {
        return new CalculoFacturaDTO(false, mensaje, BigDecimal.ZERO, new ArrayList<>());
    }
}
