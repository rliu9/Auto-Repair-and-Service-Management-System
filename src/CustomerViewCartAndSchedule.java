import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class CustomerViewCartAndSchedule {
	private int selection;
	public Scanner input;
	public Database db;
    String vin;
	String scid;
	ArrayList<Service> cart;

	public CustomerViewCartAndSchedule(Scanner input, Database db, String vin, String scid, ArrayList<Service> cart) {
		this.input = input;
		this.db = db;
        this.vin = vin;
		this.scid = scid;
		this.cart = cart;
	}

	public boolean display() {
		while(true) {
			System.out.println("------ Customer: Cart ------");
            System.out.println("Selected Services: ");
            float cost = 0;
            int duration = 0;
            for( int i = 0; i < cart.size(); i++) {
                cost += cart.get(i).cost;
                duration += cart.get(i).duration;
                System.out.println("\t> " + cart.get(i).name);
            }
            System.out.println("\nTotal Cost: " + cost);
			System.out.println(
					"Select an option: \n" +
					"\t1) Proceed with scheduling\n" +
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
                    CustomerScheduleServicesInCart schedule = new CustomerScheduleServicesInCart(input, db, duration, scid, duration, cart, cost, vin);
                    schedule.display();
					return true;
				case 2:
					return false;
				default:
					System.out.println("Invalid Selection. Try again.");
					break;
			}
		}
	}

}
