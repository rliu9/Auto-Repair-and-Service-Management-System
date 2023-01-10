import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class CustomerViewAndScheduleService {
	private int selection;
	public Scanner input;
	public Database db;
    int cid;
    String sid;
	ArrayList<Service> cart;

	public CustomerViewAndScheduleService(Scanner input, Database db, int cid, String sid, ArrayList<Service> cart) {
		this.input = input;
		this.db = db;
        this.cid = cid;
        this.sid = sid;
		this.cart = cart;
	}

	public void display() {
		while(true) {
			System.out.println("------ Customer: View and Schedule Service ------");
			System.out.println(
					"Select an option: \n" +
					"\t1) View Service History\n" +
					"\t2) Schedule Service\n" +
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
                    CustomerViewServiceHistory view_service_history = new CustomerViewServiceHistory(input, db, sid);
					view_service_history.display();
					break;
				case 2: 
                    CustomerScheduleService schedule_service = new CustomerScheduleService(input, db, cid, sid, cart);
                    boolean back = schedule_service.display();
					if(back) {
						return;
					}
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
