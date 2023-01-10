import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class CustomerPayInvoice {
	private int selection;
	public Scanner input;
	public Database db;
    int cid;
    String sid;

	public CustomerPayInvoice(Scanner input, Database db, int cid, String sid) {
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
					"\t1) Pay Invoice\n" +
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
                    boolean paid = db.pay_invoice(id);
                    if (!paid) {
                        System.out.println("Invoice is already paid");
                    }
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
