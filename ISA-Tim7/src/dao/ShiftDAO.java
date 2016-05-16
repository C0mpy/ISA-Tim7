package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import beans.Shift;

public class ShiftDAO {

	public static Shift getCurrent(String email) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/timsedam?useSSL=false", "compy", "compara");
			PreparedStatement ps = connect.prepareStatement("select * from SHIFT where EMPLOYEE_USER_EMAIL=?");	
			ps.setString(1, email);
			
			LocalDate currentDate = new LocalDate();
			int currYear = currentDate.getYear();
			int currMonth = currentDate.getMonthOfYear();
			int currDay = currentDate.getDayOfMonth();
			
			LocalTime currentTime = new LocalTime();
			int currentMinute = currentTime.getMinuteOfHour();
			int currentHour = currentTime.getHourOfDay();
			ResultSet result = ps.executeQuery();
			Shift shift = null;
			while(result.next()) {
				String date = result.getString(3);
				String []d = date.split("-");
				if(currYear == Integer.parseInt(d[0]) && currMonth == Integer.parseInt(d[1])
						&& currDay == Integer.parseInt(d[2])) {
					String time = result.getString(2);
					String startTime = time.split("-")[0];
					String endTime = time.split("-")[1];
					
					int startHour = Integer.parseInt(startTime.split(":")[0]);
					int endHour = Integer.parseInt(endTime.split(":")[0]);
					
					if(currentHour > startHour && currentHour < endHour) {
						shift = new Shift(Integer.toString(result.getInt(1)), time, date, result.getString(4));
						break;
					}
					else if(currentHour == startHour && currentHour < endHour) {
						int startMinute = Integer.parseInt(startTime.split(":")[1]);
						if(currentMinute >= startMinute) {
							shift = new Shift(Integer.toString(result.getInt(1)), time, date, result.getString(4));
							break;
						}
					}
					else if(currentHour == endHour && currentHour > startHour) {
						int endMinute = Integer.parseInt(endTime.split(":")[1]);
						if(currentMinute < endMinute) {
							shift = new Shift(Integer.toString(result.getInt(1)), time, date, result.getString(4));
							break;
						}
					}
					else if(currentHour == startHour && currentHour == endHour) {
						int startMinute = Integer.parseInt(startTime.split(":")[1]);
						int endMinute = Integer.parseInt(endTime.split(":")[1]);
						if(currentMinute > startMinute && currentMinute < endMinute) {
							shift = new Shift(Integer.toString(result.getInt(1)), time, date, result.getString(4));
							break;
						}
					}

				}
			}
			result.close();
			ps.close();
			connect.close(); 
			return shift;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
