/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelGood.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import dk.dtu.imm.airlinereservation.types.FlightInformationType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import ws.travelGood.data.Booking;
import ws.travelGood.data.Itinerary;
import ws.travelGood.representation.ItineraryRepresentation;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.travelGood.data.Flight;
import ws.travelGood.data.FlightInfo;
import javax.ws.rs.core.MediaType;
import ws.travelGood.representation.GetFlightRepresentation;
/**
 *
 * @author Jesper
 */
@Path("itinerary/{cid}/{iid}/flight")
public class FlightResource {
   
    
    @PUT
    @Consumes(ItineraryResource.MEDIATYPE_TRAVELGOOD)
    @Produces(ItineraryResource.MEDIATYPE_TRAVELGOOD)
    public Response addFlightToItineary(@PathParam("cid") String cid, @PathParam("iid") String iid,FlightInfo flightInf) {
        
        String key = ItineraryResource.createKey(cid, iid);
        Itinerary itinerary = ItineraryResource.itinerarys.get(key);
        if (itinerary == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else if(!(itinerary.getStatus().equals(ItineraryResource.PLANNING_ITINERARY)))
        {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        else {
            Booking booking = new Booking();
            booking.setBookingNumber(flightInf.getBookingNumber());
            booking.setStatus(ItineraryResource.UNCONFIRMED_BOOKING);
            booking.setBookingType("FLIGHT");
            itinerary.getBookings().add(booking);
            
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(df2.parse(flightInf.getFlight().getDateStart().toString()));
            } catch (ParseException ex) {
                Logger.getLogger(FlightResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(itinerary.getDate()!=null)
            {
               Calendar calIti = Calendar.getInstance(); 
               try {
                    calIti.setTime(df2.parse(itinerary.getDate().toString()));
                } catch (ParseException ex) {
                    Logger.getLogger(FlightResource.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(calIti.compareTo(cal)<0)
                {
                    itinerary.setDate(flightInf.getFlight().getDateStart());
                }
            }
            else
            {
                itinerary.setDate(flightInf.getFlight().getDateStart());
            }
        }

        ItineraryRepresentation response = new ItineraryRepresentation();
        
        response.setItineary(itinerary);
        ItineraryResource.addSelfLink(cid, iid, response);
        ItineraryResource.addPlanningLinks(cid, iid, response);
        
        return Response.ok(response).build();
    }
    
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(ItineraryResource.MEDIATYPE_TRAVELGOOD)
    public Response getFlightsFromWS(@PathParam("cid") String cid, @PathParam("iid") String iid,
    @QueryParam("start") String start,@QueryParam("end") String end,@QueryParam("date") String date )
    {
        XMLGregorianCalendar date1 = stringToDate(date);
        
        List<FlightInfo> flightInfo = new ArrayList<FlightInfo>();
        List<FlightInformationType> flightReservation = getFlights(start,end,date1);
        
         for(FlightInformationType fi : flightReservation )
         {
             FlightInfo flightInf = new FlightInfo();
             Flight flight = new Flight();
             
             flight.setCarrier(fi.getFlight().getCarrier());
             flight.setStart(fi.getFlight().getStart());
             flight.setEnd(fi.getFlight().getEnd());
             flight.setDateStart(fi.getFlight().getDateStart());
             flight.setDateEnd(fi.getFlight().getDateEnd());          
             
             flightInf.setBookingNumber(fi.getBookingNumber());
             flightInf.setPrice(fi.getPrice());
             flightInf.setReservationServiceName(fi.getReservationServiceName());
             flightInf.setFlight(flight);
             
             flightInfo.add(flightInf);
         }
        GetFlightRepresentation response = new GetFlightRepresentation();
        response.setFlightInfo(flightInfo);
        ItineraryResource.addSelfLink(cid, iid, response);
        ItineraryResource.addPlanningLinks(cid, iid, response);       
        
        return Response.ok(response).build();
        
    }

    private static List<FlightInformationType> getFlights(String start, String end, XMLGregorianCalendar date) {
        dk.dtu.imm.airlinereservation.AirlineReservationService service = new dk.dtu.imm.airlinereservation.AirlineReservationService();
        dk.dtu.imm.airlinereservation.AirlineReservationPortType port = service.getAirlineReservationPortTypeBindingPort();
        return port.getFlights(start, end, date);
    }
    
   
    public XMLGregorianCalendar stringToDate (String s)
    {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(s);
        } catch (DatatypeConfigurationException ex) {
            return null;
        }
    }
    
}