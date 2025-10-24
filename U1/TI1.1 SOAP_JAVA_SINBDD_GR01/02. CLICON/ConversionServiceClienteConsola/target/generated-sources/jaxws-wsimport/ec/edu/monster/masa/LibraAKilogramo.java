
package ec.edu.monster.masa;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para LibraAKilogramo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="LibraAKilogramo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="libras" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LibraAKilogramo", propOrder = {
    "libras"
})
public class LibraAKilogramo {

    protected String libras;

    /**
     * Obtiene el valor de la propiedad libras.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLibras() {
        return libras;
    }

    /**
     * Define el valor de la propiedad libras.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLibras(String value) {
        this.libras = value;
    }

}
