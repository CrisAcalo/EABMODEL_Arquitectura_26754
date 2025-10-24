
package ec.edu.monster.longitud;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para PulgadaAMilla complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="PulgadaAMilla"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pulgadas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PulgadaAMilla", propOrder = {
    "pulgadas"
})
public class PulgadaAMilla {

    protected String pulgadas;

    /**
     * Obtiene el valor de la propiedad pulgadas.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPulgadas() {
        return pulgadas;
    }

    /**
     * Define el valor de la propiedad pulgadas.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPulgadas(String value) {
        this.pulgadas = value;
    }

}
