
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
 *         &lt;element name="hotels" type="{http://travelGood.ws}bookingType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="flight" type="{http://travelGood.ws}bookingType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="itineraryStartDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
    "hotels",
    "flight",
    "itineraryStartDate"
})
public class ItineraryType {

    protected List<BookingType> hotels;
    protected List<BookingType> flight;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar itineraryStartDate;

    /**
     * Gets the value of the hotels property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hotels property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHotels().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookingType }
     * 
     * 
     */
    public List<BookingType> getHotels() {
        if (hotels == null) {
            hotels = new ArrayList<BookingType>();
        }
        return this.hotels;
    }

    /**
     * Gets the value of the flight property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flight property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlight().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookingType }
     * 
     * 
     */
    public List<BookingType> getFlight() {
        if (flight == null) {
            flight = new ArrayList<BookingType>();
        }
        return this.flight;
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

}
