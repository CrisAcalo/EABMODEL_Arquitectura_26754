
package ec.edu.monster.temperatura;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para conversionResult complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="conversionResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Exitoso" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Resultado" type="{http://ws.monster.edu.ec/}unidadConversion" minOccurs="0"/&gt;
 *         &lt;element name="Error" type="{http://ws.monster.edu.ec/}conversionError" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conversionResult", propOrder = {
    "exitoso",
    "resultado",
    "error"
})
public class ConversionResult {

    @XmlElement(name = "Exitoso")
    protected boolean exitoso;
    @XmlElement(name = "Resultado")
    protected UnidadConversion resultado;
    @XmlElement(name = "Error")
    protected ConversionError error;

    /**
     * Obtiene el valor de la propiedad exitoso.
     * 
     */
    public boolean isExitoso() {
        return exitoso;
    }

    /**
     * Define el valor de la propiedad exitoso.
     * 
     */
    public void setExitoso(boolean value) {
        this.exitoso = value;
    }

    /**
     * Obtiene el valor de la propiedad resultado.
     * 
     * @return
     *     possible object is
     *     {@link UnidadConversion }
     *     
     */
    public UnidadConversion getResultado() {
        return resultado;
    }

    /**
     * Define el valor de la propiedad resultado.
     * 
     * @param value
     *     allowed object is
     *     {@link UnidadConversion }
     *     
     */
    public void setResultado(UnidadConversion value) {
        this.resultado = value;
    }

    /**
     * Obtiene el valor de la propiedad error.
     * 
     * @return
     *     possible object is
     *     {@link ConversionError }
     *     
     */
    public ConversionError getError() {
        return error;
    }

    /**
     * Define el valor de la propiedad error.
     * 
     * @param value
     *     allowed object is
     *     {@link ConversionError }
     *     
     */
    public void setError(ConversionError value) {
        this.error = value;
    }

}
