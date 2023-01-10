import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;


public class CustomerProfile {
	public Scanner input;
	public Database db;
    private int selection;
	int cid;
	String sid;

	public CustomerProfile(Scanner input, Database db, int cid, String sid) {
		this.input = input;
		this.db = db;
		this.cid = cid;
		this.sid = sid;
	}

	public void display() {
		while(true) {
			System.out.println("\n------ Customer View and Update Profile ------\n");
			System.out.println(
					"Select an option: \n" +
					"\t1) View Profile\n" +
					"\t2) Add Car\n" +
					"\t3) Delete Car\n" +
					"\t4) Go back\n");
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
					CustomerViewProfile view_profile = new CustomerViewProfile(input, db, cid, sid);
					view_profile.display();
					return;
				case 2:
					CustomerAddCar customer_add_car = new CustomerAddCar(input, db, cid, sid);
					customer_add_car.display();
					return;
				case 3:
					CustomerDeleteCar customer_delete_car = new CustomerDeleteCar(input, db, cid, sid);
					customer_delete_car.display();
					return;
				case 4:
                    // customer landing
					return;
				default:
					System.out.println("Invalid Selection. Try again.");
					break;
			}
		}

	}
	

}