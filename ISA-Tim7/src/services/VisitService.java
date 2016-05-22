package services;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import dao.VisitDAO;

@Path("/visit")
@Singleton
public class VisitService {


	@POST
	@Path("/writeAnon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String writeAnon(JSONObject data) {

		return VisitDAO.add((String)data.get("guestEmail"), (String)data.get("tableId"), 
				(String)data.get("restId"), (String)data.get("shiftId"), (String)data.get("employeeId"));
	}
}
