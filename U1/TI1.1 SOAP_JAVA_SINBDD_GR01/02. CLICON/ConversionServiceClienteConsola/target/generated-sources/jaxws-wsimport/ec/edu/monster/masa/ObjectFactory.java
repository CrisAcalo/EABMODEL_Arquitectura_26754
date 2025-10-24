
package ec.edu.monster.masa;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ec.edu.monster.masa package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ConversionError_QNAME = new QName("http://ws.monster.edu.ec/", "ConversionError");
    private final static QName _ConversionResult_QNAME = new QName("http://ws.monster.edu.ec/", "ConversionResult");
    private final static QName _KilogramoALibra_QNAME = new QName("http://ws.monster.edu.ec/", "KilogramoALibra");
    private final static QName _KilogramoALibraResponse_QNAME = new QName("http://ws.monster.edu.ec/", "KilogramoALibraResponse");
    private final static QName _KilogramoAQuintal_QNAME = new QName("http://ws.monster.edu.ec/", "KilogramoAQuintal");
    private final static QName _KilogramoAQuintalResponse_QNAME = new QName("http://ws.monster.edu.ec/", "KilogramoAQuintalResponse");
    private final static QName _LibraAKilogramo_QNAME = new QName("http://ws.monster.edu.ec/", "LibraAKilogramo");
    private final static QName _LibraAKilogramoResponse_QNAME = new QName("http://ws.monster.edu.ec/", "LibraAKilogramoResponse");
    private final static QName _LibraAQuintal_QNAME = new QName("http://ws.monster.edu.ec/", "LibraAQuintal");
    private final static QName _LibraAQuintalResponse_QNAME = new QName("http://ws.monster.edu.ec/", "LibraAQuintalResponse");
    private final static QName _QuintalAKilogramo_QNAME = new QName("http://ws.monster.edu.ec/", "QuintalAKilogramo");
    private final static QName _QuintalAKilogramoResponse_QNAME = new QName("http://ws.monster.edu.ec/", "QuintalAKilogramoResponse");
    private final static QName _QuintalALibra_QNAME = new QName("http://ws.monster.edu.ec/", "QuintalALibra");
    private final static QName _QuintalALibraResponse_QNAME = new QName("http://ws.monster.edu.ec/", "QuintalALibraResponse");
    private final static QName _UnidadConversion_QNAME = new QName("http://ws.monster.edu.ec/", "UnidadConversion");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ec.edu.monster.masa
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConversionError }
     * 
     */
    public ConversionError createConversionError() {
        return new ConversionError();
    }

    /**
     * Create an instance of {@link ConversionResult }
     * 
     */
    public ConversionResult createConversionResult() {
        return new ConversionResult();
    }

    /**
     * Create an instance of {@link KilogramoALibra }
     * 
     */
    public KilogramoALibra createKilogramoALibra() {
        return new KilogramoALibra();
    }

    /**
     * Create an instance of {@link KilogramoALibraResponse }
     * 
     */
    public KilogramoALibraResponse createKilogramoALibraResponse() {
        return new KilogramoALibraResponse();
    }

    /**
     * Create an instance of {@link KilogramoAQuintal }
     * 
     */
    public KilogramoAQuintal createKilogramoAQuintal() {
        return new KilogramoAQuintal();
    }

    /**
     * Create an instance of {@link KilogramoAQuintalResponse }
     * 
     */
    public KilogramoAQuintalResponse createKilogramoAQuintalResponse() {
        return new KilogramoAQuintalResponse();
    }

    /**
     * Create an instance of {@link LibraAKilogramo }
     * 
     */
    public LibraAKilogramo createLibraAKilogramo() {
        return new LibraAKilogramo();
    }

    /**
     * Create an instance of {@link LibraAKilogramoResponse }
     * 
     */
    public LibraAKilogramoResponse createLibraAKilogramoResponse() {
        return new LibraAKilogramoResponse();
    }

    /**
     * Create an instance of {@link LibraAQuintal }
     * 
     */
    public LibraAQuintal createLibraAQuintal() {
        return new LibraAQuintal();
    }

    /**
     * Create an instance of {@link LibraAQuintalResponse }
     * 
     */
    public LibraAQuintalResponse createLibraAQuintalResponse() {
        return new LibraAQuintalResponse();
    }

    /**
     * Create an instance of {@link QuintalAKilogramo }
     * 
     */
    public QuintalAKilogramo createQuintalAKilogramo() {
        return new QuintalAKilogramo();
    }

    /**
     * Create an instance of {@link QuintalAKilogramoResponse }
     * 
     */
    public QuintalAKilogramoResponse createQuintalAKilogramoResponse() {
        return new QuintalAKilogramoResponse();
    }

    /**
     * Create an instance of {@link QuintalALibra }
     * 
     */
    public QuintalALibra createQuintalALibra() {
        return new QuintalALibra();
    }

    /**
     * Create an instance of {@link QuintalALibraResponse }
     * 
     */
    public QuintalALibraResponse createQuintalALibraResponse() {
        return new QuintalALibraResponse();
    }

    /**
     * Create an instance of {@link UnidadConversion }
     * 
     */
    public UnidadConversion createUnidadConversion() {
        return new UnidadConversion();
    }

    /**
     * Create an instance of {@link LocalDateTime }
     * 
     */
    public LocalDateTime createLocalDateTime() {
        return new LocalDateTime();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConversionError }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ConversionError }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "ConversionError")
    public JAXBElement<ConversionError> createConversionError(ConversionError value) {
        return new JAXBElement<ConversionError>(_ConversionError_QNAME, ConversionError.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConversionResult }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ConversionResult }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "ConversionResult")
    public JAXBElement<ConversionResult> createConversionResult(ConversionResult value) {
        return new JAXBElement<ConversionResult>(_ConversionResult_QNAME, ConversionResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KilogramoALibra }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link KilogramoALibra }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "KilogramoALibra")
    public JAXBElement<KilogramoALibra> createKilogramoALibra(KilogramoALibra value) {
        return new JAXBElement<KilogramoALibra>(_KilogramoALibra_QNAME, KilogramoALibra.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KilogramoALibraResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link KilogramoALibraResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "KilogramoALibraResponse")
    public JAXBElement<KilogramoALibraResponse> createKilogramoALibraResponse(KilogramoALibraResponse value) {
        return new JAXBElement<KilogramoALibraResponse>(_KilogramoALibraResponse_QNAME, KilogramoALibraResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KilogramoAQuintal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link KilogramoAQuintal }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "KilogramoAQuintal")
    public JAXBElement<KilogramoAQuintal> createKilogramoAQuintal(KilogramoAQuintal value) {
        return new JAXBElement<KilogramoAQuintal>(_KilogramoAQuintal_QNAME, KilogramoAQuintal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KilogramoAQuintalResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link KilogramoAQuintalResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "KilogramoAQuintalResponse")
    public JAXBElement<KilogramoAQuintalResponse> createKilogramoAQuintalResponse(KilogramoAQuintalResponse value) {
        return new JAXBElement<KilogramoAQuintalResponse>(_KilogramoAQuintalResponse_QNAME, KilogramoAQuintalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LibraAKilogramo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LibraAKilogramo }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "LibraAKilogramo")
    public JAXBElement<LibraAKilogramo> createLibraAKilogramo(LibraAKilogramo value) {
        return new JAXBElement<LibraAKilogramo>(_LibraAKilogramo_QNAME, LibraAKilogramo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LibraAKilogramoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LibraAKilogramoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "LibraAKilogramoResponse")
    public JAXBElement<LibraAKilogramoResponse> createLibraAKilogramoResponse(LibraAKilogramoResponse value) {
        return new JAXBElement<LibraAKilogramoResponse>(_LibraAKilogramoResponse_QNAME, LibraAKilogramoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LibraAQuintal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LibraAQuintal }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "LibraAQuintal")
    public JAXBElement<LibraAQuintal> createLibraAQuintal(LibraAQuintal value) {
        return new JAXBElement<LibraAQuintal>(_LibraAQuintal_QNAME, LibraAQuintal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LibraAQuintalResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LibraAQuintalResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "LibraAQuintalResponse")
    public JAXBElement<LibraAQuintalResponse> createLibraAQuintalResponse(LibraAQuintalResponse value) {
        return new JAXBElement<LibraAQuintalResponse>(_LibraAQuintalResponse_QNAME, LibraAQuintalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuintalAKilogramo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link QuintalAKilogramo }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "QuintalAKilogramo")
    public JAXBElement<QuintalAKilogramo> createQuintalAKilogramo(QuintalAKilogramo value) {
        return new JAXBElement<QuintalAKilogramo>(_QuintalAKilogramo_QNAME, QuintalAKilogramo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuintalAKilogramoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link QuintalAKilogramoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "QuintalAKilogramoResponse")
    public JAXBElement<QuintalAKilogramoResponse> createQuintalAKilogramoResponse(QuintalAKilogramoResponse value) {
        return new JAXBElement<QuintalAKilogramoResponse>(_QuintalAKilogramoResponse_QNAME, QuintalAKilogramoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuintalALibra }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link QuintalALibra }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "QuintalALibra")
    public JAXBElement<QuintalALibra> createQuintalALibra(QuintalALibra value) {
        return new JAXBElement<QuintalALibra>(_QuintalALibra_QNAME, QuintalALibra.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuintalALibraResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link QuintalALibraResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "QuintalALibraResponse")
    public JAXBElement<QuintalALibraResponse> createQuintalALibraResponse(QuintalALibraResponse value) {
        return new JAXBElement<QuintalALibraResponse>(_QuintalALibraResponse_QNAME, QuintalALibraResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnidadConversion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UnidadConversion }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "UnidadConversion")
    public JAXBElement<UnidadConversion> createUnidadConversion(UnidadConversion value) {
        return new JAXBElement<UnidadConversion>(_UnidadConversion_QNAME, UnidadConversion.class, null, value);
    }

}
