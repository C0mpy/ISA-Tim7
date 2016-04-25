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

import beans.Restaurant;
import dao.RestaurantDAO;

@Path("/restaurant")
@Singleton
public class RestaurantService {

	@Context
	HttpServletRequest request;
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void add(JSONObject data) {

		String zip = (((String) data.get("city")).split(" ")[0]);
		RestaurantDAO.add((String)data.get("name"), (String)data.get("description"), 
				(String)data.get("address"), zip);
	}
	
	@POST
	@Path("/getAll")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Restaurant> getAll() {
		
		return RestaurantDAO.getAll();
	}
	
	@POST
	@Path("/plan")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void plan(JSONObject data) {
		
		RestaurantDAO.addPlan((String)data.get("id_res"), (String)data.get("plan"));
	}
}
