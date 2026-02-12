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
@Table(name = "empleado")
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Empleado {

    @Id
    @Column(name = "chr_emplcodigo", length = 4)
    private String codigo;

    @Column(name = "vch_emplpaterno", length = 25, nullable = false)
    private String paterno;

    @Column(name = "vch_emplmaterno", length = 25, nullable = false)
    private String materno;

    @Column(name = "vch_emplnombre", length = 30, nullable = false)
    private String nombre;

    @Column(name = "vch_emplciudad", length = 30, nullable = false)
    private String ciudad;

    @Column(name = "vch_empldireccion", length = 50)
    private String direccion;

    @Column(name = "vch_emplusuario", length = 15, nullable = false, unique = true)
    private String usuario;

    @Column(name = "vch_emplclave", length = 64, nullable = false)
    private String clave;

    public String getNombreCompleto() {
        return paterno + " " + materno + " " + nombre;
    }
}
