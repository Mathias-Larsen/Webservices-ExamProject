/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelGood.data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author All
 */
@XmlRootElement()
public class Booking {
    private int bookingNumber;
    private String status = "unconfirmed";
    private String bookingType;

    /**
     * @return the bookingNumber
     */
    @XmlAttribute()
    public int getBookingNumber() {
        return bookingNumber;
    }

    /**
     * @param bookingNumber the bookingNumber to set
     */
    public void setBookingNumber(int bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    /**
     * @return the status
     */
    @XmlAttribute()
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    public void setBookingType(String bookingType)
    {
        this.bookingType=bookingType;
    }
    public String getBookingType()
    {
        return bookingType;
    }
            

}
