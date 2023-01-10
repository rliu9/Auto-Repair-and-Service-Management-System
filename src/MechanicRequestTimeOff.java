import java.util.Scanner;
import java.util.*;

public class MechanicRequestTimeOff {

	private int selection;
	public Scanner input;
	public Database db;
	public String current_eid;
	public String current_scid;

	public MechanicRequestTimeOff(Scanner input, Database db, String current_eid, String current_scid) {
		this.input = input;
		this.db = db;
		this.current_eid = current_eid;
		this.current_scid = current_scid;
	}


	public void display() {
		while(true) {
			System.out.println("Please input the following details in the order shown." );

			System.out.print("\tWeek: ");
			int week = 0;
			try {
				week = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}

			System.out.print("\tDay: ");
			int day = 0;
			try {
				day = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}

			System.out.print("\tTimeslot start: ");
			int timeslot_begins = 0;
			try {
				timeslot_begins = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}

			System.out.print("\tTimeslot end: ");
			int timeslot_ends = 0;
			try {
				timeslot_ends = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
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
					boolean request = db.get_request_timeoff(current_eid, current_scid, week,
																									day, timeslot_begins, timeslot_ends);
					if(request) {
						System.out.println("The request has been approved");
					} else {
						System.out.println("The request has been denied");
					}
					break;
				case 2:
					return;
			}
		}
	}
}
