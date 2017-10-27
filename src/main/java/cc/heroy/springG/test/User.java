package cc.heroy.springG.test;

public class User {
	private int id;
	private String name ;
	
	private User() {
		this.id = 5;
		this.name = "BeiwEi";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
