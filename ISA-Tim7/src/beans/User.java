package beans;

enum Type { 
    EMPLOYEE, R_MANAGER, SYS_MANAGER, GUEST, SUPPLIER
}

public abstract class User {
	
	String email;
	String firstName;
	String lastName;
	String password;
	Type type;
	
	public User(String email, String firstName, String lastName, String password, String type) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		setType(type);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return this.type.name();
	}

	public void setType(String type) {
		this.type = Type.valueOf(type);
	}
	
	
	
	
}
