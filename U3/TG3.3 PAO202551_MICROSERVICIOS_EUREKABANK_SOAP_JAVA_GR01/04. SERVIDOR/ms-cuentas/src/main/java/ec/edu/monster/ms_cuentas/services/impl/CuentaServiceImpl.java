package ec.edu.monster.ms_cuentas.services.impl;

import ec.edu.monster.ms_cuentas.models.Cuenta;
import ec.edu.monster.ms_cuentas.models.Sucursal;
import ec.edu.monster.ms_cuentas.repositories.CuentaRepository;
import ec.edu.monster.ms_cuentas.repositories.SucursalRepository;
import ec.edu.monster.ms_cuentas.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public List<Cuenta> listarActivas() {
        return cuentaRepository.findByEstado("ACTIVO");
    }

    @Override
    public Optional<Cuenta> obtenerPorCodigo(String codigo) {
        return cuentaRepository.findById(codigo);
    }

    @Override
    public BigDecimal obtenerSaldo(String codigo) {
        return cuentaRepository.findById(codigo)
                .map(Cuenta::getSaldo)
                .orElse(null);
    }

    @Override
    @Transactional
    public Cuenta abrirCuenta(Cuenta cuenta) throws Exception {
        if (cuenta.getCodigoCliente() == null || cuenta.getCodigoSucursal() == null) {
            throw new Exception("Cliente y Sucursal son obligatorios");
        }

        // Generar Código de Cuenta
        String codigoSucursal = cuenta.getCodigoSucursal();
        Sucursal sucursal = sucursalRepository.findById(codigoSucursal)
                .orElseThrow(() -> new Exception("Sucursal no encontrada"));

        // Incrementar contador de sucursal
        sucursal.setContadorCuentas(sucursal.getContadorCuentas() + 1);
        sucursalRepository.save(sucursal);

        // Formatear nuevo código: SUC + 0000X
        String nuevoCodigo = codigoSucursal + String.format("%05d", sucursal.getContadorCuentas());
        cuenta.setCodigo(nuevoCodigo);

        // Valores por defecto
        if (cuenta.getSaldo() == null)
            cuenta.setSaldo(BigDecimal.ZERO);
        if (cuenta.getFechaCreacion() == null)
            cuenta.setFechaCreacion(LocalDate.now());
        if (cuenta.getEstado() == null || cuenta.getEstado().trim().isEmpty())
            cuenta.setEstado("ACTIVO");
        if (cuenta.getContadorMovimientos() == null)
            cuenta.setContadorMovimientos(0);

        return cuentaRepository.save(cuenta);
    }

    @Override
    @Transactional
    public void cancelarCuenta(String codigo) throws Exception {
        Cuenta cuenta = cuentaRepository.findById(codigo)
                .orElseThrow(() -> new Exception("Cuenta no encontrada"));

        if (cuenta.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            throw new Exception("La cuenta debe tener saldo cero para cancelarse");
        }

        cuenta.setEstado("CANCELADO");
        cuentaRepository.save(cuenta);
    }

    @Override
    public boolean validarClave(String codigo, String clave) {
        Optional<Cuenta> cuenta = cuentaRepository.findById(codigo);
        return cuenta.isPresent() && cuenta.get().getClave().equals(clave);
    }
}
