package services;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import beans.Employee;
import dao.EmployeeDAO;
import dao.UserDAO;

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
		
		if(!isValidEmail((String)data.get("email"))){
			return "Invalid email address!";
		}
		
		if(!isValidDate((String)data.get("birth"))){
			return "Invalid format date birth!";
		}
		
		if(UserDAO.emailExists((String)data.get("email"))){
			return "User by entered email already exists in the database!";
		}
		
		EmployeeDAO.add((String)data.get("f_name"), (String)data.get("l_name"), 
				(String)data.get("email"), (String)data.get("pass")
				, (String)data.get("type"), Integer.parseInt((String)data.get("id_res"))
				, (String)data.get("birth"), (String)data.get("dress")
				, (String)data.get("shoe")
				);
		return "";
	}
	
	@POST
	@Path("/get")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Employee> get(JSONObject data){
		return EmployeeDAO.getEmployees((String)data.get("id_res"));
		
	}

	@POST
	@Path("/shifts")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addShift(JSONObject data){
		EmployeeDAO.addShift((String)data.get("email"),
				(String)data.get("date"),(String)data.get("time"));
		
	}
	
	public static final Pattern VALID_DATE_BIRTH_REGEX = 
		Pattern.compile("\\d{4}/\\d{2}/\\d{2}");
	
	private boolean isValidDate(String dateBirth) {
		Matcher matcher = VALID_DATE_BIRTH_REGEX .matcher(dateBirth);
		return matcher.find();
	}

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean isValidEmail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		return matcher.find();
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
		else if(((String)data.get("birth")).equals(""))
			return false;
		return true;
	}
	
}
