package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import beans.User;

public class UserDAO {

	public static User login(String email, String password) {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7113594?useSSL=false", "sql7113594", "TKeTKUdEXj");
			PreparedStatement ps = connect.prepareStatement("select F_NAME, L_NAME, TYPE USER from USER where EMAIL =? and PASS =?");
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet result = ps.executeQuery();
			User user = null;
			if(result.next()) {
				user =  new User(email, result.getString(1), result.getString(2), password,	result.getString(3));
			}
			result.close();
			ps.close();
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
