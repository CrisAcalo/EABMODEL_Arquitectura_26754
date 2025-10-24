
package ec.edu.monster.temperatura;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para KelvinACelsius complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="KelvinACelsius"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kelvin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KelvinACelsius", propOrder = {
    "kelvin"
})
public class KelvinACelsius {

    protected String kelvin;

    /**
     * Obtiene el valor de la propiedad kelvin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKelvin() {
        return kelvin;
    }

    /**
     * Define el valor de la propiedad kelvin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKelvin(String value) {
        this.kelvin = value;
    }

}
