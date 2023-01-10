import java.sql.*;
import java.util.Scanner;
import java.io.File;





public class InitializeDatabase {

	static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
	static Connection conn = null;
	static Statement stmt = null;
	static Statement stmt2 = null;
	static ResultSet rs = null;

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


			try {
				// Get a connection from the first driver in the
				// DriverManager list that recognizes the URL jdbcURL
				conn = DriverManager.getConnection(jdbcURL, user, password);

				// Create a statement object that will be sending your
				// SQL statements to the DBMS
				stmt = conn.createStatement();
				stmt2 = conn.createStatement();

				Scanner user_input_scanner = new Scanner(System.in);
				System.out.print("Are you sure you want to delete all user tables?\t");
				String answer = user_input_scanner.next();
				if (! answer.startsWith("y") && ! answer.startsWith("Y")) {
					System.out.println("Aborting procedure...");
					user_input_scanner.close();
					System.exit(1);
				}
				user_input_scanner.close();

				//Delete all user tables from the database
				boolean remaining = true;
				while(remaining) {
					remaining = false;
					rs = stmt.executeQuery("SELECT TABLE_NAME FROM USER_TABLES");
					while (rs.next()) {
						try {
							stmt2.executeUpdate("DROP TABLE " + rs.getString("TABLE_NAME"));
							System.out.println("DROPPED TABLE " + rs.getString("TABLE_NAME"));
						} catch (SQLException e) {
							remaining = true;
						}
					}
				}

				//parse sql file and make all tables, triggers, and procedures
				File sql_ddl = new File("../ddl_prod.sql");
				Scanner input = new Scanner(sql_ddl);
				String create_statement = "";
				while (input.hasNextLine()) {
					String line = input.nextLine();
					if (line.startsWith("CREATE")) {
						if (create_statement != "") {
							stmt.executeUpdate(create_statement);
						}
						create_statement = "";
					}
					create_statement += line + " ";
				}
				stmt.executeUpdate(create_statement);

				rs = stmt.executeQuery("SELECT TABLE_NAME FROM USER_TABLES");

				while (rs.next()) {
					String s = rs.getString("TABLE_NAME");
					System.out.println("TABLE " + s + " CREATED");
				}

				rs = stmt.executeQuery("SELECT TABLE_NAME, TRIGGER_NAME " +
																"FROM USER_TRIGGERS");
				while (rs.next()) {
					String s1 = rs.getString("TABLE_NAME");
					String s2 = rs.getString("TRIGGER_NAME");
					System.out.println("TRIGGER " + s2 + " CREATED FOR TABLE " + s1);
				}

				int rows_affected = stmt.executeUpdate("INSERT INTO DB_Users " +
						 "VALUES ('Admin', 'Admin123', 'Admin')");
				if (rows_affected==1) {
					System.out.println("ADMIN CREATED IN TABLE DB_Users");
				}

				// rows_affected = createStoreAndManagerUserAccount("12345678", "Store address", "1234567890",
				// 													(float) 15, (float) 20, "Manager1", "Manager123",
				// 													"Manager", "123456789", "M", "G",
				// 													"Manager address", "manager@email.com", "1234567890", (float) 100000.0);
				// if (rows_affected==4) {
				// 	System.out.println("STORE CREATED IN TABLE Service_Centers");
				// 	System.out.println("MANAGER CREATED IN TABLES DB_Users, Employees, Contract_Emp");
				// }

				// rows_affected = createEmployeeUserAccount("12345678", "Rec1", "Rec123", "Receptionist",
				// 													"000000001", "C", "D", "Receptionist address",
				// 													"receptionist@email.com", "1234567890", 60000, 0);
				// if (rows_affected==3) {
				// 	System.out.println("RECEPTIONIST CREATED IN TABLES DB_Users, Employees, Contract_Emp");
				// }

				// rows_affected = createUserAccount("12345678", 000000001, "first", "last", "address", "email", "123", "100", "Y", 1, "username", "12345678", "Toyota", 10, 2001);
				// if (rows_affected==3) {
				// 	System.out.println("Customer with Vehicle CREATED IN TABLES DB_Users, Customer, Vehicle");
				// }
				// else {
				// 	System.out.println("error: " + rows_affected);
				// }

				// rows_affected = createEmployeeUserAccount("12345678", "Mechanic1", "Mechanic123", "Mechanic",
				// 													"000000002", "A", "B", "Mechanic address",
				// 													"mechanic@email.com", "1234567890", 20, 0);
				// if (rows_affected==3) {
				// 	System.out.println("MECHANIC CREATED IN TABLES DB_Users, Employees, Hourly_Emp");
				// }
				// createUserAccount("12345678", "Customer", "Customer123", "Customer",
				// 													"000000001", "A", "B", "Mechanic address",
				// 													"mechanic@email.com", "1234567890s");
				

			} finally {
				close(rs);
				close(stmt);
				close(stmt2);
				close(conn);
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

  static int createEmployeeUserAccount(String scid, String username, String password,
														String role, String eid, String first_name, String last_name,
														String address, String email, String telephone, float pay,
														int hours_worked) {
    try {
			conn.setAutoCommit(false);
			int rows_affected = 0;
			try {
				//add user login data to DB_Users table
				rows_affected += stmt.executeUpdate("INSERT INTO DB_Users VALUES('" +
						username + "','" + password + "','" + role + "')");
				//Add user data to Employees table
				rows_affected += stmt.executeUpdate("INSERT INTO Employees VALUES('" +
						eid + "','" + scid + "','" + first_name + "','" + last_name + "','" +
						address + "','" + email + "','" + telephone + "','" + role + "','" +
						username + "')");

				if (role.equals("Manager") || role.equals("Receptionist")) {
					rows_affected += stmt.executeUpdate("INSERT INTO Contract_Emp VALUES('" +
							eid + "','" + scid + "','" + pay + "')");
				} else if (role.equals("Mechanic")) {
					rows_affected += stmt.executeUpdate("INSERT INTO Hourly_Emp VALUES('" +
							eid + "','" + scid + "','" + hours_worked + "','" + pay + "')");
				}

				conn.commit();
				conn.setAutoCommit(true);
				return rows_affected;
			} catch (Exception e) {
				conn.rollback();
				conn.setAutoCommit(true);
				e.printStackTrace();
				return -1;
			}
		} catch(Throwable oops) {
			oops.printStackTrace();
			return -1;
		}
	}

	static int createUserAccount(String scid, int cid, String first, String last, 
									String address, String email, String telephone, 
									String balance, String Active, int vehicles, String username, String vin, String manuf, int mileage, int year) {
		try {
			conn.setAutoCommit(false);
			int rows_affected = 0;
			try {
				//add data to Service Centers table
				rows_affected += stmt.executeUpdate("INSERT INTO DB_Users VALUES( '" + username + "', '" + last + "', 'Customer')");
				//add user login data to DB_Users table
				rows_affected += stmt.executeUpdate("INSERT INTO Customers VALUES('" +
				scid + "','" + cid + "','" + first + "','" + last + "','" +
				address + "','" + email + "','" + telephone + "','" + 0 + "','" + 1 + "','" +
				1 + "','" + username + "')");
				//Add user data to Employees table
				rows_affected += stmt.executeUpdate("INSERT INTO Cars VALUES('" +
				vin + "','" + manuf + "','" + mileage + "','" + year + "','C')");
				conn.commit();
				conn.setAutoCommit(true);
				return rows_affected;
			} catch (Exception e) {
				conn.rollback();
				conn.setAutoCommit(true);
				e.printStackTrace();
				return -1;
			}
		} catch(Throwable oops) {
			oops.printStackTrace();
			return -1;
		}						

	}

	static int createStoreAndManagerUserAccount(String scid, String store_address, String store_telephone,
														float min_wage, float max_wage, String username, String password,
														String role, String eid, String first_name, String last_name,
														String manager_address, String email, String manager_telephone,
														float manager_salary) {

		try {
			conn.setAutoCommit(false);
			int rows_affected = 0;
			try {
				//add data to Service Centers table
				rows_affected += stmt.executeUpdate("INSERT INTO Service_Centers VALUES('" +
						scid + "','" + store_address + "','" + store_telephone + "','" + min_wage + "','" +
						max_wage + "'," + "'N')");
				//add user login data to DB_Users table
				rows_affected += stmt.executeUpdate("INSERT INTO DB_Users VALUES('" +
						username + "','" + password + "','" + role + "')");
				//Add user data to Employees table
				rows_affected += stmt.executeUpdate("INSERT INTO Employees VALUES('" +
						eid + "','" + scid + "','" + first_name + "','" + last_name + "','" +
						manager_address + "','" + email + "','" + manager_telephone + "','" +
						role + "','" + username + "')");
				rows_affected += stmt.executeUpdate("INSERT INTO Contract_Emp VALUES('" +
						eid + "','" + scid + "','" + manager_salary + "')");
				conn.commit();
				conn.setAutoCommit(true);
				return rows_affected;
			} catch (Exception e) {
				conn.rollback();
				conn.setAutoCommit(true);
				e.printStackTrace();
				return -1;
			}
		} catch(Throwable oops) {
			oops.printStackTrace();
			return -1;
		}
	}



}
