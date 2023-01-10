import java.sql.*;
import java.io.File;
import java.util.*;

public class query {
	static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
	public static ResultSet rs = null;

    public static void main(String[] args) {
        String username = "";
        String password = "";
        Scanner input = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        int selection = 5;
		try {
			File f = new File("../Credentials.txt");
			Scanner buffer = new Scanner(f);
			username = buffer.nextLine();
			password = buffer.nextLine();
			buffer.close();
		} catch(Throwable oops) {
				oops.printStackTrace();
		}
        try {
			conn = DriverManager.getConnection(jdbcURL, username, password);
            stmt = conn.createStatement();
		} catch(Throwable oops) {
			oops.printStackTrace();
		}

        try {
			while(true) {
				System.out.println("\n------ Menu ------");
				System.out.println(
					"Select an option: \n" +
					"  1) Query 1\n" +
					"  2) Query 2\n" +
					"  3) Query 3\n" +
					"  4) Query 4\n" +
					"  5) Query 5\n" +
					"  6) Query 6\n" +
					"  7) Custom Query\n" +
					"  8) Exit\n"
				);

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
                        rs = stmt.executeQuery("SELECT scid FROM Customers C GROUP BY C.scid HAVING COUNT(*) >= ALL (SELECT Count(*) FROM Customers GROUP BY scid)");
                        while(rs.next()) {
                            System.out.println(rs.getString(1));
                        }
						break;
					case 2:
                        rs = stmt.executeQuery("SELECT AVG(price) FROM Offer WHERE service_name = 'Evaporator Repair' AND car_manuf = 'Honda'");
                        while(rs.next()) {
                            System.out.println(Float.parseFloat(rs.getString(1)));
                        }
                        break;
					case 3:
                        rs = stmt.executeQuery("SELECT C.first_name, C.last_name FROM Customers C, Appointments A WHERE A.bill_paid = 'N' AND A.scid = '30003' AND C.scid = '30003' AND A.vin = ANY (SELECT O.vin FROM Owns O WHERE O.cid = C.cid AND O.scid = '30003')");
                        while(rs.next()) {
                            System.out.println(rs.getString(1) + " " + rs.getString(2));
                        }
                        break;
					case 4:
                        rs = stmt.executeQuery("SELECT DISTINCT service_name FROM Services WHERE subtype = 'Schedule' AND repair_service = 'Y'");
                        while(rs.next()) {
                            System.out.println(rs.getString(1));
                        }
                        break;
					case 5:
                        rs = stmt.executeQuery("SELECT (O1.price + S1.price) - (O2.price + S2.price) FROM Offer O1, Offer O2, Schedule_Prices S1, Schedule_Prices S2 WHERE O1.scid = '30001' AND O1.service_name = 'Belt Replacement' AND O1.car_manuf = 'Toyota' AND S1.scid = '30001' AND S1.car_manuf = 'Toyota' AND S1.schedule = 'A' AND O2.scid = '30002' AND O2.service_name = 'Belt Replacement' AND O2.car_manuf = 'Toyota' AND S2.scid = '30002' AND S2.car_manuf = 'Toyota' AND S2.schedule = 'A'");
                        while(rs.next()) {
                            System.out.println(rs.getString(1));
                        }
                        break;
					case 6:
                        rs = stmt.executeQuery("SELECT CASE WHEN last_mant_sch = 'A' THEN 'B' WHEN last_mant_sch = 'B' THEN 'C' WHEN last_mant_sch = 'C' THEN 'A' END FROM Cars WHERE vin = '34KLE19D'");
                        while(rs.next()) {
                            System.out.println(rs.getString(1));
                        }
                        break;
                    case 7:
                        System.out.println("Input your Query");
                        String query = input.nextLine();
                        rs = stmt.executeQuery(query);
			ResultSetMetaData r = rs.getMetaData();
			int columnsNumber = r.getColumnCount();
                        while(rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					System.out.print(rs.getString(i).trim());
					System.out.print("  ");
				}
				System.out.println();
                        }
                        break;
                    case 8:
                        return;
					default:
						System.out.println("Invalid selection. Retry.");
						break;
				}

			}
		} catch(Throwable oops) {
			oops.printStackTrace();
		} finally {
			input.close();
		}
	}
}
