//package project1;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.sql.*;

/*
Checks username and password and directs to the right page according to the person's
identiy ex) Manager, Mechanic, Customer
*/
public class Login {
	private String userID;
	private String password;
	public Scanner input;
	public static Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement pstmt = null;
	static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
	private int selection;
	public Database db;

	public Login(Scanner input, Database db) {
		this.input = input;
		this.db = db;
	}

	public void display() {

		try {
			// Get a connection from the first driver in the
			// DriverManager list that recognizes the URL jdbcURL
			// File f = new File("../Credentials.txt");
			// Scanner file_scanner = new Scanner(f);
			// String db_user = file_scanner.next();
			// String db_pwd = file_scanner.next();
			// file_scanner.close();

			// conn = DriverManager.getConnection(jdbcURL, db_user, db_pwd);

			// Create a statement object that will be sending your
			// SQL statements to the DBMS
			// stmt = conn.createStatement();

			while (true) {
				System.out.println("\n------ Sign-in ------\n");
				System.out.print("Please input the following details in the order shown" +
														" below\n\nUser ID: ");
				userID = input.nextLine();
				System.out.print("Password: ");
				password = input.nextLine();

				System.out.println("\nSelect an option:\n" +
														"\t1) Sign-in\n" +
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
						String sql_prep_statement = "SELECT role FROM DB_Users " +
																		"WHERE username=? AND password=?";
						this.db.conn = DriverManager.getConnection(db.jdbcURL, db.username, db.password);
						pstmt = this.db.conn.prepareStatement(sql_prep_statement);
						pstmt.clearParameters();
						pstmt.setString(1,userID);
						pstmt.setString(2,password);
						rs = pstmt.executeQuery();
						try {
							rs.next();
							String role = rs.getString("role");
							this.db.closeConnections();
							close(rs);
							close(pstmt);
							login_user(userID, role);
							// System.out.println(rs.getString("role"));
						} catch (SQLException e) {
							System.out.println("Login incorrect. Try again.");
						}
						break;
					case 2:
						return;
					default:
						System.out.println("Invalid selection. Retry.");
						break;
				}
			}
		} catch(Throwable oops) {
			oops.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			// close(conn);
		}

	}

	private void login_user(String username, String role) {
		switch (role) {
			case "Admin":
				AdminLanding admin_landing_page = new AdminLanding(input, db, username);
				admin_landing_page.display();
				break;
			case "Manager":
				ManagerLandingPage manager_landing_page = new ManagerLandingPage(input, db, username);
				manager_landing_page.display();
				break;
			case "Receptionist":
				ReceptionistLandingPage receptionist_landing_page = new ReceptionistLandingPage(input, db, username);
				receptionist_landing_page.display();
				break;
			case "Mechanic":
				MechanicLanding mechanic_landing_page = new MechanicLanding(input, db, username);
				mechanic_landing_page.display();
				break;
			case "Customer":
				CustomerLanding customer_landing_page = new CustomerLanding(input, db, username);
				customer_landing_page.display();
				break;
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
