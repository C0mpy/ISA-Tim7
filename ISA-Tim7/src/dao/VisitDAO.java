package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.joda.time.LocalTime;

public class VisitDAO {

	public static String add(String guestEmail, String tableId, String restId, String shiftId,
			String employeeId) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			
			PreparedStatement ps = connect.prepareStatement("select `auto_increment` from INFORMATION_SCHEMA.TABLES where table_name = 'ORDERS'");
			ResultSet result = ps.executeQuery();
			result.next();
			String orderId = Integer.toString(result.getInt(1));
			
			PreparedStatement ps1 = connect.prepareStatement("insert into ORDERS(STATE, PRICE) values ('OPEN', 0)");
			ps1.executeUpdate();
			
			PreparedStatement ps2 = connect.prepareStatement("insert into VISIT(GUEST_USER_EMAIL, TABLE_has_SHIFT_TABLE_ID_TABLE, TABLE_has_SHIFT_TABLE_RESTAURANT_ID_RES, TABLE_has_SHIFT_SHIFT_ID_SHIFT, TABLE_has_SHIFT_SHIFT_EMPLOYEE_USER_EMAIL, ORDER_ID, START) VALUES ('', ?, ?, ?, ?, ?, ?)");
			ps2.setInt(1, Integer.parseInt(tableId));
			ps2.setInt(2, Integer.parseInt(restId));
			ps2.setInt(3, Integer.parseInt(shiftId));
			ps2.setString(4, employeeId);
			ps2.setInt(5, Integer.parseInt(orderId));
			LocalTime currentTime = new LocalTime();
			ps2.setString(6, currentTime.getHourOfDay() + ":" + currentTime.getMinuteOfHour());
			ps2.executeUpdate();
			ps.close();
			ps1.close();
			ps2.close();
			result.close();
			connect.close();
			return orderId;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
