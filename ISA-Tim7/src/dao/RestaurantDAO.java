package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import beans.City;
import beans.Employee;
import beans.Friend;
import beans.Product;
import beans.Restaurant;

public class RestaurantDAO {

	public static void add(String name, String description, String address, String zip) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("insert into RESTAURANT (NAME, DESCRIPTION, ADDRESS, CITY_ZIP) values(?, ?, ?, ?)");
			ps.setString(1, name);
			ps.setString(2, description);
			ps.setString(3, address);
			ps.setString(4, zip);
			ps.executeUpdate();
			ps.close();
			connect.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Restaurant> getAll() {
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("select * from RESTAURANT");
			ResultSet result = ps.executeQuery();
			ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
			while(result.next()) {
				restaurants.add(new Restaurant(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5)));
			}
			result.close();
			ps.close();
			connect.close(); 
			return restaurants;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public static void addPlan(String id_res, String plan) {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("insert into PLAN (PLAN_RES) value(?) ON DUPLICATE KEY UPDATE ID_RES=?");
			ps.setString(1, plan);
			ps.setString(2, id_res);
			ps.executeUpdate();
			ps.close();
			connect.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static boolean productExists(String name, String id_res) {
			boolean response=false;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
				PreparedStatement ps = connect.prepareStatement("select * from PRODUCT where NAME=? and RESTAURANT_ID_RES=?");
				ps.setString(1, name);
				ps.setString(2, id_res);
				ResultSet result = ps.executeQuery();
				if(result.next())
					response = true;
				else
					response = false;
				
				result.close();
				ps.close();
				connect.close();
				
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			
			return response;
		
	}

	public static void addProduct(String name, String type, String description, 
			String price, int id_res) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("insert into PRODUCT (NAME, TYPE, DESCRIPTION, PRICE, RESTAURANT_ID_RES) values(?, ?, ?, ?, ?)");
			ps.setString(1, name);
			ps.setString(2, type);
			ps.setString(3, description);
			ps.setString(4, price);
			ps.setInt(5, id_res);
			ps.executeUpdate();
			ps.close();
			connect.close();
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}

	public static ArrayList<Product> getFood(String id_res) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("select NAME,"
					+ "PRICE, DESCRIPTION from PRODUCT where RESTAURANT_ID_RES=? and TYPE=?");
			ps.setString(1, id_res);
			ps.setString(2, "FOOD");
			ResultSet result = ps.executeQuery();
			ArrayList<Product> products = new ArrayList<Product>();
			while(result.next()){
				
				products.add(new Product("FOOD",
						Double.parseDouble(result.getString(2)),result.getString(1),
						result.getString(3)));
			}
			
			result.close();
			ps.close();
			connect.close(); 
			return products;
			
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Product> getBeverage(String id_res) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("select NAME,"
					+ "PRICE, DESCRIPTION from PRODUCT where RESTAURANT_ID_RES=? and TYPE=?");
			ps.setString(1, id_res);
			ps.setString(2, "BEVERAGE");
			ResultSet result = ps.executeQuery();
			ArrayList<Product> products = new ArrayList<Product>();
			while(result.next()){
				
				products.add(new Product("BEVERAGE",
						Double.parseDouble(result.getString(2)),result.getString(1),
						result.getString(3)));
			}
			
			result.close();
			ps.close();
			connect.close(); 
			return products;
			
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<Restaurant> search(String query,String zip){
		ArrayList<Restaurant> result=new ArrayList<Restaurant>();
		String upit="";
		if(query.equals("")){
			upit="RESTAURANT.NAME like '%%'";
		}else{
			String tokens[] = query.split(" ");
			
			for(String t: tokens){
				upit+="or LOWER(RESTAURANT.NAME) like '%"+t+"%' ";
			}
			upit=upit.substring(2);
		}
		String zipic;
		if(zip.equals("All")){
			zipic="";
		}else{
			zipic="and CITY.ZIP="+zip;
		}
	
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			
			PreparedStatement ps=connect.prepareStatement("SELECT RESTAURANT.ID_RES , RESTAURANT.NAME , RESTAURANT.DESCRIPTION , RESTAURANT.ADDRESS , CITY.NAME"
					+ " FROM RESTAURANT INNER JOIN CITY ON RESTAURANT.CITY_ZIP=CITY.ZIP "
					+ "WHERE ("+upit+") "+zipic+";"
					);
			
			
			
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
					
					result.add(new Restaurant(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
				
			}
			rs.close();
			ps.close();
			
			connect.close();
	
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
}
