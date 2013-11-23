/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelGood.representation;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import ws.travelGood.data.HotelInfo;

/**
 *
 * @author Jesper
 */
@XmlRootElement()
public class GetHotelRepresentation extends Representation {
    private List<HotelInfo> hotelInfo;
    
    public List<HotelInfo>  getHotelInfo()
    {
        return hotelInfo;
    }
    public void setHotelInfo(List<HotelInfo> hotelInfo)
    {
        this.hotelInfo = hotelInfo;
    }
    
}
