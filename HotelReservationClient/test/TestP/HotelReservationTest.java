/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TestP;

import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import dk.dtu.imm.fastmoney.types.ExpirationDateType;
import dk.dtu.imm.hotelreservation.BookHotelFaultMessage;
import dk.dtu.imm.hotelreservation.CancelHotelFaultMessage;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Mathias
 */
public class HotelReservationTest {
    
    public HotelReservationTest() {
    }
    CreditCardInfoType creditcard;
    ExpirationDateType ex;
    @Before
    public void create()
    {
       ex = new ExpirationDateType();
       ex.setMonth(7);
       ex.setYear(9);
       //ex.setMonth(5);
       //ex.setYear(9);
       creditcard = new CreditCardInfoType();
       creditcard.setName("Bech Camilla");
       creditcard.setNumber("50408822");
       
       //creditcard.setName("Anne Strandberg");
       //creditcard.setNumber("50408816");
       creditcard.setExpirationDate(ex);
    }
    @Test
    public void testGetHotels() {
        DatatypeFactory df;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            return;
        }
        XMLGregorianCalendar date = df.newXMLGregorianCalendar("2013-12-25");
        String start = "Moscow";
        String end = "Berlin";
        int num = getHotels(start,date,date).size();
        assertEquals(2,num);
        
         num = getHotels(end,date,date).size();
        assertEquals(1,num);
    } 
    @Test
    public void testBookHotel() {
        try {
            boolean toReturn = bookHotel(12345,creditcard);
            assertEquals(true,toReturn);
        } catch (BookHotelFaultMessage ex) {
            assertEquals("Invalid booking number",ex.getFaultInfo().getMessage());
        }
        
    }

    private static boolean bookHotel(int bookingNumber, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo) throws BookHotelFaultMessage {
        dk.dtu.imm.hotelreservation.HotelReservationService service = new dk.dtu.imm.hotelreservation.HotelReservationService();
        dk.dtu.imm.hotelreservation.HotelReservationPortType port = service.getHotelReservationPortTypeBindingPort();
        return port.bookHotel(bookingNumber, creditCardInfo);
    }

    private static boolean cancelHotel(int bookingNumber, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCard) throws CancelHotelFaultMessage {
        dk.dtu.imm.hotelreservation.HotelReservationService service = new dk.dtu.imm.hotelreservation.HotelReservationService();
        dk.dtu.imm.hotelreservation.HotelReservationPortType port = service.getHotelReservationPortTypeBindingPort();
        return port.cancelHotel(bookingNumber, creditCard);
    }

    private static java.util.List<dk.dtu.imm.hotelreservation.types.HotelInfoType> getHotels(java.lang.String city, javax.xml.datatype.XMLGregorianCalendar arrivalDate, javax.xml.datatype.XMLGregorianCalendar departureDate) {
        dk.dtu.imm.hotelreservation.HotelReservationService service = new dk.dtu.imm.hotelreservation.HotelReservationService();
        dk.dtu.imm.hotelreservation.HotelReservationPortType port = service.getHotelReservationPortTypeBindingPort();
        return port.getHotels(city, arrivalDate, departureDate);
    }
}