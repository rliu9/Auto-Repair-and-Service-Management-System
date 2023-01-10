import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class CustomerIndividualServices {
	private int selection;
	public Scanner input;
	public Database db;
    String vin;
	String scid;
    String subtype;
	ArrayList<Service> cart;

	public CustomerIndividualServices(Scanner input, Database db, String vin, String scid, ArrayList<Service> cart, String subtype) {
		this.input = input;
		this.db = db;
        this.vin = vin;
		this.scid = scid;
		this.cart = cart;
        this.subtype = subtype;
	}

	public boolean display() {
		while(true) {
			System.out.println("------ Customer: Individuals Services in " + subtype + " Services ------");
            ArrayList<Service> services = db.get_repair_services(scid, vin, subtype);
            
			System.out.println("Select an option:");
            for( int i = 0; i < services.size(); i++ ) {
                System.out.println("\t" + (i + 1) + ") " + services.get(i).name);
            }
			System.out.println("\t" + (services.size() + 1) + ") Go Back\n");
			try {
				selection = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}
            if (selection == services.size() + 1 ) {
                return false;
            }
            else if (selection > services.size() + 1 ) {
                System.out.println("Invalid Selection. Try again.");
				continue;
            }
            else {
                cart.add(services.get(selection - 1));
                System.out.println("Added to Cart: " + services.get(selection - 1).name);
                return true;
            }
		}
	}

}
