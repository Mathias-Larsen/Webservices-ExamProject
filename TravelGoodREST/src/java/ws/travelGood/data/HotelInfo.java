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
public class HotelInfo {
    private String hotelName;
    private Address address;
    private int bookingNumber;
    private int price;
    private boolean creditCardGuaranteed;
    private String hotelReservationService;
    
    public String getHotelName()
    {
        return hotelName;
    }
    public Address getAddress()
    {
        return address;
    }
    public int getBookingNumber()
    {
        return bookingNumber;
    }
    public int getPrice()
    {
        return price;
    }
    public boolean getCreditCardGuaranteed()
    {
         return creditCardGuaranteed;
    }
    public String getHotelReservationService()
    {
        return hotelReservationService;
    }
        
    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
    }
    public void setAddress(Address address)
    {
        this.address = address;
    }
    public void setBookingNumber(int bookingNumber)
    {
        this.bookingNumber = bookingNumber;
    }
    public void setPrice(int price)
    {
        this.price = price;
    }
    public void setCreditCardGuaranteed(boolean creditCardGuaranteed)
    {
         this.creditCardGuaranteed = creditCardGuaranteed;
    }
    public void setHotelReservationService(String hotelReservationService)
    {
        this.hotelReservationService = hotelReservationService;
    }
}
