package testPackage;

import dk.dtu.imm.airlinereservation.BookFlightFaultMessage;
import dk.dtu.imm.airlinereservation.CancelFlightFaultMessage;
import org.junit.Test;
import static org.junit.Assert.*;
import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import dk.dtu.imm.fastmoney.types.ExpirationDateType;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.Before;
/**
 *
 * @author Jesper
 */
public class TestFlightReservation {
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
    @Test
    public void testGetflights() {
        DatatypeFactory df;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            return;
        }
        XMLGregorianCalendar date = df.newXMLGregorianCalendar("2013-12-25");
        String start = "Moscow";
        String end = "Berlin";
        int num = getFlights(start,end,date).size();
        assertEquals(2,num);
    }   
    @Test
    public void testBookAirlineReservation() {
        try {
            boolean toReturn = bookFlight(12345,creditcard);
            assertEquals(true,toReturn);
        } catch (BookFlightFaultMessage ex) {
            assertEquals("Error in order flight",ex.getFaultInfo().getMessage());
        }
        
    }

    private static boolean bookFlight(int bookingNumber, CreditCardInfoType creditcard) throws BookFlightFaultMessage {
        dk.dtu.imm.airlinereservation.AirlineReservationService service = new dk.dtu.imm.airlinereservation.AirlineReservationService();
        dk.dtu.imm.airlinereservation.AirlineReservationPortType port = service.getAirlineReservationPortTypeBindingPort();
        return port.bookFlight(bookingNumber, creditcard);
    }
    private static java.util.List<dk.dtu.imm.airlinereservation.types.FlightInformationType> getFlights(java.lang.String start, java.lang.String end, javax.xml.datatype.XMLGregorianCalendar date) {
        dk.dtu.imm.airlinereservation.AirlineReservationService service = new dk.dtu.imm.airlinereservation.AirlineReservationService();
        dk.dtu.imm.airlinereservation.AirlineReservationPortType port = service.getAirlineReservationPortTypeBindingPort();
        return port.getFlights(start, end, date);
    }
    private static boolean cancelFlight(int bookingNumber, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditcard) throws CancelFlightFaultMessage {
        dk.dtu.imm.airlinereservation.AirlineReservationService service = new dk.dtu.imm.airlinereservation.AirlineReservationService();
        dk.dtu.imm.airlinereservation.AirlineReservationPortType port = service.getAirlineReservationPortTypeBindingPort();
        return port.cancelFlight(bookingNumber, creditcard);
    }
}
