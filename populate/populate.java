import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class populate {

    public static void main(String[] args) {
        String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
        Statement stmt = null;
        try {
			File f = new File("../Credentials.txt");
			Scanner buffer = new Scanner(f);
			String username = buffer.nextLine();
			String password = buffer.nextLine();
			buffer.close();
            Connection conn = DriverManager.getConnection(jdbcURL, username, password);
			stmt = conn.createStatement();
		} catch(Throwable oops) {
				oops.printStackTrace();
		}


		//Service_Centers
        try{
			Scanner sc = new Scanner(new File("serviceCenter.csv"));
            sc.nextLine();
			while (sc.hasNext())  //returns a boolean value
			{
				String[] record = sc.nextLine().split(";");
                stmt.executeUpdate("INSERT INTO Service_Centers VALUES('" + record[0] + "', '" + record[1] + "', '" +
                                    record[2] + "', '" + Float.parseFloat(record[5]) + "', '" + Float.parseFloat(record[6]) +
                                    "', '" + record[3] + "')");

			}
            System.out.println("Inserted Service Centers");

		}
		catch (Exception e){
			// e.printStackTrace();
			System.out.println("Error when reading Service Centers, try again!");
		}


        //Employees
        try{
            //Managers
			Scanner sc = new Scanner(new File("managers.csv"));
            sc.nextLine();
			while (sc.hasNext())  //returns a boolean value
			{
				String[] record = sc.nextLine().split(";");
                stmt.executeUpdate("INSERT INTO DB_Users VALUES('" + record[0]+record[1] + "', '" + record[3] + "', 'Manager')");
                stmt.executeUpdate("INSERT INTO Employees VALUES('" + record[1] + "', '" + record[0] + "', '" +
                                    record[2] + "', '" + record[3] + "', '" + record[5] +  "', '" + record[6] +
                                    "', '" + record[4] + "', 'Manager', '" + record[0]+record[1] + "')");

                stmt.executeUpdate("INSERT INTO Contract_Emp VALUES('" + record[1] + "', '" + record[0] + "', '" +
                                    Float.parseFloat(record[7]) + "')");

			}
            System.out.println("Inserted Managers");

            //Receptionists
            sc = new Scanner(new File("receptionist.csv"));
            sc.nextLine();
			while (sc.hasNext())  //returns a boolean value
			{
				String[] record = sc.nextLine().split(";");
                stmt.executeUpdate("INSERT INTO DB_Users VALUES('" + record[0]+record[1] + "', '" + record[3] + "', 'Receptionist')");
                stmt.executeUpdate("INSERT INTO Employees VALUES('" + record[1] + "', '" + record[0] + "', '" +
                                    record[2] + "', '" + record[3] + "', '" + record[5] +  "', '" + record[6] +
                                    "', '" + record[4] + "', 'Receptionist', '" + record[0]+record[1] + "')");

                stmt.executeUpdate("INSERT INTO Contract_Emp VALUES('" + record[1] + "', '" + record[0] + "', '" +
                                    Float.parseFloat(record[7]) + "')");

			}
            System.out.println("Inserted Receptionists");

            //Mechanics
            sc = new Scanner(new File("mechanics.csv"));
            sc.nextLine();
			while (sc.hasNext())  //returns a boolean value
			{
				String[] record = sc.nextLine().split(";");
                stmt.executeUpdate("INSERT INTO DB_Users VALUES('" + record[8]+record[0] + "', '" + record[2] + "', 'Mechanic')");
                stmt.executeUpdate("INSERT INTO Employees VALUES('" + record[0] + "', '" + record[8] + "', '" +
                                    record[1] + "', '" + record[2] + "', '" + record[4] +  "', '" + record[5] +
                                    "', '" + record[3] + "', 'Mechanic', '" + record[8]+record[0] + "')");

                stmt.executeUpdate("INSERT INTO Hourly_Emp VALUES('" + record[0] + "', '" + record[8] + "', '0', '" +
                                    Float.parseFloat(record[6]) + "')");
                for(int i = 1; i <= 30; i++) {
                    for(int j = 1; j <= 11; j++) {
                        if(i % 7 == 1) {
                            break;
                        }
                        if ((i % 7 == 0 && (j < 2 || j > 4))) {
                            continue;
                        }
                        int day = i % 7;
                        if(day == 0) {
                            day = 7;
                        }
                        stmt.executeUpdate("INSERT INTO Day_Schedules VALUES('" + record[0] + "', '" + record[8] + "', '202409" + String.format("%02d", i) + "', '" + day + "', " + j + ", ' ')");
                    }
                }

			}
            System.out.println("Inserted Mechanics");

		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println("Error when reading Employees, try again!");
		}

        //Customers/Cars
        try{
			Scanner sc = new Scanner(new File("customers.csv"));
            sc.nextLine();
			while (sc.hasNext())  //returns a boolean value
			{
				String[] record = sc.nextLine().split(";");
                stmt.executeUpdate("INSERT INTO DB_Users VALUES('" + record[0] + record[3] + "', '" + record[2] + "', 'Customer')");
                stmt.executeUpdate("INSERT INTO Customers VALUES('" + record[3] + "', '" + record[0] + "', '" +
                                    record[1] + "', '" + record[2] + "', 'Address', 'Email', 'phone', '0', 'Y', 1, '" + record[0] + record[3] + "')");
                stmt.executeUpdate("INSERT INTO Cars VALUES('" + record[4] + "', '" + record[5] + "', '" +
                                    record[6] + "', '" + record[8] + "', '" + record[7] + "')");
                stmt.executeUpdate("INSERT INTO Owns VALUES('" + record[4] + "', '" + record[3] + "', '" +
                                    record[0] + "')");
			}
            System.out.println("Inserted Customers and Cars");

		}
		catch (Exception e){
			// e.printStackTrace();
			System.out.println("Error when reading Customers, try again!");
		}

        //Services/Offers
        try{
			Scanner sc = new Scanner(new File("repairServices.csv"));
			Scanner sp = new Scanner(new File("prices.csv"));
            sc.nextLine();
            sp.nextLine();
            ArrayList<String[]> prices = new ArrayList<>();
            while( sp.hasNext() ) {
                prices.add(sp.nextLine().split(";"));
            }
			while (sc.hasNext())  //returns a boolean value
			{
				String[] record = sc.nextLine().split(";");
                for(int i = 0; i < prices.size(); i++) {
                    if( prices.get(i)[2].equals(record[3])) {
                        if(!stmt.executeQuery("SELECT service_name FROM Services WHERE service_name = '" + record[1] + "' AND car_manuf = '" + prices.get(i)[1] + "'").next()) {
                            stmt.executeUpdate("INSERT INTO Services VALUES('" + record[2] + "', '" + prices.get(i)[1] +
                                                "', '" + record[4] + "', '" + record[1] + "', '" +
                                                record[0] + "', 'N', 'Y')");
                        }
                        stmt.executeUpdate("INSERT INTO Offer VALUES('" + prices.get(i)[0] + "', '" + record[1] +
                                            "', '" + prices.get(i)[1] + "', '"  + prices.get(i)[3] + "')");
                    }
                }
			}
            System.out.println("Inserted Repair Services");

            sc = new Scanner(new File("maintenanceServices.csv"));
            sc.nextLine();
			while (sc.hasNext())  //returns a boolean value
			{
				String[] record = sc.nextLine().split(";");
                for(int i = 0; i < prices.size(); i++) {
                    if( prices.get(i)[2].equals(record[3])) {
                        if(!stmt.executeQuery("SELECT service_name FROM Services WHERE service_name = '" + record[1] + " From Schedule: " + record[0] + "' AND car_manuf = '" + prices.get(i)[1] + "'").next()) {
                            stmt.executeUpdate("INSERT INTO Services VALUES('" + record[5] + "', '" + prices.get(i)[1] +
                                                "', '" + record[4] + "', '" + record[1] + " From Schedule: " + record[0] + "', 'Schedule', '" +
                                                record[0] + "', '" + (record[2].equals("m r") ? "Y" : "N") + "')");
                        }
                        stmt.executeUpdate("INSERT INTO Offer VALUES('" + prices.get(i)[0] + "', '" + record[1] + " From Schedule: " + record[0] +
                                            "', '" + prices.get(i)[1] + "', '" + prices.get(i)[3] + "')");
                        
                        
                        if(!stmt.executeQuery("SELECT service_id FROM Schedule_Prices WHERE scid = '" + prices.get(i)[0] + "' AND schedule = '" + record[0] + "' AND car_manuf = '" + prices.get(i)[1] + "'").next()) {
                            if(!stmt.executeQuery("SELECT service_name FROM Services WHERE service_name = '" + record[0] + "' AND car_manuf = '" + prices.get(i)[1] + "'").next()) {
                                stmt.executeUpdate("INSERT INTO Services VALUES('" + record[5] + "', '" + prices.get(i)[1] +
                                                "', '" + record[4] + "', '" + record[0] + "', 'Schedule', '" +
                                                record[0] + "', 'N')");
                            }

                            stmt.executeUpdate("INSERT INTO Offer VALUES('" + prices.get(i)[0] + "', '" + record[0] +
                                            "', '" + prices.get(i)[1] + "', '" + prices.get(i)[3] + "')");

                            stmt.executeUpdate("INSERT INTO Schedule_Prices VALUES('" + prices.get(i)[0] + "', '" + record[0] +
                                                "', '" + prices.get(i)[3] + "', '" + record[5] + "', '" + record[4] + "', '" + prices.get(i)[1] + "')");
                        }
                    }
                }
			}
            System.out.println("Inserted Maintenance Schedules");

		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println("Error when reading Services, try again!");
		}

        //Appointments/Schedule
        try{
			Scanner sc = new Scanner(new File("schedule.csv"));
            sc.nextLine();
			while (sc.hasNext())  //returns a boolean value
			{
                try {
                    String[] record = sc.nextLine().split(";");
                    ResultSet rs = stmt.executeQuery("SELECT apt_id FROM Appointments");
                    int ap_id = 0;
                    while(rs.next()) {
                        if(ap_id < rs.getInt("apt_id")) {
                            ap_id = rs.getInt("apt_id");
                        }
                    }
                    ap_id++;
                    if(record[6].equals("A") || record[6].equals("B") || record[6].equals("C") ) {
                        rs = stmt.executeQuery("SELECT price FROM Schedule_Prices WHERE scid = '" + record[2] + "' AND schedule = '" + record[6] + "' AND car_manuf = '" + record[4] + "'");
                        // Uncomment if Appointments dictate the last schedule
                        // stmt.executeUpdate("UPDATE Cars SET last_mant_sch = " + record[6] + " WHERE vin = '" + record[3] + "'");
                    }
                    else {
                        rs = stmt.executeQuery("SELECT price FROM Offer WHERE scid = '" + record[2] + "' AND service_name = '" + record[6] + "' AND car_manuf = '" + record[4] + "'");
                    }
                    rs.next();
                    float price = rs.getFloat("price");
                    rs = stmt.executeQuery("SELECT apt_id, total_amount FROM Appointments WHERE scid = '" + record[2] + "' AND vin = '" + record[3] + "' AND eid = '" + record[11]  + "'");
                    if(rs.next()) {
                        int amount = rs.getInt("total_amount");
                        ap_id = Integer.parseInt(rs.getString("apt_id"));
                        stmt.executeUpdate("UPDATE Appointments SET total_amount = " + (amount + price) + " WHERE apt_id = '" + ap_id + "'");
                    }
                    else {
                        String paid = "N";
                        if (record[12].equals("Paid")) {
                            paid = "Y";
                        }
                        stmt.executeUpdate("INSERT INTO Appointments VALUES('" + ap_id + "', '" + record[2] + "', '" + 
                                            record[11] + "', '" + record[3] + "', '202409" + ((Integer.parseInt(record[7]) - 1) * 7 + Integer.parseInt(record[8])) + 
                                            "', " + Integer.parseInt(record[8]) + ", " + record[9] + ", " + (Integer.parseInt(record[10]) - Integer.parseInt(record[9])) + 
                                            ", 'Y', '" + paid + "', '" + (int)price + "')");
                    }

                    stmt.executeUpdate("INSERT INTO Schedule VALUES('" + Integer.toString(ap_id) + "', '" + record[6] + "', '" + record[4] + "', '" + record[2] + "')");
                    
                    rs = stmt.executeQuery("SELECT hours_worked FROM Hourly_Emp WHERE eid = '" + record[11] + "' AND scid = '" + record[2] + "'");
                    rs.next();
                    int hours_worked = rs.getInt("hours_worked") + Integer.parseInt(record[10]) - Integer.parseInt(record[9]);
                    stmt.executeUpdate("UPDATE Hourly_Emp SET hours_worked = '" + hours_worked + "' WHERE eid = '" + record[11] + "' AND scid = '" + record[2] + "'");

                    for(int i = Integer.parseInt(record[9]); i < Integer.parseInt(record[10]); i++) {
                        stmt.executeUpdate("UPDATE Day_Schedules SET slot = 'S' WHERE eid = '" + record[11] + "' AND slot_time = '" + String.format("%02d", i) + "' AND sch_date = '202409" + String.format("%02d", (((Integer.parseInt(record[7])-1)*7) + Integer.parseInt(record[8]))) + "' AND dotw = '" + record[8] + "'");
                    }
                } catch (Exception e) {
                    // e.printStackTrace();
                    System.out.println("Error scheduling appoinment");
                }

			}
            System.out.println("Inserted Scheduled Services");

		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println("Error when reading schedule, try again!");
		}

	}
}