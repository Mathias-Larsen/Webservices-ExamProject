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
public class Address {
    private String street;
    private String city;
    private String zip;
    private String country;
    
    public String getStreet()
    {
        return street;
    }
    public String getCity()
    {
        return city;
    }
    public String getZip()
    {
        return zip;
    }
    public String getCountry()
    {
        return country;
    }
    
    public void setStreet(String street)
    {
        this.street = street;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public void setZip(String zip)
    {
        this.zip = zip;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }
}
