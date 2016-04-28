package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import beans.Friend;
import beans.Guest;

public class FriendDAO {

	public static boolean addFriend(String sender,String receiver){
		boolean ret=false;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps=connect.prepareStatement("INSERT INTO FRIENDS (FRIEND1, FRIEND2,ACCEPTED) VALUES (?, ?,0);");
			ps.setString(1, sender);
			ps.setString(2, receiver);
			ps.executeUpdate();
			ps.close();
			connect.close();
			
			ret=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public static ArrayList<Friend> getMyFriends(String sender,String order){
		
		ArrayList<Friend> ret = new ArrayList<Friend>();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps=connect.prepareStatement("SELECT USER.EMAIL , USER.F_NAME , USER.L_NAME , FRIENDS.ACCEPTED FROM USER INNER JOIN FRIENDS ON"
					+ " ( USER.EMAIL=FRIENDS.FRIEND1 OR USER.EMAIL=FRIENDS.FRIEND2) and USER.EMAIL!=?"+
					"WHERE FRIENDS.FRIEND1=? or FRIENDS.FRIEND2=? ORDER BY "+order+";");
			ps.setString(1, sender);
			ps.setString(2, sender);
			ps.setString(3, sender);
			
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				
				ret.add(new Friend(rs.getString(1),rs.getString(2),rs.getString(3),rs.getBoolean(4)));
			}
			rs.close();
			ps.close();
			
			connect.close();
			
			
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ret;
		
	}
	
	public static boolean deleteFriend(String user,String friend){
		boolean ret=false;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("DELETE FROM FRIENDS "
					+ "WHERE (FRIEND1=? and FRIEND2=?) or (FRIEND1=? and FRIEND2=?) ");
			ps.setString(1, user);
			ps.setString(2, friend);
			ps.setString(4, user);
			ps.setString(3, friend);
			
			ps.executeUpdate();
			
			ps.close();
			connect.close();
			ret=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ret;
	}
	
}
