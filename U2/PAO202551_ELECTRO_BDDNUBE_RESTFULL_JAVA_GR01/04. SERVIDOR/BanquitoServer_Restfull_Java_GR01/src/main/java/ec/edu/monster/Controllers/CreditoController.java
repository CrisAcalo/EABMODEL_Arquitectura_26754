package ec.edu.monster.Controllers;

import ec.edu.monster.Constants.ErrorMessages;
import ec.edu.monster.DTOs.*;
import ec.edu.monster.Models.CuotaAmortizacion;
import ec.edu.monster.Services.CreditoService;
import ec.edu.monster.Services.CreditoValidacionService;
import ec.edu.monster.Validators.CedulaValidator;
import ec.edu.monster.Validators.SolicitudCreditoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para operaciones de Crédito
 * Equivalente al CreditoSoapService del proyecto .NET
 */
@RestController
@RequestMapping("/api/creditos")
@RequiredArgsConstructor
public class CreditoController {

    private final CreditoValidacionService validacionService;
    private final CreditoService creditoService;

    /**
     * WS 1: Validar si una persona es sujeto de crédito
     * Equivalente a: ValidarSujetoCredito(string cedula)
     * GET /api/creditos/validar/{cedula}
     */
    @GetMapping("/validar/{cedula}")
    public ResponseEntity<ValidacionCreditoDTO> validarSujetoCredito(@PathVariable String cedula) {
        try {
            // Validar formato de cédula
            CedulaValidator.ValidationResult validacionCedula = CedulaValidator.validar(cedula);
            if (!validacionCedula.isValido()) {
                return ResponseEntity.ok(ValidacionCreditoDTO.builder()
                        .esValido(false)
                        .mensaje(validacionCedula.getMensajeError())
                        .cedula(cedula)
                        .nombreCompleto(null)
                        .build());
            }

            CreditoValidacionService.ResultadoValidacion resultado = validacionService.validarSujetoCredito(cedula);

            return ResponseEntity.ok(ValidacionCreditoDTO.builder()
                    .esValido(resultado.isEsValido())
                    .mensaje(resultado.getMensaje())
                    .cedula(cedula)
                    .nombreCompleto(resultado.getCliente() != null ? resultado.getCliente().getNombreCompleto() : null)
                    .build());

        } catch (Exception ex) {
            return ResponseEntity.ok(ValidacionCreditoDTO.builder()
                    .esValido(false)
                    .mensaje(String.format(ErrorMessages.ERROR_INTERNO, ex.getMessage()))
                    .cedula(cedula)
                    .nombreCompleto(null)
                    .build());
        }
    }

    /**
     * WS 2: Obtener el monto máximo de crédito
     * Equivalente a: ObtenerMontoMaximo(string cedula)
     * GET /api/creditos/monto-maximo/{cedula}
     */
    @GetMapping("/monto-maximo/{cedula}")
    public ResponseEntity<MontoMaximoCreditoDTO> obtenerMontoMaximo(@PathVariable String cedula) {
        try {
            // Validar formato de cédula
            CedulaValidator.ValidationResult validacionCedula = CedulaValidator.validar(cedula);
            if (!validacionCedula.isValido()) {
                return ResponseEntity.ok(MontoMaximoCreditoDTO.builder()
                        .cedula(cedula)
                        .montoMaximo(java.math.BigDecimal.ZERO)
                        .promedioDepositos(java.math.BigDecimal.ZERO)
                        .promedioRetiros(java.math.BigDecimal.ZERO)
                        .mensaje(validacionCedula.getMensajeError())
                        .build());
            }

            CreditoValidacionService.ResultadoMontoMaximo resultado = validacionService.calcularMontoMaximo(cedula);

            return ResponseEntity.ok(MontoMaximoCreditoDTO.builder()
                    .cedula(cedula)
                    .montoMaximo(resultado.getMontoMaximo())
                    .promedioDepositos(resultado.getPromedioDepositos())
                    .promedioRetiros(resultado.getPromedioRetiros())
                    .mensaje(resultado.getMensaje())
                    .build());

        } catch (Exception ex) {
            return ResponseEntity.ok(MontoMaximoCreditoDTO.builder()
                    .cedula(cedula)
                    .montoMaximo(java.math.BigDecimal.ZERO)
                    .promedioDepositos(java.math.BigDecimal.ZERO)
                    .promedioRetiros(java.math.BigDecimal.ZERO)
                    .mensaje(String.format(ErrorMessages.ERROR_INTERNO, ex.getMessage()))
                    .build());
        }
    }

    /**
     * WS 3: Otorgar un crédito y generar tabla de amortización
     * Equivalente a: OtorgarCredito(SolicitudCreditoDTO solicitud)
     * POST /api/creditos
     */
    @PostMapping
    public ResponseEntity<RespuestaCreditoDTO> otorgarCredito(@RequestBody SolicitudCreditoDTO solicitud) {
        try {
            // Validar y convertir solicitud
            SolicitudCreditoValidator.ValidationResult validacion = SolicitudCreditoValidator
                    .validarYConvertir(solicitud);

            if (!validacion.isValido()) {
                return ResponseEntity.ok(RespuestaCreditoDTO.builder()
                        .exito(false)
                        .mensaje(validacion.getMensajeError())
                        .cedula(solicitud != null ? solicitud.getCedula() : null)
                        .build());
            }

            CreditoService.ResultadoCredito resultado = creditoService.otorgarCredito(
                    solicitud.getCedula(),
                    validacion.getPrecio(),
                    validacion.getCuotas());

            RespuestaCreditoDTO.RespuestaCreditoDTOBuilder responseBuilder = RespuestaCreditoDTO.builder()
                    .exito(resultado.isExito())
                    .mensaje(resultado.getMensaje())
                    .cedula(solicitud.getCedula());

            if (resultado.isExito() && resultado.getCredito() != null) {
                responseBuilder
                        .numeroCredito(resultado.getCredito().getNumeroCredito())
                        .montoCredito(resultado.getCredito().getMontoCredito())
                        .numeroCuotas(resultado.getCredito().getNumeroCuotas())
                        .cuotaMensual(resultado.getCredito().getCuotaMensual())
                        .tasaInteres(resultado.getCredito().getTasaInteres())
                        .tablaAmortizacion(
                                resultado.getTablaAmortizacion().stream()
                                        .map(c -> CuotaAmortizacionDTO.builder()
                                                .numeroCuota(c.getNumeroCuota())
                                                .valorCuota(c.getValorCuota())
                                                .interes(c.getInteres())
                                                .capitalPagado(c.getCapitalPagado())
                                                .saldo(c.getSaldo())
                                                .build())
                                        .collect(Collectors.toList()));
            }

            return ResponseEntity.ok(responseBuilder.build());

        } catch (Exception ex) {
            return ResponseEntity.ok(RespuestaCreditoDTO.builder()
                    .exito(false)
                    .mensaje(String.format(ErrorMessages.ERROR_INTERNO, ex.getMessage()))
                    .cedula(solicitud != null ? solicitud.getCedula() : null)
                    .build());
        }
    }

    /**
     * WS 4: Obtener la tabla de amortización de un crédito
     * Equivalente a: ObtenerTablaAmortizacion(string numeroCredito)
     * GET /api/creditos/{numeroCredito}/amortizacion
     */
    @GetMapping("/{numeroCredito}/amortizacion")
    public ResponseEntity<List<CuotaAmortizacionDTO>> obtenerTablaAmortizacion(
            @PathVariable String numeroCredito) {
        try {
            List<CuotaAmortizacion> tabla = creditoService.obtenerTablaAmortizacion(numeroCredito);

            List<CuotaAmortizacionDTO> resultado = tabla.stream()
                    .map(c -> CuotaAmortizacionDTO.builder()
                            .numeroCuota(c.getNumeroCuota())
                            .valorCuota(c.getValorCuota())
                            .interes(c.getInteres())
                            .capitalPagado(c.getCapitalPagado())
                            .saldo(c.getSaldo())
                            .build())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(resultado);

        } catch (Exception ex) {
            // Retornar lista vacía en caso de error
            return ResponseEntity.ok(List.of());
        }
    }
}
