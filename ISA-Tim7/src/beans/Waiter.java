package beans;

public class Waiter extends Employee {

	public Waiter(String email, String firstName, String lastName, String password, String type, String employeeType,
			String restaurantId, String birth, String dress, String shoe) {
		super(email, firstName, lastName, password, type, employeeType, restaurantId, birth, dress, shoe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Waiter [employeeType=" + employeeType + ", restaurantId=" + restaurantId + ", email=" + email
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", type=" + type
				+ "]";
	}
	
	

}
