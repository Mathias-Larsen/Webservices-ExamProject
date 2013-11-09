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
import java.util.ArrayList;
import dk.dtu.imm.airlinereservation.types.FlightType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
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
    
    private static ArrayList<FlightInformationType> FLIGHTS = new ArrayList<FlightInformationType>();
    private static ArrayList<Integer> BOOKEDFLIGHTS = new ArrayList<Integer>();
    private static int GROUPNUMBER = 11;
    private static AccountType ACCOUNT;
    
    public AirlineReservation()
    {
        ACCOUNT = new AccountType();//GENERATE THE LAMEDUCK ACCOUNT
        ACCOUNT.setName("LameDuck");
        ACCOUNT.setNumber("50208812");
        generateFLightInformation(); //GENERATE INFORMATION
    }
    
    public List<FlightInformationType> getFlights(String start, String end, XMLGregorianCalendar date) {
        ArrayList<FlightInformationType> output = new ArrayList<FlightInformationType>();
        for(FlightInformationType flightInfo : FLIGHTS)
        {
            if(flightInfo.getFlight().getStart().equals(start) && flightInfo.getFlight().getEnd().equals(end) && flightInfo.getFlight().getDateStart().equals(date))
            {
                output.add(flightInfo);
            }
        }
        return output;
    }

    public boolean bookFlight(int bookingNumber, CreditCardInfoType creditcard) throws BookFlightFaultMessage {

        boolean toReturn;
        FlightInformationType choosenFlightInfo = null;
        for(FlightInformationType flightInfo : FLIGHTS)
        {
            if(flightInfo.getBookingNumber()==bookingNumber)
            {
               choosenFlightInfo = flightInfo;
               break; // breakede her fordi den ikke behøver køre resten igennem
            }
        }
        if(choosenFlightInfo==null)
        {
           BookFlightFault fault = new BookFlightFault();
           fault.setMessage("Cannot find the booking number");
           throw new BookFlightFaultMessage("Error",fault); 
        }
        else
        {
            try {
                int price = choosenFlightInfo.getPrice();
                toReturn = chargeCreditCard(GROUPNUMBER,creditcard,price,ACCOUNT);
                BOOKEDFLIGHTS.add(choosenFlightInfo.getBookingNumber());
            } catch (CreditCardFaultMessage ex) {
                BookFlightFault fault = new BookFlightFault();
                fault.setMessage("Error in paying for the order");
                throw new BookFlightFaultMessage("Error",fault); 
            }
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
    
    //Generate flight information
    private void generateFLightInformation()
    {
        DatatypeFactory df;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            return;
        }
        FlightInformationType flightInformation = new FlightInformationType();
        FlightType flight = new FlightType();
        
        XMLGregorianCalendar date = df.newXMLGregorianCalendar("2013-12-25");
        flightInformation.setBookingNumber(12345);
        flightInformation.setReservationServiceName("SAS");
        flightInformation.setPrice(1200);
        flight.setCarrier("Scandinavians Airlines");
        flight.setStart("Moscow");
        flight.setEnd("Berlin");
        
        flight.setDateStart(date);
        flight.setDateEnd(date);
        flightInformation.setFlight(flight);

        FLIGHTS.add(flightInformation);
    }
    
}
