/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelGood.resource;

import dk.dtu.imm.airlinereservation.BookFlightFaultMessage;
import dk.dtu.imm.airlinereservation.CancelFlightFaultMessage;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import ws.travelGood.data.Booking;
import ws.travelGood.data.Itinerary;
import ws.travelGood.representation.BookingStatusRepresentation;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.QueryParam;
import ws.travelGood.data.CreditCardInfoType;

/**
 *
 * @author Jesper
 */
@Path("itinerary/{cid}/{iid}/booking/{action}")
public class BookingResource {
    
    @PUT
    @Consumes(ItineraryResource.MEDIATYPE_TRAVELGOOD)
    @Produces(ItineraryResource.MEDIATYPE_TRAVELGOOD)
    public Response bookItinerary(@PathParam("cid") String cid, @PathParam("iid") String iid,@PathParam("action") String action, CreditCardInfoType creditcard) {
        
        String key = ItineraryResource.createKey(cid, iid);
        Itinerary itinerary = ItineraryResource.itinerarys.get(key);

        if (itinerary == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
   
        dk.dtu.imm.fastmoney.types.CreditCardInfoType cc = new dk.dtu.imm.fastmoney.types.CreditCardInfoType();
        dk.dtu.imm.fastmoney.types.ExpirationDateType ed = new dk.dtu.imm.fastmoney.types.ExpirationDateType();
        ed.setMonth(creditcard.getExpirationDate().getMonth());
        ed.setYear(creditcard.getExpirationDate().getYear());
        cc.setName(creditcard.getName());
        cc.setNumber(creditcard.getNumber());
        cc.setExpirationDate(ed);
        if(action.equals("book"))
        {
            if(itinerary.getStatus().equals(ItineraryResource.PLANNING_ITINERARY))
            {
                Response response  = bookItinerary(itinerary,cid,iid,cc);
                return response;         
            }
            else
            {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        }
        else if(action.equals("cancel"))
        {
            if(itinerary.getStatus().equals(ItineraryResource.BOOKED_ITINERARY))
            {
                return cancelBooking(itinerary,cid,iid,cc);
            }
            else
            {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        else
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


    } 
    private Response cancelBooking(Itinerary itinerary, String cid, String iid,dk.dtu.imm.fastmoney.types.CreditCardInfoType cc )
    {
         boolean fail = false;
         for(int i=0; i<itinerary.getBookings().size();i++)
         {
            Booking b = itinerary.getBookings().get(i);
            if(b.getStatus().equals(ItineraryResource.CONFIRMED_BOOKING))
            {
                if(b.getBookingType().equals("FLIGHT"))
                {    
                    try {
                        cancelFlight(b.getBookingNumber(),cc);
                        b.setStatus(ItineraryResource.CANCELLED_BOOKING);
                    } 
                    catch (CancelFlightFaultMessage ex) {
                        fail = true;
                    }
                }
                //add cancelHotel
            }
        }
        BookingStatusRepresentation response = new BookingStatusRepresentation();
        ItineraryResource.addSelfLink(cid, iid, response);
        if(fail)
        {
            itinerary.setStatus(ItineraryResource.FAILCANCELLED_ITINERARY);
        }
        else
        {
            itinerary.setStatus(ItineraryResource.CANCELLED_ITINERARY);
        }
        response.setBookingStatus(itinerary.getStatus());
        return Response.ok(response).build();
            
    }
    private Response bookItinerary(Itinerary itinerary, String cid, String iid, dk.dtu.imm.fastmoney.types.CreditCardInfoType cc )
    {
      //The following list is used if a booking fails, and the previous booked items need to bee cancelled.
        List<Booking> bookingsToCancel = new ArrayList<Booking>();     
        boolean fail = false;
       
        try {    
            for(int i=0; i<itinerary.getBookings().size();i++)
            {
                Booking b = itinerary.getBookings().get(i);
                if(b.getStatus().equals(ItineraryResource.UNCONFIRMED_BOOKING))
                {
                    if(b.getBookingType().equals("FLIGHT"))
                    {                
                        bookFlight(b.getBookingNumber(),cc);
                        b.setStatus(ItineraryResource.CONFIRMED_BOOKING);
                        bookingsToCancel.add(b);   
                    }//TODO add hotel booking. 
                }
            }
         }catch (BookFlightFaultMessage ex) {
            fail = true;
            for(int j = 0; j<bookingsToCancel.size();j++)
            {
                Booking b = bookingsToCancel.get(j);
                if(b.getBookingType().equals("FLIGHT"))
                {
                    try 
                    {
                        cancelFlight(b.getBookingNumber(),cc);
                        b.setStatus(ItineraryResource.CANCELLED_BOOKING);
                    } catch (CancelFlightFaultMessage ex1) {
                        Logger.getLogger(BookingResource.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }//TODO add hotel booking. 
            }
         }
        BookingStatusRepresentation response = new BookingStatusRepresentation();
        ItineraryResource.addSelfLink(cid, iid, response);
        if(!fail)
        {
            itinerary.setStatus(ItineraryResource.BOOKED_ITINERARY);
            ItineraryResource.addcancelBookingLink(cid, iid, response); //If the booking is booked, you can only cancel the booking. 
        }
        else
        {
            ItineraryResource.addPlanningLinks(cid, iid, response); //If the booking of one item fails, the planning can continue. 
        }
        
        response.setBookingStatus(itinerary.getStatus());
        
        return Response.ok(response).build();
    }

    private static boolean bookFlight(int bookingNumber, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditcard) throws BookFlightFaultMessage {
        dk.dtu.imm.airlinereservation.AirlineReservationService service = new dk.dtu.imm.airlinereservation.AirlineReservationService();
        dk.dtu.imm.airlinereservation.AirlineReservationPortType port = service.getAirlineReservationPortTypeBindingPort();
        return port.bookFlight(bookingNumber, creditcard);
    }

    private static boolean cancelFlight(int bookingNumber, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditcard) throws CancelFlightFaultMessage {
        dk.dtu.imm.airlinereservation.AirlineReservationService service = new dk.dtu.imm.airlinereservation.AirlineReservationService();
        dk.dtu.imm.airlinereservation.AirlineReservationPortType port = service.getAirlineReservationPortTypeBindingPort();
        return port.cancelFlight(bookingNumber, creditcard);
    }


}
