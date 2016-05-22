package beans;

public class Notification {
	
	int id;
	String from;
	String to;
	String text;
	
	public Notification(int id, String from, String to, String text) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.text = text;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", from=" + from + ", to=" + to + ", text=" + text + "]";
	}


}
