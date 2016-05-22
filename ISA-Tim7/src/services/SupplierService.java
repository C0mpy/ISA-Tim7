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

import dao.SupplierDAO;

@Path("/supplier")
@Singleton
public class SupplierService {

	@Context
	HttpServletRequest request;
	
	@POST
	@Path("/checkPassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String checkPassword(JSONObject data) {
		
		return SupplierDAO.isChangedPassword((String)data.get("email"));
	}
	
	@POST
	@Path("/changePassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String changePassword(JSONObject data) {
		
		if(((String)data.get("new_password")).trim().length()==0 || ((String)data.get("confirm_password")).trim().length()==0){
			return "Fields \"New Password\" and \"Confirm Password\" must be filled!";
		}
		
		if(!((String)data.get("new_password")).equals(((String)data.get("confirm_password")))){
			return "Passwords are different!";
		}
		
		SupplierDAO.changePassword((String)data.get("email"), (String)data.get("new_password") );
		return "";
	}
}
