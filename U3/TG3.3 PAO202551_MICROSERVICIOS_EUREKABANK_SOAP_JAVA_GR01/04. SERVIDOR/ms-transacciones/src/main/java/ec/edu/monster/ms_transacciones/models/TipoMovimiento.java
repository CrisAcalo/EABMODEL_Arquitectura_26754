package ec.edu.monster.ms_transacciones.models;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipomovimiento")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class TipoMovimiento {

    @Id
    @Column(name = "chr_tipocodigo", length = 3)
    private String codigo;

    @Column(name = "vch_tipodescripcion", length = 40, nullable = false)
    private String descripcion;

    @Column(name = "vch_tipoaccion", length = 10, nullable = false)
    private String accion;

    @Column(name = "vch_tipoestado", length = 15, nullable = false)
    private String estado;
}
