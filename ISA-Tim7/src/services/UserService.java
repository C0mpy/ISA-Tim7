package services;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import beans.User;
import dao.UserDAO;

@Path("/user")
@Singleton
public class UserService {
	
	@Context
	HttpServletRequest request;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(JSONObject data) {
		System.out.println("aloooooo alooooooo");
		return UserDAO.login((String)data.get("email"), 
				(String)data.get("password"));
	}
	
	
	@POST
	@Path("/addManager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addManager(JSONObject data) {

		String restId = (((String) data.get("city")).split(" ")[0]);
		UserDAO.addManager((String)data.get("email"), (String)data.get("name"), (String)data.get("lName"), (String)data.get("pass"), restId);
	}
	
	
}
