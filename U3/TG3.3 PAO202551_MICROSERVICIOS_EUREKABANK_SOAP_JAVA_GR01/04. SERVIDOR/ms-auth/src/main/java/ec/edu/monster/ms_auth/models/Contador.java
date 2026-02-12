package ec.edu.monster.ms_auth.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contador {

    @Id
    @Column(name = "vch_conttabla", length = 30)
    private String tabla;

    @Column(name = "int_contitem", nullable = false)
    private Integer item;

    @Column(name = "int_contlongitud", nullable = false)
    private Integer longitud;
}
