
package ws.travelgood;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for itineraryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="itineraryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bookings" type="{http://travelGood.ws}bookingType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="itineraryStartDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "itineraryType", propOrder = {
    "bookings",
    "itineraryStartDate",
    "status"
})
public class ItineraryType {

    protected List<BookingType> bookings;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar itineraryStartDate;
    @XmlElement(required = true)
    protected String status;

    /**
     * Gets the value of the bookings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bookings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBookings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookingType }
     * 
     * 
     */
    public List<BookingType> getBookings() {
        if (bookings == null) {
            bookings = new ArrayList<BookingType>();
        }
        return this.bookings;
    }

    /**
     * Gets the value of the itineraryStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getItineraryStartDate() {
        return itineraryStartDate;
    }

    /**
     * Sets the value of the itineraryStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setItineraryStartDate(XMLGregorianCalendar value) {
        this.itineraryStartDate = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
