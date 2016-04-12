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
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
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
				
				ps1=connect.prepareStatement("select GUEST.ACTIVATED from USER inner join GUEST"+
											" on USER.EMAIL=GUEST.USER_EMAIL"+
											" where USER.EMAIL=? ; ");
				ps1.setString(1, email);
				result1=ps1.executeQuery();
				result1.next();
				boolean activated=result1.getBoolean(1);
				
				user = new Guest(email, fName, lName, password, type,activated);
			}
			else if(type.equals("EMPLOYEE")) {
				ps1 = connect.prepareStatement("select EMP_TYPE, RESTAURANT_ID_RES, DATE_OF_BIRTH, DRESS_SIZE, SHOE_SIZE from EMPLOYEE where USER_EMAIL=?");
				ps1.setString(1, email);
				result1 = ps1.executeQuery();
				result1.next();
				String empType = result1.getString(1);
				String restId = result1.getString(2);
				String birth = result1.getString(3);
				String dress = result1.getString(4);
				String shoe = result1.getString(5);
				
				
				if(empType.equals("COOK")) {
					user = new Cook(email, fName, lName, password, type, empType, restId, birth, dress, shoe);
				}
				else if(empType.equals("WAITER")) {	
					user = new Waiter(email, fName, lName, password, type, empType, restId, birth, dress, shoe);
				}
				else if(empType.equals("BARTENDER")) {	
					user = new Bartender(email, fName, lName, password, type, empType, restId, birth, dress, shoe);
				}
			}
			
			result.close();
			ps.close();
			if(result1 != null)
				result1.close();
			if(ps1 != null)
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
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
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
	
public static void addGuest(String email, String name, String lName, String pass) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("insert into USER (EMAIL, F_NAME, L_NAME, PASS, TYPE) values(?, ?, ?, ?, ?)");
			ps.setString(1, email);
			ps.setString(2, name);
			ps.setString(3, lName);
			ps.setString(4, pass);
			ps.setString(5, "GUEST");
			ps.executeUpdate();
			ps.close();
			
			PreparedStatement ps1 = connect.prepareStatement("insert into GUEST (USER_EMAIL, ACTIVATED) values(?, 0)");
			ps1.setString(1, email);
			
			ps1.executeUpdate();
			ps1.close();
			connect.close();
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
	
	public static boolean emailExists(String email) {
		boolean response=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("select * from USER where EMAIL=?");
			ps.setString(1, email);
			ResultSet result = ps.executeQuery();
			if(result.next())
				response = true;
			else
				response = false;
			
			result.close();
			ps.close();
			connect.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
}
