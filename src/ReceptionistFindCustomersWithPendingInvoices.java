import java.sql.*;
import java.util.*;
import java.io.File;

public class ReceptionistFindCustomersWithPendingInvoices {

	private int selection;
	public Scanner input;
	public Database db;
	public String current_scid;

	public ReceptionistFindCustomersWithPendingInvoices(Scanner input, Database db, String current_scid) {
		this.input = input;
		this.db = db;
		this.current_scid = current_scid;
	}


	public void display() {
		while(true) {
		
		ArrayList<InvoiceCustomer> invoice = db.get_invoice_customer(current_scid);
	    for( int i = 0; i < invoice.size(); i++) {
	         System.out.println("Customer ID: " + invoice.get(i).getCid());
	         System.out.println("\tFirst name: " + invoice.get(i).getFirstname());
	         System.out.println("\tLast name: " + invoice.get(i).getLastname());
	         System.out.println("\tInvoice ID: " + invoice.get(i).getAptid());
	         
	         String day = invoice.get(i).getAptdate().substring(Math.max(invoice.get(i).getAptdate().length() - 2, 0));
	         String convDay = day.trim();
	         int day2 = Integer.parseInt(convDay);
				
			 int week = (int)((day2/7) + 1);
			 day2 = day2 % 7 ;
			 if(day2 == 0)
		 	 {
					day2 = 7;
			 }
	         
	         System.out.println("\tInvoice Week: " + week + " Day " + day2 );
	         System.out.println("\tAmount: " + invoice.get(i).total_amount());
	        }
	         			
		System.out.println("\nSelect an option:\n" +
														"\t1) Go Back\n");

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
			return;
		default:
			System.out.println("Invalid Selection. Try again.");
			break;	
		}

	} 
	}

}
