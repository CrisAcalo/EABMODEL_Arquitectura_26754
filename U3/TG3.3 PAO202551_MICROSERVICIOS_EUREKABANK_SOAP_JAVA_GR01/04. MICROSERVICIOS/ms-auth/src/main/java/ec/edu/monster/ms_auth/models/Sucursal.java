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
@Table(name = "sucursal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://monster.edu.ec/ms-auth/ws")
public class Sucursal {

    @Id
    @Column(name = "chr_sucucodigo", length = 3)
    private String codigo;

    @Column(name = "vch_sucunombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "vch_sucuciudad", length = 30, nullable = false)
    private String ciudad;

    @Column(name = "vch_sucudireccion", length = 50)
    private String direccion;

    @Column(name = "int_sucucontcuenta", nullable = false)
    private Integer contadorCuenta;
}
