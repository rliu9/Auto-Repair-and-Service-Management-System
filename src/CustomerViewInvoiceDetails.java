import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class CustomerViewInvoiceDetails {
	private int selection;
	public Scanner input;
	public Database db;
    int cid;
    String sid;

	public CustomerViewInvoiceDetails(Scanner input, Database db, int cid, String sid) {
		this.input = input;
		this.db = db;
        this.cid = cid;
        this.sid = sid;
	}

	public void display() {
		while(true) {
			System.out.println("------ Customer: Invoice Details ------");
			System.out.print("Invoice ID: ");
			String id = "";
			try {
				id = input.next();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}
            
            System.out.println(
					"Select an option: \n" +
					"\t1) View Invoice\n" +
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
                    Invoice invoice = db.get_invoice(sid, cid, id);
                    System.out.println("Invoice ID: " + invoice.id);
                    System.out.println("\tCustomer ID: " + cid);
                    System.out.println("\tVIN: " + invoice.vin);
					int date = Integer.parseInt(invoice.date.substring(Math.max(invoice.date.length() - 2, 0)).trim());
					int week = (int)((date / 7) + 1);
					int day = date % 7;
					if (day == 0) {
						day = 7;
					}
                    System.out.println("\tService Date: Week " + week + " Day " + day);
                    System.out.println("\tServices:");
                    for(int i = 0; i < invoice.services.size(); i++) {
                        System.out.println("\t\tService ID: " + invoice.services.get(i).id);
                        System.out.println("\t\tService Type: " + (invoice.services.get(i).repair ? "Repair " : "") + (invoice.services.get(i).group.equals("N") ? "" : "Maintainance"));
                        System.out.println("\t\tService Cost: " + (invoice.services.get(i).cost));
                    }
                    System.out.println("\tMechanic: " + invoice.mechanicName);
                    System.out.println("\tTotal Cost: " + invoice.totalCost);
                    System.out.println("\tStatus: " + (invoice.status ? "paid" : "unpaid"));
                    break;
				case 2:
					return;
				default:
					System.out.println("Invalid Selection. Try again.");
					break;
			}
		}
	}

}
