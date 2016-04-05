package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeDAO {

	/**
	 * @param f_name
	 * @param l_name 
	 * @param email
	 * @param pass
	 * @param type
	 */
	public static void add(String f_name, String l_name, 
			String email, String pass, String type){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7113594?useSSL=false", "sql7113594", "TKeTKUdEXj");
			PreparedStatement ps = connect.prepareStatement("insert into USER (F_NAME, L_NAME, EMAIL, PASS, TYPE) values(?, ?, ?, ?, ?)");
			ps.setString(1, f_name);
			ps.setString(2, l_name);
			ps.setString(3, email);
			ps.setString(4, pass);
			ps.setString(5, "EMPLOYEE");
			ps.executeUpdate();
			ps.close();
			
			PreparedStatement ps2 = connect.prepareStatement("insert into EMPLOYEE (USER_EMAIL, EMP_TYPE, RESTAURANT_ID_RES) values(?, ?, ?)");
			ps2.setString(1, email);
			ps2.setString(2, type);
			//ps2.setString(3, email); id restorana
			
			connect.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean emailExists(String email) {
		boolean response=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7113594?useSSL=false", "sql7113594", "TKeTKUdEXj");
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
