import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class ManagerSetupOperationalHours {
	private int selection;
	public Scanner input;
	public Database db;
  public String current_scid;

	public ManagerSetupOperationalHours(Scanner input, Database db, String current_scid) {
		this.input = input;
		this.db = db;
    this.current_scid = current_scid;
	}

	public void display() {
		while(true) {
			System.out.println("------ Manager: Setup Operational Hours ------");
			System.out.print("Please input the following details in the order shown" +
													" below\n\n");
			System.out.print("\tOperational on Saturdays (Y/N): ");
			String saturdays = input.nextLine();

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

            String update_stmt = "UPDATE Service_Centers " +
                                  "SET saturdays = '" + saturdays + "'" +
                                  "WHERE scid = '" + current_scid + "'";
            int rows_affected = this.db.update(update_stmt);

						if (rows_affected == 1) {
							System.out.println("Service Center update successful.\n");
						} else {
							System.out.println("Service Center update failed.\n");
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Service Center update failed.\n");
					}
					return;
				case 2:
					return;
				default:
					System.out.println("Invalid Selection. Try again.\n");
					break;
			}
		}
	}

}
