/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelReservation.imm.dtu;

import dk.dtu.imm.hotelreservation.BookHotelFaultMessage;
import javax.jws.WebService;

/**
 *
 * @author Mathias
 */
@WebService(serviceName = "HotelReservationService", portName = "HotelReservationPortTypeBindingPort", endpointInterface = "dk.dtu.imm.hotelreservation.HotelReservationPortType", targetNamespace = "urn://hotelReservation.imm.dtu.dk", wsdlLocation = "WEB-INF/wsdl/HotelReservation/HotelReservation.wsdl")
public class HotelReservation {

    public java.util.List<dk.dtu.imm.hotelreservation.types.HotelInfoType> getHotels(javax.xml.datatype.XMLGregorianCalendar arrivalDate, javax.xml.datatype.XMLGregorianCalendar departureDate) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean bookHotel(java.lang.String bookingNumber, dk.dtu.imm.hotelreservation.types.CreditCardType creditCardInfo, boolean creditCardGuaranteeRequired) throws BookHotelFaultMessage {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String cancelHotel(java.lang.String bookingNumber) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
