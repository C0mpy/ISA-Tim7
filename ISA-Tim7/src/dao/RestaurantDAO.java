package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import beans.City;
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
}
