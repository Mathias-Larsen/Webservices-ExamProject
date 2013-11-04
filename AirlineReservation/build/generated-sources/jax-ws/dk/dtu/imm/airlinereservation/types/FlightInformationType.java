
package dk.dtu.imm.airlinereservation.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for flightInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="flightInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bookingNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reservationServiceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flight" type="{urn://types.airlineReservation.imm.dtu.dk}flightType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "flightInformationType", propOrder = {
    "bookingNumber",
    "price",
    "reservationServiceName",
    "flight"
})
public class FlightInformationType {

    protected int bookingNumber;
    protected int price;
    @XmlElement(required = true)
    protected String reservationServiceName;
    @XmlElement(required = true)
    protected FlightType flight;

    /**
     * Gets the value of the bookingNumber property.
     * 
     */
    public int getBookingNumber() {
        return bookingNumber;
    }

    /**
     * Sets the value of the bookingNumber property.
     * 
     */
    public void setBookingNumber(int value) {
        this.bookingNumber = value;
    }

    /**
     * Gets the value of the price property.
     * 
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     */
    public void setPrice(int value) {
        this.price = value;
    }

    /**
     * Gets the value of the reservationServiceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReservationServiceName() {
        return reservationServiceName;
    }

    /**
     * Sets the value of the reservationServiceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReservationServiceName(String value) {
        this.reservationServiceName = value;
    }

    /**
     * Gets the value of the flight property.
     * 
     * @return
     *     possible object is
     *     {@link FlightType }
     *     
     */
    public FlightType getFlight() {
        return flight;
    }

    /**
     * Sets the value of the flight property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlightType }
     *     
     */
    public void setFlight(FlightType value) {
        this.flight = value;
    }

}
