import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;


public class AdminAddNewService {
	private int selection;
	public Scanner input;
	public Database db;

	public AdminAddNewService(Scanner input, Database db) {
		this.input = input;
		this.db = db;
	}

	public void display() {
		while(true) {
			System.out.println("------ Admin: Add New Service ------");

			System.out.print("Service ID (8 digits): ");
			String service_id = input.nextLine();
			//System.out.print("Car manufacturer (Honda, Nissan, Toyota, Lexus, Infiniti): ");
			//String car_manuf = input.nextLine();
			System.out.print("Duration: ");
			String duration = input.nextLine();
			System.out.print("Service name: ");
			String service_name = input.nextLine();
			System.out.print("Service subtype (Engine, Exhaust, Electrical, Transmission, Tire, Heating and AC, or Schedule for maintenance only): ");
			String subtype = input.nextLine();
			System.out.print("Is this a repair service (Y/N): ");
			String repair_service = input.nextLine();
			System.out.print("Service maintenance schedule status (A, B, C, or N for not maintenance service): ");
			String schedule = input.nextLine();

			System.out.print("\nSelect an option:\n" +
													"\t1) Add Service\n" +
													"\t2) Go Back\n");

			try {
				selection = input.nextInt();
				if(input.hasNextLine()){input.nextLine();}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input type. Try again.");
				if(input.hasNextLine()){input.nextLine();}
				continue;
			}

			switch (selection){

				case 1:
					if (service_id.trim().isEmpty()  || duration.trim().isEmpty() || service_name.trim().isEmpty()){
						System.out.println("Failed. At least one of your inputs is empty.");
						continue;
					}

					try {
						/* 
						String insert_stmt = "INSERT INTO Services VALUES('" + service_id +
															"','" + car_manuf + "','" + duration + "','" +
															service_name + "','" + subtype + "','" +
															schedule + "','" + repair_service + "')";
						*/

						int rows_affected = this.db.addServiceToEachStore(service_id, duration, service_name, subtype, schedule, repair_service);

						if (rows_affected < 1) {
							System.out.println("Add service failed.");
							continue;
						} else {
							System.out.println("Add service successful.");
						}
					} catch (Exception e) {
						System.out.println("Sorry! Failed to create a new service due to invalid information.\nPlease try again!");
					}
					break;
				case 2:
					return;
				default:
					System.out.println("Invalid selection. Retry.");
					break;
			}
		}
	}
}
