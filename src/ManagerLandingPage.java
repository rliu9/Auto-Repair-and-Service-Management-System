import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;


public class ManagerLandingPage {
	public Scanner input;
	private int selection;
	public Database db;
	public String username;
  public String current_scid;

	public ManagerLandingPage(Scanner input, Database db, String username) {
		this.input = input;
		this.db = db;
		this.username = username;
    	this.current_scid = this.db.get_scid_from_employee_username(username);
	}

	public void display() {

		while(true) {
			System.out.println("------ Manager ------");
			System.out.println(
					"Select an option: \n" +
					"\t1) Setup Store\n" +
					"\t2) Add New Employee\n" +
					"\t3) Logout\n");
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
				case 1: //go to setup store
          ManagerSetupStore setup_store_landing = new ManagerSetupStore(input, db, username, current_scid);
					setup_store_landing.display();
					break;
				case 2: //go to add new employee
					ManagerAddEmployees managerAddEmployees = new ManagerAddEmployees(input, db, current_scid);
					managerAddEmployees.display();
					break;
				case 3:
					return;
				default:
					System.out.println("Invalid Selection. Try again.");
					break;
			}
		}
	}

}
