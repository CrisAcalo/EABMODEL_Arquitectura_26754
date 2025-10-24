
package ec.edu.monster.temperatura;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CelsiusAKelvin complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CelsiusAKelvin"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="celsius" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CelsiusAKelvin", propOrder = {
    "celsius"
})
public class CelsiusAKelvin {

    protected String celsius;

    /**
     * Obtiene el valor de la propiedad celsius.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCelsius() {
        return celsius;
    }

    /**
     * Define el valor de la propiedad celsius.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCelsius(String value) {
        this.celsius = value;
    }

}
