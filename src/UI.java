import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.*;

/*
 * Running UI class gives you defualt display of 1. login , 2.sign up, 3. exit
 * If login is chosen, this class calls UI from LoginDisplay
 * Sign up is not implemented yet
 *
 */
public class UI {

	private int selection;
	public Scanner input;
	public Database db;

	public UI(Scanner input, Database db) {
		this.input = input;
		this.db = db;
	}

	public void display() {
		try {
			while(true) {
				System.out.println("\n------ Menu ------");
				System.out.println(
					"Select an option: \n" +
					"  1) Login\n" +
					"  2) Exit\n"
				);

				try {
					selection = input.nextInt();
					if(input.hasNextLine()){input.nextLine();}
				}
				catch (InputMismatchException e) {
					System.out.println("Invalid input type. Try again.");
					if(input.hasNextLine()){input.nextLine();}
					continue;
				}

				switch (selection) {
					case 1:
						this.login();
						break;
					case 2:
						this.exit();
						break;
					default:
						System.out.println("Invalid selection. Retry.");
						break;
				}

			}
		} finally {
			this.db.closeConnections();
			this.input.close();
		}

	}

	private void login() {
		Login login = new Login(input, db);
		login.display();
	}

	private void exit() {
		input.close();
		System.out.println("Exiting...");
		System.exit(1);
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Database db = new Database();
		UI menu = new UI(input, db);
		//while (true)
		menu.display();
	}

}
