import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class ManagerSetupServicePrices {
	private int selection;
	public Scanner input;
	public Database db;
  public String current_scid;

	public ManagerSetupServicePrices(Scanner input, Database db, String current_scid) {
		this.input = input;
		this.db = db;
    this.current_scid = current_scid;
	}

  public void display() {

		while(true) {
			System.out.println("------ Manager: Setup Service Prices ------");
			System.out.println(
					"Select an option: \n" +
					"\t1) Setup Maintenance Service Prices\n" +
					"\t2) Setup Repair Service Prices\n" +
          "\t3) Go Back\n");
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
				case 1: //Setup Maintenance Service Prices
          ManagerSetupMaintenanceServicePrices setup_maint_serv_prices =
                new ManagerSetupMaintenanceServicePrices(input, db, current_scid);
					setup_maint_serv_prices.display();
					break;
				case 2: //Setup Repair Service Prices
					ManagerSetupRepairServicePrices setup_repair_serv_prices = new ManagerSetupRepairServicePrices(input, db, current_scid);
					setup_repair_serv_prices.display();
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
