/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelReservation.imm.dtu;

import dk.dtu.imm.hotelreservation.BookHotelFaultMessage;
import dk.dtu.imm.hotelreservation.CancelHotelFaultMessage;
import javax.jws.WebService;
import java.util.*;
import dk.dtu.imm.fastmoney.*;
import dk.dtu.imm.fastmoney.types.AccountType;
import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import dk.dtu.imm.hotelreservation.types.BookHotelFault;
import dk.dtu.imm.hotelreservation.types.HotelInfoType;


/**
 *
 * @author Mathias
 */
@WebService(serviceName = "HotelReservationService", 
        portName = "HotelReservationPortTypeBindingPort", 
        endpointInterface = "dk.dtu.imm.hotelreservation.HotelReservationPortType", 
        targetNamespace = "urn://hotelReservation.imm.dtu.dk", 
        wsdlLocation = "WEB-INF/wsdl/HotelReservation/HotelReservation.wsdl")
public class HotelReservation {
    
    private BankService service = new BankService();
    
    private static ArrayList<HotelInfoType> HOTELS = new ArrayList<HotelInfoType>();
    private static ArrayList<Integer> BOOKEDHOTELS = new ArrayList<Integer>();
    private static AccountType ACCOUNT;
    private static int GROUPNUMBER = 11;
    
    public HotelReservation()
    {
        ACCOUNT = new AccountType();
        ACCOUNT.setName("NiceView");
        ACCOUNT.setNumber("50308815");
    }

    public java.util.List<dk.dtu.imm.hotelreservation.types.HotelInfoType> getHotels(java.lang.String city, javax.xml.datatype.XMLGregorianCalendar arrivalDate, javax.xml.datatype.XMLGregorianCalendar departureDate) {
                ArrayList<HotelInfoType> output = new ArrayList<HotelInfoType>();
        for(HotelInfoType hotelInfo : HOTELS)
        {
            if(hotelInfo.getAddress().equals(city))
            {
                output.add(hotelInfo);
            }
        }
        return output;
    }

    public boolean bookHotel(int bookingNumber, dk.dtu.imm.hotelreservation.types.CreditCardInfoType creditCardInfo, boolean creditCardGuaranteeRequired) throws BookHotelFaultMessage {
        boolean success;
        System.out.println("BookedHotel " + bookingNumber);
        HotelInfoType choosenHotelInfo = null;
        for(HotelInfoType hotelInfo : HOTELS)
        {
            if(hotelInfo.getBookingNumber()==bookingNumber)
            {
                choosenHotelInfo = hotelInfo;
                break;
            }
        }
        if(choosenHotelInfo==null)
        {
            BookHotelFault fault = new BookHotelFault();
            fault.setMessage("Invalid booking number");
            throw new BookHotelFaultMessage("Error", fault);
        } 
        else
        {
            try 
            {
                int price = choosenHotelInfo.getPrice();
                success = chargeCreditCard(GROUPNUMBER, creditCardInfo, price, ACCOUNT);
                BOOKEDHOTELS.add(choosenHotelInfo.getBookingNumber());
            } catch(CreditCardFaultMessage ex)
            {
                BookHotelFault fault = new BookHotelFault();
                fault.setMessage("Error in paying for the order");
                throw new BookHotelFaultMessage("Error", fault);
            }
        }
        return success;
    }

    public boolean cancelHotel(int bookingNumber, dk.dtu.imm.hotelreservation.types.CreditCardInfoType creditCard) throws CancelHotelFaultMessage {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
        //The following methods are calling the bank webservice
    private boolean chargeCreditCard(int group, CreditCardInfoType creditCardInfo, int amount, AccountType account) throws CreditCardFaultMessage {
        dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
        return port.chargeCreditCard(group, creditCardInfo, amount, account);
    }

    private boolean refundCreditCard(int group, CreditCardInfoType creditCardInfo, int amount, AccountType account) throws CreditCardFaultMessage {
        dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
        return port.refundCreditCard(group, creditCardInfo, amount, account);
    }

    private boolean validateCreditCard(int group, CreditCardInfoType creditCardInfo, int amount) throws CreditCardFaultMessage {
        dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
        return port.validateCreditCard(group, creditCardInfo, amount);
    }
    
}
