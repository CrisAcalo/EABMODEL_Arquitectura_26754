package ec.edu.monster.ms_cuentas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ec.edu.monster.ms_cuentas.utils.LocalDateAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cuenta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://monster.edu.ec/ms-cuentas/ws")
public class Cuenta {

    @Id
    @Column(name = "chr_cuencodigo", length = 8)
    private String codigo;

    @Column(name = "chr_monecodigo", length = 2, nullable = false)
    private String codigoMoneda;

    @Column(name = "chr_sucucodigo", length = 3, nullable = false)
    private String codigoSucursal;

    @Column(name = "chr_emplcreacuenta", length = 4, nullable = false)
    private String codigoEmpleadoCreador;

    @Column(name = "chr_cliecodigo", length = 5, nullable = false)
    private String codigoCliente;

    @Column(name = "dec_cuensaldo", precision = 12, scale = 2, nullable = false)
    private BigDecimal saldo;

    @Column(name = "dtt_cuenfechacreacion", nullable = false)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaCreacion;

    @Column(name = "vch_cuenestado", length = 15, nullable = false)
    private String estado;

    @Column(name = "int_cuencontmov", nullable = false)
    private Integer contadorMovimientos;

    @Column(name = "chr_cuenclave", length = 6, nullable = false)
    private String clave;
}
