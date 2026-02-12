package ec.edu.monster.ms_auth.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "ventanilla")
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://monster.edu.ec/ms-auth/ws")
public class Ventanilla {

    @Id
    @Column(name = "chr_ventcodigo", length = 4)
    private String codigo;

    @Column(name = "vch_ventnombre", length = 30, nullable = false)
    private String nombre;

    @Column(name = "chr_emplcodigo", length = 4)
    private String codigoEmpleado;

    @Column(name = "vch_ventestado", length = 15, nullable = false)
    private String estado;
}
