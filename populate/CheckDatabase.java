import java.sql.*;
import java.util.Scanner;
import java.io.File;

public class CheckDatabase {
	static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";

	public static void main(String[] args) {
		try {
			// Load the driver. This creates an instance of the driver
			// and calls the registerDriver method to make Oracle Thin
			// driver available to clients.
			//  Class.forName("oracle.jdbc.driver.OracleDriver"); older
			Class.forName("oracle.jdbc.OracleDriver");

			File database_credentials = new File("../Credentials.txt");
			Scanner cred_scanner = new Scanner(database_credentials);
			String user = "";
			String password = "";
			try {
				user = cred_scanner.nextLine();
				password = cred_scanner.nextLine();
			} catch (Exception e) {
				e.printStackTrace(System.out);
				System.exit(1);
			}
			cred_scanner.close();

			Connection conn = null;
			Statement stmt = null;
			Statement stmt2 = null;
			ResultSet rs = null;
			Scanner user_input_scanner = new Scanner(System.in);

			try {
				System.out.println();
				while (true) {
					System.out.print("What table do you want to view?\t");
					String answer = user_input_scanner.next();
					if (answer.startsWith("Q") || answer.startsWith("q")) {
						System.out.println("Aborting procedure...");
						System.exit(1);
					}
					conn = DriverManager.getConnection(jdbcURL, user, password);
					stmt = conn.createStatement();

					if (answer.equals("errors")) {
						rs = stmt.executeQuery("SELECT * FROM USER_ERRORS WHERE TYPE='TRIGGER'");
					} else {
						rs = stmt.executeQuery("SELECT * FROM " + answer);
					}

					ResultSetMetaData rsmd = rs.getMetaData();
					int columnsNumber = rsmd.getColumnCount();

					System.out.println();
					while (rs.next()) {
						System.out.print("\t");
						for (int i = 1; i <= columnsNumber; i++) {
								if (i > 1) System.out.print(", ");
								String columnValue = rs.getString(i).strip();
								System.out.print(columnValue + " " + rsmd.getColumnName(i));
						}
						System.out.println();
					}
					System.out.println();
				}
			} catch (SQLException e){
				e.printStackTrace();
			} finally {
				close(rs);
				close(stmt);
				close(conn);
				user_input_scanner.close();
			}
		} catch(Throwable oops) {
			oops.printStackTrace();
		}
	}

	static void close(Connection conn) {
		if(conn != null) {
			try {conn.close();} catch(Throwable whatever) {}
		}
	}

	static void close(Statement st) {
		if(st != null) {
			try { st.close(); } catch(Throwable whatever) {}
		}
	}

	static void close(ResultSet rs) {
		if(rs != null) {
			try { rs.close(); } catch(Throwable whatever) {}
		}
	}
}
