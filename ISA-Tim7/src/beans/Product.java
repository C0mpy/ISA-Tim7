package beans;

public class Product {

	int orderId;
	String type;
	double price;
	String name;
	int idRestaurant;
	String description;
	
	public Product(int id, String type, double price, String name, int idRestaurant) {
		super();
		this.orderId = id;
		this.type = type;
		this.price = price;
		this.name = name;
		this.idRestaurant = idRestaurant;
	}
	
	public Product(int id, String type, double price, String name, String description) {
		
		this.orderId = id;
		this.type = type;
		this.price = price;
		this.name = name;
		this.description = description;
	
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int id) {
		this.orderId = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdRestaurant() {
		return idRestaurant;
	}

	public void setIdRestaurant(int idRestaurant) {
		this.idRestaurant = idRestaurant;
	}

	@Override
	public String toString() {
		return "Product [orderId=" + orderId + ", type=" + type + ", price=" + price + ", name=" + name
				+ ", idRestaurant=" + idRestaurant + "]";
	}
	
	
}
