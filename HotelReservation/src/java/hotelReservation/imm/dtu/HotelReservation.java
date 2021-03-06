/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelReservation.imm.dtu;

import dk.dtu.imm.fastmoney.BankService;
import dk.dtu.imm.fastmoney.CreditCardFaultMessage;
import dk.dtu.imm.fastmoney.types.AccountType;
import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import dk.dtu.imm.hotelreservation.BookHotelFaultMessage;
import dk.dtu.imm.hotelreservation.types.HotelBookingType;
import dk.dtu.imm.hotelreservation.CancelHotelFaultMessage;
import dk.dtu.imm.hotelreservation.types.AddressType;
import dk.dtu.imm.hotelreservation.types.BookHotelFault;
import dk.dtu.imm.hotelreservation.types.CancelHotelFault;
import dk.dtu.imm.hotelreservation.types.HotelInfoType;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 *
 * @author Mathias and Jacob
 */
@WebService(serviceName = "HotelReservationService", portName = "HotelReservationPortTypeBindingPort", endpointInterface = "dk.dtu.imm.hotelreservation.HotelReservationPortType", targetNamespace = "urn://hotelReservation.imm.dtu.dk", wsdlLocation = "WEB-INF/wsdl/HotelReservation/HotelReservation.wsdl")
public class HotelReservation {
    private BankService service = new BankService();

    private static ArrayList<HotelInfoType> HOTELS = new ArrayList<HotelInfoType>();
    private static AccountType ACCOUNT;
    private static int GROUPNUMBER = 11;
    
    public HotelReservation()
    {
        ACCOUNT = new AccountType();
        ACCOUNT.setName("NiceView");
        ACCOUNT.setNumber("50308815");
        generateHotelInformation();
    }
    
    public List<HotelInfoType> getHotels(String city, XMLGregorianCalendar arrivalDate, XMLGregorianCalendar departureDate) {
        ArrayList<HotelInfoType> output = new ArrayList<HotelInfoType>();
        for(HotelInfoType hotelInfo : HOTELS)
        {
            
            if(hotelInfo.getAddress().getCity().equals(city))
            {
                output.add(hotelInfo);
            }
        }
        return output;
    }

    public boolean bookHotel(int bookingNumber, CreditCardInfoType creditCardInfo) throws BookHotelFaultMessage {
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
            try {
                int price = choosenHotelInfo.getPrice();
                validateCreditCard(GROUPNUMBER,creditCardInfo,price);
                success = chargeCreditCard(GROUPNUMBER, creditCardInfo, price, ACCOUNT);
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

     private void generateHotelInformation()
    {
        DatatypeFactory df;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            return;
        }
        XMLGregorianCalendar date = df.newXMLGregorianCalendar("2013-12-25");
        HotelInfoType hotelInfo = new HotelInfoType();
        HotelBookingType hotelBooking = new HotelBookingType(); 
        
        // hotel 1
        hotelInfo.setBookingNumber(1234);
        hotelInfo.setNameOfHotelReservationService("Hotels.com");
        hotelInfo.setPrice(300);
        
        AddressType addr = new AddressType();
        addr.setCity("Berlin");
        addr.setStreet("Karl Marx Strasse");
        addr.setCountry("Germany");
        addr.setZip("1234");
        hotelInfo.setAddress(addr);
        hotelInfo.setHotelName("Das Baumeister");
        hotelInfo.setCreditCardGuaranteeRequired(true);
        
        HOTELS.add(hotelInfo);
        
        // hotel 2        
        hotelInfo = new HotelInfoType();
        hotelBooking = new HotelBookingType(); 
        addr = new AddressType();
        
        hotelInfo.setBookingNumber(4321);
        hotelInfo.setNameOfHotelReservationService("Trivago");
        hotelInfo.setPrice(200);
        
        addr.setCity("Moscow");
        addr.setStreet("Staraya Square");
        addr.setCountry("Russia");
        addr.setZip("4321");
        hotelInfo.setAddress(addr);
        hotelInfo.setHotelName("Nostrovia!");
        hotelInfo.setCreditCardGuaranteeRequired(false);
        
        HOTELS.add(hotelInfo);
        
        // hotel 3
        hotelInfo = new HotelInfoType();
        hotelBooking = new HotelBookingType(); 
        addr = new AddressType();
        
        hotelInfo.setBookingNumber(45321);
        hotelInfo.setNameOfHotelReservationService("Trivago");
        hotelInfo.setPrice(1000);
        
        addr.setCity("Moscow");
        addr.setStreet("Staraya Square");
        addr.setCountry("Russia");
        addr.setZip("4321");
        hotelInfo.setAddress(addr);
        hotelInfo.setHotelName("VODKA!");
        hotelInfo.setCreditCardGuaranteeRequired(true);
        
        HOTELS.add(hotelInfo);
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
