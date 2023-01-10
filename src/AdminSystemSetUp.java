import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;


public class AdminSystemSetUp {
	public Scanner input;
	private int selection;
	private String service_file_name;
	private String store_file_name;
	private File service_info;
	private File store_info;
	public Database db;

	public AdminSystemSetUp(Scanner input, Database db) {
		this.input = input;
		this.db = db;
	}

	public void display() {

		while(true) {
			System.out.println("------ Admin: System Set Up ------");

			System.out.print("Please input the following details in the order shown" +
													" below\n\nService General Information File Name: ");
			service_file_name = input.nextLine();
			System.out.print("Store General Information File Name: ");
			store_file_name = input.nextLine();

			System.out.println("\nSelect an option:\n" +
													"\t1) Upload service general information\n" +
													"\t2) Upload store general information\n" +
													"\t3) Go Back\n");
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
				  // upload information using try/catch and print "success" or "fail"
				  //stay on same page
				  try{

				    CallableStatement stmt = null;
				    ArrayList<String[]> records = AdminLanding.getData(service_file_name);
				    String SQL = "{call Services (?, ?, ?, ?, ?, ?, ?)}"; // admin_add_store would be the table name in DDL

				    for(String[] record : records){
				      stmt = Database.conn.prepareCall (SQL);
				      stmt.setString(1, record[0]); // service_id
				      stmt.setString(2, record[1]); // car_manuf
				      stmt.setInt(3, Integer.parseInt(record[2])); // duration
				      stmt.setString(4, record[3]); // service_name
				      stmt.setString(5, record[4]); // subtype
				      stmt.setString(6, record[5]); // schedule
				      stmt.setString(7, record[6]); // repair_service

				      stmt.registerOutParameter(7, java.sql.Types.INTEGER);
				      stmt.executeUpdate();
				      int result = stmt.getInt(7);
				      if (result == 0){
					System.out.println("Failed due to invalid input.");
					continue;
				      }
				      else{
					System.out.println("Upload successfully.");
				      }
				    }
				  }
				  catch (SQLException e){
				    System.out.println("Failed. Error on reading file. Please make sure the format.");
				    continue;
				  }
				  break;
				case 2:
				  // upload information using try/catch and print "success" or "fail"
				  // stay on same page
				  try{

				    CallableStatement stmt = null;
				    ArrayList<String[]> records = AdminLanding.getData(store_file_name);
				    String SQL = "{call Service_Centers (?, ?, ?, ?, ?, ?)}"; // admin_add_store would be the table name in DDL


				    for(String[] record : records){
				      stmt = Database.conn.prepareCall (SQL);
				      stmt.setString(1, record[0]); // scid
				      stmt.setString(2, record[1]); // address
				      stmt.setInt(3, Integer.parseInt(record[2])); // telephone
				      stmt.setString(4, record[3]); // min_wage
				      stmt.setString(5, record[4]); // max_wage
				      stmt.setString(6, record[5]); // saturdays

				      stmt.registerOutParameter(7, java.sql.Types.INTEGER);
				      stmt.executeUpdate();
				      int result = stmt.getInt(7);
				      if (result == 0){
					System.out.println("Failed due to invalid input.");
					continue;
				      }
				      else{
					System.out.println("Upload successfully.");
				      }
				    }
				  }
				  catch (SQLException e){
				    System.out.println("Failed. Error on reading file. Please make sure the format.");
				    continue;
				  }
				  break;
				case 3:
				  return;
				default:
				  System.out.println("Invalid Selection. Try again.");
				  break;
			 }
		 }

  	}

}
