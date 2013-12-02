/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelGood.data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author All
 */
@XmlRootElement()
public class ExpirationDateType {
    int month;
    int year;
    
    public int getMonth()
    {
        return month;
    }
    public int getYear()
    {
        return year;
    }
    public void setMonth(int month)
    {
        this.month = month;
    }
    public void setYear(int year)
    {
        this.year = year;
    }
}
