package services;

import java.util.ArrayList;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import beans.ProductOrder;
import dao.OrderDAO;

@SuppressWarnings("serial")
@Path("/order")
@Singleton
public class OrderService extends HttpServlet {

	@Context
	HttpServletRequest request;
	
	@POST
	@Path("/getOpen")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ProductOrder> getOpen(JSONObject data) {
		
		return OrderDAO.getOrder((String)data.get("rest"));
		
	}
	
	@POST
	@Path("/getOrderForTable")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ProductOrder> getOrderForTable(JSONObject data) {
		
		return OrderDAO.getOrderForTable((String)data.get("table_id"), 
				(String)data.get("rest_id"), (String)data.get("order_id"));
		
	}
	
	@POST
	@Path("/getOrderIdForTable")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getOrderIdForTable(JSONObject data) {
		
		return OrderDAO.getOrderIdForTable((String)data.get("table_id"), (String)data.get("rest_id"));
	}
	
	@POST
	@Path("/addProductForOrder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addProductForOrder(JSONObject data) {
		
		OrderDAO.addProductForOrder((Integer)data.get("product_id"), (String)data.get("rest_id"),
				(String)data.get("order_id"));
	}
	

	@POST
	@Path("/removeOrder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void removeOrder(JSONObject data) {
		
		OrderDAO.removeOrder((Integer)data.get("order_id"));
	}
	
	@POST
	@Path("/closeTab")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void closeTab(JSONObject data) {
		
		OrderDAO.closeTab((String)data.get("order_id"));
		
	}
	
	@POST
	@Path("/cookToWaiterNotify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void cookToWaiterNotify(JSONObject data) {
		
		OrderDAO.cookToWaiterNotify((int)data.get("productorder_id"), (String)data.get("cook_id"));

	}
}
