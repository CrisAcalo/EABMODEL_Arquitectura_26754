/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ec.edu.monster.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * REST Web Service
 *
 * @author crist
 */
@Path("longitudserviceport")
public class LongitudServicePort {

    private ec.edu.monster.ws_client.LongitudService port;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LongitudServicePort
     */
    public LongitudServicePort() {
        port = getPort();
    }

    /**
     * Invokes the SOAP method MillaAMetro
     * @param millas resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<ec.edu.monster.ws_client.ConversionResult>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("millaametro/")
    public JAXBElement<ec.edu.monster.ws_client.ConversionResult> getMillaAMetro(@QueryParam("millas") String millas) {
        try {
            // Call Web Service Operation
            if (port != null) {
                ec.edu.monster.ws_client.ConversionResult result = port.millaAMetro(millas);
                return new JAXBElement<ec.edu.monster.ws_client.ConversionResult>(new QName("http//ws_client.monster.edu.ec/", "conversionresult"), ec.edu.monster.ws_client.ConversionResult.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method MetroAMilla
     * @param metros resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<ec.edu.monster.ws_client.ConversionResult>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("metroamilla/")
    public JAXBElement<ec.edu.monster.ws_client.ConversionResult> getMetroAMilla(@QueryParam("metros") String metros) {
        try {
            // Call Web Service Operation
            if (port != null) {
                ec.edu.monster.ws_client.ConversionResult result = port.metroAMilla(metros);
                return new JAXBElement<ec.edu.monster.ws_client.ConversionResult>(new QName("http//ws_client.monster.edu.ec/", "conversionresult"), ec.edu.monster.ws_client.ConversionResult.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method MillaAPulgada
     * @param millas resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<ec.edu.monster.ws_client.ConversionResult>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("millaapulgada/")
    public JAXBElement<ec.edu.monster.ws_client.ConversionResult> getMillaAPulgada(@QueryParam("millas") String millas) {
        try {
            // Call Web Service Operation
            if (port != null) {
                ec.edu.monster.ws_client.ConversionResult result = port.millaAPulgada(millas);
                return new JAXBElement<ec.edu.monster.ws_client.ConversionResult>(new QName("http//ws_client.monster.edu.ec/", "conversionresult"), ec.edu.monster.ws_client.ConversionResult.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method PulgadaAMilla
     * @param pulgadas resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<ec.edu.monster.ws_client.ConversionResult>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("pulgadaamilla/")
    public JAXBElement<ec.edu.monster.ws_client.ConversionResult> getPulgadaAMilla(@QueryParam("pulgadas") String pulgadas) {
        try {
            // Call Web Service Operation
            if (port != null) {
                ec.edu.monster.ws_client.ConversionResult result = port.pulgadaAMilla(pulgadas);
                return new JAXBElement<ec.edu.monster.ws_client.ConversionResult>(new QName("http//ws_client.monster.edu.ec/", "conversionresult"), ec.edu.monster.ws_client.ConversionResult.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method MetroAPulgada
     * @param metros resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<ec.edu.monster.ws_client.ConversionResult>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("metroapulgada/")
    public JAXBElement<ec.edu.monster.ws_client.ConversionResult> getMetroAPulgada(@QueryParam("metros") String metros) {
        try {
            // Call Web Service Operation
            if (port != null) {
                ec.edu.monster.ws_client.ConversionResult result = port.metroAPulgada(metros);
                return new JAXBElement<ec.edu.monster.ws_client.ConversionResult>(new QName("http//ws_client.monster.edu.ec/", "conversionresult"), ec.edu.monster.ws_client.ConversionResult.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method PulgadaAMetro
     * @param pulgadas resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<ec.edu.monster.ws_client.ConversionResult>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("pulgadaametro/")
    public JAXBElement<ec.edu.monster.ws_client.ConversionResult> getPulgadaAMetro(@QueryParam("pulgadas") String pulgadas) {
        try {
            // Call Web Service Operation
            if (port != null) {
                ec.edu.monster.ws_client.ConversionResult result = port.pulgadaAMetro(pulgadas);
                return new JAXBElement<ec.edu.monster.ws_client.ConversionResult>(new QName("http//ws_client.monster.edu.ec/", "conversionresult"), ec.edu.monster.ws_client.ConversionResult.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     *
     */
    private ec.edu.monster.ws_client.LongitudService getPort() {
        try {
            // Call Web Service Operation
            ec.edu.monster.ws_client.LongitudService_Service service = new ec.edu.monster.ws_client.LongitudService_Service();
            ec.edu.monster.ws_client.LongitudService p = service.getLongitudServicePort();
            return p;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }
}
