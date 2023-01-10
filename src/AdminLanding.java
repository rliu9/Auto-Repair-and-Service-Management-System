import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;


public class AdminLanding {
	public Scanner input;
	private int selection;
	public Database db;
	public String username;

	public AdminLanding(Scanner input, Database db, String username) {
		this.input = input;
		this.db = db;
		this.username = username;
	}

	public void display() {

		while(true) {
			System.out.println("------ Admin ------");
			System.out.println(
					"Select an option: \n" +
					"\t1) System Set Up\n" +
					"\t2) Add New Store\n" +
					"\t3) Add New Service\n" +
					"\t4) Logout\n");
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
					AdminSystemSetUp admin_sys_setup_landing = new AdminSystemSetUp(input, db);
					admin_sys_setup_landing.display();
					break;
				case 2:
					AdminAddNewStore admin_add_new_store = new AdminAddNewStore(input, db);
					admin_add_new_store.display();
					break;
				case 3:
					AdminAddNewService admin_add_new_service = new AdminAddNewService(input, db);
					admin_add_new_service.display();
					break;
				case 4:
					return;
				default:
					System.out.println("Invalid Selection. Try again.");
					break;
			}
		}

	}


	// get data from csv file
	public static ArrayList<String[]> getData(String input){
		ArrayList<String[]> records = new ArrayList<String[]>();
		try{
			Scanner sc = new Scanner(new File(input));

			while (sc.hasNext())  //returns a boolean value
			{
				String[] record = sc.nextLine().split(";");
				records.add(record);
			}

		}
		catch (Exception e){
			// e.printStackTrace();
			System.out.println("Error on reading file, try again!");
		}
		return records;
	}

}
