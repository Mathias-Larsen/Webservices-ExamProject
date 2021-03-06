
package dk.dtu.imm.hotelreservation;

import javax.xml.ws.WebFault;
import dk.dtu.imm.hotelreservation.types.BookHotelFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.5-b05 
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "bookHotelFault", targetNamespace = "urn://types.hotelReservation.imm.dtu.dk")
public class BookHotelFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private BookHotelFault faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public BookHotelFaultMessage(String message, BookHotelFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public BookHotelFaultMessage(String message, BookHotelFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: dk.dtu.imm.hotelreservation.types.BookHotelFault
     */
    public BookHotelFault getFaultInfo() {
        return faultInfo;
    }

}
