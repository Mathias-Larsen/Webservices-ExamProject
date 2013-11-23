
package dk.dtu.imm.hotelreservation.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the dk.dtu.imm.hotelreservation.types package. 
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

    private final static QName _CancelHotel_QNAME = new QName("urn://types.hotelReservation.imm.dtu.dk", "cancelHotel");
    private final static QName _BookHotelFault_QNAME = new QName("urn://types.hotelReservation.imm.dtu.dk", "bookHotelFault");
    private final static QName _BookHotelResponse_QNAME = new QName("urn://types.hotelReservation.imm.dtu.dk", "bookHotelResponse");
    private final static QName _GetHotels_QNAME = new QName("urn://types.hotelReservation.imm.dtu.dk", "getHotels");
    private final static QName _GetHotelsResponse_QNAME = new QName("urn://types.hotelReservation.imm.dtu.dk", "getHotelsResponse");
    private final static QName _CancelHotelFault_QNAME = new QName("urn://types.hotelReservation.imm.dtu.dk", "cancelHotelFault");
    private final static QName _CancelHotelResponse_QNAME = new QName("urn://types.hotelReservation.imm.dtu.dk", "cancelHotelResponse");
    private final static QName _BookHotel_QNAME = new QName("urn://types.hotelReservation.imm.dtu.dk", "bookHotel");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: dk.dtu.imm.hotelreservation.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CancelHotel }
     * 
     */
    public CancelHotel createCancelHotel() {
        return new CancelHotel();
    }

    /**
     * Create an instance of {@link BookHotelFault }
     * 
     */
    public BookHotelFault createBookHotelFault() {
        return new BookHotelFault();
    }

    /**
     * Create an instance of {@link BookHotelResponse }
     * 
     */
    public BookHotelResponse createBookHotelResponse() {
        return new BookHotelResponse();
    }

    /**
     * Create an instance of {@link GetHotels }
     * 
     */
    public GetHotels createGetHotels() {
        return new GetHotels();
    }

    /**
     * Create an instance of {@link HotelInfoArray }
     * 
     */
    public HotelInfoArray createHotelInfoArray() {
        return new HotelInfoArray();
    }

    /**
     * Create an instance of {@link CancelHotelFault }
     * 
     */
    public CancelHotelFault createCancelHotelFault() {
        return new CancelHotelFault();
    }

    /**
     * Create an instance of {@link CancelHotelResponse }
     * 
     */
    public CancelHotelResponse createCancelHotelResponse() {
        return new CancelHotelResponse();
    }

    /**
     * Create an instance of {@link BookHotel }
     * 
     */
    public BookHotel createBookHotel() {
        return new BookHotel();
    }

    /**
     * Create an instance of {@link HotelInfoType }
     * 
     */
    public HotelInfoType createHotelInfoType() {
        return new HotelInfoType();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link ExpirationDateType }
     * 
     */
    public ExpirationDateType createExpirationDateType() {
        return new ExpirationDateType();
    }

    /**
     * Create an instance of {@link HotelBookingType }
     * 
     */
    public HotelBookingType createHotelBookingType() {
        return new HotelBookingType();
    }

    /**
     * Create an instance of {@link CreditCardInfoType }
     * 
     */
    public CreditCardInfoType createCreditCardInfoType() {
        return new CreditCardInfoType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelHotel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.hotelReservation.imm.dtu.dk", name = "cancelHotel")
    public JAXBElement<CancelHotel> createCancelHotel(CancelHotel value) {
        return new JAXBElement<CancelHotel>(_CancelHotel_QNAME, CancelHotel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookHotelFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.hotelReservation.imm.dtu.dk", name = "bookHotelFault")
    public JAXBElement<BookHotelFault> createBookHotelFault(BookHotelFault value) {
        return new JAXBElement<BookHotelFault>(_BookHotelFault_QNAME, BookHotelFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookHotelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.hotelReservation.imm.dtu.dk", name = "bookHotelResponse")
    public JAXBElement<BookHotelResponse> createBookHotelResponse(BookHotelResponse value) {
        return new JAXBElement<BookHotelResponse>(_BookHotelResponse_QNAME, BookHotelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotels }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.hotelReservation.imm.dtu.dk", name = "getHotels")
    public JAXBElement<GetHotels> createGetHotels(GetHotels value) {
        return new JAXBElement<GetHotels>(_GetHotels_QNAME, GetHotels.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HotelInfoArray }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.hotelReservation.imm.dtu.dk", name = "getHotelsResponse")
    public JAXBElement<HotelInfoArray> createGetHotelsResponse(HotelInfoArray value) {
        return new JAXBElement<HotelInfoArray>(_GetHotelsResponse_QNAME, HotelInfoArray.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelHotelFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.hotelReservation.imm.dtu.dk", name = "cancelHotelFault")
    public JAXBElement<CancelHotelFault> createCancelHotelFault(CancelHotelFault value) {
        return new JAXBElement<CancelHotelFault>(_CancelHotelFault_QNAME, CancelHotelFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelHotelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.hotelReservation.imm.dtu.dk", name = "cancelHotelResponse")
    public JAXBElement<CancelHotelResponse> createCancelHotelResponse(CancelHotelResponse value) {
        return new JAXBElement<CancelHotelResponse>(_CancelHotelResponse_QNAME, CancelHotelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookHotel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn://types.hotelReservation.imm.dtu.dk", name = "bookHotel")
    public JAXBElement<BookHotel> createBookHotel(BookHotel value) {
        return new JAXBElement<BookHotel>(_BookHotel_QNAME, BookHotel.class, null, value);
    }

}
