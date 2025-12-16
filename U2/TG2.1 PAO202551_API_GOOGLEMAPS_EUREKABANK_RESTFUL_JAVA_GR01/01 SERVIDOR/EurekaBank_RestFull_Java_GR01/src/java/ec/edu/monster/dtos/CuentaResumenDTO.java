package ec.edu.monster.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;

/**
 * DTO para resumen de cuenta en operaciones
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class CuentaResumenDTO {

    @JsonbProperty("Codigo")
    private String codigo;

    @JsonbProperty("SaldoAnterior")
    private BigDecimal saldoAnterior;

    @JsonbProperty("SaldoNuevo")
    private BigDecimal saldoNuevo;

    // Constructores
    public CuentaResumenDTO() {
    }

    public CuentaResumenDTO(String codigo, BigDecimal saldoAnterior, BigDecimal saldoNuevo) {
        this.codigo = codigo;
        this.saldoAnterior = saldoAnterior;
        this.saldoNuevo = saldoNuevo;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public BigDecimal getSaldoNuevo() {
        return saldoNuevo;
    }

    public void setSaldoNuevo(BigDecimal saldoNuevo) {
        this.saldoNuevo = saldoNuevo;
    }
}
