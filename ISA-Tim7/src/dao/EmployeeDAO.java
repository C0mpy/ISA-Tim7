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
			String email, String pass, String type, Integer id_res,
			String birth, String dress, String shoe){
		
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
			
			PreparedStatement ps2 = connect.prepareStatement("insert into EMPLOYEE (USER_EMAIL, EMP_TYPE, RESTAURANT_ID_RES, DATE_OF_BIRTH, DRESS_SIZE, SHOE_SIZE) values(?, ?, ?, ?, ?, ?)");
			
			ps2.setString(1, email);
			ps2.setString(2, type);
			ps2.setString(3, String.valueOf(id_res));
			ps2.setString(4, birth);
			ps2.setString(5, dress);
			ps2.setString(6, shoe);
			ps2.executeUpdate();
			ps2.close();
			
			connect.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}

	
}
