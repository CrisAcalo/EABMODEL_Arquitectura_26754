package ec.edu.monster.ms_cuentas.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContadorService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public String generarCodigo(String tabla) {
        // 1. Incrementar contador
        Query updateQuery = entityManager.createNativeQuery(
                "UPDATE contador SET int_contitem = int_contitem + 1 WHERE vch_conttabla = :tabla");
        updateQuery.setParameter("tabla", tabla);
        updateQuery.executeUpdate();

        // 2. Obtener código generado
        Query selectQuery = entityManager.createNativeQuery(
                "SELECT LPAD(int_contitem, int_contlongitud, '0') FROM contador WHERE vch_conttabla = :tabla");
        selectQuery.setParameter("tabla", tabla);

        return (String) selectQuery.getSingleResult();
    }
}
