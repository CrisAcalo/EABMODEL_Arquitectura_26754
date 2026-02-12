package ec.edu.monster.ms_auth.services;

import ec.edu.monster.ms_auth.models.Contador;
import ec.edu.monster.ms_auth.repositories.ContadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContadorService {

    @Autowired
    private ContadorRepository contadorRepository;

    @Transactional
    public String generarCodigo(String tabla) {
        Contador contador = contadorRepository.findById(tabla)
                .orElseThrow(() -> new RuntimeException("No se encontró el contador para la tabla: " + tabla));

        // Incrementar el contador
        contador.setItem(contador.getItem() + 1);
        contadorRepository.save(contador);

        // Formatear el código con ceros a la izquierda según la longitud definida
        return String.format("%0" + contador.getLongitud() + "d", contador.getItem());
    }
}
