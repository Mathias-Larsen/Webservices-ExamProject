
package dk.dtu.imm.hotelreservation;

import javax.xml.ws.WebFault;
import dk.dtu.imm.hotelreservation.types.CancelHotelFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.5-b05 
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "cancelHotelFault", targetNamespace = "urn://types.hotelReservation.imm.dtu.dk")
public class CancelHotelFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private CancelHotelFault faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public CancelHotelFaultMessage(String message, CancelHotelFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public CancelHotelFaultMessage(String message, CancelHotelFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: dk.dtu.imm.hotelreservation.types.CancelHotelFault
     */
    public CancelHotelFault getFaultInfo() {
        return faultInfo;
    }

}