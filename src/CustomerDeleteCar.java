import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;


public class CustomerDeleteCar {
	public Scanner input;
	public Database db;
    private int cid;
    private String sid;
    private String selection;

	public CustomerDeleteCar(Scanner input, Database db, int cid, String sid) {
		this.input = input;
		this.db = db;
        this.cid = cid;
        this.sid = sid;
	}

	public void display() {
        while (true){
            System.out.println("------ Deleting your cars ------");
            System.out.println("\t1) Select the car to delete");
            System.out.println("\t2) Go Back");
            selection = input.nextLine();
            switch (selection){
                case "2":
                    return;
                case "1":
                    db.print_all_cars(cid, sid); // print all cars

                    System.out.println("Insert the VIN number of the car to delete:");
                    String delete_vin = input.nextLine();
                    try{
                        int rows_affected = this.db.deleteCar(delete_vin);
                        if (rows_affected == 1) {
                            System.out.println("Delete Car successful.\n");
                        } else {
                            System.out.println("Delete Car failed.\n");
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Delete Car failed.\n");
                    }
                    return; 
            }
        }
    }
}