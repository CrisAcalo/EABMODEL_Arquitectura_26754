
package ec.edu.monster.masa;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para KilogramoAQuintal complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="KilogramoAQuintal"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kilogramos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KilogramoAQuintal", propOrder = {
    "kilogramos"
})
public class KilogramoAQuintal {

    protected String kilogramos;

    /**
     * Obtiene el valor de la propiedad kilogramos.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKilogramos() {
        return kilogramos;
    }

    /**
     * Define el valor de la propiedad kilogramos.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKilogramos(String value) {
        this.kilogramos = value;
    }

}
