package beans;

public class ProductOrder {

	int id;
	int productId;
	int restaurantId;
	int orderId;
	String state;
	
	public ProductOrder(int id, int productId, int restaurantId, int orderId, String state) {
		super();
		this.id = id;
		this.productId = productId;
		this.restaurantId = restaurantId;
		this.orderId = orderId;
		this.state = state;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "ProductOrder [id=" + id + ", productId=" + productId + ", restaurantId=" + restaurantId + ", orderId="
				+ orderId + ", state=" + state + "]";
	}
}
