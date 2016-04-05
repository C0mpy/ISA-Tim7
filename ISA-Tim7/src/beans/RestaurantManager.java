package beans;

public class RestaurantManager extends User {

	String restaurantId;
	
	public RestaurantManager(String email, String firstName, String lastName, String password, String type, String restaurantId) {
		super(email, firstName, lastName, password, type);
		this.restaurantId = restaurantId;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	

}
