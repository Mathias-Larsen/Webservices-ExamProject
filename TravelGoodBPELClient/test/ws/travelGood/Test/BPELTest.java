/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelGood.Test;

import dk.dtu.imm.airlinereservation.types.FlightInfoArray;
import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import dk.dtu.imm.fastmoney.types.ExpirationDateType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ws.travelgood.ItineraryType;

/**
 *
 * @author Jesper
 */
public class BPELTest {
    CreditCardInfoType creditcard;
    ExpirationDateType ex;    
    @Before
    public void create()
    {
       ex = new ExpirationDateType();
       ex.setMonth(5);
       ex.setYear(9);
       creditcard = new CreditCardInfoType();
       creditcard.setName("Anne Strandberg");
       creditcard.setNumber("50408816");
       creditcard.setExpirationDate(ex);
    }
    
    public BPELTest() {
    }
   //@Test
    public void createTwoItinerarys() {
        boolean ok =  createItinerary(1, 2);
        assertEquals(true,ok);
        boolean ok1 = cancelPlanning(1,2);
        assertEquals(true,ok1);
        boolean ok2 =  createItinerary(1, 2);
        assertEquals(true,ok2);
    }
   @Test
    public void testGetflights() {
        boolean ok =  createItinerary(1, 2);
        assertEquals(true,ok);
       DatatypeFactory df;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            return;
        }
        XMLGregorianCalendar date = df.newXMLGregorianCalendar("2013-12-25");
        String start = "Moscow";
        String end = "Berlin";
        //Get flight 
        FlightInfoArray info = getFlights(start,end,date,1,2);
        assertEquals(2,info.getFlightInformation().size());
       
       //adding the two flights
       boolean ok2 = addFlight(info.getFlightInformation().get(0).getBookingNumber(), 1, 2);
       boolean ok3 = addFlight(info.getFlightInformation().get(1).getBookingNumber(), 1, 2);
       assertEquals(true,ok2);
       assertEquals(true,ok3);
       
       //Getting the itinerary
       ItineraryType itinerary = getItinerary(1, 2);
       assertEquals(2,itinerary.getFlight().size());
       
       //Checking the two flights consits with the itinerary
       assertEquals(info.getFlightInformation().get(0).getBookingNumber(),itinerary.getFlight().get(0).getBookingNumber());
       assertEquals("unconfirmed",itinerary.getFlight().get(0).getStatus());
       System.out.println(itinerary.getFlight().get(0).getBookingNumber());
       
       assertEquals(info.getFlightInformation().get(1).getBookingNumber(),itinerary.getFlight().get(1).getBookingNumber());
       assertEquals("unconfirmed",itinerary.getFlight().get(1).getStatus());
       System.out.println(itinerary.getFlight().get(1).getBookingNumber());
       
       
       //Book the itinerary
       boolean book =  bookItinerary(1,creditcard,2);
       assertEquals(true,book);
       
       //Getting the itinerary and check that it is "confirmed"
       itinerary = getItinerary(1, 2);
       assertEquals(2,itinerary.getFlight().size());       
       assertEquals("confirmed",itinerary.getFlight().get(0).getStatus());
       assertEquals("confirmed",itinerary.getFlight().get(1).getStatus());
       
       
    }
    private static boolean createItinerary(int customerId, int bookingNumber) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.createItinerary(customerId, bookingNumber);
    }

    private static FlightInfoArray getFlights(java.lang.String start, java.lang.String end, javax.xml.datatype.XMLGregorianCalendar date, int customerId, int itineraryId) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.getFlights(start, end, date, customerId, itineraryId);
    }

    private static boolean addFlight(int bookingNumber, int customerId, int itineraryId) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.addFlight(bookingNumber, customerId, itineraryId);
    }

    private static ItineraryType getItinerary(int customerId, int itineraryId) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.getItinerary(customerId, itineraryId);
    }

    private static boolean cancelItinerary(int customerId, int itineraryId, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditcard) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.cancelItinerary(customerId, itineraryId, creditcard);
    }

    private static boolean bookItinerary(int customerId, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditcard, int itineraryId) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.bookItinerary(customerId, creditcard, itineraryId);
    }

    private static boolean cancelPlanning(int customerId, int itineraryId) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.cancelPlanning(customerId, itineraryId);
    }


}
