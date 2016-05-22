package beans;

public class Product {

	int id;
	String type;
	double price;
	String name;
	String idRestaurant;
	String description;
	
	public Product(int id, String type, double price, String name, String idRestaurant, String desc) {
		super();
		this.id = id;
		this.type = type;
		this.price = price;
		this.name = name;
		this.idRestaurant = idRestaurant;
		this.description = desc;
	}
	
	public Product(int id, String type, double price, String name, String description) {
		
		this.id = id;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getIdRestaurant() {
		return idRestaurant;
	}

	public void setIdRestaurant(String idRestaurant) {
		this.idRestaurant = idRestaurant;
	}

	@Override
	public String toString() {
		return "Product [orderId=" + id + ", type=" + type + ", price=" + price + ", name=" + name
				+ ", idRestaurant=" + idRestaurant + "]";
	}
	
	
}
