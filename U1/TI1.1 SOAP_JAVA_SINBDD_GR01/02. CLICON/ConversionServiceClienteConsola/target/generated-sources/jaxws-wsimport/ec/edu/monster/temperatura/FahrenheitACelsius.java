
package ec.edu.monster.temperatura;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para FahrenheitACelsius complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="FahrenheitACelsius"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="fahrenheit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FahrenheitACelsius", propOrder = {
    "fahrenheit"
})
public class FahrenheitACelsius {

    protected String fahrenheit;

    /**
     * Obtiene el valor de la propiedad fahrenheit.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFahrenheit() {
        return fahrenheit;
    }

    /**
     * Define el valor de la propiedad fahrenheit.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFahrenheit(String value) {
        this.fahrenheit = value;
    }

}
