package services;

import java.util.ArrayList;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import beans.City;
import beans.User;
import dao.CityDAO;
import dao.UserDAO;

@Path("/city")
@Singleton
public class CityService {

	@Context
	HttpServletRequest request;
	
	@POST
	@Path("/getAll")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<City> get(JSONObject data) {

		return CityDAO.getAll();
	}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void add(JSONObject data) {

		CityDAO.add((String)data.get("zip"), (String)data.get("name"));
	}
}
