package ws.travelGood.resource;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.travelGood.representation.Link;
import ws.travelGood.data.Itinerary;
import ws.travelGood.representation.BookingStatusRepresentation;
import ws.travelGood.representation.ItineraryRepresentation;
import ws.travelGood.representation.Representation;
/**
 * @author Jesper
 */
@Path("itinerary/{cid}/{iid}")
public class ItineraryResource {

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
    static final String BASE_URI = "http://localhost:8080/TravelGoodREST/webresources/itinerary";

    static Map<String,Itinerary> itinerarys = new HashMap<String,Itinerary>();

    static void reset() {
        itinerarys = new HashMap<String,Itinerary>();
    }
    @GET
    @Produces(MEDIATYPE_TRAVELGOOD)
    public Response getItinerary(@PathParam("cid") String cid, @PathParam("iid") String iid) {

        String key = createKey(cid, iid); //Create the key from customer id and itinerary id
        
        if (!(itinerarys.containsKey(key))) {
            return Response.status(Response.Status.NOT_FOUND).entity("Itinerary not found").build();
        }
        Itinerary itinerary = itinerarys.get(key); //Get the itineary from the generated key
        ItineraryRepresentation itineraryRep = new ItineraryRepresentation();
        itineraryRep.setItineary(itinerary);

        
        addSelfLink(cid, iid, itineraryRep); //You can always get the itineary. 
        if(itinerary.getStatus().equals(PLANNING_ITINERARY))
        {
            addPlanningLinks(cid, iid, itineraryRep); //adding several links which is used when planning.
        }
        else if(itinerary.getStatus().equals(BOOKED_ITINERARY))
        {
            addcancelBookingLink(cid,iid,itineraryRep); //The itinerary is booked and we can cencel it
        }
        
        return Response.ok(itineraryRep).build();
    }

    @PUT
    @Consumes(MEDIATYPE_TRAVELGOOD)
    @Produces(MEDIATYPE_TRAVELGOOD)
    public Response createItinerary(@PathParam("cid") String cid, @PathParam("iid") String iid, Itinerary itinerary) {

        String key = createKey(cid, iid); //Create the key from customer id and itinerary id
 
        if (itinerarys.containsKey(key)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Itineary already excists").build();
        }
        
        ItineraryRepresentation itineraryRep = new ItineraryRepresentation();
        itineraryRep.setItineary(itinerary);
        itinerary.setStatus(PLANNING_ITINERARY);
        itinerarys.put(key, itinerary);
        
        
        addSelfLink(cid, iid, itineraryRep); //You can always get the itineary. 
        addPlanningLinks(cid, iid, itineraryRep);
        
        return Response.ok(itineraryRep).build();
    }
    
    @DELETE
    @Produces(MEDIATYPE_TRAVELGOOD)
    public Response deleteItineraryPlanning(@PathParam("cid") String cid, @PathParam("iid") String iid)
    {
        String key = createKey(cid, iid); //Create the key from customer id and itinerary id
 
        if (!(itinerarys.containsKey(key))) {
            return Response.status(Response.Status.NOT_FOUND).entity("Itinerary not found").build();
        }
        Itinerary itinerary = itinerarys.get(key); //Get the itineary from the generated key
        if(!(itinerary.getStatus().equals(PLANNING_ITINERARY)))
        {
            return Response.status(Response.Status.FORBIDDEN).entity("Itineary is already done planning").build();
        }
        itinerarys.remove(key);
        BookingStatusRepresentation response = new BookingStatusRepresentation();
        response.setBookingStatus(CANCELLED_ITINERARY);
        response.setIt(itinerary);
        
        return Response.ok(response).build();
        
    }
    
    //This method is used for creating a key based on the customer id and the itineary id.
    static String createKey(String cid, String iid) {
        return String.format("%s,%s", cid, iid);
    }
    
    //The following links is used when planning a itinerary
    static void addPlanningLinks(String cid, String iid, Representation response)
    {
            addCancelPlanningLink(cid,iid,response);
            addbookItineraryLink(cid, iid, response);
            addgetFlightsLink(cid, iid, response);
            addFlightLink(cid, iid, response);
            addgetHotelsLink(cid, iid, response);
            addHotelLink(cid, iid, response);
    }
    
    //Itinerary resource methods
    static void addCancelPlanningLink(String cid, String iid, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/%s/%s", BASE_URI, cid, iid));
        link.setRel(CANCELPLANNING_RELATION);
        response.getLinks().add(link);
    }
    static void addSelfLink(String cid, String iid, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/%s/%s", BASE_URI, cid, iid));
        link.setRel(SELF_RELATION);
        response.getLinks().add(link);
    }
    
    //flight resource methods
    static void addFlightLink(String cid, String iid, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/%s/%s/flight", BASE_URI, cid, iid));
        link.setRel(ADDFLIGHT_RELATION);
        response.getLinks().add(link);
    }
    static void addgetFlightsLink(String cid, String iid, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/%s/%s/flight", BASE_URI, cid, iid));
        link.setRel(GETFLIGHTS_RELATION);
        response.getLinks().add(link);
    }
    
    //hotel resource methods
    static void addHotelLink(String cid, String iid, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/%s/%s/hotel", BASE_URI, cid, iid));
        link.setRel(ADDHOTEL_RELATION);
        response.getLinks().add(link);
    } 
     static void addgetHotelsLink(String cid, String iid, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/%s/%s/hotel", BASE_URI, cid, iid));
        link.setRel(GETHOTELS_RELATION);
        response.getLinks().add(link);
    }  
     
    //Booking resource methods
    static void addbookItineraryLink(String cid, String iid, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/%s/%s/booking/%s", BASE_URI, cid, iid,"book"));
        link.setRel(BOOKING_RELATION);
        response.getLinks().add(link);
    } 
     static void addcancelBookingLink(String cid, String iid, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/%s/%s/booking/%s", BASE_URI, cid, iid,"cancel"));
        link.setRel(CANCELBOOKING_RELATION);
        response.getLinks().add(link);
    }  
    
}
