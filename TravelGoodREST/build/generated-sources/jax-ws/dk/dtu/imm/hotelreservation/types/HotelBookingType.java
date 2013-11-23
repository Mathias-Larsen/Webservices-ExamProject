
package dk.dtu.imm.hotelreservation.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for hotelBookingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="hotelBookingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hotelAddress" type="{urn://types.hotelReservation.imm.dtu.dk}addressType"/>
 *         &lt;element name="bookingNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="creditCardReq" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hotelResServiceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hotelBookingType", propOrder = {
    "name",
    "hotelAddress",
    "bookingNumber",
    "price",
    "creditCardReq",
    "hotelResServiceName"
})
public class HotelBookingType {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected AddressType hotelAddress;
    protected int bookingNumber;
    protected int price;
    protected boolean creditCardReq;
    @XmlElement(required = true)
    protected String hotelResServiceName;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the hotelAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getHotelAddress() {
        return hotelAddress;
    }

    /**
     * Sets the value of the hotelAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setHotelAddress(AddressType value) {
        this.hotelAddress = value;
    }

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
     * Gets the value of the creditCardReq property.
     * 
     */
    public boolean isCreditCardReq() {
        return creditCardReq;
    }

    /**
     * Sets the value of the creditCardReq property.
     * 
     */
    public void setCreditCardReq(boolean value) {
        this.creditCardReq = value;
    }

    /**
     * Gets the value of the hotelResServiceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHotelResServiceName() {
        return hotelResServiceName;
    }

    /**
     * Sets the value of the hotelResServiceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHotelResServiceName(String value) {
        this.hotelResServiceName = value;
    }

}
