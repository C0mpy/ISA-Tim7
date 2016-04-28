package beans;

public class Friend extends User {

	private boolean status;
	private String status1;
	public Friend(String email, String firstName, String lastName, String password, String type) {
		super(email, firstName, lastName, password, type);
		
	}
	
	public Friend(String email,String firstName,String lastName, boolean status){
		super(email, firstName, lastName, null, null);
		this.setStatus(status);
	}

	public Friend(String email,String firstName,String lastName, String status1){
		super(email, firstName, lastName, null, null);
		this.setStatus1(status1);
	}
	
	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

}
