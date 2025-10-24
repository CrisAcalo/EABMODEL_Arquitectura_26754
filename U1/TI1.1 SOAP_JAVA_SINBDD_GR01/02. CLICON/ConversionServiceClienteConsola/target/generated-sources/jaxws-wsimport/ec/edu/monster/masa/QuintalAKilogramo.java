
package ec.edu.monster.masa;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para QuintalAKilogramo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="QuintalAKilogramo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="quintales" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuintalAKilogramo", propOrder = {
    "quintales"
})
public class QuintalAKilogramo {

    protected String quintales;

    /**
     * Obtiene el valor de la propiedad quintales.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuintales() {
        return quintales;
    }

    /**
     * Define el valor de la propiedad quintales.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuintales(String value) {
        this.quintales = value;
    }

}
