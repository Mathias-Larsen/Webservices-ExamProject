package ws.travelGood.representation;

import javax.xml.bind.annotation.XmlRootElement;
import ws.travelGood.data.Itinerary;
/**
 * @author Jesper
 */
@XmlRootElement()
public class ItineraryRepresentation extends Representation {

    private Itinerary itineary;

    public Itinerary getItineary() {
        return itineary;
    }

    public void setItineary(Itinerary itineary) {
        this.itineary = itineary;
    }


}
