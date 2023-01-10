import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class CustomerInvoices {
	private int selection;
	public Scanner input;
	public Database db;
    int cid;
    String sid;

	public CustomerInvoices(Scanner input, Database db, int cid, String sid) {
		this.input = input;
		this.db = db;
        this.cid = cid;
        this.sid = sid;
	}

	public void display() {
		while(true) {
			System.out.println("------ Customer: Invoices ------");
            ArrayList<String> vins = db.get_vins(sid, cid);
            for(int j = 0; j < vins.size(); j++ ) {
                ArrayList<Invoice> invoices = db.get_invoices(vins.get(j), sid, cid);
                for(int i = 0; i < invoices.size(); i++) {
                    System.out.println("\t" + (i+1) + ") " + invoices.get(i).id.strip() + ", " + (invoices.get(i).status ? "paid" : "unpaid"));
                }
            }
			System.out.println(
					"\nSelect an option: \n" +
					"\t1) View Invoice details\n" +
					"\t2) Pay Invoice\n" +
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
				case 1:
                    CustomerViewInvoiceDetails details = new CustomerViewInvoiceDetails(input, db, cid, sid);
                    details.display();
					break;
				case 2:
					CustomerPayInvoice pay = new CustomerPayInvoice(input, db, cid, sid);
                    pay.display();
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
