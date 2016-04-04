package beans;

public class City {

	int zip;
	String name;
	
	public City(String zip, String name) {
		super();
		this.zip = Integer.parseInt(zip);
		this.name = name;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
