package beans;

enum EmployeeType { 
    COOK, BARTENDER, WAITER
}

public abstract class Employee extends User {

	EmployeeType employeeType;
	String restaurantId;
	
	public Employee(String email, String firstName, String lastName, String password, String type, String employeeType, String restaurantId) {
		super(email, firstName, lastName, password, type);
		setEmployeeType(employeeType);
		this.restaurantId = restaurantId;
	}

	public String getEmployeeType() {
		return this.employeeType.name();
	}

	public void setEmployeeType(String type) {
		this.employeeType = EmployeeType.valueOf(type);
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	
	
}
