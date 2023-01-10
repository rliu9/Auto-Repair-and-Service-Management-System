import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;


public class CustomerLanding {
	public Scanner input;
	public Database db;
    private int selection;
	public String scid;
	public int cid;
	public ArrayList<Service> cart;


	public CustomerLanding(Scanner input, Database db, String username) {
		this.input = input;
		this.db = db;
		this.scid = db.get_scid_from_customer_username(username);
		this.cid = db.get_cid_from_customer_username(username);
		this.cart = new ArrayList<>();
	}

	public void display() {

		while(true) {
			System.out.println("\n------ Customer ------\n");
			System.out.println(
					"Select an option: \n" +
					"\t1) View and Update Profile\n" +
					"\t2) View and Schedule Service\n" +
					"\t3) Invoices\n" +
					"\t4) Logout\n");
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
					CustomerProfile view_profile = new CustomerProfile(input, db, cid, scid);
					view_profile.display();
					break;
				case 2:
					CustomerViewAndScheduleService view_and_schedule_service = new CustomerViewAndScheduleService(input, db, cid, scid, cart);
					view_and_schedule_service.display();
					break;
				case 3:
					CustomerInvoices invoice = new CustomerInvoices(input, db, cid, scid);
					invoice.display();
					break;
				case 4:
					return;
				default:
					System.out.println("Invalid Selection. Try again.");
					break;
			}
		}

	}
	

}