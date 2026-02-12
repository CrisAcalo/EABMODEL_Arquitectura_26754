package ec.edu.monster.ms_cuentas.services.impl;

import ec.edu.monster.ms_cuentas.models.Moneda;
import ec.edu.monster.ms_cuentas.repositories.MonedaRepository;
import ec.edu.monster.ms_cuentas.services.MonedaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MonedaServiceImpl implements MonedaService {

    @Autowired
    private MonedaRepository monedaRepository;

    @Override
    public List<Moneda> listarTodas() {
        return monedaRepository.findAll();
    }

    @Override
    public Optional<Moneda> obtenerPorCodigo(String codigo) {
        return monedaRepository.findById(codigo);
    }

    @Override
    @Transactional
    public Moneda guardar(Moneda moneda) {
        return monedaRepository.save(moneda);
    }

    @Override
    @Transactional
    public void eliminar(String codigo) {
        monedaRepository.deleteById(codigo);
    }
}
