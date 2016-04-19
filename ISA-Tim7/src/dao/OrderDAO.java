package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import beans.Product;

public class OrderDAO {

	public static ArrayList<Product> getOpen(String rest, String product) {
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("select b.ORDER_ID, p.TYPE, p.PRICE, p.NAME "
					+ "from PRODUCT_has_ORDER b "
					+ "join ORDERS o on b.ORDER_ID=o.ID "
					+ "join PRODUCT p on b.PRODUCT_ID_PRODUCT=p.ID_PRODUCT "
					+ "where p.RESTAURANT_ID_RES=? and p.TYPE=? and o.STATE=?");	
			
			ps.setInt(1, Integer.parseInt(rest));
			ps.setString(2, product);
			ps.setString(3, "OPEN");
			ArrayList<Product> produce = new ArrayList<Product>();
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				Product p = new Product(result.getInt(1), result.getString(2), result.getDouble(3), result.getString(4), Integer.parseInt(rest));
				produce.add(p);
			}
			result.close();
			ps.close();
			connect.close(); 
			return produce;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
