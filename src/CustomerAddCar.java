import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;


public class CustomerAddCar {
	public Scanner input;
	public Database db;
    private int cid;
    private String sid;
    private String selection;

	public CustomerAddCar(Scanner input, Database db, int cid, String sid) {
		this.input = input;
		this.db = db;
        this.cid = cid;
        this.sid = sid;
	}

	public void display() {
        while(true){
            System.out.println();
            System.out.println("------ Adding your cars ------");

            System.out.println("VIN number: ");
            String vin = input.nextLine();
            if (vin.length() != 8){
                System.out.println("The length VIN number should be 8!");
                continue;
            }

            System.out.println("Car manufacturer name: ");
            String car_manuf = input.nextLine();
            String[] car_list = {"honda","nissan","toyota","lexus","infiniti"};
            if (!Arrays.asList(car_list).contains(car_manuf.toLowerCase())){
                System.out.println("Invalid Car Manufacturer!");
                continue;
            }

            System.out.println("Current mileage: ");
            String mileage = input.nextLine();

            System.out.println("Year: ");
            String year = input.nextLine();

            String last_mant_sch = "C";

            System.out.println("\t)1) Save information");
            System.out.println("\t)2) Cancel ");
            selection = input.nextLine();
            switch(selection){
                case "1":
                    if (vin.trim().isEmpty() || car_manuf.trim().isEmpty() || mileage.trim().isEmpty() || year.trim().isEmpty()){
                        System.out.println("Failed. At least one of your inputs is empty.");
                    }
                    try{
                        int rows_affected = this.db.addCar(cid, sid, vin, car_manuf, mileage, year, last_mant_sch);
                        if (rows_affected == 2 || rows_affected == 1) {
							System.out.println("Add Car successful.\n");
                            
						} else {
							System.out.println("Add Car failed.\n");
						}
                    }
                    catch (Exception e) {
						e.printStackTrace();
						System.out.println("Add Car failed.\n");
					}
                    return;
                case "2":
                    return;
            }
        }
    }
}