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
    XMLGregorianCalendar date;
    DatatypeFactory df;
    String start;
    String end;
    private static int id = 0;
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
       
        DatatypeFactory df;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            return;
        }
        date = df.newXMLGregorianCalendar("2013-12-25");
        start = "Moscow";
        end = "Berlin";
    }
    
    public BPELTest() {
    }
    public int getNextNum()
    {
        return id++;
    }
    
    /**
    *Test P1 plans a trip by getting a list of hotels and flights, adds them to the itinerary,
    * and books them.
    * There should not be any fails in the proces. 
    */    
    @Test
    public void P1()
    {
        int cId = getNextNum();
        boolean ok =  createItinerary(cId, 2);
        assertEquals(true,ok);
        
        //Get the flights
        FlightInfoArray info = getFlights(start,end,date,cId,2);
        assertEquals(3,info.getFlightInformation().size());
        
       boolean ok2 = addBooking(info.getFlightInformation().get(0).getBookingNumber(),"flight",info.getFlightInformation().get(0).getFlight().getDateStart(), cId, 2);
       boolean ok3 = addBooking(info.getFlightInformation().get(0).getBookingNumber(),"flight",info.getFlightInformation().get(0).getFlight().getDateStart(), cId, 2);
       assertEquals(true,ok2);
       assertEquals(true,ok3);
       
       ItineraryType itinerary = getItinerary(cId, 2);
       assertEquals("planning",itinerary.getStatus());
       assertEquals(2,itinerary.getBookings().size());
       System.out.println(itinerary.getItineraryStartDate());
       for (ws.travelgood.BookingType fl : itinerary.getBookings())
       {
           assertEquals("unconfirmed",fl.getStatus());
       }
       
       //Book the itinerary
       boolean book =  bookItinerary(cId,creditcard,2);
       assertEquals(true,book);
       
       //check that the bookings is confirmed by getting the itinerary again.
       itinerary = getItinerary(cId, 2);
       assertEquals("booked",itinerary.getStatus());
       for (ws.travelgood.BookingType fl : itinerary.getBookings())
       {
           assertEquals("confirmed",fl.getStatus());
       }
    }
     /**
    *Test P2 plans a trip by getting a list of flights and adds one of them to the itinerary,
    * After adding the flight, the itinerary planning should be cancelled, and the itinerary removed. 
    * There should not be any fails in the proces. 
    */
    @Test
    public void P2()
    {
        int cId = getNextNum();
        boolean ok =  createItinerary(cId, 2);
        assertEquals(true,ok);
        
        //Get the flights
        FlightInfoArray info = getFlights(start,end,date,cId,2);
        assertEquals(3,info.getFlightInformation().size());
        
        ok = addBooking(info.getFlightInformation().get(0).getBookingNumber(),"flight",info.getFlightInformation().get(0).getFlight().getDateStart(), cId, 2);
        assertEquals(true,ok);
        
        ItineraryType itinerary = getItinerary(cId, 2);
        assertEquals("planning",itinerary.getStatus());
       assertEquals(1,itinerary.getBookings().size());
       for (ws.travelgood.BookingType fl : itinerary.getBookings())
       {
           assertEquals("unconfirmed",fl.getStatus());
       }
       
       ok = cancelPlanning(cId,2);
       assertEquals(true,ok);
       
       //Checking by create the itineary again
       ok =  createItinerary(cId, 2);
       assertEquals(true,ok);   
    }
       
    /**
    *Test B plans a trip by getting a list of hotels and flights, adds them to the itinerary,
    * and books them.
    * Booking of the second item should fail, and the status for the itinerary should be planning. 
    */
    @Test
    public void B()
    {
        int cId = getNextNum();
        boolean ok =  createItinerary(cId, 2);
        assertEquals(true,ok);
        
        //Get the flights
        FlightInfoArray info = getFlights(start,end,date,cId,2);
        assertEquals(3,info.getFlightInformation().size());
        
       boolean ok2 = addBooking(info.getFlightInformation().get(0).getBookingNumber(),"flight",info.getFlightInformation().get(0).getFlight().getDateStart(), cId, 2);
       boolean ok3 = addBooking(info.getFlightInformation().get(0).getBookingNumber(),"flight",info.getFlightInformation().get(0).getFlight().getDateStart(), cId, 2);
       boolean ok4 = addBooking(1,"flight",info.getFlightInformation().get(0).getFlight().getDateStart(), cId, 2);
       boolean ok5 = addBooking(info.getFlightInformation().get(0).getBookingNumber(),"flight",info.getFlightInformation().get(0).getFlight().getDateStart(), cId, 2);
       assertEquals(true,ok2);
       assertEquals(true,ok3);
       assertEquals(true,ok4);
       assertEquals(true,ok5);
       
       ItineraryType itinerary = getItinerary(cId, 2);
       assertEquals("planning",itinerary.getStatus());
       assertEquals(4,itinerary.getBookings().size());
       for (ws.travelgood.BookingType fl : itinerary.getBookings())
       {
           assertEquals("unconfirmed",fl.getStatus());
       }
              //Book the itinerary
       boolean book =  bookItinerary(cId,creditcard,2);
       assertEquals("planning",itinerary.getStatus());
       assertEquals(false,book);
       
       //check that the bookings is unconfirmed by getting the itinerary again.
       itinerary = getItinerary(cId, 2);
       assertEquals("cancelled",itinerary.getBookings().get(0).getStatus());
       assertEquals("cancelled",itinerary.getBookings().get(1).getStatus());
       assertEquals("unconfirmed",itinerary.getBookings().get(2).getStatus());
       assertEquals("unconfirmed",itinerary.getBookings().get(3).getStatus());  
       
    }
      /**
    *Test C1 plans a trip by getting a list of hotels and flights, adds them to the itinerary,
    * and books them. After booking the itinerary should be cancelled. 
    * There should not be any fails in the proces. 
    */     
    @Test
    public void C1(){
        int cId = getNextNum();
        boolean ok =  createItinerary(cId, 2);
        assertEquals(true,ok);
        
        //Get the flights
        FlightInfoArray info = getFlights(start,end,date,cId,2);
        assertEquals(3,info.getFlightInformation().size());
        
       boolean ok2 = addBooking(info.getFlightInformation().get(0).getBookingNumber(),"flight",info.getFlightInformation().get(0).getFlight().getDateStart(), cId, 2);
       boolean ok3 = addBooking(info.getFlightInformation().get(0).getBookingNumber(),"flight",info.getFlightInformation().get(0).getFlight().getDateStart(), cId, 2);
       assertEquals(true,ok2);
       assertEquals(true,ok3);
       
       ItineraryType itinerary = getItinerary(cId, 2);
       assertEquals(2,itinerary.getBookings().size());
       for (ws.travelgood.BookingType fl : itinerary.getBookings())
       {
           assertEquals("unconfirmed",fl.getStatus());
       }
       
       //Book the itinerary
       boolean book =  bookItinerary(cId,creditcard,2);
       assertEquals(true,book);
       
       //check that the bookings is confirmed by getting the itinerary again.
       itinerary = getItinerary(cId, 2);
       assertEquals("booked",itinerary.getStatus());
       for (ws.travelgood.BookingType fl : itinerary.getBookings())
       {
           assertEquals("confirmed",fl.getStatus());
       }
       
       ok  = cancelItinerary(cId,2,creditcard);
       assertEquals(true,ok);
       
       itinerary = getItinerary(cId, 2);
       assertEquals("cancelled",itinerary.getStatus());
       for (ws.travelgood.BookingType b : itinerary.getBookings())
       {
           assertEquals("cancelled",b.getStatus());
       }
    }
        
    /**
    *Test C2 plans a trip by getting a list of hotels and flights, adds them to the itinerary,
    * and books them. After booking the itinerary should be cancelled. 
    * Cancelling of the second item should fail, and the status should kepp being confirmed. 
    */  
    @Test
    public void C2()
    {
        int cId = getNextNum();
        boolean ok =  createItinerary(cId, 2);
        assertEquals(true,ok);
        
        //Get the flights
        FlightInfoArray info = getFlights(start,end,date,cId,2);
        assertEquals(3,info.getFlightInformation().size());
        
       boolean ok2 = addBooking(info.getFlightInformation().get(0).getBookingNumber(),"flight",info.getFlightInformation().get(0).getFlight().getDateStart(), cId, 2);
       boolean ok3 = addBooking(info.getFlightInformation().get(2).getBookingNumber(),"flight",info.getFlightInformation().get(0).getFlight().getDateStart(), cId, 2);
       boolean ok4 = addBooking(info.getFlightInformation().get(1).getBookingNumber(),"flight",info.getFlightInformation().get(0).getFlight().getDateStart(), cId, 2);
       assertEquals(true,ok2);
       assertEquals(true,ok3);
       assertEquals(true,ok4);
       
       ItineraryType itinerary = getItinerary(cId, 2);
       assertEquals("planning",itinerary.getStatus());
       assertEquals(3,itinerary.getBookings().size());
       for (ws.travelgood.BookingType fl : itinerary.getBookings())
       {
           assertEquals("unconfirmed",fl.getStatus());
       }
       
       //Book the itinerary
       boolean book =  bookItinerary(cId,creditcard,2);
       assertEquals(true,book);
       
       //check that the bookings is confirmed by getting the itinerary again.
       itinerary = getItinerary(cId, 2);
       assertEquals("booked",itinerary.getStatus());
       for (ws.travelgood.BookingType fl : itinerary.getBookings())
       {
           assertEquals("confirmed",fl.getStatus());
       }
       
       ok  = cancelItinerary(cId,2,creditcard);
       assertEquals(false,ok);
       
       itinerary = getItinerary(cId, 2);
       assertEquals("Failt in cancelling one or more bookings",itinerary.getStatus());
       
       assertEquals("cancelled",itinerary.getBookings().get(0).getStatus());
       assertEquals("confirmed",itinerary.getBookings().get(1).getStatus());
       assertEquals("cancelled",itinerary.getBookings().get(2).getStatus());
   
    }
    
    private static boolean addBooking(int bookingNumber, java.lang.String type, javax.xml.datatype.XMLGregorianCalendar date, int customerId, int itineraryId) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.addBooking(bookingNumber, type, date, customerId, itineraryId);
    }

    private static boolean bookItinerary(int customerId, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditcard, int itineraryId) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.bookItinerary(customerId, creditcard, itineraryId);
    }

    private static boolean cancelItinerary(int customerId, int itineraryId, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditcard) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.cancelItinerary(customerId, itineraryId, creditcard);
    }

    private static boolean cancelPlanning(int customerId, int itineraryId) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.cancelPlanning(customerId, itineraryId);
    }

    private static boolean createItinerary(int customerId, int itineraryId) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.createItinerary(customerId, itineraryId);
    }

    private static FlightInfoArray getFlights(java.lang.String start, java.lang.String end, javax.xml.datatype.XMLGregorianCalendar date, int customerId, int itineraryId) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.getFlights(start, end, date, customerId, itineraryId);
    }

    private static ItineraryType getItinerary(int customerId, int itineraryId) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.getItinerary(customerId, itineraryId);
    }



}
