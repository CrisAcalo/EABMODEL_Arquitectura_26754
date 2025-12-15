package ec.edu.monster.Controllers;

import ec.edu.monster.DTOs.*;
import ec.edu.monster.Services.FacturacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para facturación
 */
@RestController
@RequestMapping("/api/facturas")
@Tag(name = "Facturación", description = "Gestión de facturas de electrodomésticos")
public class FacturacionController {

    private final FacturacionService facturacionService;

    public FacturacionController(FacturacionService facturacionService) {
        this.facturacionService = facturacionService;
    }

    /**
     * Calcular total de factura SIN generarla
     * Útil para conocer el monto antes de solicitar crédito en BanQuito
     */
    @PostMapping("/calcular")
    @Operation(summary = "Calcular total", description = "Calcula el total de una factura SIN generarla. Útil para conocer el monto antes de solicitar crédito en BanQuito.")
    public ResponseEntity<CalculoFacturaDTO> calcularTotalFactura(@RequestBody SolicitudCalculoDTO solicitud) {
        CalculoFacturaDTO resultado = facturacionService.calcularTotalFactura(solicitud);
        if (resultado.getExitoso()) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.badRequest().body(resultado);
    }

    /**
     * Generar factura
     * EFECTIVO: 33% descuento
     * CREDITO: Sin descuento (requiere NumeroCredito)
     */
    @PostMapping
    @Operation(summary = "Generar factura", description = "Genera una factura. EFECTIVO: 33% descuento. CREDITO: sin descuento, requiere NumeroCredito de BanQuito.")
    public ResponseEntity<?> generarFactura(@RequestBody SolicitudFacturaDTO solicitud) {
        try {
            FacturaDTO factura = facturacionService.generarFactura(solicitud);
            return ResponseEntity.status(HttpStatus.CREATED).body(factura);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(RespuestaDTO.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RespuestaDTO.error("Error al generar factura: " + e.getMessage()));
        }
    }

    /**
     * Obtener facturas por cédula de cliente
     */
    @GetMapping("/cliente/{cedula}")
    @Operation(summary = "Facturas por cliente", description = "Lista todas las facturas de un cliente por su cédula")
    public ResponseEntity<List<FacturaDTO>> obtenerFacturasPorCliente(
            @Parameter(description = "Cédula del cliente") @PathVariable String cedula) {
        return ResponseEntity.ok(facturacionService.obtenerFacturasPorCliente(cedula));
    }

    /**
     * Obtener factura por número
     */
    @GetMapping("/{numeroFactura}")
    @Operation(summary = "Obtener por número", description = "Busca una factura por su número")
    public ResponseEntity<FacturaDTO> obtenerFacturaPorNumero(
            @Parameter(description = "Número de factura") @PathVariable String numeroFactura) {
        FacturaDTO factura = facturacionService.obtenerFacturaPorNumero(numeroFactura);
        if (factura != null) {
            return ResponseEntity.ok(factura);
        }
        return ResponseEntity.notFound().build();
    }
}
