package beans;

public class Restaurant {

	String id;
	String name;
	String description;
	String address;
	String zip;
	String city;
	
	public Restaurant(String id){
		this.id = id;
	}
	public Restaurant(String id, String name, String description, String address, String zip) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.zip = zip;
	}
	
	public Restaurant(String id, String name, String description, String address,String zip, String city) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.city = city;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
