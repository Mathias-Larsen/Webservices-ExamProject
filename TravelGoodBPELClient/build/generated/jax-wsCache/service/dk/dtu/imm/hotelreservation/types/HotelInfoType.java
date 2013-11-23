
package dk.dtu.imm.hotelreservation.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for hotelInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="hotelInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="hotelName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address" type="{urn://types.hotelReservation.imm.dtu.dk}addressType"/>
 *         &lt;element name="bookingNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="creditCardGuaranteeRequired" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="nameOfHotelReservationService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hotelInfoType", propOrder = {
    "hotelName",
    "address",
    "bookingNumber",
    "price",
    "creditCardGuaranteeRequired",
    "nameOfHotelReservationService"
})
public class HotelInfoType {

    @XmlElement(required = true)
    protected String hotelName;
    @XmlElement(required = true)
    protected AddressType address;
    protected int bookingNumber;
    protected int price;
    protected boolean creditCardGuaranteeRequired;
    @XmlElement(required = true)
    protected String nameOfHotelReservationService;

    /**
     * Gets the value of the hotelName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHotelName() {
        return hotelName;
    }

    /**
     * Sets the value of the hotelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHotelName(String value) {
        this.hotelName = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setAddress(AddressType value) {
        this.address = value;
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
     * Gets the value of the creditCardGuaranteeRequired property.
     * 
     */
    public boolean isCreditCardGuaranteeRequired() {
        return creditCardGuaranteeRequired;
    }

    /**
     * Sets the value of the creditCardGuaranteeRequired property.
     * 
     */
    public void setCreditCardGuaranteeRequired(boolean value) {
        this.creditCardGuaranteeRequired = value;
    }

    /**
     * Gets the value of the nameOfHotelReservationService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameOfHotelReservationService() {
        return nameOfHotelReservationService;
    }

    /**
     * Sets the value of the nameOfHotelReservationService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameOfHotelReservationService(String value) {
        this.nameOfHotelReservationService = value;
    }

}
