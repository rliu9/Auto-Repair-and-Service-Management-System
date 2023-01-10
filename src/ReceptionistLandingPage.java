import java.sql.*;
import java.util.*;
import java.io.File;

public class ReceptionistLandingPage{

	public Scanner input;
	private int selection;
	public Database db;
	public String username;
	public String current_scid;

	public ReceptionistLandingPage (Scanner input, Database db, String username) {
		this.input = input;
		this.db = db;
		this.username = username;

	}

	public void display() {

		while(true) {
			System.out.println("------ Receptionist ------");
		    System.out.println(
		    		"Select an option: \n" +
					"1. Add New Customer Profile\n" +
					"2. Find Customers with Pending Invoices\n"	+
					"3. Logout\n");
			current_scid = db.get_scid_from_employee_username(username);

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
						ReceptionistAddNewCustomerProfilePage  recept_add_new_customer = new ReceptionistAddNewCustomerProfilePage (input, db, current_scid);
						recept_add_new_customer.display();
						break;
					case 2:
						ReceptionistFindCustomersWithPendingInvoices  recept_find_customer_pending_invoice = new ReceptionistFindCustomersWithPendingInvoices(input, db, current_scid);
						recept_find_customer_pending_invoice.display();
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
