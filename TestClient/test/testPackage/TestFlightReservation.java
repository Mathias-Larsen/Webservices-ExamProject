/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testPackage;

import dk.dtu.imm.airlinereservation.BookFlightFaultMessage;
import org.junit.Test;
import static org.junit.Assert.*;
import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import dk.dtu.imm.fastmoney.types.ExpirationDateType;
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
}
