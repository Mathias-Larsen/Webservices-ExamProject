/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.ws;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Jesper
 */
@WebService(serviceName = "travelGoodBPELHelperService", portName = "travelGoodBPELHelperPort", endpointInterface = "dk.dtu.imm.airlinereservation.TravelGoodBPELHelperPortType", targetNamespace = "urn://airlineReservation.imm.dtu.dk", wsdlLocation = "WEB-INF/wsdl/travelGoodBPELHelper/travelGoodBPELHelper.wsdl")
public class travelGoodBPELHelper {

    public javax.xml.datatype.XMLGregorianCalendar subtractDay(javax.xml.datatype.XMLGregorianCalendar input) {
        String d = input.toString();
                
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(df2.parse(d));
            System.out.println(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DATE, -1);
        GregorianCalendar c1 = new GregorianCalendar();
        XMLGregorianCalendar date2 = null;
        try {
 
            c1.setTimeInMillis(cal.getTimeInMillis());
            date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c1);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(travelGoodBPELHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date2;
        
    }
    
}
