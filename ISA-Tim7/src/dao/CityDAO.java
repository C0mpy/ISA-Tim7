package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import beans.City;

public class CityDAO {

	
	public static ArrayList<City> get() {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7113594?useSSL=false", "sql7113594", "TKeTKUdEXj");
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
}
