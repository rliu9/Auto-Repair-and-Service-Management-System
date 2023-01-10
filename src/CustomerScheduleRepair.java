import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class CustomerScheduleRepair {
	private int selection;
	public Scanner input;
	public Database db;
    String vin;
	String scid;
	ArrayList<Service> cart;

	public CustomerScheduleRepair(Scanner input, Database db, String vin, String scid, ArrayList<Service> cart) {
		this.input = input;
		this.db = db;
        this.vin = vin;
		this.scid = scid;
		this.cart = cart;
	}

	public void display() {
		while(true) {
			System.out.println("------ Customer: Schedule Repair ------");
			System.out.println(
					"Select an option: \n" +
					"\t1) Engine Services\n" +
					"\t2) Exhaust Services\n" +
					"\t3) Electrical Services\n" +
					"\t4) Transmission Services\n" +
					"\t5) Tire Services\n" +
					"\t6) Heating and AC Services\n" +
					"\t7) Go Back\n");
			try {
				selection = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}
            CustomerIndividualServices customer_service_page;
			boolean back = true;
			switch (selection) {
				case 1:
                    customer_service_page = new CustomerIndividualServices(input, db, vin, scid, cart, "Engine");
                    back = customer_service_page.display();
					if (back) {
						return;
					}
					break;
				case 2:
                    customer_service_page = new CustomerIndividualServices(input, db, vin, scid, cart, "Exhaust");
                    back = customer_service_page.display();
					if (back) {
						return;
					}
					break;
				case 3:
                    customer_service_page = new CustomerIndividualServices(input, db, vin, scid, cart, "Electrical");
                    back = customer_service_page.display();
					if (back) {
						return;
					}
					break;
				case 4:
                    customer_service_page = new CustomerIndividualServices(input, db, vin, scid, cart, "Transmission");
                    back = customer_service_page.display();
					if (back) {
						return;
					}
					break;
				case 5:
                    customer_service_page = new CustomerIndividualServices(input, db, vin, scid, cart, "Tire");
                    back = customer_service_page.display();
					if (back) {
						return;
					}
					break;
				case 6:
                    customer_service_page = new CustomerIndividualServices(input, db, vin, scid, cart, "Heating and AC");
                    back = customer_service_page.display();
					if (back) {
						return;
					}
					break;
				case 7:
					return;
				default:
					System.out.println("Invalid Selection. Try again.");
					break;
			}
		}
	}

}
