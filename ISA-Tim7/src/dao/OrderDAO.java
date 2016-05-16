package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.joda.time.LocalTime;

import beans.Notification;
import beans.Product;
import beans.ProductOrder;

public class OrderDAO {

	public static ArrayList<ProductOrder> getOrder(String rest) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("select * "
					+ "from PRODUCT_has_ORDER "
					+ "where PRODUCT_RESTAURANT_ID_RES=? and STATE='OPEN'");	
			
			ps.setInt(1, Integer.parseInt(rest));
			ArrayList<ProductOrder> orders = new ArrayList<ProductOrder>();
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				orders.add(new ProductOrder(result.getInt(1), result.getInt(2), result.getInt(3),
						result.getInt(4), result.getString(5)));
			}
			result.close();
			ps.close();
			connect.close(); 
			return orders;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<ProductOrder> getOrderForTable(String table, String rest, String orderId) {
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			ArrayList<ProductOrder> products = new ArrayList<ProductOrder>();
			if(orderId != null && !orderId.equals("")){
				PreparedStatement ps1 = connect.prepareStatement("select * from PRODUCT_has_ORDER"
						+ " where ORDER_ID = ? and PRODUCT_RESTAURANT_ID_RES = ?");
				ps1.setString(1, orderId);
				ps1.setString(2, rest);
				ResultSet result1 = ps1.executeQuery();
				while(result1.next()) {
					products.add(new ProductOrder(result1.getInt(1), result1.getInt(2),
							result1.getInt(3), result1.getInt(4), result1.getString(5)));
				}
			}
			else {
				return null;
			}
			connect.close(); 
			return products;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getOrderIdForTable(String table, String rest) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("select ORDER_ID from VISIT where"
					+ " TABLE_has_SHIFT_TABLE_ID_TABLE=? and TABLE_has_SHIFT_TABLE_RESTAURANT_ID_RES=? and COALESCE(END, '') = ''");	
			ps.setString(1, table);
			ps.setString(2, rest);
			String orderId = "";
			ResultSet result = ps.executeQuery();
			if(result.next()) {
				orderId = result.getString(1);
			}
			ps.close();
			result.close();
			connect.close();
			return orderId;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public static void addProductForOrder(int product_id, String rest_id, String order_id) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("insert into PRODUCT_has_ORDER (PRODUCT_ID_PRODUCT, PRODUCT_RESTAURANT_ID_RES"
					+ ", ORDER_ID, STATE) values(?, ?, ?, 'OPEN')");
			ps.setInt(1, product_id);
			ps.setInt(2, Integer.parseInt(rest_id));
			ps.setInt(3, Integer.parseInt(order_id));
			ps.executeUpdate();
			ps.close();		
			connect.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	
	public static void removeOrder(int order_id) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("delete from PRODUCT_has_ORDER where"
					+ " ID=?");
			ps.setInt(1, order_id);
			ps.executeUpdate();
			ps.close();		
			connect.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	
	public static void closeTab(String order_id) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			LocalTime currentTime = new LocalTime();
			int endHour = currentTime.getHourOfDay();
			int endMinute = currentTime.getMinuteOfHour();
			String time = endHour + ":" + endMinute;
			PreparedStatement ps = connect.prepareStatement("update VISIT set END=?"
					+ " where ORDER_ID=?");
			ps.setString(1, time);
			ps.setInt(2, Integer.parseInt(order_id));
			ps.executeUpdate();
			ps.close();		
			connect.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	
	public static void cookToWaiterNotify(int productorder_id, String cookId) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("update PRODUCT_has_ORDER set STATE='FINISHED'"
					+ " where ID=?");
			ps.setInt(1, productorder_id);
			ps.executeUpdate();
			ps.close();	
			
			PreparedStatement ps1 = connect.prepareStatement("select ORDER_ID from PRODUCT_has_ORDER"
					+ " where ID=?");
			ps1.setInt(1, productorder_id);
			ResultSet result = ps1.executeQuery();
			result.next();
			int orderId = result.getInt(1);
			ps1.close();
			result.close();
			
			PreparedStatement ps2 = connect.prepareStatement("select TABLE_has_SHIFT_SHIFT_EMPLOYEE_USER_EMAIL from VISIT"
					+ " where ORDER_ID=?");
			ps2.setInt(1, orderId);
			ResultSet result1 = ps2.executeQuery();
			result1.next();
			String employeeId = result1.getString(1);
			ps2.close();
			result1.close();
			
			String text = "Pick up order with an id: " + productorder_id;
			PreparedStatement ps3 = connect.prepareStatement("insert into NOTIFICATION (FROM_ID, TO_ID, TEXT) "
					+ " VALUES (?, ?, ?)");
			ps3.setString(1, cookId);
			ps3.setString(2, employeeId);
			ps3.setString(3, text);
			ps3.executeUpdate();
			ps3.close();
			connect.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	
	public static Notification waiterNotificationRead(String email) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("select * from NOTIFICATION"
					+ " where TO_ID=?");
			ps.setString(1, email);
			ResultSet result = ps.executeQuery();
			Notification n = null;
			if(result.next()) {
				n = new Notification(result.getInt(1), result.getString(2), result.getString(3),
						result.getString(4));
				PreparedStatement ps1 = connect.prepareStatement("delete from NOTIFICATION where TO_ID=?");
				ps1.setString(1, email);
				ps1.executeUpdate();
				ps1.close();
			}
			
			ps.close();	
			result.close();
			connect.close();
			return n;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		return null;
	}
}
