package beans;

enum EmployeeType { 
    COOK, BARTENDER, WAITER
}

public class Employee extends User {

	String employeeType;
	String restaurantId;
	String dress;
	String birth;
	String shoe;
	
	public Employee(String email, String firstName, String lastName, 
			String password, String type, String employeeType, 
			String restaurantId, String birth, String dress, String shoe) {
		super(email, firstName, lastName, password, type);
		this.employeeType = employeeType;
		this.restaurantId = restaurantId;
		this.birth = birth;
		this.dress = dress;
		this.shoe = shoe;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getDress() {
		return dress;
	}

	public void setDress(String dress) {
		this.dress = dress;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getShoe() {
		return shoe;
	}

	public void setShoe(String shoe) {
		this.shoe = shoe;
	}

	

	
}
