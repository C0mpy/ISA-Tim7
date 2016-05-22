package beans;

public class Supplier extends User {

	String changed;
	
	public Supplier(String email, String firstName, String lastName, String password, String type) {
		super(email, firstName, lastName, password, type);
		// TODO Auto-generated constructor stub
	}

	public Supplier(String email, String fName, String lName, String password, String type, String changed) {
		super(email, fName, lName, password, type);
		this.changed = changed;
	}

}
