package banking_system.users;

public class Credentials {

	protected String username;
	protected String password;
	
	public Credentials(String username, String password)
	{
		setUsername(username);
		setPassword(password);
	}
	
	
	public void setUsername(String username) {
		 
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
