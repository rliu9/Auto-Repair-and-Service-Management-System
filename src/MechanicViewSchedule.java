import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.*;


public class MechanicViewSchedule {
	public Scanner input;
	private int selection;
	public Database db;
	public String current_eid;
	public String current_scid;

	public MechanicViewSchedule (Scanner input, Database db, String current_eid, String current_scid) {
		this.input = input;
		this.db = db;
		this.current_eid = current_eid;
		this.current_scid = current_scid;
	}

	public void display() {

		ArrayList<MechanicScheduleHistory> schedule = db.get_mechanic_schedule(current_eid, current_scid);
		System.out.println("\tWeek\tDay\tTime Slot");
    for( int i = 0; i < schedule.size(); i++) {
				String date = schedule.get(i).schedule_date;
        System.out.print("\t" + (Integer.parseInt(date.substring(6).trim())/7+1));
        System.out.print("\t" + schedule.get(i).dotw);
        System.out.println("\t" + schedule.get(i).start_time + "-" +
																				(schedule.get(i).start_time + schedule.get(i).duration));
    }

		while(true) {
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
