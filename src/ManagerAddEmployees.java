import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class ManagerAddEmployees {
	private int selection;
	public Scanner input;
	public Database db;
  public String current_scid;

	public ManagerAddEmployees(Scanner input, Database db, String current_scid) {
		this.input = input;
		this.db = db;
    this.current_scid = current_scid;
	}

	public void display() {
		while(true) {
			System.out.println("------ Manager: Add Employees ------");
			System.out.print("Please input the following details in the order shown" +
													" below\n\n");
			System.out.print("\tFirst name: ");
			String emp_first_name = input.nextLine();
			System.out.print("\tLast name: ");
			String emp_last_name = input.nextLine();
      System.out.print("\tAddress: ");
			String emp_address = input.nextLine();
			System.out.print("\tEmail: ");
			String emp_email = input.nextLine();
			System.out.print("\tPhone number: ");
			String emp_phone = input.nextLine();
      System.out.print("\tRole: ");
			String emp_role = input.nextLine();
      System.out.print("\tStart Date: ");
			String emp_start_date = input.nextLine();

      System.out.print("\tCompensation: ");
			float emp_compensation = 0;
			try {
				emp_compensation = input.nextFloat();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}

      System.out.print("\tUsername: ");
			String emp_username = input.nextLine();
			System.out.print("\tPassword: ");
			String emp_password = input.nextLine();
      System.out.print("\tEmployee id (9 digits): ");
			String emp_eid = input.nextLine();

			System.out.println("\nSelect an option:\n" +
													"\t1) Add\n" +
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
            int rows_affected = this.db.createEmployeeUserAccount(this.current_scid,
                                    emp_username, emp_password, emp_role, emp_eid,
                                    emp_first_name, emp_last_name, emp_address,
                                    emp_email, emp_phone, emp_compensation, 0);

						if (rows_affected >= 3) {
							System.out.println("Add Employee successful.\n");
							return;
						} else {
							System.out.println("Add Employee failed.\n");
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
