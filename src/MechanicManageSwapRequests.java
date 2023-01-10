import java.sql.*;
import java.util.Scanner;
import java.util.*;

public class MechanicManageSwapRequests {

	private int selection;
	public Scanner input;
	public Database db;
	public String current_eid;
	public String current_scid;
	public int request_id;

	public MechanicManageSwapRequests(Scanner input, Database db, String current_eid, String current_scid) {
		this.input = input;
		this.db = db;
		this.current_eid = current_eid;
		this.current_scid = current_scid;

	}

	public void display() {
		while(true) {
			try {
				System.out.print("\nEnter the following information\n" +
						"\tRequest ID:");
				request_id = input.nextInt();

				System.out.println("\nSelect an option:\n" +
						"\t1. Accept Swap\n"+
						"\t2. Reject Swap\n" +
						"\t3. Go back\n");
				selection = input.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}

			switch (selection) {
				case 1:
					try {
						int rows_affected = this.db.manageSwapRequest(request_id, true, current_eid, current_scid);
						if(rows_affected == -1) {
							System.out.println("Swap request update failed.\n");
						} else {
							System.out.println("Swap accepted.");
						}
					} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Swap request update failed.\n");
					}
					return;
				case 2:
					try {
						int rows_affected = db.manageSwapRequest(request_id, false, current_eid, current_scid);
						if(rows_affected == -1) {
							System.out.println("Swap request update failed.\n");
						} else {
							System.out.println("Swap accepted.");
						}
					} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Swap request update failed.\n");
					}
					return;
				case 3:
					return;
				default:
					System.out.println("Invalid Selection. Try again.");
					break;
			}
		}
	}
}
