package beans;

public class Shift {

	String id;
	String time;
	String date;
	String employee;
	
	public Shift(String id, String time, String date, String employee) {
		super();
		this.id = id;
		this.time = time;
		this.date = date;
		this.employee = employee;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

}
