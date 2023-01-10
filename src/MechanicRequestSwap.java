import java.util.Scanner;
import java.util.*;

public class MechanicRequestSwap {

	private int selection;
	public Scanner input;
	public Database db;
	public String current_eid;
	public String current_scid;



	public MechanicRequestSwap(Scanner input, Database db, String current_eid, String current_scid) {
		this.input = input;
		this.db = db;
		this.current_eid = current_eid;
		this.current_scid = current_scid;
	}

	public void display() {
		while(true) {
			int requestor_week, requestor_day, requestor_slot_start, requestor_slot_end,
					requestee_week, requestee_day, requestee_slot_start, requestee_slot_end;
			String requestee_eid = "";
			try {
				System.out.println("Please input the following details in the order shown." );
				System.out.println("Your timeslots to swap: " );
				System.out.print("\tWeek: ");
				requestor_week = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
				System.out.print("\tDay: ");
				requestor_day = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
				System.out.print("\tTimeslot start: ");
				requestor_slot_start = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
				System.out.print("\tTimeslot end: ");
				requestor_slot_end = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
				System.out.print("Employee ID of mechanic being requested to swap:  " );
				requestee_eid = input.nextLine();
				System.out.print("\tRequested mechanic week: ");
				requestee_week = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
				System.out.print("\tRequested mechanic day: ");
				requestee_day = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
				System.out.print("\tRequested mechanic timeslot start: ");
				requestee_slot_start = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
				System.out.print("\tRequested mechanic timeslot end: ");
				requestee_slot_end = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}

			System.out.println(
					"Select an option: \n" +
					"\t1) Send the request\n" +
					"\t2) Go back\n" );

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
					try {
						int rows_affected = this.db.updateSwapRequest(current_scid,
																		current_eid, requestor_week, requestor_day,
																		requestor_slot_start, requestor_slot_end, requestee_eid,
																		requestee_week, requestee_day, requestee_slot_start,
																		requestee_slot_end);
						if(rows_affected ==1) {
							System.out.println("Mechanic swap request update successful.\n");
						}
						else {
							System.out.println("Mechanic swap request update failed.\n");
						}
					}
					 catch (Exception e) {
							e.printStackTrace();
							System.out.println("Mechanic swap request update failed.\n");
					}
					break;
				case 2:
					return;
			}
		}
	}
}
