package ec.edu.monster.ms_transacciones.models;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoId implements Serializable {

    @Column(name = "chr_cuencodigo", length = 8)
    private String codigoCuenta;

    @Column(name = "int_movinumero")
    private Integer numero;
}
