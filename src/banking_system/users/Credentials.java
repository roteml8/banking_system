package banking_system.users;

import java.util.Objects;

/**
 * a class that represents credentials by username and password
 * @author Rotem
 *
 */
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
	
	/**
	 * checks if given username is valid according to the constraints
	 * @param username the given username
	 * @return true if username is valid, false otherwise
	 */
	public static boolean isUsernameLegal(String username)
	{
		for (int i=0; i<username.length(); i++)
		{
			char currentChar = username.charAt(i);
			if (!Character.isDigit(currentChar) && !Character.isLetter(currentChar)) 
				return false;

		}
		return true;
	}
	
	/**
	 * checks if given password is valid according to the constraints
	 * @param password the given password
	 * @return true if password is valid, false otherwise
	 */
	public static boolean isPasswordLegal(String password)
	{
		if (password.length() > 8 || password.length() < 4)
			return false;
		boolean foundLetter = false, foundDigit = false;
		for (int i=0; i<password.length(); i++)
		{
			char currentChar = password.charAt(i);
			if (Character.isLetter(currentChar))
				foundLetter = true;
			else if (Character.isDigit(currentChar))
				foundDigit = true;

		}
		return (foundLetter && foundDigit);
	}


	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credentials other = (Credentials) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}


	@Override
	public String toString() {
		return "Credentials [username=" + username + ", password=" + password + "]";
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}
	
	
	
}
