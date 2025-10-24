
package ec.edu.monster.longitud;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ec.edu.monster.longitud package. 
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
    private final static QName _MetroAMilla_QNAME = new QName("http://ws.monster.edu.ec/", "MetroAMilla");
    private final static QName _MetroAMillaResponse_QNAME = new QName("http://ws.monster.edu.ec/", "MetroAMillaResponse");
    private final static QName _MetroAPulgada_QNAME = new QName("http://ws.monster.edu.ec/", "MetroAPulgada");
    private final static QName _MetroAPulgadaResponse_QNAME = new QName("http://ws.monster.edu.ec/", "MetroAPulgadaResponse");
    private final static QName _MillaAMetro_QNAME = new QName("http://ws.monster.edu.ec/", "MillaAMetro");
    private final static QName _MillaAMetroResponse_QNAME = new QName("http://ws.monster.edu.ec/", "MillaAMetroResponse");
    private final static QName _MillaAPulgada_QNAME = new QName("http://ws.monster.edu.ec/", "MillaAPulgada");
    private final static QName _MillaAPulgadaResponse_QNAME = new QName("http://ws.monster.edu.ec/", "MillaAPulgadaResponse");
    private final static QName _PulgadaAMetro_QNAME = new QName("http://ws.monster.edu.ec/", "PulgadaAMetro");
    private final static QName _PulgadaAMetroResponse_QNAME = new QName("http://ws.monster.edu.ec/", "PulgadaAMetroResponse");
    private final static QName _PulgadaAMilla_QNAME = new QName("http://ws.monster.edu.ec/", "PulgadaAMilla");
    private final static QName _PulgadaAMillaResponse_QNAME = new QName("http://ws.monster.edu.ec/", "PulgadaAMillaResponse");
    private final static QName _UnidadConversion_QNAME = new QName("http://ws.monster.edu.ec/", "UnidadConversion");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ec.edu.monster.longitud
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
     * Create an instance of {@link MetroAMilla }
     * 
     */
    public MetroAMilla createMetroAMilla() {
        return new MetroAMilla();
    }

    /**
     * Create an instance of {@link MetroAMillaResponse }
     * 
     */
    public MetroAMillaResponse createMetroAMillaResponse() {
        return new MetroAMillaResponse();
    }

    /**
     * Create an instance of {@link MetroAPulgada }
     * 
     */
    public MetroAPulgada createMetroAPulgada() {
        return new MetroAPulgada();
    }

    /**
     * Create an instance of {@link MetroAPulgadaResponse }
     * 
     */
    public MetroAPulgadaResponse createMetroAPulgadaResponse() {
        return new MetroAPulgadaResponse();
    }

    /**
     * Create an instance of {@link MillaAMetro }
     * 
     */
    public MillaAMetro createMillaAMetro() {
        return new MillaAMetro();
    }

    /**
     * Create an instance of {@link MillaAMetroResponse }
     * 
     */
    public MillaAMetroResponse createMillaAMetroResponse() {
        return new MillaAMetroResponse();
    }

    /**
     * Create an instance of {@link MillaAPulgada }
     * 
     */
    public MillaAPulgada createMillaAPulgada() {
        return new MillaAPulgada();
    }

    /**
     * Create an instance of {@link MillaAPulgadaResponse }
     * 
     */
    public MillaAPulgadaResponse createMillaAPulgadaResponse() {
        return new MillaAPulgadaResponse();
    }

    /**
     * Create an instance of {@link PulgadaAMetro }
     * 
     */
    public PulgadaAMetro createPulgadaAMetro() {
        return new PulgadaAMetro();
    }

    /**
     * Create an instance of {@link PulgadaAMetroResponse }
     * 
     */
    public PulgadaAMetroResponse createPulgadaAMetroResponse() {
        return new PulgadaAMetroResponse();
    }

    /**
     * Create an instance of {@link PulgadaAMilla }
     * 
     */
    public PulgadaAMilla createPulgadaAMilla() {
        return new PulgadaAMilla();
    }

    /**
     * Create an instance of {@link PulgadaAMillaResponse }
     * 
     */
    public PulgadaAMillaResponse createPulgadaAMillaResponse() {
        return new PulgadaAMillaResponse();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link MetroAMilla }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MetroAMilla }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "MetroAMilla")
    public JAXBElement<MetroAMilla> createMetroAMilla(MetroAMilla value) {
        return new JAXBElement<MetroAMilla>(_MetroAMilla_QNAME, MetroAMilla.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetroAMillaResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MetroAMillaResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "MetroAMillaResponse")
    public JAXBElement<MetroAMillaResponse> createMetroAMillaResponse(MetroAMillaResponse value) {
        return new JAXBElement<MetroAMillaResponse>(_MetroAMillaResponse_QNAME, MetroAMillaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetroAPulgada }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MetroAPulgada }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "MetroAPulgada")
    public JAXBElement<MetroAPulgada> createMetroAPulgada(MetroAPulgada value) {
        return new JAXBElement<MetroAPulgada>(_MetroAPulgada_QNAME, MetroAPulgada.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetroAPulgadaResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MetroAPulgadaResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "MetroAPulgadaResponse")
    public JAXBElement<MetroAPulgadaResponse> createMetroAPulgadaResponse(MetroAPulgadaResponse value) {
        return new JAXBElement<MetroAPulgadaResponse>(_MetroAPulgadaResponse_QNAME, MetroAPulgadaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MillaAMetro }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MillaAMetro }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "MillaAMetro")
    public JAXBElement<MillaAMetro> createMillaAMetro(MillaAMetro value) {
        return new JAXBElement<MillaAMetro>(_MillaAMetro_QNAME, MillaAMetro.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MillaAMetroResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MillaAMetroResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "MillaAMetroResponse")
    public JAXBElement<MillaAMetroResponse> createMillaAMetroResponse(MillaAMetroResponse value) {
        return new JAXBElement<MillaAMetroResponse>(_MillaAMetroResponse_QNAME, MillaAMetroResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MillaAPulgada }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MillaAPulgada }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "MillaAPulgada")
    public JAXBElement<MillaAPulgada> createMillaAPulgada(MillaAPulgada value) {
        return new JAXBElement<MillaAPulgada>(_MillaAPulgada_QNAME, MillaAPulgada.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MillaAPulgadaResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MillaAPulgadaResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "MillaAPulgadaResponse")
    public JAXBElement<MillaAPulgadaResponse> createMillaAPulgadaResponse(MillaAPulgadaResponse value) {
        return new JAXBElement<MillaAPulgadaResponse>(_MillaAPulgadaResponse_QNAME, MillaAPulgadaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PulgadaAMetro }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PulgadaAMetro }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "PulgadaAMetro")
    public JAXBElement<PulgadaAMetro> createPulgadaAMetro(PulgadaAMetro value) {
        return new JAXBElement<PulgadaAMetro>(_PulgadaAMetro_QNAME, PulgadaAMetro.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PulgadaAMetroResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PulgadaAMetroResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "PulgadaAMetroResponse")
    public JAXBElement<PulgadaAMetroResponse> createPulgadaAMetroResponse(PulgadaAMetroResponse value) {
        return new JAXBElement<PulgadaAMetroResponse>(_PulgadaAMetroResponse_QNAME, PulgadaAMetroResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PulgadaAMilla }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PulgadaAMilla }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "PulgadaAMilla")
    public JAXBElement<PulgadaAMilla> createPulgadaAMilla(PulgadaAMilla value) {
        return new JAXBElement<PulgadaAMilla>(_PulgadaAMilla_QNAME, PulgadaAMilla.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PulgadaAMillaResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PulgadaAMillaResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "PulgadaAMillaResponse")
    public JAXBElement<PulgadaAMillaResponse> createPulgadaAMillaResponse(PulgadaAMillaResponse value) {
        return new JAXBElement<PulgadaAMillaResponse>(_PulgadaAMillaResponse_QNAME, PulgadaAMillaResponse.class, null, value);
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
