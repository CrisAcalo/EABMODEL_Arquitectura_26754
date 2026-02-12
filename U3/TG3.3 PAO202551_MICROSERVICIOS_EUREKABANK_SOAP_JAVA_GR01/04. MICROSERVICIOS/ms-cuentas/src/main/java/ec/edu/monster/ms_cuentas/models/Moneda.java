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
@Table(name = "moneda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://monster.edu.ec/ms-cuentas/ws")
public class Moneda {

    @Id
    @Column(name = "chr_monecodigo", length = 2)
    private String codigo;

    @Column(name = "vch_monedescripcion", length = 20, nullable = false)
    private String descripcion;
}
