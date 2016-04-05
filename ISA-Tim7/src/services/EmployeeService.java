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

import dao.EmployeeDAO;

@Path("/employee")
@Singleton
public class EmployeeService {

	@Context
	HttpServletRequest request;
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String add(JSONObject data) {
		
		if(!checkFields(data)){
			return "All fields must be filled!";
		}
		
		if(EmployeeDAO.emailExists((String)data.get("email"))){
			return "User by entered email already exists in the database!";
		}
		
		EmployeeDAO.add((String)data.get("f_name"), (String)data.get("l_name"), 
				(String)data.get("email"), (String)data.get("pass")
				, (String)data.get("type"));
		return "";
	}

	private boolean checkFields(JSONObject data) {
		if(((String)data.get("f_name")).equals(""))
			return false;
		else if(((String)data.get("l_name")).equals(""))
			return false;
		else if(((String)data.get("email")).equals(""))
			return false;
		else if(((String)data.get("pass")).equals(""))
			return false;
		else if(((String)data.get("type")).equals(""))
			return false;
		return true;
	}
	
}
