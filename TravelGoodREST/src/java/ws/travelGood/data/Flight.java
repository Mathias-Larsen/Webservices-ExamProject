/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelGood.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author All
 */
@XmlRootElement()
public class Flight {
    private String start;
    private String end;
    private XMLGregorianCalendar dateStart;
    private XMLGregorianCalendar dateEnd;
    private String carrier;
    
    public void setStart(String start)
    {
        this.start = start;
    }
    public void setEnd(String end)
    {
        this.end = end;
    }
    public void setDateStart(XMLGregorianCalendar dateStart)
    {
        this.dateStart = dateStart;
    }
    public void setDateEnd(XMLGregorianCalendar dateEnd)
    {
        this.dateEnd = dateEnd;
    }
    public void setCarrier(String carrier)
    {
        this.carrier = carrier;
    }
    public String getStart()
    {
        return start;
    }
    public String getEnd()
    {
        return end;
    }
    public XMLGregorianCalendar getDateStart()
    {
        return dateStart;
    }
    public XMLGregorianCalendar getDateEnd()
    {
        return dateEnd;
    }
    public String getCarrier()
    {
        return carrier;
    }
}