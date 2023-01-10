import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class ManagerSetupRepairServicePrices {
	private int selection;
	public Scanner input;
	public Database db;
  public String current_scid;

	public ManagerSetupRepairServicePrices(Scanner input, Database db, String current_scid) {
		this.input = input;
		this.db = db;
    this.current_scid = current_scid;
	}

	public void display() {
		while(true) {
			System.out.println("------ Manager: Setup Repair Service Prices ------");
			System.out.print("Please input the following details in the order shown" +
													" below\n\n");

      HashMap<String, Float> repair_services_prices = new HashMap<>();

      HashMap<String, String> repair_services = this.db.load_repair_services_available();

      int count = 0;

      for (Map.Entry<String, String> entry : repair_services.entrySet()) {
        String service_name = entry.getKey();
        String service_id = entry.getValue();

        System.out.print("\t" + service_name + " price: ");
        float price = 0;
        while(true) {
          try {
            price = input.nextFloat();
            if(input.hasNextLine()){input.nextLine();}
            break;
          } catch (InputMismatchException e) {
            System.out.println("Invalid input type. Try again.");
            if(input.hasNextLine()){input.nextLine();}
          }
        }
        repair_services_prices.put(service_id, price);
        count += 1;
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
            int rows_affected = this.db.upload_repair_services_prices(repair_services_prices, current_scid);
						if (rows_affected >= count) {
							System.out.println("Repair service prices update successful.\n");
						} else {
							System.out.println("Repair service prices update failed.\n");
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Repair service prices update failed.\n");
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
