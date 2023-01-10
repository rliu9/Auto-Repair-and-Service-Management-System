import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class CustomerViewServiceHistory {
	private int selection;
	public Scanner input;
	public Database db;
	String scid;

	public CustomerViewServiceHistory(Scanner input, Database db, String scid) {
		this.input = input;
		this.db = db;
		this.scid = scid;
	}

	public void display() {
		while(true) {
			System.out.println("------ Customer: View Service History ------");
            System.out.print("Please input the following details in the order shown below\n\n");

            System.out.print("\tCar Vin Number: ");
			String vin = "";
			try {
				vin = input.next();
				if(input.hasNextLine()){input.nextLine();}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}
			System.out.println(
					"Select an option: \n" +
					"\t1) Show History\n" +
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
                    ArrayList<ServiceHistory> history = db.get_service_history(vin, scid);
                    for( int i = 0; i < history.size(); i++) {
						if(history.get(i).getName().equals("A") || history.get(i).getName().equals("B") || history.get(i).getName().equals("C")) {
							ArrayList<ServiceHistory> services = db.get_schedule_services(vin, scid, history.get(i).getName());
							System.out.println("Schedule: " + history.get(i).getName() + " (" + history.get(i).getId().strip() + "):");
							System.out.println("\tCost: " + history.get(i).getCost());
							System.out.println("\tMechanic Name: " + history.get(i).getMechanicName());
							int date = Integer.parseInt(history.get(i).getDate().substring(Math.max(history.get(i).getDate().length() - 2, 0)).trim());
							int week = (int)((date / 7) + 1);
							int day = date % 7;
							if (day == 0) {
								day = 7;
							}
							System.out.println("\tSchedule Start: Week: " + week + " | Day: " + day + " | Time Slot: " + history.get(i).getStart());
							System.out.println("\tSchedule End: Week: " + week + " | Day: " + day + " | Time Slot: " + history.get(i).getEnd());
							System.out.println("\tServices Done:");
							for(int j = 0; j < services.size(); j++) {
							System.out.println("\t\tService: " + services.get(j).getName());
							System.out.println("\t\tService Type: " + services.get(j).getServiceType() + "\n");
							}

						}
						else {
							System.out.println("Service: " + history.get(i).getName() + " (" + history.get(i).getId().strip() + "):");
							System.out.println("\tVin: " + history.get(i).getVin());
							System.out.println("\tService Type: " + history.get(i).getServiceType());
							System.out.println("\tCost: " + history.get(i).getCost());
							System.out.println("\tMechanic Name: " + history.get(i).getMechanicName());
							int date = Integer.parseInt(history.get(i).getDate().substring(Math.max(history.get(i).getDate().length() - 2, 0)).trim());
							int week = (int)((date / 7) + 1);
							int day = date % 7;
							if (day == 0) {
								day = 7;
							}
							System.out.println("\tService Start: Week: " + week + " | Day: " + day + " | Time Slot: " + history.get(i).getStart());
							System.out.println("\tService End: Week: " + week + " | Day: " + day + " | Time Slot: " + history.get(i).getEnd());
						}
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