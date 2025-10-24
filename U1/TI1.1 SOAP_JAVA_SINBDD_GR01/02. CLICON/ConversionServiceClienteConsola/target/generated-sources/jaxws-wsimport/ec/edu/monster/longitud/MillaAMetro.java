
package ec.edu.monster.longitud;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para MillaAMetro complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="MillaAMetro"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="millas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MillaAMetro", propOrder = {
    "millas"
})
public class MillaAMetro {

    protected String millas;

    /**
     * Obtiene el valor de la propiedad millas.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMillas() {
        return millas;
    }

    /**
     * Define el valor de la propiedad millas.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMillas(String value) {
        this.millas = value;
    }

}
