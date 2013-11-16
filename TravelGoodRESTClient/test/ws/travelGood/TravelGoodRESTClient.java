/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class TravelGoodRESTClient {
    

    public static final String MEDIATYPE_TRAVELGOOD = "application/travelGood+xml";
    Client client;
    String baseURI;
    WebResource itinerarys;
    static int intineraryId = 0;
    
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
    
    public TravelGoodRESTClient() {
        client = Client.create();
        // Uses tcpmon. To use the service directly replace 8070 by 8080
        // The same needs to be done in class OrderResource
        baseURI = "http://localhost:8080/TravelGoodREST/webresources";
        itinerarys = client.resource(baseURI).path("itinerary");
    }
   @Before()
    public void resetItinerary() {
        client.resource(itinerarys.getURI().toString() + "/reset").put();
    }
    public String newItinearyId() {
        return "" + intineraryId++;
    }
   //@Test
   public void addItinerary()
   {
        String URI = String.format("%s/itinerary/%s/%s", baseURI, "2", newItinearyId());
        Itinerary itinerary = new Itinerary();
        ItineraryRepresentation result = client.resource(URI).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,itinerary);
        assertEquals(null,result.getItineary().getDate()); 
        
   }
  //@Test
   public void getItinerary()
   {
       String id = newItinearyId();
       String URI = String.format("%s/itinerary/%s/%s", baseURI, "2", id);
       Itinerary itinerary = new Itinerary();
       ItineraryRepresentation result = client.resource(URI).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,itinerary);
       assertEquals(null,result.getItineary().getDate()); 
        
        Link link = result.getLinkByRelation("http://travelGood.ws/relations/self");
        
        assertNotNull(link);
        assertEquals(URI,link.getUri()); 
        System.out.print(URI);

        ItineraryRepresentation getResult = client.resource(link.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).get(ItineraryRepresentation.class);
        assertNotNull(getResult.getItineary());
        
   }
    //@Test
    public void testGetflightAndAddFlight() {
        
        //createItinerary
        String iid = newItinearyId();
        String URICreate = String.format("%s/itinerary/%s/%s", baseURI, "2", iid);
        Itinerary itinerary = new Itinerary();
        ItineraryRepresentation result = client.resource(URICreate).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,itinerary);
        assertEquals(null,result.getItineary().getDate()); 
        
        //Get the flight
        DatatypeFactory df;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            return;
        }
        XMLGregorianCalendar date = df.newXMLGregorianCalendar("2013-12-25");
        String start = "Moscow";
        String end = "Berlin";
        
        String URIFlight = String.format("%s/itinerary/%s/%s/flight", baseURI, "2", iid);
        GetFlightRepresentation resultFlight = client.resource(URIFlight).queryParam("start", start).queryParam("end", end).queryParam("date", date.toString()).get(GetFlightRepresentation.class);
        assertEquals(2,resultFlight.getFlightInfo().size());   
        
        //Adding the flight
        ItineraryRepresentation resultAddFlight = client.resource(URIFlight).put(ItineraryRepresentation.class,resultFlight.getFlightInfo().get(0));
        assertEquals(1,resultAddFlight.getItineary().getBookings().size());
        assertEquals("unconfirmed",resultAddFlight.getItineary().getBookings().get(0).getStatus());
        
        //Checking by getting the itinerary
        Link link = resultAddFlight.getLinkByRelation("http://travelGood.ws/relations/self");
        ItineraryRepresentation getResult = client.resource(link.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).get(ItineraryRepresentation.class);
        assertEquals(1,getResult.getItineary().getBookings().size());
        assertEquals("planning",getResult.getItineary().getStatus());
    }
    
   @Test
   public void testBooking() {
        
        //createItinerary
        String iid = newItinearyId();
        String URICreate = String.format("%s/itinerary/%s/%s", baseURI, "2", iid);
        Itinerary itinerary = new Itinerary();
        ItineraryRepresentation result = client.resource(URICreate).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,itinerary);
        assertEquals(null,result.getItineary().getDate()); 
        
        //Get the flight
        DatatypeFactory df;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            return;
        }
        XMLGregorianCalendar date = df.newXMLGregorianCalendar("2013-12-25");
        String start = "Moscow";
        String end = "Berlin";
        
        String URIFlight = String.format("%s/itinerary/%s/%s/flight", baseURI, "2", iid);
        GetFlightRepresentation resultFlight = client.resource(URIFlight).queryParam("start", start).queryParam("end", end).queryParam("date", date.toString()).get(GetFlightRepresentation.class);
        assertEquals(2,resultFlight.getFlightInfo().size());   
        
        //Adding two flights
        ItineraryRepresentation resultAddFlight = client.resource(URIFlight).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,resultFlight.getFlightInfo().get(0));
        assertEquals(1,resultAddFlight.getItineary().getBookings().size());
        assertEquals("unconfirmed",resultAddFlight.getItineary().getBookings().get(0).getStatus());
        
        ItineraryRepresentation resultAddFlight1 = client.resource(URIFlight).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,resultFlight.getFlightInfo().get(1));
        assertEquals(2,resultAddFlight1.getItineary().getBookings().size());
        assertEquals("unconfirmed",resultAddFlight1.getItineary().getBookings().get(1).getStatus());
        
        //Checking by getting the itinerary
        Link link = resultAddFlight.getLinkByRelation("http://travelGood.ws/relations/self");
        ItineraryRepresentation getResult = client.resource(link.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).get(ItineraryRepresentation.class);
        assertEquals(2,getResult.getItineary().getBookings().size());
        assertEquals("planning",getResult.getItineary().getStatus());
        assertEquals(date,getResult.getItineary().getDate());
        
        //booking the itinerary
        String URIbook = String.format("%s/itinerary/%s/%s/booking/book", baseURI, "2", iid);
        BookingStatusRepresentation resultBookFlight = client.resource(URIbook).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(BookingStatusRepresentation.class,creditcard);
        assertEquals("booked",resultBookFlight.getBookingStatus());
        
        //Checking by getting the itinerary
        link = resultAddFlight.getLinkByRelation("http://travelGood.ws/relations/self");
        getResult = client.resource(link.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).get(ItineraryRepresentation.class);
        assertEquals(2,getResult.getItineary().getBookings().size());
        assertEquals("booked",getResult.getItineary().getStatus());
        
        assertEquals("confirmed",getResult.getItineary().getBookings().get(0).getStatus());
        assertEquals("confirmed",getResult.getItineary().getBookings().get(1).getStatus());
    }
   
  //@Test
   public void testCancelItinerary() {
        
        //createItinerary
        String iid = newItinearyId();
        String URICreate = String.format("%s/itinerary/%s/%s", baseURI, "2", iid);
        Itinerary itinerary = new Itinerary();
        ItineraryRepresentation result = client.resource(URICreate).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,itinerary);
        assertEquals(null,result.getItineary().getDate()); 
        
        //Get the flight
        DatatypeFactory df;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            return;
        }
        XMLGregorianCalendar date = df.newXMLGregorianCalendar("2013-12-25");
        String start = "Moscow";
        String end = "Berlin";
        
        String URIFlight = String.format("%s/itinerary/%s/%s/flight", baseURI, "2", iid);
        GetFlightRepresentation resultFlight = client.resource(URIFlight).queryParam("start", start).queryParam("end", end).queryParam("date", date.toString()).get(GetFlightRepresentation.class);
        assertEquals(2,resultFlight.getFlightInfo().size());   
        
        //Adding two flights
        ItineraryRepresentation resultAddFlight = client.resource(URIFlight).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,resultFlight.getFlightInfo().get(0));
        assertEquals(1,resultAddFlight.getItineary().getBookings().size());
        assertEquals("unconfirmed",resultAddFlight.getItineary().getBookings().get(0).getStatus());
        
        ItineraryRepresentation resultAddFlight1 = client.resource(URIFlight).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).put(ItineraryRepresentation.class,resultFlight.getFlightInfo().get(1));
        assertEquals(2,resultAddFlight1.getItineary().getBookings().size());
        assertEquals("unconfirmed",resultAddFlight1.getItineary().getBookings().get(1).getStatus());
        
        //Checking by getting the itinerary
        Link link = resultAddFlight.getLinkByRelation("http://travelGood.ws/relations/self");
        ItineraryRepresentation getResult = client.resource(link.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).get(ItineraryRepresentation.class);
        assertEquals(2,getResult.getItineary().getBookings().size());
        assertEquals("planning",getResult.getItineary().getStatus());
        assertEquals(date,getResult.getItineary().getDate());
        
        //cancel planning
        Link link2 = getResult.getLinkByRelation("http://travelGood.ws/relations/cancelPlanning");
        BookingStatusRepresentation resultCancel = client.resource(link2.getUri()).delete(BookingStatusRepresentation.class);
        assertEquals("cancelled",resultCancel.getBookingStatus());
        assertEquals(0,resultCancel.getLinks().size());
       
        
        //Checking that the itinerary is removed
        ClientResponse  response = client.resource(link.getUri()).accept(MEDIATYPE_TRAVELGOOD).type(MEDIATYPE_TRAVELGOOD).get(ClientResponse.class);
        assertEquals(javax.ws.rs.core.Response.Status.NOT_FOUND.getStatusCode(),response.getStatus());
   }
    
}
