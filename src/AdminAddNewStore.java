import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class AdminAddNewStore {
	private int selection;
	public Scanner input;
	public Database db;

	public AdminAddNewStore(Scanner input, Database db) {
		this.input = input;
		this.db = db;
	}

	public void display() {
		while(true) {
			System.out.println("------ Admin: Add New Store ------");
			System.out.print("Please input the following details in the order shown" +
													" below\n\n");
			System.out.print("\tStore ID: ");
			String storeID = input.nextLine();
			System.out.print("\tStore Address: ");
			String store_address = input.nextLine();
			System.out.print("\tStore Phone: ");
			String store_phone = input.nextLine();
			System.out.print("\tManager first name: ");
			String manager_first_name = input.nextLine();
			System.out.print("\tManager last name: ");
			String manager_last_name = input.nextLine();
			System.out.print("\tManager username: ");
			String manager_username = input.nextLine();
			System.out.print("\tManager password: ");
			String manager_password = input.nextLine();

			System.out.print("\tManager salary: ");
			float manager_salary = 0;
			try {
				manager_salary = input.nextFloat();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}

			System.out.print("\tManager employee id: ");
			String manager_eid = input.nextLine();

			System.out.print("\tStore minimum mechanic wage: ");
			// String store_min_wage = input.nextLine();
			float store_min_wage = 0;
			try {
				store_min_wage = input.nextFloat();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}

			System.out.print("\tStore maximum mechanic wage: ");
			float store_max_wage = 0;
			try {
				store_max_wage = input.nextFloat();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}

			System.out.print("\tManager address: ");
			String manager_address = input.nextLine();
			System.out.print("\tManager email: ");
			String manager_email = input.nextLine();
			System.out.print("\tManager phone number: ");
			String manager_phone = input.nextLine();

			System.out.println("\nSelect an option:\n" +
													"\t1) Add Store\n" +
													"\t2) Go Back\n");
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
					try {
						int rows_affected = db.createStoreAndManagerUserAccount(storeID, store_address,
																		store_phone, store_min_wage, store_max_wage,
																		manager_username, manager_password, "Manager",
																		manager_eid, manager_first_name, manager_last_name,
																		manager_address, manager_email, manager_phone,
																		manager_salary);
						if (rows_affected == 4) {
							System.out.println("Add store successful.\n");
						} else {
							System.out.println("Add store failed.\n");
						}

					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Add store failed.\n");
					}
					break;
				case 2:
					return;
				default:
					System.out.println("Invalid Selection. Try again.\n");
					break;
			}
		}
	}

}
