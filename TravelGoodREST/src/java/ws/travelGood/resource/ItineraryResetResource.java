package ws.travelGood.resource;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author Jesper
 * This class reset the resources. Is ONLY used for test purpose 
 */
@Path("itinerary/reset")
public class ItineraryResetResource {

    @PUT
    public Response reset() {
        ItineraryResource.reset();
        return Response.ok().build();
    }
}
