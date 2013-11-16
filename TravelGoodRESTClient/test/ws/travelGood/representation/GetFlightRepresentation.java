/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelGood.representation;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import ws.travelGood.data.FlightInfo;

/**
 *
 * @author Jesper
 */
@XmlRootElement()
public class GetFlightRepresentation extends Representation {
    private List<FlightInfo> flightInfo;
    
    public List<FlightInfo>  getFlightInfo()
    {
        return flightInfo;
    }
    public void setFlightInfo(List<FlightInfo> flightInfo)
    {
        this.flightInfo = flightInfo;
    }
    
}
