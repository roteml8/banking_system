package banking_system.users;

import java.util.Scanner;

public class Runner {

	public static void main(String[]args)
	{
		Scanner reader = new Scanner(System.in);
		AppManager app = new AppManager();
		app.run();

	}
}
