/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelGood.resource;

import dk.dtu.imm.airlinereservation.BookFlightFaultMessage;
import dk.dtu.imm.airlinereservation.CancelFlightFaultMessage;
import dk.dtu.imm.hotelreservation.BookHotelFaultMessage;
import dk.dtu.imm.hotelreservation.CancelHotelFaultMessage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    static Map<String,Thread> threads = new HashMap<String,Thread>();
    
    @PUT
    @Consumes(ItineraryResource.MEDIATYPE_TRAVELGOOD)
    @Produces(ItineraryResource.MEDIATYPE_TRAVELGOOD)
    public Response bookItinerary(@PathParam("cid") String cid, @PathParam("iid") String iid,@PathParam("action") String action, CreditCardInfoType creditcard) {
        
        String key = ItineraryResource.createKey(cid, iid);
        Itinerary itinerary = ItineraryResource.itinerarys.get(key);

        if (itinerary == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Itineary not found").build();
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
                return Response.status(Response.Status.FORBIDDEN).entity("Itineary cannot be booked").build();
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
                return Response.status(Response.Status.FORBIDDEN).entity("Itineary cannot be cancelled").build();
            }
        }
        else
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Action not allowed").build();
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
                if(b.getBookingType().equals("HOTEL"))
                {    
                    try {
                        cancelHotel(b.getBookingNumber(),cc);
                        b.setStatus(ItineraryResource.CANCELLED_BOOKING);
                    } 
                    catch (CancelHotelFaultMessage ex) {
                        fail = true;
                    }
                }
            }
        }        
        BookingStatusRepresentation response = new BookingStatusRepresentation();
        
        if(fail)
        {
            ItineraryResource.addSelfLink(cid, iid, response);
            itinerary.setStatus(ItineraryResource.FAILCANCELLED_ITINERARY);
        }
        else
        {
            itinerary.setStatus(ItineraryResource.CANCELLED_ITINERARY);
            String key = ItineraryResource.createKey(cid, iid);
            Thread t = threads.get(key);
            t.interrupt();
        }
        
        response.setBookingStatus(itinerary.getStatus());
        response.setIt(itinerary);
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
                    }
                    else if(b.getBookingType().equals("HOTEL"))
                    {                
                        bookHotel(b.getBookingNumber(),cc);
                        b.setStatus(ItineraryResource.CONFIRMED_BOOKING);
                        bookingsToCancel.add(b);   
                    }
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
                }
                else if(b.getBookingType().equals("HOTEL"))
                {
                    try 
                    {
                        cancelHotel(b.getBookingNumber(),cc);
                        b.setStatus(ItineraryResource.CANCELLED_BOOKING);
                    } catch (CancelHotelFaultMessage ex1) {
                        Logger.getLogger(BookingResource.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }                
            }
         }
        catch(BookHotelFaultMessage ex2)
        {
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
                }
                else if(b.getBookingType().equals("HOTEL"))
                {
                    try 
                    {
                        cancelHotel(b.getBookingNumber(),cc);
                        b.setStatus(ItineraryResource.CANCELLED_BOOKING);
                    } catch (CancelHotelFaultMessage ex1) {
                        Logger.getLogger(BookingResource.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }                
            }
        }
        
        BookingStatusRepresentation response = new BookingStatusRepresentation();
        ItineraryResource.addSelfLink(cid, iid, response);
        if(!fail)
        {
            itinerary.setStatus(ItineraryResource.BOOKED_ITINERARY);
            ItineraryResource.addcancelBookingLink(cid, iid, response); //If the booking is booked, you can only cancel the booking. 
            String key = ItineraryResource.createKey(cid, iid);
            
            Runnable r = new SleepThread(itinerary,key);
            Thread t = new Thread(r);
            t.start();
            threads.put(key, t);
        }
        else
        {
            ItineraryResource.addPlanningLinks(cid, iid, response); //If the booking of one item fails, the planning can continue. 
        }
        response.setIt(itinerary);
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

    private static boolean cancelHotel(int bookingNumber, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCard) throws CancelHotelFaultMessage {
        dk.dtu.imm.hotelreservation.HotelReservationService service = new dk.dtu.imm.hotelreservation.HotelReservationService();
        dk.dtu.imm.hotelreservation.HotelReservationPortType port = service.getHotelReservationPortTypeBindingPort();
        return port.cancelHotel(bookingNumber, creditCard);
    }

    private static boolean bookHotel(int bookingNumber, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo) throws BookHotelFaultMessage {
        dk.dtu.imm.hotelreservation.HotelReservationService service = new dk.dtu.imm.hotelreservation.HotelReservationService();
        dk.dtu.imm.hotelreservation.HotelReservationPortType port = service.getHotelReservationPortTypeBindingPort();
        return port.bookHotel(bookingNumber, creditCardInfo);
    }

    private class SleepThread implements Runnable
    {
        Itinerary it;
        String key;
        public SleepThread(Itinerary it, String key)
        {
            this.it = it;
            this.key = key;
       }
        public void run()
        {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            
            String date = it.getDate().toString();
            DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            Calendar d = Calendar.getInstance();
            try {
                d.setTime(fm.parse(date));
            } catch (ParseException ex) {
                Logger.getLogger(BookingResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            long toSleep = d.getTimeInMillis() - c.getTimeInMillis(); 
            try {
                Thread.sleep(toSleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(BookingResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            ItineraryResource.itinerarys.remove(key);
            
        }
    }
}
