
package ws.travelGood;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Response;
import ws.travelGood.data.Booking;
import ws.travelGood.data.CreditCardInfoType;
import ws.travelGood.data.ExpirationDateType;
import ws.travelGood.data.FlightInfo;
import ws.travelGood.data.Itinerary;
import ws.travelGood.representation.BookingStatusRepresentation;
import ws.travelGood.representation.GetFlightRepresentation;
import ws.travelGood.representation.ItineraryRepresentation;
import ws.travelGood.representation.Link;

/**
 *
 * @author Jesper
 */
public class TravelGoodRESTTest {
    //The bookings can be either: unconfirmed, confirmed or cancelled
    static final String UNCONFIRMED_BOOKING = "unconfirmed";
    static final String CONFIRMED_BOOKING = "confirmed";
    static final String CANCELLED_BOOKING = "cancelled";
    
    //The itinerarys can ither be:
    static final String PLANNING_ITINERARY = "planning";
    static final String BOOKED_ITINERARY = "booked";
    static final String CANCELLED_ITINERARY = "cancelled";
    static final String FAILCANCELLED_ITINERARY = "FAILED IN CANCELLING ALL BOOKINGS";
    
    static final String RELATION_BASE = "http://travelGood.ws/relations/";
    //Itinerary resource
    static final String CANCELPLANNING_RELATION = RELATION_BASE + "cancelPlanning";
    static final String SELF_RELATION = RELATION_BASE + "self";
    
    //Flight resource
    static final String GETFLIGHTS_RELATION = RELATION_BASE+"getFlights";
    static final String ADDFLIGHT_RELATION = RELATION_BASE+"addFlight";
    
    //Hotel resource
    static final String GETHOTELS_RELATION = RELATION_BASE+"getHotels";
    static final String ADDHOTEL_RELATION = RELATION_BASE+"addHotel";
    
    //Booking resource
    static final String BOOKING_RELATION = RELATION_BASE+"booking";
    static final String CANCELBOOKING_RELATION = RELATION_BASE+"cancelBooking";
    
    static final String MEDIATYPE_TRAVELGOOD = "application/travelGood+xml";
    
    Client client;
    String baseURI;
    WebResource itinerarys;
    
    CreditCardInfoType creditcard;
    ExpirationDateType ex;    
    
    XMLGregorianCalendar date;
    String start = "Moscow";
    String end = "Berlin";
    static int intineraryId = 0;
    
    public TravelGoodRESTTest()
    {
        client = Client.create();
        baseURI = "http://localhost:8080/TravelGoodREST/webresources";
        itinerarys = client.resource(baseURI).path("itinerary");
    }
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
    }
    @Before()
    public void resetItinerary() {
        client.resource(itinerarys.getURI().toString() + "/reset").put();
    }
    public String newItinearyId() {
        return "" + intineraryId++;
    }
    
    /**
    *Test P1 plans a trip by getting a list of hotels and flights, adds them to the itinerary,
    * and books them.
    * There should not be any fails in the proces. 
    */    
    @Test
    public void P1()
    {
        //createItinerary
        String iid = newItinearyId();
        String URICreate = String.format("%s/itinerary/%s/%s", baseURI, "2", iid);
        Itinerary itinerary = new Itinerary();
        ItineraryRepresentation itineraryRep = client.resource(URICreate).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,itinerary);
        assertEquals(PLANNING_ITINERARY,itineraryRep.getItineary().getStatus());
        
        Link linkGetFlight = itineraryRep.getLinkByRelation(GETFLIGHTS_RELATION);
        assertNotNull(linkGetFlight);
        
        //get flights
        GetFlightRepresentation resultFlight = client.resource(linkGetFlight.getUri()).queryParam("start", start).queryParam("end", end).queryParam("date", date.toString()).get(GetFlightRepresentation.class);
        assertEquals(3,resultFlight.getFlightInfo().size());  
        
        //adding the flights
        ItineraryRepresentation resultAddFlight = client.resource(resultFlight.getLinkByRelation(GETFLIGHTS_RELATION).getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,resultFlight.getFlightInfo().get(0));
        assertEquals(1,resultAddFlight.getItineary().getBookings().size());
        
        ItineraryRepresentation resultAddFlight1 = client.resource(resultFlight.getLinkByRelation(GETFLIGHTS_RELATION).getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,resultFlight.getFlightInfo().get(1));
        assertEquals(2,resultAddFlight1.getItineary().getBookings().size());
        for(Booking b : resultAddFlight1.getItineary().getBookings() )
        {
            assertEquals(UNCONFIRMED_BOOKING,b.getStatus());
        }
        
        //booking the itinerary
        Link bookLink = resultAddFlight.getLinkByRelation(BOOKING_RELATION);
        BookingStatusRepresentation resultBookFlight = client.resource(bookLink.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(BookingStatusRepresentation.class,creditcard);
        assertEquals(BOOKED_ITINERARY,resultBookFlight.getBookingStatus());
        
        //Checking by getting the itinerary
        Link linkGetItinerary = resultAddFlight.getLinkByRelation(SELF_RELATION);
        ItineraryRepresentation getResult = client.resource(linkGetItinerary.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).get(ItineraryRepresentation.class);
        assertEquals(2,getResult.getItineary().getBookings().size());
        assertEquals(BOOKED_ITINERARY,getResult.getItineary().getStatus());
        for(Booking b : getResult.getItineary().getBookings() )
        {
            assertEquals(CONFIRMED_BOOKING,b.getStatus());
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
        //createItinerary
        String iid = newItinearyId();
        String URICreate = String.format("%s/itinerary/%s/%s", baseURI, "2", iid);
        Itinerary itinerary = new Itinerary();
        ItineraryRepresentation itineraryRep = client.resource(URICreate).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,itinerary);
        assertEquals(PLANNING_ITINERARY,itineraryRep.getItineary().getStatus());
        
        Link linkGetFlight = itineraryRep.getLinkByRelation(GETFLIGHTS_RELATION);
        assertNotNull(linkGetFlight);
        
        //get flights
        GetFlightRepresentation resultFlight = client.resource(linkGetFlight.getUri()).queryParam("start", start).queryParam("end", end).queryParam("date", date.toString()).get(GetFlightRepresentation.class);
        assertEquals(3,resultFlight.getFlightInfo().size());  
        
        //adding the flights
        ItineraryRepresentation resultAddFlight = client.resource(resultFlight.getLinkByRelation(GETFLIGHTS_RELATION).getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,resultFlight.getFlightInfo().get(0));
        assertEquals(1,resultAddFlight.getItineary().getBookings().size());
        assertEquals("unconfirmed",resultAddFlight.getItineary().getBookings().get(0).getStatus());
        
        //cancel planning
        Link link2 = resultAddFlight.getLinkByRelation(CANCELPLANNING_RELATION);
        BookingStatusRepresentation resultCancel = client.resource(link2.getUri()).delete(BookingStatusRepresentation.class);
        assertEquals("cancelled",resultCancel.getBookingStatus());
        assertEquals(0,resultCancel.getLinks().size());
       
        //Checking that the itinerary is removed
        ClientResponse  response = client.resource(URICreate).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).get(ClientResponse.class);
        assertEquals(javax.ws.rs.core.Response.Status.NOT_FOUND.getStatusCode(),response.getStatus());
     }
    
    /**
    *Test B plans a trip by getting a list of hotels and flights, adds them to the itinerary,
    * and books them.
    * Booking of the second item should fail, and the status for the itinerary should be planning. 
    */ 
    @Test
    public void B()
    {
        //createItinerary
        String iid = newItinearyId();
        String URICreate = String.format("%s/itinerary/%s/%s", baseURI, "2", iid);
        Itinerary itinerary = new Itinerary();
        ItineraryRepresentation itineraryRep = client.resource(URICreate).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,itinerary);
        assertEquals(PLANNING_ITINERARY,itineraryRep.getItineary().getStatus());
        
        Link linkGetFlight = itineraryRep.getLinkByRelation(GETFLIGHTS_RELATION);
        assertNotNull(linkGetFlight);
        
        //get flights
        GetFlightRepresentation resultFlight = client.resource(linkGetFlight.getUri()).queryParam("start", start).queryParam("end", end).queryParam("date", date.toString()).get(GetFlightRepresentation.class);
        assertEquals(3,resultFlight.getFlightInfo().size());  
        
        //adding the flights
        ItineraryRepresentation resultAddFlight = client.resource(resultFlight.getLinkByRelation(GETFLIGHTS_RELATION).getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,resultFlight.getFlightInfo().get(0));
        assertEquals(1,resultAddFlight.getItineary().getBookings().size());
        
        FlightInfo inf = resultFlight.getFlightInfo().get(1);
        inf.setBookingNumber(12);
        ItineraryRepresentation resultAddFlight1 = client.resource(resultFlight.getLinkByRelation(GETFLIGHTS_RELATION).getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,inf);
        assertEquals(2,resultAddFlight1.getItineary().getBookings().size());
        for(Booking b : resultAddFlight1.getItineary().getBookings() )
        {
            assertEquals(UNCONFIRMED_BOOKING,b.getStatus());
        }
        
        //try to book the itinerary which fails
        Link bookLink = resultAddFlight.getLinkByRelation(BOOKING_RELATION);
        BookingStatusRepresentation resultBookFlight = client.resource(bookLink.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(BookingStatusRepresentation.class,creditcard);
        assertEquals(PLANNING_ITINERARY,resultBookFlight.getBookingStatus());
        
        //Checking by getting the itinerary
        Link linkGetItinerary = resultAddFlight.getLinkByRelation(SELF_RELATION);
        ItineraryRepresentation getResult = client.resource(linkGetItinerary.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).get(ItineraryRepresentation.class);
        assertEquals(2,getResult.getItineary().getBookings().size());
        assertEquals(PLANNING_ITINERARY,getResult.getItineary().getStatus());
        
        assertEquals(CANCELLED_BOOKING,getResult.getItineary().getBookings().get(0).getStatus());
        assertEquals(UNCONFIRMED_BOOKING,getResult.getItineary().getBookings().get(1).getStatus());
    }
    
     /**
    *Test C1 plans a trip by getting a list of hotels and flights, adds them to the itinerary,
    * and books them. After booking the itinerary should be cancelled. 
    * There should not be any fails in the proces. 
    */     
    @Test
    public void C1(){
        //createItinerary
        String iid = newItinearyId();
        String URICreate = String.format("%s/itinerary/%s/%s", baseURI, "2", iid);
        Itinerary itinerary = new Itinerary();
        ItineraryRepresentation itineraryRep = client.resource(URICreate).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,itinerary);
        assertEquals(PLANNING_ITINERARY,itineraryRep.getItineary().getStatus());
        
        Link linkGetFlight = itineraryRep.getLinkByRelation(GETFLIGHTS_RELATION);
        assertNotNull(linkGetFlight);
        
        //get flights
        GetFlightRepresentation resultFlight = client.resource(linkGetFlight.getUri()).queryParam("start", start).queryParam("end", end).queryParam("date", date.toString()).get(GetFlightRepresentation.class);
        assertEquals(3,resultFlight.getFlightInfo().size());  
        
        //adding the flights
        ItineraryRepresentation resultAddFlight = client.resource(resultFlight.getLinkByRelation(GETFLIGHTS_RELATION).getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,resultFlight.getFlightInfo().get(0));
        assertEquals(1,resultAddFlight.getItineary().getBookings().size());
        
        ItineraryRepresentation resultAddFlight1 = client.resource(resultFlight.getLinkByRelation(GETFLIGHTS_RELATION).getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,resultFlight.getFlightInfo().get(1));
        assertEquals(2,resultAddFlight1.getItineary().getBookings().size());
        for(Booking b : resultAddFlight1.getItineary().getBookings() )
        {
            assertEquals(UNCONFIRMED_BOOKING,b.getStatus());
        }
        
        //booking the itinerary
        Link bookLink = resultAddFlight.getLinkByRelation(BOOKING_RELATION);
        BookingStatusRepresentation resultBookFlight = client.resource(bookLink.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(BookingStatusRepresentation.class,creditcard);
        assertEquals(BOOKED_ITINERARY,resultBookFlight.getBookingStatus());
        
        //Checking by getting the itinerary
        Link linkGetItinerary = resultAddFlight.getLinkByRelation(SELF_RELATION);
        ItineraryRepresentation getResult = client.resource(linkGetItinerary.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).get(ItineraryRepresentation.class);
        assertEquals(2,getResult.getItineary().getBookings().size());
        assertEquals(BOOKED_ITINERARY,getResult.getItineary().getStatus());
        for(Booking b : getResult.getItineary().getBookings() )
        {
            assertEquals(CONFIRMED_BOOKING,b.getStatus());
        }
        
        //cancel the itinerary
        Link cancelLink = getResult.getLinkByRelation(CANCELBOOKING_RELATION);
        BookingStatusRepresentation resultCancelFlight = client.resource(cancelLink.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(BookingStatusRepresentation.class,creditcard);
        assertEquals(CANCELLED_ITINERARY,resultCancelFlight.getBookingStatus()); 
        
        //Checking by getting the itinerary
        linkGetItinerary = resultCancelFlight.getLinkByRelation(SELF_RELATION);
        getResult = client.resource(linkGetItinerary .getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).get(ItineraryRepresentation.class);
        assertEquals(2,getResult.getItineary().getBookings().size());
        assertEquals(CANCELLED_ITINERARY,getResult.getItineary().getStatus());
        for(Booking b : getResult.getItineary().getBookings() )
        {
            assertEquals(CANCELLED_BOOKING,b.getStatus());
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
        //createItinerary
        String iid = newItinearyId();
        String URICreate = String.format("%s/itinerary/%s/%s", baseURI, "2", iid);
        Itinerary itinerary = new Itinerary();
        ItineraryRepresentation itineraryRep = client.resource(URICreate).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,itinerary);
        assertEquals(PLANNING_ITINERARY,itineraryRep.getItineary().getStatus());
        
        Link linkGetFlight = itineraryRep.getLinkByRelation(GETFLIGHTS_RELATION);
        assertNotNull(linkGetFlight);
        
        //get flights
        GetFlightRepresentation resultFlight = client.resource(linkGetFlight.getUri()).queryParam("start", start).queryParam("end", end).queryParam("date", date.toString()).get(GetFlightRepresentation.class);
        assertEquals(3,resultFlight.getFlightInfo().size());  
        
        //adding the flights
        ItineraryRepresentation resultAddFlight = client.resource(resultFlight.getLinkByRelation(GETFLIGHTS_RELATION).getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,resultFlight.getFlightInfo().get(0));
        assertEquals(1,resultAddFlight.getItineary().getBookings().size());
        
        ItineraryRepresentation resultAddFlight1 = client.resource(resultFlight.getLinkByRelation(GETFLIGHTS_RELATION).getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,resultFlight.getFlightInfo().get(2));
        assertEquals(2,resultAddFlight1.getItineary().getBookings().size());
        for(Booking b : resultAddFlight1.getItineary().getBookings() )
        {
            assertEquals(UNCONFIRMED_BOOKING,b.getStatus());
        }
        
        //booking the itinerary
        Link bookLink = resultAddFlight.getLinkByRelation(BOOKING_RELATION);
        BookingStatusRepresentation resultBookFlight = client.resource(bookLink.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(BookingStatusRepresentation.class,creditcard);
        assertEquals(BOOKED_ITINERARY,resultBookFlight.getBookingStatus());
        
        //Checking by getting the itinerary
        Link linkGetItinerary = resultAddFlight.getLinkByRelation(SELF_RELATION);
        ItineraryRepresentation getResult = client.resource(linkGetItinerary.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).get(ItineraryRepresentation.class);
        assertEquals(2,getResult.getItineary().getBookings().size());
        assertEquals(BOOKED_ITINERARY,getResult.getItineary().getStatus());
        for(Booking b : getResult.getItineary().getBookings() )
        {
            assertEquals(CONFIRMED_BOOKING,b.getStatus());
        }
        
        //cancel the itinerary
        Link cancelLink = getResult.getLinkByRelation(CANCELBOOKING_RELATION);
        BookingStatusRepresentation resultCancelFlight = client.resource(cancelLink.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(BookingStatusRepresentation.class,creditcard);
        assertEquals(FAILCANCELLED_ITINERARY,resultCancelFlight.getBookingStatus());
        
        //Checking by getting the itinerary
        linkGetItinerary = resultCancelFlight.getLinkByRelation(SELF_RELATION);
        getResult = client.resource(linkGetItinerary.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).get(ItineraryRepresentation.class);
        
        assertEquals(CANCELLED_BOOKING,getResult.getItineary().getBookings().get(0).getStatus());
        assertEquals(CONFIRMED_BOOKING,getResult.getItineary().getBookings().get(1).getStatus());
        
        
    }
}
