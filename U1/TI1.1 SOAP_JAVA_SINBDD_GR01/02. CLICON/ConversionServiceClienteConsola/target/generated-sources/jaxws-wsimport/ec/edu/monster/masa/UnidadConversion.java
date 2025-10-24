
package ec.edu.monster.masa;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para unidadConversion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="unidadConversion"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="valorOriginal" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="valorConvertidoExacto" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="valorConvertidoRedondeado" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="unidadOrigen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="unidadDestino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="factorConversion" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="fechaConversion" type="{http://ws.monster.edu.ec/}localDateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unidadConversion", propOrder = {
    "valorOriginal",
    "valorConvertidoExacto",
    "valorConvertidoRedondeado",
    "unidadOrigen",
    "unidadDestino",
    "factorConversion",
    "fechaConversion"
})
public class UnidadConversion {

    protected double valorOriginal;
    protected double valorConvertidoExacto;
    protected double valorConvertidoRedondeado;
    protected String unidadOrigen;
    protected String unidadDestino;
    protected double factorConversion;
    protected LocalDateTime fechaConversion;

    /**
     * Obtiene el valor de la propiedad valorOriginal.
     * 
     */
    public double getValorOriginal() {
        return valorOriginal;
    }

    /**
     * Define el valor de la propiedad valorOriginal.
     * 
     */
    public void setValorOriginal(double value) {
        this.valorOriginal = value;
    }

    /**
     * Obtiene el valor de la propiedad valorConvertidoExacto.
     * 
     */
    public double getValorConvertidoExacto() {
        return valorConvertidoExacto;
    }

    /**
     * Define el valor de la propiedad valorConvertidoExacto.
     * 
     */
    public void setValorConvertidoExacto(double value) {
        this.valorConvertidoExacto = value;
    }

    /**
     * Obtiene el valor de la propiedad valorConvertidoRedondeado.
     * 
     */
    public double getValorConvertidoRedondeado() {
        return valorConvertidoRedondeado;
    }

    /**
     * Define el valor de la propiedad valorConvertidoRedondeado.
     * 
     */
    public void setValorConvertidoRedondeado(double value) {
        this.valorConvertidoRedondeado = value;
    }

    /**
     * Obtiene el valor de la propiedad unidadOrigen.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnidadOrigen() {
        return unidadOrigen;
    }

    /**
     * Define el valor de la propiedad unidadOrigen.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnidadOrigen(String value) {
        this.unidadOrigen = value;
    }

    /**
     * Obtiene el valor de la propiedad unidadDestino.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnidadDestino() {
        return unidadDestino;
    }

    /**
     * Define el valor de la propiedad unidadDestino.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnidadDestino(String value) {
        this.unidadDestino = value;
    }

    /**
     * Obtiene el valor de la propiedad factorConversion.
     * 
     */
    public double getFactorConversion() {
        return factorConversion;
    }

    /**
     * Define el valor de la propiedad factorConversion.
     * 
     */
    public void setFactorConversion(double value) {
        this.factorConversion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaConversion.
     * 
     * @return
     *     possible object is
     *     {@link LocalDateTime }
     *     
     */
    public LocalDateTime getFechaConversion() {
        return fechaConversion;
    }

    /**
     * Define el valor de la propiedad fechaConversion.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDateTime }
     *     
     */
    public void setFechaConversion(LocalDateTime value) {
        this.fechaConversion = value;
    }

}
