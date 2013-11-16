/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelGood.data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jesper
 */
@XmlRootElement()
public class FlightInfo {
    private int bookingNumber;
    private int price;
    private String reservationServiceName;
    private Flight flight;
    
    public void setBookingNumber(int bookingNumber)
    {
        this.bookingNumber = bookingNumber;
    }
    public void setPrice(int price)
    {
        this.price = price;
    }
    public void setReservationServiceName(String reservationServiceName)
    {
        this.reservationServiceName = reservationServiceName;
    }
    public void setFlight(Flight flight)
    {
        this.flight = flight;
    }
    public int getBookingNumber()
    {
        return bookingNumber;
    }
    public int getPrice()
    {
        return price;
    }
    public String getReservationServiceName()
    {
        return reservationServiceName;
    }
    public Flight getFlight()
    {
        return flight;
    }
}
