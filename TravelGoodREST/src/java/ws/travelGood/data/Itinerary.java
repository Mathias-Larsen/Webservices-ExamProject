/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelGood.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Jesper
 */
@XmlRootElement()
public class Itinerary {
    private XMLGregorianCalendar date;
    private String status;
    private List<Booking> bookings = new ArrayList<Booking>();

    

    public XMLGregorianCalendar getDate() {
        return date;
    }

    public void setDate(XMLGregorianCalendar date) {
        this.date = date;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public String getStatus()
    {
        return status;
    }


}
