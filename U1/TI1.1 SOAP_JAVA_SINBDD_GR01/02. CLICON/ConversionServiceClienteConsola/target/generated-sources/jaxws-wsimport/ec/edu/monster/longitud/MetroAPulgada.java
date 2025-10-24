
package ec.edu.monster.longitud;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para MetroAPulgada complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="MetroAPulgada"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="metros" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetroAPulgada", propOrder = {
    "metros"
})
public class MetroAPulgada {

    protected String metros;

    /**
     * Obtiene el valor de la propiedad metros.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetros() {
        return metros;
    }

    /**
     * Define el valor de la propiedad metros.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetros(String value) {
        this.metros = value;
    }

}
