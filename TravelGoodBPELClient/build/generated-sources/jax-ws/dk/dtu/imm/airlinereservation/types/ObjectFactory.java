
package dk.dtu.imm.airlinereservation.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the dk.dtu.imm.airlinereservation.types package. 
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

    private final static QName _BookFlight_QNAME = new QName("urn://types.airlineReservation.imm.dtu.dk", "bookFlight");
    private final static QName _BookFlightFault_QNAME = new QName("urn://types.airlineReservation.imm.dtu.dk", "bookFlightFault");
    private final static QName _BookFlightResponse_QNAME = new QName("urn://types.airlineReservation.imm.dtu.dk", "bookFlightResponse");
    private final static QName _GetFlights_QNAME = new QName("urn://types.airlineReservation.imm.dtu.dk", "getFlights");
    private final static QName _GetFlightsResponse_QNAME = new QName("urn://types.airlineReservation.imm.dtu.dk", "getFlightsResponse");
    private final static QName _CancelFlightResponse_QNAME = new QName("urn://types.airlineReservation.imm.dtu.dk", "cancelFlightResponse");
    private final static QName _CancelFlight_QNAME = new QName("urn://types.airlineReservation.imm.dtu.dk", "cancelFlight");
    private final static QName _CancelFlightFault_QNAME = new QName("urn://types.airlineReservation.imm.dtu.dk", "cancelFlightFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: dk.dtu.imm.airlinereservation.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BookFlightFault }
     * 
     */
    public BookFlightFault createBookFlightFault() {
        return new BookFlightFault();
    }

    /**
     * Create an instance of {@link FlightInfoArray }
     * 
     */
    public FlightInfoArray createFlightInfoArray() {
        return new FlightInfoArray();
    }

    /**
     * Create an instance of {@link CancelFlightResponse }
     * 
     */
    public CancelFlightResponse createCancelFlightResponse() {
        return new CancelFlightResponse();
    }

    /**
     * Create an instance of {@link BookFlight }
     * 
     */
    public BookFlight createBookFlight() {
        return new BookFlight();
    }

    /**
     * Create an instance of {@link GetFlights }
     * 
     */
    public GetFlights createGetFlights() {
        return new GetFlights();
    }

    /**
     * Create an instance of {@link BookFlightResponse }
     * 
     */
    public BookFlightResponse createBookFlightResponse() {
        return new BookFlightResponse();
    }

    /**
     * Create an instance of {@link CancelFlight }
     * 
     */
    public CancelFlight createCancelFlight() {
        return new CancelFlight();
    }

    /**
     * Create an instance of {@link CancelFlightFault }
     * 
     */
    public CancelFlightFault createCancelFlightFault() {
        return new CancelFlightFault();
    }

    /**
     * Create an instance of {@link ExpirationDateType }
     * 
     */
    public ExpirationDateType createExpirationDateType() {
        return new ExpirationDateType();
    }

    /**
     * Create an instance of {@link FlightType }
     * 
     */
    public FlightType createFlightType() {
        return new FlightType();
    }

    /**
     * Create an instance of {@link FlightInformationType }
     * 
     */
    public FlightInformationType createFlightInformationType() {
        return new FlightInformationType();
    }

    /**
     * Create an instance of {@link CreditCardInfoType }
     * 
     */
    public CreditCardInfoType createCreditCardInfoType() {
        return new CreditCardInfoType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookFlight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.airlineReservation.imm.dtu.dk", name = "bookFlight")
    public JAXBElement<BookFlight> createBookFlight(BookFlight value) {
        return new JAXBElement<BookFlight>(_BookFlight_QNAME, BookFlight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookFlightFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.airlineReservation.imm.dtu.dk", name = "bookFlightFault")
    public JAXBElement<BookFlightFault> createBookFlightFault(BookFlightFault value) {
        return new JAXBElement<BookFlightFault>(_BookFlightFault_QNAME, BookFlightFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookFlightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.airlineReservation.imm.dtu.dk", name = "bookFlightResponse")
    public JAXBElement<BookFlightResponse> createBookFlightResponse(BookFlightResponse value) {
        return new JAXBElement<BookFlightResponse>(_BookFlightResponse_QNAME, BookFlightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFlights }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.airlineReservation.imm.dtu.dk", name = "getFlights")
    public JAXBElement<GetFlights> createGetFlights(GetFlights value) {
        return new JAXBElement<GetFlights>(_GetFlights_QNAME, GetFlights.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FlightInfoArray }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.airlineReservation.imm.dtu.dk", name = "getFlightsResponse")
    public JAXBElement<FlightInfoArray> createGetFlightsResponse(FlightInfoArray value) {
        return new JAXBElement<FlightInfoArray>(_GetFlightsResponse_QNAME, FlightInfoArray.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelFlightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.airlineReservation.imm.dtu.dk", name = "cancelFlightResponse")
    public JAXBElement<CancelFlightResponse> createCancelFlightResponse(CancelFlightResponse value) {
        return new JAXBElement<CancelFlightResponse>(_CancelFlightResponse_QNAME, CancelFlightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelFlight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.airlineReservation.imm.dtu.dk", name = "cancelFlight")
    public JAXBElement<CancelFlight> createCancelFlight(CancelFlight value) {
        return new JAXBElement<CancelFlight>(_CancelFlight_QNAME, CancelFlight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelFlightFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.airlineReservation.imm.dtu.dk", name = "cancelFlightFault")
    public JAXBElement<CancelFlightFault> createCancelFlightFault(CancelFlightFault value) {
        return new JAXBElement<CancelFlightFault>(_CancelFlightFault_QNAME, CancelFlightFault.class, null, value);
    }

}
