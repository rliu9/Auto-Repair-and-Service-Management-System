import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class CustomerScheduleServicesInCart {
	private int selection;
	public Scanner input;
	public Database db;
    int cid;
    String sid;
    int duration;
    float cost;
    ArrayList<Service> cart;
    String vin;

	public CustomerScheduleServicesInCart(Scanner input, Database db, int cid, String sid, int duration, ArrayList<Service> cart, float cost, String vin) {
		this.input = input;
		this.db = db;
        this.cid = cid;
        this.sid = sid;
		this.duration = duration;
        this.cart = cart;
        this.cost = cost;
        this.vin = vin;
	}

	public void display() {
		while(true) {
			System.out.println("------ Customer: Schedule Services In Cart ------");
            System.out.println("Select an option:");
            ArrayList<Schedule> schedules = db.get_open_time_slots(sid, duration);
            for( int i = 0; i < schedules.size(); i++ ){
                int day = (int)(Float.parseFloat(schedules.get(i).date) % 7);
                if(day == 0) {
                    day = 7;
                }
                if(schedules.get(i).start_time == 0) {
                    continue;
                }
                System.out.println("\t" + (i+1) + ") Week: " + (int)((Integer.parseInt(schedules.get(i).date)/7) + 1) + " Day: " + day + "  At Time Slot: " + schedules.get(i).start_time + "-" + (schedules.get(i).start_time + duration));
            }
			try {
				selection = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}
            if (selection > schedules.size()) {
                System.out.println("Invalid Selection. Try again.");
				continue;
            }
            else {
                Schedule schedule = schedules.get(selection - 1);
                int day = (int)(Float.parseFloat(schedule.date) % 7);
                if(day == 0) {
                    day = 7;
                }
                db.create_appointment(sid, schedule.eid, vin, schedule.date, day, schedule.start_time, duration, (int)cost, "N", "N", cart);
                cart.clear();
                return;
            }
		}
	}

}
