package ec.edu.monster.ms_transacciones.services.impl;

import ec.edu.monster.ms_transacciones.constants.CostosConstants;
import ec.edu.monster.ms_transacciones.constants.TipoMovimientoConstants;
import ec.edu.monster.ms_transacciones.dtos.RespuestaDTO;
import ec.edu.monster.ms_transacciones.models.Cuenta;
import ec.edu.monster.ms_transacciones.models.Movimiento;
import ec.edu.monster.ms_transacciones.repositories.CuentaRepository;
import ec.edu.monster.ms_transacciones.repositories.MovimientoRepository;
import ec.edu.monster.ms_transacciones.services.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    private static final Logger LOGGER = Logger.getLogger(TransaccionServiceImpl.class.getName());

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    // ===============================
    // DEPOSITO
    // ===============================
    @Override
    @Transactional
    public RespuestaDTO realizarDeposito(String codigoCuenta, String claveCuenta,
            BigDecimal importe, String codigoEmpleado) {
        try {
            // Validar importe
            validarImporte(importe);

            // Obtener y validar cuenta
            Cuenta cuenta = cuentaRepository.findById(codigoCuenta)
                    .orElseThrow(() -> new Exception("Cuenta no encontrada"));
            validarCuentaActiva(cuenta);

            // Guardar saldo anterior
            BigDecimal saldoAnterior = cuenta.getSaldo();
            BigDecimal nuevoSaldo = saldoAnterior.add(importe);

            // Obtener número base de movimiento
            int numeroBase = movimientoRepository.obtenerUltimoNumero(cuenta.getCodigo());

            // Registrar depósito
            Movimiento movDeposito = new Movimiento();
            movDeposito.setCodigoCuenta(cuenta.getCodigo());
            movDeposito.setNumero(++numeroBase);
            movDeposito.setFecha(LocalDate.now());
            movDeposito.setCodigoEmpleado(codigoEmpleado);
            movDeposito.setCodigoTipo(TipoMovimientoConstants.DEPOSITO);
            movDeposito.setImporte(importe);
            movimientoRepository.save(movDeposito);
            cuentaRepository.incrementarContadorMovimientos(cuenta.getCodigo());

            // Actualizar saldo
            cuentaRepository.actualizarSaldo(cuenta.getCodigo(), nuevoSaldo);

            LOGGER.info("Depósito exitoso en cuenta: " + cuenta.getCodigo());
            return RespuestaDTO.exito("Depósito realizado exitosamente");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error en depósito", e);
            return RespuestaDTO.error(e.getMessage());
        }
    }

    // ===============================
    // RETIRO
    // ===============================
    @Override
    @Transactional
    public RespuestaDTO realizarRetiro(String codigoCuenta, String claveCuenta,
            BigDecimal importe, String codigoEmpleado) {
        try {
            // Validar importe
            validarImporte(importe);

            // Obtener cuenta
            Cuenta cuenta = cuentaRepository.findById(codigoCuenta)
                    .orElseThrow(() -> new Exception("Cuenta no encontrada"));
            validarCuentaActiva(cuenta);
            validarClave(cuenta, claveCuenta);

            // Calcular costos
            BigDecimal itf = calcularITF(importe);
            BigDecimal cargo = calcularCargoMovimiento(cuenta);
            BigDecimal totalDescontar = importe.add(itf).add(cargo);

            // Validar saldo
            validarSaldoSuficiente(cuenta, totalDescontar);

            BigDecimal saldoAnterior = cuenta.getSaldo();
            BigDecimal nuevoSaldo = saldoAnterior.subtract(totalDescontar);

            int numeroBase = movimientoRepository.obtenerUltimoNumero(cuenta.getCodigo());

            // 1. Retiro principal
            Movimiento movRetiro = new Movimiento();
            movRetiro.setCodigoCuenta(cuenta.getCodigo());
            movRetiro.setNumero(++numeroBase);
            movRetiro.setFecha(LocalDate.now());
            movRetiro.setCodigoEmpleado(codigoEmpleado);
            movRetiro.setCodigoTipo(TipoMovimientoConstants.RETIRO);
            movRetiro.setImporte(importe);
            movimientoRepository.save(movRetiro);
            cuentaRepository.incrementarContadorMovimientos(cuenta.getCodigo());

            // 2. ITF
            Movimiento movITF = new Movimiento();
            movITF.setCodigoCuenta(cuenta.getCodigo());
            movITF.setNumero(++numeroBase);
            movITF.setFecha(LocalDate.now());
            movITF.setCodigoEmpleado(codigoEmpleado);
            movITF.setCodigoTipo(TipoMovimientoConstants.ITF);
            movITF.setImporte(itf);
            movimientoRepository.save(movITF);
            cuentaRepository.incrementarContadorMovimientos(cuenta.getCodigo());

            // 3. Cargo por movimiento (si aplica)
            if (cargo.compareTo(BigDecimal.ZERO) > 0) {
                Movimiento movCargo = new Movimiento();
                movCargo.setCodigoCuenta(cuenta.getCodigo());
                movCargo.setNumero(++numeroBase);
                movCargo.setFecha(LocalDate.now());
                movCargo.setCodigoEmpleado(codigoEmpleado);
                movCargo.setCodigoTipo(TipoMovimientoConstants.CARGO_MOVIMIENTO);
                movCargo.setImporte(cargo);
                movimientoRepository.save(movCargo);
                cuentaRepository.incrementarContadorMovimientos(cuenta.getCodigo());
            }

            // 4. Actualizar saldo
            cuentaRepository.actualizarSaldo(cuenta.getCodigo(), nuevoSaldo);

            LOGGER.info("Retiro exitoso de cuenta: " + cuenta.getCodigo());
            return RespuestaDTO.exito("Retiro realizado exitosamente");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error en retiro", e);
            return RespuestaDTO.error(e.getMessage());
        }
    }

    // ===============================
    // TRANSFERENCIA
    // ===============================
    @Override
    @Transactional
    public RespuestaDTO realizarTransferencia(String ctaOrigen, String ctaDestino,
            String claveCuentaOrigen, BigDecimal importe,
            String codigoEmpleado) {
        try {
            validarImporte(importe);

            Cuenta cuentaOrigen = cuentaRepository.findById(ctaOrigen)
                    .orElseThrow(() -> new Exception("Cuenta origen no encontrada"));
            Cuenta cuentaDestino = cuentaRepository.findById(ctaDestino)
                    .orElseThrow(() -> new Exception("Cuenta destino no encontrada"));

            // Validaciones
            validarCuentaActiva(cuentaOrigen);
            validarCuentaActiva(cuentaDestino);
            validarClave(cuentaOrigen, claveCuentaOrigen);

            if (cuentaOrigen.getCodigo().equals(cuentaDestino.getCodigo())) {
                throw new Exception("Las cuentas origen y destino no pueden ser iguales");
            }
            if (!cuentaOrigen.getCodigoMoneda().equals(cuentaDestino.getCodigoMoneda())) {
                throw new Exception("Las cuentas deben ser de la misma moneda");
            }

            BigDecimal itf = calcularITF(importe);
            BigDecimal cargo = calcularCargoMovimiento(cuentaOrigen);
            BigDecimal totalDescontar = importe.add(itf).add(cargo);

            validarSaldoSuficiente(cuentaOrigen, totalDescontar);

            BigDecimal saldoAnteriorOrigen = cuentaOrigen.getSaldo();
            BigDecimal saldoAnteriorDestino = cuentaDestino.getSaldo();
            BigDecimal nuevoSaldoOrigen = saldoAnteriorOrigen.subtract(totalDescontar);
            BigDecimal nuevoSaldoDestino = saldoAnteriorDestino.add(importe);

            int numeroBaseOrigen = movimientoRepository.obtenerUltimoNumero(cuentaOrigen.getCodigo());
            int numeroMovimientoDestino = movimientoRepository.obtenerUltimoNumero(cuentaDestino.getCodigo()) + 1;

            // 1. Salida en cuenta origen
            Movimiento movOrigen = new Movimiento();
            movOrigen.setCodigoCuenta(cuentaOrigen.getCodigo());
            movOrigen.setNumero(++numeroBaseOrigen);
            movOrigen.setFecha(LocalDate.now());
            movOrigen.setCodigoEmpleado(codigoEmpleado);
            movOrigen.setCodigoTipo(TipoMovimientoConstants.TRANSFERENCIA_SALIDA);
            movOrigen.setImporte(importe);
            movOrigen.setCuentaReferencia(cuentaDestino.getCodigo());
            movimientoRepository.save(movOrigen);
            cuentaRepository.incrementarContadorMovimientos(cuentaOrigen.getCodigo());

            // 2. ITF en cuenta origen
            Movimiento movITF = new Movimiento();
            movITF.setCodigoCuenta(cuentaOrigen.getCodigo());
            movITF.setNumero(++numeroBaseOrigen);
            movITF.setFecha(LocalDate.now());
            movITF.setCodigoEmpleado(codigoEmpleado);
            movITF.setCodigoTipo(TipoMovimientoConstants.ITF);
            movITF.setImporte(itf);
            movimientoRepository.save(movITF);
            cuentaRepository.incrementarContadorMovimientos(cuentaOrigen.getCodigo());

            // 3. Cargo por movimiento en cuenta origen (si aplica)
            if (cargo.compareTo(BigDecimal.ZERO) > 0) {
                Movimiento movCargo = new Movimiento();
                movCargo.setCodigoCuenta(cuentaOrigen.getCodigo());
                movCargo.setNumero(++numeroBaseOrigen);
                movCargo.setFecha(LocalDate.now());
                movCargo.setCodigoEmpleado(codigoEmpleado);
                movCargo.setCodigoTipo(TipoMovimientoConstants.CARGO_MOVIMIENTO);
                movCargo.setImporte(cargo);
                movimientoRepository.save(movCargo);
                cuentaRepository.incrementarContadorMovimientos(cuentaOrigen.getCodigo());
            }

            // 4. Ingreso en cuenta destino
            Movimiento movDestino = new Movimiento();
            movDestino.setCodigoCuenta(cuentaDestino.getCodigo());
            movDestino.setNumero(numeroMovimientoDestino);
            movDestino.setFecha(LocalDate.now());
            movDestino.setCodigoEmpleado(codigoEmpleado);
            movDestino.setCodigoTipo(TipoMovimientoConstants.TRANSFERENCIA_INGRESO);
            movDestino.setImporte(importe);
            movDestino.setCuentaReferencia(cuentaOrigen.getCodigo());
            movimientoRepository.save(movDestino);
            cuentaRepository.incrementarContadorMovimientos(cuentaDestino.getCodigo());

            // 5. Actualizar saldos
            cuentaRepository.actualizarSaldo(cuentaOrigen.getCodigo(), nuevoSaldoOrigen);
            cuentaRepository.actualizarSaldo(cuentaDestino.getCodigo(), nuevoSaldoDestino);

            LOGGER.info("Transferencia exitosa de " + cuentaOrigen.getCodigo()
                    + " a " + cuentaDestino.getCodigo());
            return RespuestaDTO.exito("Transferencia realizada exitosamente");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error en transferencia", e);
            return RespuestaDTO.error(e.getMessage());
        }
    }

    // ===============================
    // LISTAR MOVIMIENTOS
    // ===============================
    @Override
    public List<Movimiento> listarMovimientos(String codigoCuenta, LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio != null && fechaFin != null) {
            return movimientoRepository.findByCodigoCuentaAndFechaBetween(codigoCuenta, fechaInicio, fechaFin);
        }
        return movimientoRepository.findByCodigoCuentaOrderByNumeroDesc(codigoCuenta);
    }

    // =====================
    // MÉTODOS AUXILIARES
    // =====================
    private void validarImporte(BigDecimal importe) throws Exception {
        if (importe == null || importe.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("El importe debe ser mayor a cero");
        }
    }

    private void validarCuentaActiva(Cuenta cuenta) throws Exception {
        if (!"ACTIVO".equals(cuenta.getEstado())) {
            throw new Exception("La cuenta no está activa");
        }
    }

    private void validarClave(Cuenta cuenta, String clave) throws Exception {
        if (!cuenta.getClave().equals(clave)) {
            throw new Exception("Clave de cuenta incorrecta");
        }
    }

    private void validarSaldoSuficiente(Cuenta cuenta, BigDecimal importeNecesario) throws Exception {
        if (cuenta.getSaldo().compareTo(importeNecesario) < 0) {
            throw new Exception("Saldo insuficiente");
        }
    }

    private BigDecimal calcularITF(BigDecimal importe) {
        return importe.multiply(CostosConstants.TASA_ITF).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularCargoMovimiento(Cuenta cuenta) {
        if (cuenta.getContadorMovimientos() >= CostosConstants.OPERACIONES_GRATUITAS) {
            if (CostosConstants.MONEDA_SOLES.equals(cuenta.getCodigoMoneda())) {
                return CostosConstants.COSTO_MOVIMIENTO_SOLES;
            } else if (CostosConstants.MONEDA_DOLARES.equals(cuenta.getCodigoMoneda())) {
                return CostosConstants.COSTO_MOVIMIENTO_DOLARES;
            }
        }
        return BigDecimal.ZERO;
    }
}
