import java.sql.*;
import java.util.*;
import java.io.File;

public class ReceptionistAddNewCustomerProfilePage {
	private int selection;
	public Scanner input;
	public Database db;
	public String current_scid;

	public ReceptionistAddNewCustomerProfilePage(Scanner input, Database db, String current_scid) {
		this.input = input;
		this.db = db;
		this.current_scid = current_scid;
	}

	public void display() {
		while(true) {
			System.out.println("------ Add New Customer ProfilePage ------");
			System.out.print("Please input the following details in the order shown" );

			
			System.out.print("\tCustomer's first name: ");
			String customer_first_name = input.nextLine();
			System.out.print("\tCustomer's last name: ");
			String customer_last_name = input.nextLine();
			System.out.print("\tAddress: ");
			String adress = input.nextLine();
			System.out.print("\tEmail address: ");
			String email_adress = input.nextLine();
			System.out.print("\tPhone number: ");
			String phone_number = input.nextLine();
			System.out.print("\tUsername: ");
			String username = input.nextLine();
			System.out.print("\tVIN number: ");
			String vin_number = input.nextLine();
			System.out.print("\tCar manufacture: ");
			String car_manufacture = input.nextLine();
			System.out.print("\tCurrecnt mileage: ");
			String current_mileage = input.nextLine();
			System.out.print("\tYear: ");
			String year = input.nextLine();

			try{

	
				int cid = db.get_new_cid(current_scid);
				int row_affected = db.addNewCustomer(current_scid, cid, customer_first_name,  customer_last_name, adress,email_adress
						, phone_number ,username, vin_number, car_manufacture, current_mileage, year );
				

				if (row_affected == 4) {
					System.out.println("New customer profile added successful.\n");
				} else {
					System.out.println("New customer profile added failed.\n");
				}
	
			}
	
			catch (Exception e) {
					e.printStackTrace();
					System.out.println("Add customer profilepage failed.\n");
			}
	
	
	
			System.out.println("\nSelect an option:\n" +
														"\t1) Go Back\n");
	
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
				return;
			default:
				System.out.println("Invalid Selection. Try again.");
				break;
			
			
			
			}
	}
	
}
}
