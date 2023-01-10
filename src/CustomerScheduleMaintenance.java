import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class CustomerScheduleMaintenance {
	private int selection;
	public Scanner input;
	public Database db;
    String vin;
	String scid;
	ArrayList<Service> cart;

	public CustomerScheduleMaintenance(Scanner input, Database db, String vin, String scid, ArrayList<Service> cart) {
		this.input = input;
		this.db = db;
        this.vin = vin;
		this.scid = scid;
		this.cart = cart;
	}

	public void display() {
		while(true) {
			System.out.println("------ Customer: Schedule Maintenance ------");
            String service = db.get_next_schedule(vin);
			float cost = db.get_schedule_price(vin, scid, service);
			int duration = db.get_schedule_duration(vin, scid, service);
            System.out.println("Eligible Service: " + service);
            System.out.println("Cost: " + cost);
			System.out.println(
					"Select an option: \n" +
					"\t1) Accept\n" +
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
                    cart.add(new Service(service, cost, duration));
					return;
				case 2:
					return;
				default:
					System.out.println("Invalid Selection. Try again.");
					break;
			}
		}
	}

}
