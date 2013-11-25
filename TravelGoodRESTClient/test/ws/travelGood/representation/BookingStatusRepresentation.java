package ws.travelGood.representation;

import javax.xml.bind.annotation.XmlRootElement;
import ws.travelGood.data.Itinerary;
/**
 *
 * @author Jesper
 */
@XmlRootElement()
public class BookingStatusRepresentation extends Representation {

    private String bookingStatus;
    private Itinerary it;
    
    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public void setIt(Itinerary it)
    {
        this.it = it;
    }
    public Itinerary getIt()
    {
        return it;
    }


}
