package hotelReservation.imm.dtu;

import dk.dtu.imm.hotelreservation.BookHotelFaultMessage;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;
import dk.dtu.imm.hotelreservation.types.AddressType;
import dk.dtu.imm.hotelreservation.types.BookHotel;
import dk.dtu.imm.hotelreservation.types.BookHotelFault;
import dk.dtu.imm.hotelreservation.types.BookHotelResponse;
import dk.dtu.imm.hotelreservation.types.CancelHotel;
import dk.dtu.imm.hotelreservation.types.CancelHotelFault;
import dk.dtu.imm.hotelreservation.CancelHotelFaultMessage;
import dk.dtu.imm.hotelreservation.types.ExpirationDateType;
import dk.dtu.imm.hotelreservation.types.GetHotels;
import dk.dtu.imm.hotelreservation.types.HotelBookingType;
import dk.dtu.imm.hotelreservation.types.HotelInfoArray;
import dk.dtu.imm.hotelreservation.types.HotelInfoType;
import dk.dtu.imm.fastmoney.*;
import dk.dtu.imm.fastmoney.types.*;

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
    
    public List<HotelInfoType> getHotels(String city, XMLGregorianCalendar arrivalDate, XMLGregorianCalendar departureDate)
    {
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


    public boolean bookHotel(int bookingNumber, CreditCardInfoType creditCardInfo, boolean creditCardGuaranteeRequired) throws BookHotelFaultMessage 
    {
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

    public boolean cancelHotel(int bookingNumber, CreditCardInfoType creditCard) throws CancelHotelFaultMessage {
        boolean success;
        HotelInfoType hotel = null;
        
        for(HotelInfoType hotelInfo : HOTELS)
        {
            if(hotelInfo.getBookingNumber()==bookingNumber)
            {
                hotel = hotelInfo;
                break;
            }
        }
        if(hotel==null)
        {
            CancelHotelFault fault = new CancelHotelFault();
            fault.setMessage("Hotel not found, could not cancel order");
            // The cancel hotel is not constructed with a real fault, just an output
            throw new CancelHotelFaultMessage("Error", fault);
        }
        try {
            int price = hotel.getPrice(); //is changed to an int now
            int refund = price/2;
            success = refundCreditCard(GROUPNUMBER, creditCard, refund, ACCOUNT);
            return success;
        } catch(Exception ex){
            CancelHotelFault fault = new CancelHotelFault();
            fault.setMessage("Cancellation of hotel booking failed: " + ex);
            throw new CancelHotelFaultMessage("Cancel failed ", fault);
        }
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
    
    private void generateHotelInformation()
    {
        //TOTO implement some hotel generation
    }
    
}
