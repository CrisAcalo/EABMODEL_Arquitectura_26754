package ec.edu.monster.ms_transacciones.models;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cuenta")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
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
    private LocalDate fechaCreacion;

    @Column(name = "vch_cuenestado", length = 15, nullable = false)
    private String estado;

    @Column(name = "int_cuencontmov", nullable = false)
    private Integer contadorMovimientos;

    @Column(name = "chr_cuenclave", length = 6, nullable = false)
    private String clave;
}
