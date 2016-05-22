package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SupplierDAO {

	public static String isChangedPassword(String email) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("select CHANGED_PASSWORD from SUPPLIER where USER_EMAIL=?");
			ps.setString(1, email);
			ResultSet result = ps.executeQuery();
			String changed=null;
			while(result.next()){
				changed = result.getString(1);
				
			}
			
			result.close();
			ps.close();
			connect.close(); 
			return changed;
			
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public static void changePassword(String email, String new_password) {
		

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("update USER set PASS=? where EMAIL=?");
			ps.setString(1, new_password);
			ps.setString(2, email);
			
			ps.executeUpdate();
			ps.close();
			
			PreparedStatement ps2 = connect.prepareStatement("update SUPPLIER set CHANGED_PASSWORD=? where USER_EMAIL=?");
			ps2.setString(1, "1");
			ps2.setString(2, email);
			
			ps2.executeUpdate();
			ps2.close();
			connect.close();
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
		
	}

}
