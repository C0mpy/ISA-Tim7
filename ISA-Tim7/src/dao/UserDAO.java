package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import beans.Bartender;
import beans.Cook;
import beans.Guest;
import beans.RestaurantManager;
import beans.Supplier;
import beans.SystemManager;
import beans.User;
import beans.Waiter;

public class UserDAO {

	public static User login(String email, String password) {
		try {
			
			User user = null;

			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7113594?useSSL=false", "sql7113594", "TKeTKUdEXj");
			PreparedStatement ps = connect.prepareStatement("select F_NAME, L_NAME, TYPE USER from USER where EMAIL =? and PASS =?");
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet result = ps.executeQuery();
			
			String fName = "";
			String lName = "";
			String type = "";
			
			if(result.next()) {
				fName = result.getString(1);
				lName = result.getString(2);
				type = result.getString(3);
			}
			
			PreparedStatement ps1 = null;
			ResultSet result1 = null;
			
			
			if(type.equals("R_MANAGER")) { 
				ps1 = connect.prepareStatement("select RESTAURANT_ID_RES from R_MANAGER where USER_EMAIL=?");
				ps1.setString(1, email);
				result1 = ps1.executeQuery();
				result1.next();
				user = new RestaurantManager(email, fName, lName, password, type, result1.getString(1));
			}
			else if(type.equals("SYS_MANAGER")) {
				user = new SystemManager(email, fName, lName, password, type);
			}
			else if(type.equals("SUPPLIER")) {
				user = new Supplier(email, fName, lName, password, type);
			}
			else if(type.equals("GUEST")) {
				user = new Guest(email, fName, lName, password, type);
			}
			else if(type.equals("EMPLOYEE")) {
				ps1 = connect.prepareStatement("select EMP_TYPE, RESTAURANT_ID_RES from EMPLOYEE where USER_EMAIL=?");
				ps1.setString(1, email);
				result1 = ps1.executeQuery();
				result1.next();
				String restId = result1.getString(1);
				String empType = result1.getString(2);
				
				if(empType.equals("COOK")) {
					user = new Cook(email, fName, lName, password, type, empType, restId);
				}
				else if(empType.equals("WAITER")) {	
					user = new Waiter(email, fName, lName, password, type, empType, restId);
				}
				else if(empType.equals("BARTENDER")) {	
					user = new Bartender(email, fName, lName, password, type, empType, restId);
				}
			}
			
			result.close();
			if(result1 != null)
				result1.close();
			ps.close();
			if(result1 != null)
				ps1.close();
			connect.close(); 
			return user;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void addManager(String email, String name, String lName, String pass, String restId) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7113594?useSSL=false", "sql7113594", "TKeTKUdEXj");
			PreparedStatement ps = connect.prepareStatement("insert into USER (EMAIL, F_NAME, L_NAME, PASS, TYPE) values(?, ?, ?, ?, ?)");
			ps.setString(1, email);
			ps.setString(2, name);
			ps.setString(3, lName);
			ps.setString(4, pass);
			ps.setString(5, "R_MANAGER");
			ps.executeUpdate();
			ps.close();
			
			PreparedStatement ps1 = connect.prepareStatement("insert into R_MANAGER (USER_EMAIL, RESTAURANT_ID_RES) values(?, ?)");
			ps1.setString(1, email);
			ps1.setString(2, restId);
			ps1.executeUpdate();
			ps1.close();
			connect.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
