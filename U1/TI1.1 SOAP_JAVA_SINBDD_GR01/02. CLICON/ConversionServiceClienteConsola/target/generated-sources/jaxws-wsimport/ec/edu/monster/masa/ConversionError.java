
package ec.edu.monster.masa;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para conversionError complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="conversionError"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodigoError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Mensaje" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TipoError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ValorProblematico" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="Unidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FechaError" type="{http://ws.monster.edu.ec/}localDateTime" minOccurs="0"/&gt;
 *         &lt;element name="Detalles" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conversionError", propOrder = {
    "codigoError",
    "mensaje",
    "tipoError",
    "valorProblematico",
    "unidad",
    "fechaError",
    "detalles"
})
public class ConversionError {

    @XmlElement(name = "CodigoError")
    protected String codigoError;
    @XmlElement(name = "Mensaje")
    protected String mensaje;
    @XmlElement(name = "TipoError")
    protected String tipoError;
    @XmlElement(name = "ValorProblematico")
    protected Double valorProblematico;
    @XmlElement(name = "Unidad")
    protected String unidad;
    @XmlElement(name = "FechaError")
    protected LocalDateTime fechaError;
    @XmlElement(name = "Detalles")
    protected String detalles;

    /**
     * Obtiene el valor de la propiedad codigoError.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * Define el valor de la propiedad codigoError.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoError(String value) {
        this.codigoError = value;
    }

    /**
     * Obtiene el valor de la propiedad mensaje.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Define el valor de la propiedad mensaje.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensaje(String value) {
        this.mensaje = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoError.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoError() {
        return tipoError;
    }

    /**
     * Define el valor de la propiedad tipoError.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoError(String value) {
        this.tipoError = value;
    }

    /**
     * Obtiene el valor de la propiedad valorProblematico.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getValorProblematico() {
        return valorProblematico;
    }

    /**
     * Define el valor de la propiedad valorProblematico.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setValorProblematico(Double value) {
        this.valorProblematico = value;
    }

    /**
     * Obtiene el valor de la propiedad unidad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * Define el valor de la propiedad unidad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnidad(String value) {
        this.unidad = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaError.
     * 
     * @return
     *     possible object is
     *     {@link LocalDateTime }
     *     
     */
    public LocalDateTime getFechaError() {
        return fechaError;
    }

    /**
     * Define el valor de la propiedad fechaError.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDateTime }
     *     
     */
    public void setFechaError(LocalDateTime value) {
        this.fechaError = value;
    }

    /**
     * Obtiene el valor de la propiedad detalles.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetalles() {
        return detalles;
    }

    /**
     * Define el valor de la propiedad detalles.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetalles(String value) {
        this.detalles = value;
    }

}
