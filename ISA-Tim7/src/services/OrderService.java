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

import beans.Product;
import dao.OrderDAO;

@Path("/order")
@Singleton
public class OrderService {

	@Context
	HttpServletRequest request;
	
	@POST
	@Path("/getOpen")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Product> getOpen(JSONObject data) {
		
		String product = "";
		String employee = (String)data.get("employee");
		if(employee.equals("COOK"))
			product = "FOOD";
		else
			product = "BEVERAGE";
		return OrderDAO.getOpen((String)data.get("rest"), product);
		
	}
	
	@POST
	@Path("/getOrderForTable")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Product> getOrderForTable(JSONObject data) {
		
		return OrderDAO.getOpenForTable((String)data.get("table_id"), 
				(String)data.get("rest_id"));
		
	}
}
