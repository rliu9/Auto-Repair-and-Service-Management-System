import java.sql.*;
import java.util.Scanner;
import java.util.*;

public class MechanicAcceptRejectSwap {

	private int selection;
	public Scanner input;
	public Database db;
	public String current_eid;
	public String current_scid;

	public MechanicAcceptRejectSwap(Scanner input, Database db, String current_eid, String current_scid) {
		this.input = input;
		this.db = db;
		this.current_eid = current_eid;
		this.current_scid = current_scid;
	}

	public void display() {
		while(true) {
			System.out.println("\tRequest ID\tRequestor\t\tWeek\tDay\tTime Start\tTime End");
			ArrayList<SwapRequestList> schedule = db.get_swap_request(current_scid, current_eid);
			for( int i = 0; i < schedule.size(); i++) {
				System.out.print("\t"+schedule.get(i).swap_id);
				System.out.print("\t\t"+schedule.get(i).firstname_requestor+ " " + schedule.get(i).lastname_requestor);
				System.out.print("\t\t"+schedule.get(i).week);
				System.out.print("\t"+schedule.get(i).day);
				System.out.print("\t"+schedule.get(i).time_start);
				System.out.println("\t\t"+schedule.get(i).time_end+"\n");
			}
			System.out.println(
					"Select an option: \n" +
					"\t1) Manage Swap request\n" +
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
					MechanicManageSwapRequests manage_swap = new MechanicManageSwapRequests(input, db, current_eid, current_scid);
					manage_swap.display();
					break;
				case 2:
					return;
			}


		}
	}

}
