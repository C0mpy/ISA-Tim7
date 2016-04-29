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
	
	public static ArrayList<Product> getOpenForTable(String table, String rest) {
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("select ORDER_ID from VISIT where"
					+ " TABLE_has_SHIFT_TABLE_ID_TABLE=? and TABLE_has_SHIFT_TABLE_RESTAURANT_ID_RES=? and COALESCE(END, '') = ''");	
			ps.setString(1, table);
			ps.setString(2, rest);
			String orderId = "";
			ArrayList<Product> products = new ArrayList<Product>();
			ResultSet result = ps.executeQuery();
			if(result.next()) {
				orderId = result.getString(1);
			}
			if(!orderId.equals("")){
				PreparedStatement ps1 = connect.prepareStatement("select PRODUCT_ID_PRODUCT from PRODUCT_has_ORDER"
						+ " where ORDER_ID = ? and PRODUCT_RESTAURANT_ID_RES = ?");
				ps1.setString(1, orderId);
				ps1.setString(2, rest);
				ResultSet result1 = ps1.executeQuery();
				ArrayList<String> productIds = new ArrayList<String>();
				while(result1.next()) {
					productIds.add(result1.getString(1));
				}
				
				if(productIds.size()!=0) {
					for(int i = 0; i < productIds.size(); i++) {
						PreparedStatement ps2 = connect.prepareStatement("select * from PRODUCT where RESTAURANT_ID_RES=? and ID_PRODUCT=?");
						ps2.setString(1, rest);
						ps2.setString(2, productIds.get(i));
						ResultSet result2 = ps2.executeQuery();
						if(result2.next()){
							products.add(new Product(result2.getInt(1), result2.getString(2),
									result2.getDouble(3), result2.getString(4),
									result2.getInt(5)));
						}
						ps2.close();
						result2.close();
					}	
					
				}
			}
			result.close();
			ps.close();
			connect.close(); 
			return products;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
