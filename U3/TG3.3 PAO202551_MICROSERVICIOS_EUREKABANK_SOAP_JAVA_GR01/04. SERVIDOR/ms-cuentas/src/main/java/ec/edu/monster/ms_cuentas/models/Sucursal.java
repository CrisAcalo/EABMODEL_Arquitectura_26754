package ec.edu.monster.ms_cuentas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "sucursal")
@Data
public class Sucursal {
    @Id
    @Column(name = "chr_sucucodigo", length = 3)
    private String codigo;

    @Column(name = "vch_sucunombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "vch_sucuciudad", length = 30, nullable = false)
    private String ciudad;

    @Column(name = "int_sucucontcuenta", nullable = false)
    private Integer contadorCuentas;
}
