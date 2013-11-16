package ws.travelGood.representation;

import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Jesper
 */
@XmlRootElement()
public class BookingStatusRepresentation extends Representation {

    private String bookingStatus;

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }


}
