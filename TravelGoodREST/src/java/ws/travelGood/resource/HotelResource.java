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
import dk.dtu.imm.hotelreservation.types.HotelInfoType;
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
import ws.travelGood.data.Address;
import ws.travelGood.data.HotelInfo;
import javax.ws.rs.core.MediaType;
import ws.travelGood.representation.GetHotelRepresentation;
/**
 *
 * @author Jesper
 */
@Path("itinerary/{cid}/{iid}/hotel")
public class HotelResource {
   
    
    @PUT
    @Consumes(ItineraryResource.MEDIATYPE_TRAVELGOOD)
    @Produces(ItineraryResource.MEDIATYPE_TRAVELGOOD)
    public Response addHotelToItineary(@PathParam("cid") String cid, @PathParam("iid") String iid,HotelInfo hotelInf) {
        
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
            booking.setBookingNumber(hotelInf.getBookingNumber());
            booking.setStatus(ItineraryResource.UNCONFIRMED_BOOKING);
            booking.setBookingType("HOTEL");
            itinerary.getBookings().add(booking);
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
    public Response getHotelsFromWS(@PathParam("cid") String cid, @PathParam("iid") String iid,
    @QueryParam("city") String city,@QueryParam("start") String start,@QueryParam("end") String end )
    {
        String key = ItineraryResource.createKey(cid, iid);
        Itinerary itinerary = ItineraryResource.itinerarys.get(key);
        if (itinerary == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        XMLGregorianCalendar startDate = stringToDate(start);
        XMLGregorianCalendar endDate = stringToDate(end);
        
        List<HotelInfo> hotelInfo = new ArrayList<HotelInfo>();
        List<HotelInfoType> hotelReservation = getHotels(city,startDate,endDate);
        
         for(HotelInfoType hi : hotelReservation )
         {
             HotelInfo hotelInf = new HotelInfo();
             Address address = new Address();
             
             address.setCity(hi.getAddress().getCity());
             address.setCountry(hi.getAddress().getCountry());
             address.setStreet(hi.getAddress().getStreet());
             address.setZip(hi.getAddress().getZip());
             
             hotelInf.setAddress(address);
             hotelInf.setBookingNumber(hi.getBookingNumber());
             hotelInf.setCreditCardGuaranteed(hi.isCreditCardGuaranteeRequired());
             hotelInf.setHotelName(hi.getHotelName());
             hotelInf.setHotelReservationService(hi.getNameOfHotelReservationService());
             hotelInf.setPrice(hi.getPrice());
             
             hotelInfo.add(hotelInf);
             
         }
        GetHotelRepresentation response = new GetHotelRepresentation();
        response.setHotelInfo(hotelInfo);
        ItineraryResource.addSelfLink(cid, iid, response);
        ItineraryResource.addPlanningLinks(cid, iid, response);       
        
        return Response.ok(response).build();
        
    }

    
   
    public XMLGregorianCalendar stringToDate (String s)
    {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(s);
        } catch (DatatypeConfigurationException ex) {
            return null;
        }
    }

    private static List<HotelInfoType> getHotels(java.lang.String city, javax.xml.datatype.XMLGregorianCalendar arrivalDate, javax.xml.datatype.XMLGregorianCalendar departureDate) {
        dk.dtu.imm.hotelreservation.HotelReservationService service = new dk.dtu.imm.hotelreservation.HotelReservationService();
        dk.dtu.imm.hotelreservation.HotelReservationPortType port = service.getHotelReservationPortTypeBindingPort();
        return port.getHotels(city, arrivalDate, departureDate);
    }
    
}