package ec.edu.monster.ms_websocket.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bloqueo_cuenta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BloqueoCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "int_bloqueoid")
    private Long id;

    @Column(name = "chr_cuencodigo", length = 8, nullable = false)
    private String codigoCuenta;

    @Column(name = "chr_ventcodigo", length = 4, nullable = false)
    private String codigoVentanilla;

    @Column(name = "dtt_bloqueo_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "dtt_bloqueo_expira", nullable = false)
    private LocalDateTime fechaExpiracion;

    @Column(name = "vch_bloqueo_estado", length = 15, nullable = false)
    private String estado;

    public boolean haExpirado() {
        return LocalDateTime.now().isAfter(fechaExpiracion);
    }

    public boolean estaActivo() {
        return "ACTIVO".equals(estado) && !haExpirado();
    }
}
