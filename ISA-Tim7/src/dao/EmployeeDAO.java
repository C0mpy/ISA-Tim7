package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import beans.Employee;
import beans.Shift;

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
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
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

	public static ArrayList<Employee> getEmployees(String id){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("select EMAIL,"
					+ "F_NAME, L_NAME, PASS, EMP_TYPE, RESTAURANT_ID_RES, "
					+ "DATE_OF_BIRTH, DRESS_SIZE, EMPLOYEE.SHOE_SIZE from USER inner join "
					+ "EMPLOYEE on EMAIL=EMPLOYEE.USER_EMAIL where EMPLOYEE.RESTAURANT_ID_RES=?");
			ps.setString(1, id);
			
			ResultSet result = ps.executeQuery();
			ArrayList<Employee> employees = new ArrayList<Employee>();
			while(result.next()){
				employees.add(new Employee(result.getString(1),
						result.getString(2),result.getString(3),
						result.getString(4),"EMPLOYEE", result.getString(5),
						result.getString(6), result.getString(7),
						result.getString(8),result.getString(9)));
			}
			
			result.close();
			ps.close();
			connect.close(); 
			return employees;
			
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void addShift(String email, String date, String time) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("insert into SHIFT (EMPLOYEE_USER_EMAIL, DATE, TIME) values(?, ?, ?)");
			ps.setString(1, email);
			ps.setString(2, date);
			ps.setString(3, time);
			ps.executeUpdate();
			ps.close();
			connect.close(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<Shift> getShifts(String email){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("select * from SHIFT where EMPLOYEE_USER_EMAIL=?");
			ps.setString(1, email);
			
			ResultSet result = ps.executeQuery();
			ArrayList<Shift> shifts = new ArrayList<Shift>();
			while(result.next()){
				shifts.add(new Shift(result.getString(1),
						result.getString(2),result.getString(3), email));
			}
			
			result.close();
			ps.close();
			connect.close(); 
			return shifts;
			
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
