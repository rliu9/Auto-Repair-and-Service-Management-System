import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class ManagerSetupStore {
	private int selection;
	public Scanner input;
	public Database db;
  public String username;
  public String current_scid;

	public ManagerSetupStore(Scanner input, Database db, String username,
                              String current_scid) {
		this.input = input;
		this.db = db;
    this.username = username;
    this.current_scid = current_scid;
	}

  public void display() {

		while(true) {
			System.out.println("------ Setup Store ------");
			System.out.println(
					"Select an option: \n" +
					"\t1) Add Employees\n" +
					"\t2) Setup Operational Hours\n" +
					"\t3) Setup Service Prices\n" +
          "\t4) Go Back\n");
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
				case 1: //add employees
          ManagerAddEmployees add_emp_landing = new ManagerAddEmployees(input, db, current_scid);
					add_emp_landing.display();
					break;
				case 2: //setup operational hours
					ManagerSetupOperationalHours oper_hours_landing = new ManagerSetupOperationalHours(input, db, current_scid);
					oper_hours_landing.display();
					break;
        case 3: //setup service prices
					ManagerSetupServicePrices setup_service_prices_landing =
                        new ManagerSetupServicePrices(input, db, current_scid);
					setup_service_prices_landing.display();
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
