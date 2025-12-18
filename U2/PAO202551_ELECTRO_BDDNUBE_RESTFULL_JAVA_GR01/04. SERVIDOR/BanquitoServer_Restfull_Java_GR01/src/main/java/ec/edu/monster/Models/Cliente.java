package ec.edu.monster.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad Cliente del banco
 */
@Entity
@Table(name = "Cliente")
@Data
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClienteId")
    private Long clienteId;

    @Column(name = "Cedula", nullable = false, length = 10, unique = true)
    private String cedula;

    @Column(name = "Nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "Apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "FechaNacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "EstadoCivil", length = 20)
    private String estadoCivil;

    @Column(name = "Direccion", length = 200)
    private String direccion;

    @Column(name = "Telefono", length = 20)
    private String telefono;

    @Column(name = "Email", length = 100)
    private String email;

    // Relaciones
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Cuenta> cuentas = new HashSet<>();

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Credito> creditos = new HashSet<>();

    // Propiedad calculada
    @Transient
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}
