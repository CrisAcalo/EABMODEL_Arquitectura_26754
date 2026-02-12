package ec.edu.monster.ms_cuentas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://monster.edu.ec/ms-cuentas/ws")
public class Cliente {

    @Id
    @Column(name = "chr_cliecodigo", length = 5)
    private String codigo;

    @Column(name = "vch_cliepaterno", length = 25, nullable = false)
    private String paterno;

    @Column(name = "vch_cliematerno", length = 25, nullable = false)
    private String materno;

    @Column(name = "vch_clienombre", length = 30, nullable = false)
    private String nombre;

    @Column(name = "chr_cliedni", length = 10, nullable = false)
    private String dni;

    @Column(name = "vch_clieciudad", length = 30, nullable = false)
    private String ciudad;

    @Column(name = "vch_cliedireccion", length = 50, nullable = false)
    private String direccion;

    @Column(name = "vch_clietelefono", length = 20)
    private String telefono;

    @Column(name = "vch_clieemail", length = 50)
    private String email;
}
