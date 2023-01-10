import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class ManagerSetupMaintenanceServicePrices {
	private int selection;
	public Scanner input;
	public Database db;
  public String current_scid;

	public ManagerSetupMaintenanceServicePrices(Scanner input, Database db, String current_scid) {
		this.input = input;
		this.db = db;
    this.current_scid = current_scid;
	}

	public void display() {
		while(true) {
			System.out.println("------ Manager: Setup Maintenance Service Prices ------");
			System.out.print("Please input the following details in the order shown" +
													" below\n\n");

      System.out.print("\tSchedule A price: ");
			float a_price = 0;
			try {
				a_price = input.nextFloat();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}

      System.out.print("\tSchedule B price: ");
			float b_price = 0;
			try {
				b_price = input.nextFloat();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}

      System.out.print("\tSchedule C price: ");
      float c_price = 0;
      try {
        c_price = input.nextFloat();
        if(input.hasNextLine()){input.nextLine();}
      }
      catch (InputMismatchException e) {
        System.out.println("Invalid input type. Try again.");
        if(input.hasNextLine()){input.nextLine();}
        continue;
      }

	  System.out.print("\tCar Manufacturer (Honda, Nissan, Toyota, Lexus, Infiniti): ");
      String manuf = "";
      try {
        manuf = input.next();
        if(input.hasNextLine()){input.nextLine();}
      }
      catch (InputMismatchException e) {
        System.out.println("Invalid input type. Try again.");
        if(input.hasNextLine()){input.nextLine();}
        continue;
      }

			System.out.println("\nSelect an option:\n" +
													"\t1) Setup prices\n" +
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
            int rows_affected = this.db.updateSchedulePrices(current_scid, a_price, b_price, c_price, manuf);
						if (rows_affected == 3) {
							System.out.println("Maintenance service prices update successful.\n");
						} else {
							System.out.println("Maintenance service prices update failed.\n");
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Maintenance service prices update failed.\n");
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
