
package dk.dtu.imm.airlinereservation;

import javax.xml.ws.WebFault;
import dk.dtu.imm.airlinereservation.types.CancelFlightFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.5-b05 
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "cancelFlightFault", targetNamespace = "urn://types.airlineReservation.imm.dtu.dk")
public class CancelFlightFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private CancelFlightFault faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public CancelFlightFaultMessage(String message, CancelFlightFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public CancelFlightFaultMessage(String message, CancelFlightFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: dk.dtu.imm.airlinereservation.types.CancelFlightFault
     */
    public CancelFlightFault getFaultInfo() {
        return faultInfo;
    }

}
