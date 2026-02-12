package ec.edu.monster.ms_transacciones.models;

import ec.edu.monster.ms_transacciones.utils.LocalDateAdapter;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "movimiento")
@IdClass(MovimientoId.class)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Movimiento", propOrder = {
        "codigoCuenta", "numero", "fecha", "codigoEmpleado",
        "codigoTipo", "importe", "cuentaReferencia"
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {

    @Id
    @Column(name = "chr_cuencodigo", length = 8)
    private String codigoCuenta;

    @Id
    @Column(name = "int_movinumero")
    private Integer numero;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @Column(name = "dtt_movifecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "chr_emplcodigo", length = 4, nullable = false)
    private String codigoEmpleado;

    @Column(name = "chr_tipocodigo", length = 3, nullable = false)
    private String codigoTipo;

    @Column(name = "dec_moviimporte", precision = 12, scale = 2, nullable = false)
    private BigDecimal importe;

    @Column(name = "chr_cuenreferencia", length = 8)
    private String cuentaReferencia;
}
