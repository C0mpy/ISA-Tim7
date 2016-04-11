package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import beans.City;

public class CityDAO {

	
	public static ArrayList<City> getAll() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("select * from CITY");
			ResultSet result = ps.executeQuery();
			ArrayList<City> cities = new ArrayList<City>();
			while(result.next()) {
				cities.add(new City(result.getString(1), result.getString(2)));
			}
			result.close();
			ps.close();
			connect.close(); 
			return cities;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void add(String zip, String name) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("insert into CITY (ZIP, NAME) values(?, ?)");
			ps.setString(1, zip);
			ps.setString(2, name);
			ps.executeUpdate();
			ps.close();
			connect.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
