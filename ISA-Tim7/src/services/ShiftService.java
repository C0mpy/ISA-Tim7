package services;

import java.util.ArrayList;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import beans.Shift;
import dao.ShiftDAO;

@Path("/shift")
@Singleton
public class ShiftService {

	@Context
	HttpServletRequest request;
	
	@POST
	@Path("/getCurrent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Shift getOpen(JSONObject data) {
		
		return ShiftDAO.getCurrent((String)data.get("email"));
		
	}
}
