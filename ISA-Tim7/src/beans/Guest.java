package beans;

public class Guest extends User {
	private boolean activated;
	
	public Guest(String email,String firstName,String lastName){
		super(email, firstName, lastName, null,"GUEST");
	}
	
	public Guest(String email, String firstName, String lastName, String password, String type,boolean activated) {
		super(email, firstName, lastName, password, type);
		this.activated=activated;
	}
	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	

}
