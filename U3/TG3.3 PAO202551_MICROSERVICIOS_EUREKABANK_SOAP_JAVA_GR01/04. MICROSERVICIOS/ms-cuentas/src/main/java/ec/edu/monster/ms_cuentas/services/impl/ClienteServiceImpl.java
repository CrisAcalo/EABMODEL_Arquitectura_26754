package ec.edu.monster.ms_cuentas.services.impl;

import ec.edu.monster.ms_cuentas.models.Cliente;
import ec.edu.monster.ms_cuentas.repositories.ClienteRepository;
import ec.edu.monster.ms_cuentas.services.ClienteService;
import ec.edu.monster.ms_cuentas.services.ContadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContadorService contadorService;

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> obtenerPorCodigo(String codigo) {
        return clienteRepository.findById(codigo);
    }

    @Override
    public Optional<Cliente> obtenerPorDni(String dni) {
        return clienteRepository.findByDni(dni);
    }

    @Override
    @Transactional
    public Cliente registrar(Cliente cliente) throws Exception {
        if (cliente.getDni() == null || cliente.getDni().trim().isEmpty()) {
            throw new Exception("El DNI es obligatorio");
        }

        if (clienteRepository.findByDni(cliente.getDni()).isPresent()) {
            throw new Exception("Ya existe un cliente con el DNI: " + cliente.getDni());
        }

        if (cliente.getCodigo() == null || cliente.getCodigo().isEmpty()) {
            String nuevoCodigo = contadorService.generarCodigo("Cliente");
            cliente.setCodigo(nuevoCodigo);
        }

        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public Cliente actualizar(Cliente cliente) throws Exception {
        if (!clienteRepository.existsById(cliente.getCodigo())) {
            throw new Exception("Cliente no encontrado");
        }
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public void eliminar(String codigo) throws Exception {
        if (!clienteRepository.existsById(codigo)) {
            throw new Exception("Cliente no encontrado");
        }
        clienteRepository.deleteById(codigo);
    }
}
