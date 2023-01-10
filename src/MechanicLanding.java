import java.sql.*;
import java.util.Scanner;
import java.util.*;
import java.io.File;

public class MechanicLanding{

	public Scanner input;
	private int selection;
	public Database db;
	public String username;
	public String current_scid;
	public String current_eid;

	public MechanicLanding (Scanner input, Database db, String username) {
		this.input = input;
		this.db = db;
		this.username = username;
		this.current_scid = this.db.get_scid_from_employee_username(username);
		this.current_eid = this.db.get_eid_from_employee_username(username);
	}

	public void display() {

		while(true) {
			 System.out.println("------ Mechanic ------");
			    System.out.println(
			    		"Select an option: \n" +
						"1. View Schedule\n" +
						"2. Request TimeOff\n"	+
						"3. Request Swap\n" +
						"4. Accept/Reject Swap\n" +
						"5. Logout"
						);
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
					MechanicViewSchedule  mechanic_view_schedule = new MechanicViewSchedule(input, db, current_eid, current_scid);
					mechanic_view_schedule.display();
					break;
				case 2:
					MechanicRequestTimeOff mechanic_request_timeoff = new MechanicRequestTimeOff(input, db, current_eid, current_scid);
					mechanic_request_timeoff.display();
					break;
				case 3:
					MechanicRequestSwap mechanic_request_swap = new MechanicRequestSwap(input, db, current_eid, current_scid);
					mechanic_request_swap.display();
					break;
				case 4:
					MechanicAcceptRejectSwap mechanic_accept_reject_swap = new MechanicAcceptRejectSwap(input, db, current_eid, current_scid);
					mechanic_accept_reject_swap.display();
					break;
				case 5:
					return;
				default:
					System.out.println("Invalid Selection. Try again.");
					break;
			}
		}
	}
}
