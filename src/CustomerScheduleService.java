import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class CustomerScheduleService {
	private int selection;
	public Scanner input;
	public Database db;
    int cid;
    String sid;
	ArrayList<Service> cart;

	public CustomerScheduleService(Scanner input, Database db, int cid, String sid, ArrayList<Service> cart) {
		this.input = input;
		this.db = db;
        this.cid = cid;
        this.sid = sid;
		this.cart = cart;
	}

	public boolean display() {
		while(true) {
			System.out.println("------ Customer: Schedule Service ------");
			System.out.print("Car Vin Number: ");
			String vin = "";
			try {
				vin = input.next();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}
            System.out.print("Car Mileage: ");
			int mileage = 0;
			try {
				mileage = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}
			System.out.println(
					"Select an option: \n" +
					"\t1) Add Schedule Maintenance\n" +
					"\t2) Add Schedule Repair\n" +
					"\t3) View cart and select schedule time\n" +
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
				case 1:
                    CustomerScheduleMaintenance schedule_maintenance = new CustomerScheduleMaintenance(input, db, vin, sid, cart);
					schedule_maintenance.display();
					break;
				case 2:
					CustomerScheduleRepair schedule_repair = new CustomerScheduleRepair(input, db, vin, sid, cart);
					schedule_repair.display(); 
					break;
                case 3: 
					CustomerViewCartAndSchedule view_cart = new CustomerViewCartAndSchedule(input, db, vin, sid, cart);
					boolean back = view_cart.display();
					if (back) {
						return true;
					}
					break;
				case 4:
					return false;
				default:
					System.out.println("Invalid Selection. Try again.");
					break;
			}
		}
	}

}
