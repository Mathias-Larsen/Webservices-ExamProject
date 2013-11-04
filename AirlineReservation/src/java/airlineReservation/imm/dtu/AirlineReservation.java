/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package airlineReservation.imm.dtu;


import dk.dtu.imm.airlinereservation.BookFlightFaultMessage;
import dk.dtu.imm.airlinereservation.CancelFlightFaultMessage;
import dk.dtu.imm.airlinereservation.types.BookFlightFault;
import java.util.List;
import javax.jws.WebService;
import dk.dtu.imm.airlinereservation.types.FlightInformationType;
import dk.dtu.imm.fastmoney.BankService;
import dk.dtu.imm.fastmoney.CreditCardFaultMessage;
import javax.xml.datatype.XMLGregorianCalendar;
import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import dk.dtu.imm.fastmoney.types.AccountType;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Jesper
 */
@WebService(serviceName = "AirlineReservationService", portName = "AirlineReservationPortTypeBindingPort", endpointInterface = "dk.dtu.imm.airlinereservation.AirlineReservationPortType", targetNamespace = "urn://airlineReservation.imm.dtu.dk", wsdlLocation = "WEB-INF/wsdl/AirlineReservation/AirlineReservation.wsdl")
public class AirlineReservation {
    //We need to reomve the following auto-generated line:
        //@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    //and replaing it with the following line to get the bankService to work:
    private BankService service = new BankService();

    public List<FlightInformationType> getFlights(String start, String end, XMLGregorianCalendar date) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean bookFlight(int bookingNumber, CreditCardInfoType creditcard) throws BookFlightFaultMessage {
        AccountType account = new AccountType();
        account.setName("TravelGood");
        account.setNumber("50108811");
        boolean toReturn;
        try {
            toReturn = chargeCreditCard(11,creditcard,200,account);
        } catch (CreditCardFaultMessage ex) {
           BookFlightFault fault = new BookFlightFault();
           fault.setMessage("Error in order flight");
           throw new BookFlightFaultMessage("Error",fault); 
        }
        return toReturn;
    }

    public boolean cancelFlight(int bookingNumber, CreditCardInfoType creditcard) throws CancelFlightFaultMessage {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    
    //The following methods are calling the bank webservice
    private boolean chargeCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount, dk.dtu.imm.fastmoney.types.AccountType account) throws CreditCardFaultMessage {
        dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
        return port.chargeCreditCard(group, creditCardInfo, amount, account);
    }

    private boolean refundCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount, dk.dtu.imm.fastmoney.types.AccountType account) throws CreditCardFaultMessage {
        dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
        return port.refundCreditCard(group, creditCardInfo, amount, account);
    }

    private boolean validateCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount) throws CreditCardFaultMessage {
        dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
        return port.validateCreditCard(group, creditCardInfo, amount);
    }
    
}
