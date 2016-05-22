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
	@Path("/searchRestaurants")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Restaurant> search(JSONObject data) {

		String zip = (((String) data.get("city")).split(" ")[0]);
		
		return RestaurantDAO.search((String)data.get("query"), zip);
	}
	@POST
	@Path("/getAll")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Restaurant> getAll() {
		
		return RestaurantDAO.getAll();
	}
	
	@POST
	@Path("/getResDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant getResDetails(JSONObject data) {
		
		return RestaurantDAO.getRestaurant((String)data.get("id_res"));
	}
	
	@POST
	@Path("/getProdDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Product getProdDetails(JSONObject data) {
		
		return RestaurantDAO.getProduct((String)data.get("id_res"),(String)data.get("id_product"));
	}
	
	@POST
	@Path("/addResDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addResDetails(JSONObject data) {
		if(((String)data.get("name")).trim().length()==0){
			return "Field \"Name\" must be filled!";
		}
		RestaurantDAO.modifyRestaurantDetails((String)data.get("id_res"), (String)data.get("name"), (String)data.get("description") );
		return "";
	}
	
	@POST
	@Path("/plan")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void plan(JSONObject data) {
		
		RestaurantDAO.addPlan((String)data.get("id_res"), (String)data.get("plan"));
	}
	
	@POST
	@Path("/modifyPlan")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void modifyPlan(JSONObject data) {
		
		RestaurantDAO.modifyPlan((String)data.get("id_res"), (String)data.get("plan"));
	}
	
	@POST
	@Path("/deleteProd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteProd(JSONObject data) {
		
		RestaurantDAO.deleteProduct((String)data.get("id_res"), (String)data.get("id_product"));
	}
	
	@POST
	@Path("/getPlan")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getPlan(JSONObject data) {
		
		return RestaurantDAO.getPlan((String)data.get("id_res"));
	}
	
	@POST
	@Path("/addProduct")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addProduct(JSONObject data) {
		
		if(!checkFields(data)){
			return "Fields \"Name\" and \"Price\" must be filled!";
		}
		
		if(!isValidPrice((String)data.get("price"))){
			return "Invalid price!";
		}
		
		if(RestaurantDAO.productExists((String)data.get("name"),(String)data.get("id_res"))){
			return "Product by entered name already exists in the database!";
		}
		
		RestaurantDAO.addProduct((String)data.get("name"), (String)data.get("type"), 
				(String)data.get("description"), (String)data.get("price")
				, Integer.parseInt((String)data.get("id_res"))
				);
		return "";
	}
	
	@POST
	@Path("/modifyProdDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String modifyProdDetails(JSONObject data) {
		
		if(!checkFields(data)){
			return "Fields \"Name\" and \"Price\" must be filled!";
		}
		
		if(!isValidPrice((String)data.get("price"))){
			return "Invalid price!";
		}
		
		RestaurantDAO.modifyProduct((String)data.get("name"), (String)data.get("type"), 
				(String)data.get("description"), (String)data.get("price")
				, (String)data.get("id_res"),(String)data.get("id_product"));
		return "";
	}

	private boolean isValidPrice(String price) {
		Double pr=0.0;
		try{
			pr=Double.parseDouble(price);
		}catch(Exception e){
			return false;
		}
		
		if(pr<=0)
			return false;
		
		return true;
	}

	private boolean checkFields(JSONObject data) {
		if(((String)data.get("name")).equals(""))
			return false;
		else if(((String)data.get("price")).equals(""))
			return false;
		return true;
	}
	
	@POST
	@Path("/getFood")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Product> getFood(JSONObject data){
		return RestaurantDAO.getFood((String)data.get("id_res"));
		
	}
	
	@POST
	@Path("/getBeverage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Product> getBeverage(JSONObject data){
		return RestaurantDAO.getBeverage((String)data.get("id_res"));
		
	}
	
	@POST
	@Path("/getProducts")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Product> getProducts(JSONObject data){
		return RestaurantDAO.getProducts((String)data.get("id_res"));
		
	}
}
