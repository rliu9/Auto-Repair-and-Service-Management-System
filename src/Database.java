import java.sql.*;
import java.io.File;
import java.util.*;

// import project1.InvoiceCustomer;

public class Database {
	static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
	public static Connection conn = null;
	public static Statement stmt = null;
	public static Statement stmt2 = null;
	public static ResultSet rs = null;
	public static ResultSet rs2 = null;
	public String username = "";
	public String password = "";
	int appointment_id = 0;

	public Database() {
		try {
			File f = new File("../Credentials.txt");
			Scanner buffer = new Scanner(f);
			username = buffer.nextLine();
			password = buffer.nextLine();
			buffer.close();
		} catch(Throwable oops) {
				//oops.printStackTrace();
		}
	}

	public int update(String statement){
		try {
			this.conn = DriverManager.getConnection(jdbcURL, username, password);
			this.stmt = conn.createStatement();
			int rows_affected = stmt.executeUpdate(statement);
			return rows_affected;
		} catch(Throwable oops) {
			//oops.printStackTrace();
		} finally {
			this.closeConnections();
		}
		return -1;
	}

	// public ResultSet query(String query){
	// 	try {
	// 		this.conn = DriverManager.getConnection(jdbcURL, username, password);
	// 		this.stmt = conn.createStatement();
	// 		ResultSet rs = stmt.executeQuery(query);
	// 		return rs;
	// 	} catch(Throwable oops) {
	// 		//oops.printStackTrace();
	// 	} finally {
	// 		this.closeConnections();
	// 	}
	// 	return null;
	// }


	public int createStoreAndManagerUserAccount(String scid, String store_address, String store_telephone,
														float min_wage, float max_wage, String username, String password,
														String role, String eid, String first_name, String last_name,
														String manager_address, String email, String manager_telephone,
														float manager_salary) {
    try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.conn.setAutoCommit(false);
			int rows_affected = 0;
			try {
				this.stmt = this.conn.createStatement();

				//add data to Service Centers table
				rows_affected += stmt.executeUpdate("INSERT INTO Service_Centers VALUES('" +
						scid + "','" + store_address + "','" + store_telephone + "','" + min_wage + "','" +
						max_wage + "'," + "'N')");
				//add user login data to DB_Users table
				rows_affected += this.stmt.executeUpdate("INSERT INTO DB_Users VALUES('" +
						username + "','" + password + "','" + role + "')");
				//Add user data to Employees table
				rows_affected += this.stmt.executeUpdate("INSERT INTO Employees VALUES('" +
						eid + "','" + scid + "','" + first_name + "','" + last_name + "','" +
						manager_address + "','" + email + "','" + manager_telephone + "','" +
						role + "','" + username + "')");
				rows_affected += this.stmt.executeUpdate("INSERT INTO Contract_Emp VALUES('" +
						eid + "','" + scid + "','" + manager_salary + "')");
				this.conn.commit();
				return rows_affected;
			} catch (SQLException e) {
				this.conn.rollback();
				//e.printStackTrace();
				return -1;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return -1;
		} finally {
			this.closeConnections();
		}
	}

	public String get_scid_from_employee_username(String username) {

		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				this.stmt = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT scid FROM Employees WHERE username = '"+ username + "'");
				this.rs.next();
				String scid = this.rs.getString(1);
				return scid.trim();
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}
	public String get_eid_from_employee_username(String username) {

		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				this.stmt = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT eid FROM Employees WHERE username = '"+ username + "'");
				this.rs.next();
				String eid = this.rs.getString(1);
				return eid;
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}

	public int get_cid_from_customer_username(String username) {

		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				this.stmt = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT cid FROM Customers WHERE username = '"+ username + "'");
				this.rs.next();
				int cid = this.rs.getInt(1);
				return cid;
			} catch (SQLException e) {
				//e.printStackTrace();
				return -1;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return -1;
		} finally {
			this.closeConnections();
		}
	}

	public String get_scid_from_customer_username(String username) {

		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				this.stmt = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT scid FROM Customers WHERE username = '"+ username + "'");
				this.rs.next();
				String scid = this.rs.getString(1);
				return scid.trim();
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}

	/*
	public void getService(String vin) {

		try{
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.stmt = this.conn.createStatement();
			ArrayList<String> mechanic_Name_list = new ArrayList<String>();
			ArrayList<String> apt_id_list = new ArrayList<String>();
			ArrayList<String> car_manuf_list = new ArrayList<String>();
			ArrayList<String> service_name_list = new ArrayList<String>();
			ArrayList<String> scid_list = new ArrayList<String>();
			ArrayList<String> eid_list = new ArrayList<String>();
			ArrayList<Integer> start_list = new ArrayList<Integer>();
			ArrayList<Integer> duration_list = new ArrayList<Integer>();
			ArrayList<Integer> total_amount_list = new ArrayList<Integer>();

			System.out.println("calling getService try");
			//service_name, car_manuf, scid
			rs = this.stmt.executeQuery("SELECT * FROM Appointments WHERE vin = '" + vin + "'");

			System.out.println("rs.next outside");
			while(rs.next()){ // Appointment Table
				//Display values
				apt_id_list.add(rs.getString("apt_id"));
				System.out.println(rs.getString("apt_id"));

				scid_list.add(rs.getString("scid"));
				eid_list.add(rs.getString("eid"));
				start_list.add(rs.getInt("start_time"));
				duration_list.add(rs.getInt("duration"));
				total_amount_list.add(rs.getInt("total_amount"));

			}
			/*
			for (int i = 0; i < eid_list.size(); i++){
				rs = this.stmt.executeQuery("SELECT * FROM Employees WHERE eid = '" + eid_list.get(i) + "'");
				while (rs.next()){ //Employee Table
					mechanic_Name_list.add(rs.getString("first_name") + " " + rs.getString("last_name"));
				}
				String sql = "SELECT * FROM Schedule WHERE apt_id = '" + apt_id_list.get(i) + "'";
				rs = this.stmt.executeQuery(sql);

				ArrayList<String> car_manuf_list = new ArrayList<String>();
				ArrayList<String> service_name_list = new ArrayList<String>();

				while(rs.next()){ // Schedule Table
					car_manuf_list.add(rs.getString("car_manuf"));
					service_name_list.add(rs.getString("service_name"));
				}
				for (int j = 0; j < car_manuf_list.size(); j++){
					String sql2 = "SELECT * FROM Services WHERE service_name = '" + service_name_list.get(j) + "' AND car_manuf = '" + car_manuf_list.get(j) + "' AND scid = '" + scid_list.get(i) + "'";
					rs = this.stmt.executeQuery(sql2);
					while(rs.next()){ //Services Table
						service_name = rs.getString("service_name");
						subtype = rs.getString("subtype");
						service_id = rs.getString("service_id");
					}
					System.out.println("Service ID: " + service_id);
					System.out.println("VIN Number: " + vin);
					System.out.println("Service Type: " + subtype);
					System.out.println("Service Cost: " + total_amount_list.get(i));

					System.out.println("Service Start Date/Time: " + start_list.get(i));
					System.out.println("Service End Date/Time: " + start_list.get(i) + duration_list.get(i));
				}
			}


		}
		catch (Exception e){
			//e.printStackTrace();
		}
		finally {
			this.closeConnections();
		}
	}
	*/

	public void getService(String vin) {
		ArrayList<String> mechanic_Name_list = new ArrayList<String>();
		ArrayList<String> apt_id_list = new ArrayList<String>();
		ArrayList<String> car_manuf_list = new ArrayList<String>();
		ArrayList<String> service_name_list = new ArrayList<String>();
		ArrayList<String> service_id_list = new ArrayList<String>();
		ArrayList<String> scid_list = new ArrayList<String>();
		ArrayList<String> eid_list = new ArrayList<String>();
		ArrayList<String> subtype_list = new ArrayList<String>();
		ArrayList<Integer> start_list = new ArrayList<Integer>();
		ArrayList<Integer> duration_list = new ArrayList<Integer>();
		ArrayList<Integer> total_amount_list = new ArrayList<Integer>();

		try{
			//service_name, car_manuf, scid
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.stmt = this.conn.createStatement();
			String QUERY = "SELECT * FROM Appointments WHERE vin = '"+vin+"'";
			//String QUERY = "SELECT * FROM Appointments WHERE vin = '15DC9A87'";
			rs = this.stmt.executeQuery(QUERY);

			while(rs.next()){ // Appointment Table
				apt_id_list.add(rs.getString("apt_id"));
				scid_list.add(rs.getString("scid"));
				eid_list.add(rs.getString("eid"));
				start_list.add(rs.getInt("start_time"));
				duration_list.add(rs.getInt("duration"));
				total_amount_list.add(rs.getInt("total_amount"));
			}

			for (int i = 0; i < apt_id_list.size(); i++){
				rs = this.stmt.executeQuery("SELECT * FROM Employees WHERE eid = '"+eid_list.get(i)+"'");
				while (rs.next()){ //Services Table
					mechanic_Name_list.add(rs.getString("first_name") + " " + rs.getString("last_name"));
				}
			}

			for (int i = 0; i < apt_id_list.size(); i++){
				String sql = "SELECT * FROM Schedule WHERE apt_id = '"+apt_id_list.get(i)+"'";
				rs = this.stmt.executeQuery(sql);
				while(rs.next()){ // Schedule Table
					service_name_list.add(rs.getString("service_name"));
					car_manuf_list.add(rs.getString("car_manuf"));
				}
			}

			for (int i = 0; i < apt_id_list.size(); i++){
				String sql2 = "SELECT * FROM Services WHERE service_name = '"+service_name_list.get(i)+"' AND car_manuf = '"+car_manuf_list.get(i)+"'";
				rs = this.stmt.executeQuery(sql2);
				while(rs.next()){ //Services Table
					//service_name_list.add(rs.getString("service_name"));
					subtype_list.add(rs.getString("subtype"));
					service_id_list.add(rs.getString("service_id"));
				}
			}

			// print out
			for (int i = 0; i < apt_id_list.size(); i++){ // Schedule Table
				System.out.println("Service ID: " + service_id_list.get(i));
				System.out.println("VIN Number: " + vin);
				System.out.println("Service Type: " + subtype_list.get(i));
				System.out.println("Service Cost: " + total_amount_list.get(i));
				System.out.println("Mechanic Name: " + mechanic_Name_list.get(i));
				System.out.println("Service Start Date/Time: " + start_list.get(i));
				System.out.println("Service End Date/Time: " + start_list.get(i) + duration_list.get(i));
			}
		}
		catch (Exception e){
			//e.printStackTrace();
		}
		finally {
			this.closeConnections();
		}
	}


	public void print_all_cars(int cid, String scid){
		try{
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.stmt = this.conn.createStatement();
			rs = this.stmt.executeQuery("SELECT * FROM Owns WHERE scid = '" + scid + "' AND cid = " + cid + "");
			List<String> vins = new ArrayList<String>();
			while(rs.next()){
				String vin = rs.getString("vin");
				vins.add(vin);
			}
			System.out.println("Your Cars Information: ");
			System.out.println("\tVIN\t\tManufacture\tMileage\t\tYear");
			for (int i = 0; i < vins.size(); i++){
				rs = this.stmt.executeQuery("SELECT * FROM Cars WHERE vin = '" + vins.get(i) + "'");
				String VIN = "";
				String Manufacture = "";
				int Mileage = 0;
				int Year = 0;
				while(rs.next()){
					VIN = rs.getString("vin").trim();
					Manufacture = rs.getString("manuf").trim();
					Mileage = rs.getInt("mileage");
					Year = rs.getInt("year");
					System.out.println("\t" + VIN + "\t" + Manufacture + "\t\t" + Mileage + "\t\t" + Year);
				}
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
		} finally {
			this.closeConnections();
		}
	}

	public void viewProfile(int cid, String scid){
		try{
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.stmt = this.conn.createStatement();
			String sql = "SELECT * FROM Customers WHERE scid = '" + scid + "' AND cid= " + cid +"";
			rs = this.stmt.executeQuery(sql);
			String cust_id, name, address, email, phone, cars;
			while(rs.next()){
                cust_id = String.valueOf(rs.getInt("cid"));
                String first = rs.getString("first_name").trim();
                String last = rs.getString("last_name").trim();
                name = first + " " + last;
                address = rs.getString("address").trim();
                email = rs.getString("email").trim();
                phone = rs.getString("telephone").trim();
                System.out.println("Customer ID: " + cust_id);
                System.out.println("Full Name: " + name);
                System.out.println("Address: " + address);
                System.out.println("Email: " + email);
                System.out.println("Phone Number: " + phone);
            }

		}
		catch (Exception e) {
			//e.printStackTrace();
		} finally {
			this.closeConnections();
		}
	}


	public int addCar(int cid, String sid, String vin, String car_manuf, String mileage, String year, String last_mant_sch){
		try{
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.conn.setAutoCommit(false);
			this.stmt = this.conn.createStatement();

			// check if the car exists
			boolean exist = false;
			String sql = "SELECT * FROM Cars WHERE vin = '" + vin + "'";
			rs = this.stmt.executeQuery(sql);
			while(rs.next()){
				exist = true;
			}
			int rows_affected = 0;
			if (!exist){
				rows_affected += this.stmt.executeUpdate("INSERT INTO Cars VALUES('" + vin + "','" + car_manuf + "', " + mileage + ", " + year + ", '" + last_mant_sch + "')");
			}
			rows_affected += this.stmt.executeUpdate("INSERT INTO Owns VALUES('" + vin + "','" + sid + "','" + cid + "')");
			conn.commit();
			return rows_affected;
		}
		catch(Throwable oops) {
			//oops.printStackTrace();
			return -1;
		} finally {
			this.closeConnections();
		}
	}

	public int deleteCar(String delete_vin){
		try{
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.conn.setAutoCommit(false);
			this.stmt = this.conn.createStatement();
			int rows_affected = 0;

			//rows_affected += this.stmt.executeUpdate("DELETE FROM Cars WHERE vin = '"+ delete_vin + "'");

			rows_affected += this.stmt.executeUpdate("DELETE FROM Owns WHERE vin = '"+ delete_vin + "'");

			conn.commit();
			return rows_affected;
		}
		catch(Throwable oops) {
			//oops.printStackTrace();
			return -1;
		} finally {
			this.closeConnections();
		}
	}

	public int addServiceToEachStore(String service_id, String duration, String service_name, String subtype, String schedule, String repair_service){
		try{
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.conn.setAutoCommit(false);
			this.stmt = this.conn.createStatement();
			ArrayList<String> stores = new ArrayList<String>();
			String sql = "SELECT * FROM Service_Centers";
			rs = this.stmt.executeQuery(sql);
			int rows_affected = 0;
			while (rs.next()){
				String scid = rs.getString("scid").trim();
				stores.add(scid);
			}
			int scid = 30003;
			String insert_stmt = "";
			if(subtype.equals("Schedule") || !(schedule.equals("N"))) {
				rows_affected += this.stmt.executeUpdate("INSERT INTO Services VALUES('" + service_id + "','Honda','" + duration + "','" + service_name + " From Schedule: " + schedule + "','" + subtype + "','" + schedule + "','" + repair_service + "')");
				rows_affected += this.stmt.executeUpdate("INSERT INTO Services VALUES('" + service_id + "','Nissan','" + duration + "','" + service_name + " From Schedule: " + schedule + "','" + subtype + "','" + schedule + "','" + repair_service + "')");
				rows_affected += this.stmt.executeUpdate("INSERT INTO Services VALUES('" + service_id + "','Toyota','" + duration + "','" + service_name + " From Schedule: " + schedule + "','" + subtype + "','" + schedule + "','" + repair_service + "')");
				rows_affected += this.stmt.executeUpdate("INSERT INTO Services VALUES('" + service_id + "','Lexus','" + duration + "','" + service_name + " From Schedule: " + schedule + "','" + subtype + "','" + schedule + "','" + repair_service + "')");
				rows_affected += this.stmt.executeUpdate("INSERT INTO Services VALUES('" + service_id + "','Infiniti','" + duration + "','" + service_name + " From Schedule: " + schedule + "','" + subtype + "','" + schedule + "','" + repair_service + "')");

			}
			if(repair_service.equals("Y")) {
				rows_affected += this.stmt.executeUpdate("INSERT INTO Services VALUES('" + service_id + "','Honda','" + duration + "','" + service_name + "','" + subtype + "','" + schedule + "','" + repair_service + "')");
				rows_affected += this.stmt.executeUpdate("INSERT INTO Services VALUES('" + service_id + "','Nissan','" + duration + "','" + service_name + "','" + subtype + "','" + schedule + "','" + repair_service + "')");
				rows_affected += this.stmt.executeUpdate("INSERT INTO Services VALUES('" + service_id + "','Toyota','" + duration + "','" + service_name + "','" + subtype + "','" + schedule + "','" + repair_service + "')");
				rows_affected += this.stmt.executeUpdate("INSERT INTO Services VALUES('" + service_id + "','Lexus','" + duration + "','" + service_name + "','" + subtype + "','" + schedule + "','" + repair_service + "')");
				rows_affected += this.stmt.executeUpdate("INSERT INTO Services VALUES('" + service_id + "','Infiniti','" + duration + "','" + service_name + "','" + subtype + "','" + schedule + "','" + repair_service + "')");

			}
			//rows_affected += this.stmt.executeUpdate(insert_stmt);
			return rows_affected;
		}
		catch(Throwable oops) {
			//oops.printStackTrace();
			return -1;
		} finally {
			this.closeConnections();
		}
	}

	//Should return 3
	public int createEmployeeUserAccount(String scid, String username, String password,
														String role, String eid, String first_name, String last_name,
														String address, String email, String telephone, float pay,
														int hours_worked) {
    try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.conn.setAutoCommit(false);
			int rows_affected = 0;
			try {
				this.stmt = this.conn.createStatement();

				//add user login data to DB_Users table
				rows_affected += this.stmt.executeUpdate("INSERT INTO DB_Users VALUES('" +
						username + "','" + password + "','" + role + "')");
				//Add user data to Employees table
				rows_affected += this.stmt.executeUpdate("INSERT INTO Employees VALUES('" +
						eid + "','" + scid + "','" + first_name + "','" + last_name + "','" +
						address + "','" + email + "','" + telephone + "','" + role + "','" +
						username + "')");

				if (role.equals("Manager") || role.equals("Receptionist")) {
					rows_affected += this.stmt.executeUpdate("INSERT INTO Contract_Emp VALUES('" +
							eid + "','" + scid + "','" + pay + "')");
				} else if (role.equals("Mechanic")) {
					rows_affected += this.stmt.executeUpdate("INSERT INTO Hourly_Emp VALUES('" +
							eid + "','" + scid + "','" + hours_worked + "','" + pay + "')");

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
							rows_affected += this.stmt.executeUpdate("INSERT INTO Day_Schedules VALUES('" + eid + "','" + scid + "', '202409" + String.format("%02d", i) + "','" + day + "', " + j + ", ' ')");
						}
					}

				} else {
					return -1;
				}
				this.conn.commit();
				return rows_affected;
			} catch (Exception e) {
				conn.rollback();
				//e.printStackTrace();
				return -1;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return -1;
		} finally {
			this.closeConnections();
		}
	}

	//Should return 3
	public int updateSchedulePrices(String scid, float a_price, float b_price,
																			float c_price, String manuf) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.conn.setAutoCommit(false);
			int rows_affected = 0;
			try {
				this.stmt = this.conn.createStatement();
				ArrayList<String> car_man = new ArrayList<String>();
				car_man.add("Honda");
				car_man.add("Nissan");
				car_man.add("Toyota");
				car_man.add("Lexus");
				car_man.add("Infiniti");
				for (int i = 0; i < car_man.size(); i++) {



				}
				if(!this.stmt.executeQuery("SELECT schedule FROM Schedule_Prices WHERE scid = '" + scid + "' AND schedule = 'A' AND car_manuf = '" + manuf + "'").next()){
					rows_affected += this.stmt.executeUpdate("INSERT INTO Schedule_Prices VALUES('" +
																scid + "','A','" + a_price +"', NULL, NULL, '" + manuf + "')");
				}
				else {
					rows_affected += this.stmt.executeUpdate("UPDATE Schedule_Prices SET price = '" + a_price + "' WHERE scid = '" + scid + "' AND schedule = 'A' AND car_manuf = '" + manuf + "'");
				}

				if(!this.stmt.executeQuery("SELECT schedule FROM Schedule_Prices WHERE scid = '" + scid + "' AND schedule = 'B' AND car_manuf = '" + manuf + "'").next()){
					rows_affected += this.stmt.executeUpdate("INSERT INTO Schedule_Prices VALUES('" +
																scid + "','B','" + b_price +"', NULL, NULL, '" + manuf + "')");
				}
				else {
					rows_affected += this.stmt.executeUpdate("UPDATE Schedule_Prices SET price = '" + b_price + "' WHERE scid = '" + scid + "' AND schedule = 'B' AND car_manuf = '" + manuf + "'");
				}

				if(!this.stmt.executeQuery("SELECT schedule FROM Schedule_Prices WHERE scid = '" + scid + "' AND schedule = 'C' AND car_manuf = '" + manuf + "'").next()){
					rows_affected += this.stmt.executeUpdate("INSERT INTO Schedule_Prices VALUES('" +
																scid + "','C','" + c_price +"', NULL, NULL, '" + manuf + "')");
				}
				else {
					rows_affected += this.stmt.executeUpdate("UPDATE Schedule_Prices SET price = '" + c_price + "' WHERE scid = '" + scid + "' AND schedule = 'C' AND car_manuf = '" + manuf + "'");
				}

				conn.commit();
				return rows_affected;
			} catch (Exception e) {
				conn.rollback();
				//e.printStackTrace();
				return -1;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return -1;
		} finally {
			this.closeConnections();
		}
	}







	public int updateSwapRequest(String current_scid, String current_eid, int requestor_week,
													int requestor_day, int requestor_slot_start, int requestor_slot_end,
													String requestee_eid, int requestee_week, int requestee_day,
													int requestee_slot_start, int requestee_slot_end) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.conn.setAutoCommit(false);
			int rows_affected = 0;
			try {
				this.stmt = this.conn.createStatement();

				this.rs = stmt.executeQuery("SELECT * FROM Employees WHERE eid = '" + requestee_eid +"' AND scid = '" + current_scid + "'");
				if (!this.rs.next()) {
					throw new Exception("No such employee.");
				}
				this.rs = stmt.executeQuery("SELECT saturdays FROM Service_Centers WHERE scid = '" + current_scid + "'");
				boolean s = false;
				if (this.rs.next()) {
					if (rs.getString("saturdays").equals('Y')) {
						s = true;
					}
				} else {
					throw new Exception("No such store.");
				}

				if (requestor_day==1 || requestee_day==1 || ((requestor_day==7 || requestee_day==7) && !s)
								|| 0 > requestor_slot_start || requestor_slot_start > 11 || 0 > requestor_slot_end || requestor_slot_end > 11
								|| 0 > requestee_slot_start || requestee_slot_start > 11 || 0 > requestee_slot_end || requestee_slot_end > 11) {
					throw new Exception("Store not open during given hours.");
				}

				this.rs = stmt.executeQuery("SELECT swap_id FROM Swaps");
				int swap_id = 0;
				while(rs.next()) {
						if(swap_id < rs.getInt("swap_id")) {
								swap_id = rs.getInt("swap_id");
						}
				}
				swap_id++;


				rows_affected += this.stmt.executeUpdate("INSERT INTO Swaps VALUES( '" + swap_id + "', '" +
																	current_scid + "', '" + current_eid + "', '" +
																	requestor_week + "', '" + requestor_day + "', '" +
																	requestor_slot_start + "', '" + requestor_slot_end + "', '" +
																	requestee_eid + "', '" + requestee_week + "', '" +
																	requestee_day + "', '" + requestee_slot_start + "', '" +
																	requestee_slot_end + "','P')");
				if (rows_affected!=1) {
					throw new Exception("Insert swap failed.");
				}
				conn.commit();
				return rows_affected;
			} catch (Exception e) {
				conn.rollback();
				//e.printStackTrace();
				return -1;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return -1;
		} finally {
			this.closeConnections();
		}
}























//query on time slots. if "S" find appointment and switch appintment

	public int manageSwapRequest(int request_id, boolean accepted,
														String requested_emp_eid, String scid) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.conn.setAutoCommit(false);
			int rows_affected = 0;
			try {
				this.stmt = this.conn.createStatement();

				if (accepted) {
					this.rs = this.stmt.executeQuery("SELECT eid_requestor, requestor_week, " +
								"requestor_day, requestor_slot_start, requestor_slot_end, " +
								"requestee_week, requestee_day, requestee_slot_start, " +
								"requestee_slot_end FROM Swaps WHERE swap_id = " + request_id);
					this.rs.next();
					String eid_requestor = this.rs.getString("eid_requestor");
					int requestor_week = this.rs.getInt("requestor_week");
					int requestor_day = this.rs.getInt("requestor_day");
					int requestor_slot_start = this.rs.getInt("requestor_slot_start");
					int requestor_slot_end = this.rs.getInt("requestor_slot_end");
					int requestee_week = this.rs.getInt("requestee_week");
					int requestee_day = this.rs.getInt("requestee_day");
					int requestee_slot_start = this.rs.getInt("requestee_slot_start");
					int requestee_slot_end = this.rs.getInt("requestee_slot_end");

					int requestor_month_day_ = (requestor_week-1) * 7 + requestor_day;
					int requestee_month_day_ = (requestee_week-1) * 7 + requestee_day;

					String requestor_month_day = "" + requestor_month_day_;
					String requestee_month_day = "" + requestee_month_day_;
					if (requestor_month_day.length()==1) {
						requestor_month_day = "0"+requestor_month_day;
					}
					if (requestee_month_day.length()==1) {
						requestee_month_day = "0"+requestee_month_day;
					}

					String requestor_slot_attrs = "";
					String requestee_slot_attrs = "";
					ArrayList<String> requestor_apts = new ArrayList<String>();
					ArrayList<String> requestee_apts = new ArrayList<String>();

					for (int i = requestor_slot_start; i < requestor_slot_end; i++) {
						this.rs = this.stmt.executeQuery("SELECT slot FROM Day_Schedules WHERE " +
																					"scid = '" + scid + "' AND eid = '" +
																					eid_requestor + "' AND sch_date = '202409" +
																					requestor_month_day + "' AND slot_time = " +
																					i);
						this.rs.next();
						String new_slot = this.rs.getString("slot");
						requestor_slot_attrs += new_slot;
						this.stmt.executeUpdate("UPDATE Day_Schedules SET slot = ' ' WHERE " +
																					"scid = '" + scid + "' AND eid = '" +
																					eid_requestor + "' AND sch_date = '202409" +
																					requestor_month_day + "' AND slot_time = " +
																					i);

						if (new_slot.equals("S")) {
							this.rs = this.stmt.executeQuery("SELECT apt_id FROM Appointments WHERE scid = '" +
																							scid + "' AND eid = '" +
																							eid_requestor + "' AND apt_date = '202409" +
																							requestor_month_day_ + "' AND start_time = '" +
																							i + "'");
							if (this.rs.next()) {
								requestor_apts.add(this.rs.getString("apt_id"));
							}
						}
					}

					for (int i = requestee_slot_start; i < requestee_slot_end; i++) {
						this.rs = this.stmt.executeQuery("SELECT slot FROM Day_Schedules WHERE " +
																					"scid = '" + scid + "' AND eid = '" +
																					requested_emp_eid + "' AND sch_date = '202409" +
																					requestee_month_day + "' AND slot_time = " +
																					i);
						this.rs.next();
						String new_slot = this.rs.getString("slot");
						requestee_slot_attrs += new_slot;

						this.stmt.executeUpdate("UPDATE Day_Schedules SET slot = ' ' WHERE " +
																					"scid = '" + scid + "' AND eid = '" +
																					requested_emp_eid + "' AND sch_date = '202409" +
																					requestee_month_day + "' AND slot_time = " +
																					i);

						if (new_slot.equals("S")) {
							this.rs = this.stmt.executeQuery("SELECT apt_id FROM Appointments WHERE " +
																							"scid = '" + scid + "' AND eid = '" +
																							requested_emp_eid + "' AND apt_date = '202409" +
																							requestee_month_day_ + "' AND start_time = " +
																							i);
							if (this.rs.next()) {
								requestee_apts.add(rs.getString("apt_id"));
							}
						}
					}

					int j = 0;
					for (int i = requestor_slot_start; i < requestor_slot_end; i++) {
						String slot_attr = requestor_slot_attrs.substring(j,j+1);

						if (slot_attr.equals("S")) {
							this.stmt.executeUpdate("UPDATE Day_Schedules SET slot = 'S' WHERE " +
																						"scid = '" + scid + "' AND eid = '" +
																						requested_emp_eid + "' AND sch_date = '202409" +
																						requestor_month_day + "' AND slot_time = '" +
																						i + "'");
						} else if (slot_attr.equals("L")) {
							this.stmt.executeUpdate("UPDATE Day_Schedules SET slot = 'L' WHERE " +
																						"scid = '" + scid + "' AND eid = '" +
																						requested_emp_eid + "' AND sch_date = '202409" +
																						requestor_month_day + "' AND slot_time = '" +
																						i + "'");
						} else {
							this.stmt.executeUpdate("UPDATE Day_Schedules SET slot = ' ' WHERE " +
																						"scid = '" + scid + "' AND eid = '" +
																						requested_emp_eid + "' AND sch_date = '202409" +
																						requestor_month_day + "' AND slot_time = '" +
																						i + "'");
						}
						j += 1;
					}

					j = 0;
					for (int i = requestee_slot_start; i < requestee_slot_end; i++) {
						String slot_attr = requestee_slot_attrs.substring(j,j+1);

						if (slot_attr.equals("S")) {
							this.stmt.executeUpdate("UPDATE Day_Schedules SET slot = 'S' WHERE " +
																						"scid = '" + scid + "' AND eid = '" +
																						eid_requestor + "' AND sch_date = '202409" +
																						requestee_month_day + "' AND slot_time = '" +
																						i + "'");
						} else if (slot_attr.equals("L")) {
							this.stmt.executeUpdate("UPDATE Day_Schedules SET slot = 'L' WHERE " +
																						"scid = '" + scid + "' AND eid = '" +
																						eid_requestor + "' AND sch_date = '202409" +
																						requestee_month_day + "' AND slot_time = '" +
																						i + "'");
						} else {
							this.stmt.executeUpdate("UPDATE Day_Schedules SET slot = ' ' WHERE " +
																						"scid = '" + scid + "' AND eid = '" +
																						eid_requestor + "' AND sch_date = '202409" +
																						requestee_month_day + "' AND slot_time = '" +
																						i + "'");
						}
						j += 1;
					}

					for (int i = 0; i < requestor_apts.size(); i++) {
						String a_id = requestor_apts.get(i);
						this.stmt.executeUpdate("UPDATE Appointments SET eid = '" +
																						requested_emp_eid + "' WHERE apt_id = '" +
																						a_id + "'");
					}

					for (int i = 0; i < requestee_apts.size(); i++) {
						String a_id = requestee_apts.get(i);
						this.stmt.executeUpdate("UPDATE Appointments SET eid = '" +
																						eid_requestor + "' WHERE apt_id = '" +
																						a_id + "'");
					}

					this.stmt.executeUpdate("UPDATE Swaps SET status = 'A' WHERE " +
																					"swap_id = " + request_id);
				} else {
					this.stmt.executeUpdate("UPDATE Swaps SET status = 'R' WHERE " +
 																					"swap_id = " + request_id);
				}
				conn.commit();
				return rows_affected;
			} catch (Exception e) {
			conn.rollback();
			//e.printStackTrace();
			return -1;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return -1;
		} finally {
			this.closeConnections();
		}
	}

	public int addNewCustomer(String current_scid , int cid, String first_name, String last_name, String address, String email_adress, String phone_number
			, String username, String vin_number, String car_manufacture, String current_mileage, String year) {

		 try {
				this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
				this.conn.setAutoCommit(false);
				int rows_affected = 0;
				try {
					this.stmt = this.conn.createStatement();

					//update into DB_USERS
					rows_affected += this.stmt.executeUpdate("INSERT INTO DB_Users VALUES( '" + username + "', '" + last_name + "', 'Customer')");
					//update into customer table
					rows_affected += this.stmt.executeUpdate("INSERT INTO Customers VALUES('" +
							current_scid + "','" + cid + "','" + first_name + "','" + last_name + "','" +
							address + "','" + email_adress + "','" + phone_number + "','" + 0 + "','" + 1 + "','" +
							1 + "','" + username + "')");
					//update into Car table
					rows_affected += this.stmt.executeUpdate("INSERT INTO Cars VALUES('" +
							vin_number + "','" + car_manufacture + "','" + current_mileage + "','" + year + "','C')");
					//update into Owns table
					rows_affected += this.stmt.executeUpdate("INSERT INTO Owns VALUES('" +
							vin_number + "','" + current_scid + "','" + cid + "')");

					this.conn.commit();
					return rows_affected;
				} catch (SQLException e) {
					this.conn.rollback();
					//e.printStackTrace();
					return -1;
				}
			}
		 	catch(Throwable oops) {
				//oops.printStackTrace();
				return -1;
			} finally {
				this.closeConnections();
			}

}







	public boolean create_appointment(String scid, String eid, String vin, String apt_date, int dotw, int start_time,
										int duration, int total_amount, String done, String paid, ArrayList<Service> cart) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.conn.setAutoCommit(false);
			try {
				this.stmt = this.conn.createStatement();

				rs = this.stmt.executeQuery("SELECT apt_id FROM Appointments");
				int ap_id = 0;
				while(rs.next()) {
					if(ap_id < rs.getInt("apt_id")) {
						ap_id = rs.getInt("apt_id");
					}
				}
				ap_id++;

				this.stmt.executeUpdate("INSERT INTO Appointments VALUES('" + Integer.toString(ap_id) + "', '" + scid.strip() +
																			"', '" + eid + "', '" + vin + "', '202409" + apt_date + "', " +
																			dotw + ", " + start_time + ", " + duration + ", '" + "Y" +
																			"', '" + paid + "', " + total_amount + ")");

				rs = this.stmt.executeQuery("SELECT hours_worked FROM Hourly_Emp WHERE eid = '" + eid + "' AND scid = '" + scid + "'");
				rs.next();
				int hours_worked = rs.getInt("hours_worked") + duration;

				this.rs = stmt.executeQuery("SELECT manuf FROM Cars WHERE vin = '" + vin + "'");
				rs.next();
				String manuf = rs.getString("manuf");

				this.stmt.executeUpdate("UPDATE Hourly_Emp SET hours_worked = '" + hours_worked + "' WHERE eid = '" + eid + "' AND scid = '" + scid + "'");

				for(int i = 0; i < cart.size(); i++) {
					this.stmt.executeUpdate("INSERT INTO Schedule VALUES('" + Integer.toString(ap_id) + "', '" + cart.get(i).name + "', '" + manuf + "', '" + scid + "')");
					if(cart.get(i).name.equals("A") || cart.get(i).name.equals("B") || cart.get(i).name.equals("C")){
						this.stmt.executeUpdate("UPDATE Cars SET last_mant_sch = '" + cart.get(i).name + "' WHERE vin = '" + vin + "'");
					}
				}

				for(int i = start_time; i < start_time + duration + 1; i++) {
					this.stmt.executeUpdate("UPDATE Day_Schedules SET slot = 'S' WHERE eid = '" + eid + "' AND slot_time = '" + i + "' AND sch_date = '202409" + apt_date + "' AND dotw = '" + dotw + "' AND scid = '" + scid.strip() + "'");
				}
				conn.commit();
				return true;
			} catch (Exception e) {
				conn.rollback();
				//e.printStackTrace();
				return false;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return false;
		} finally {
			this.closeConnections();
		}
	}




	public ArrayList<Invoice> get_invoices(String vin, String scid, int cid) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				ArrayList<Invoice> list = new ArrayList<>();
				this.stmt = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT apt_id, bill_paid FROM Appointments WHERE vin = '" + vin +"'");
				while (this.rs.next()) {
					String apt_id = rs.getString("apt_id");
					String paid = rs.getString("bill_paid");
					list.add(new Invoice(apt_id, cid, vin, null, null, paid.equals("Y"), null, -1));
				}
				return list;
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}

	public boolean pay_invoice(String apt_id) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				ArrayList<Invoice> list = new ArrayList<>();
				this.stmt = this.conn.createStatement();
				rs = stmt.executeQuery("SELECT bill_paid FROM Appointments WHERE apt_id = '" + apt_id +"'");
				rs.next();
				String paid = rs.getString("bill_paid");
				if (paid.equals("Y")) {
					return false;
				}
				else {
					stmt.executeUpdate("UPDATE Appointments SET bill_paid = 'Y' WHERE apt_id = '" + apt_id + "'");
					return true;
				}
			} catch (SQLException e) {
				//e.printStackTrace();
				return false;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return false;
		} finally {
			this.closeConnections();
		}
	}

	//gets new cid for creating new customer from receptionist
	// added
	public int get_new_cid(String current_scid) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			try {
				this.stmt = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT MAX(cid)  FROM Customers WHERE scid = '" + current_scid +"'");
				while (this.rs.next()) {
					int cid = this.rs.getInt(1);
					return cid + 1;
				}
				return 1;

			} catch (SQLException e) {
				//e.printStackTrace();
				return -1;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return -1;
		} finally {
			this.closeConnections();
		}


	}

	public ArrayList<String> get_vins(String scid, int cid) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				ArrayList<String> list = new ArrayList<>();
				this.stmt = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT vin FROM Owns WHERE scid = '" + scid +"' AND cid = " + cid);
				while (this.rs.next()) {
					list.add(rs.getString("vin"));
				}
				return list;
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}

	public Invoice get_invoice(String scid, int cid, String apt_id) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				ArrayList<Invoice> list = new ArrayList<>();
				this.stmt = this.conn.createStatement();
				Statement stmt1 = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT apt_date, total_amount, eid, bill_paid, vin FROM Appointments WHERE apt_id = '" + apt_id +"'");
				rs.next();
				float totalCost = rs.getFloat("total_amount");
				String date = rs.getString("apt_date");
				String paid = rs.getString("bill_paid");
				String eid = rs.getString("eid");
				String vin = rs.getString("vin");
				boolean status = false;
				if(paid.equals("Y")) {
					status = true;
				}
				this.rs = stmt.executeQuery("SELECT manuf FROM Cars WHERE vin = '" + vin + "'");
				rs.next();
				String manuf = rs.getString("manuf");
				rs = stmt.executeQuery("SELECT first_name, last_name FROM Employees WHERE eid = '" + eid + "' AND scid = '" + scid + "'");
				rs.next();
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				this.rs = stmt.executeQuery("SELECT service_name FROM Schedule WHERE apt_id = '" + apt_id +"'");
				ArrayList<Service> services = new ArrayList<>();
				while(rs.next()){
					String serviceid = rs.getString("service_name");
					ResultSet ls = stmt1.executeQuery("SELECT duration, service_name, subtype, repair_service, schedule FROM Services WHERE service_name = '" + serviceid +"'");
					ls.next();
					int duration = ls.getInt("duration");
					String name = ls.getString("service_name");
					String type = ls.getString("subtype");
					String repair = ls.getString("repair_service");
					String group = ls.getString("schedule");
					ls = stmt1.executeQuery("SELECT price FROM Offer WHERE scid = '" + scid + "' AND service_name = '" + serviceid + "' AND car_manuf = '" + manuf + "'");
					ls.next();
					float cost = ls.getFloat("price");
					services.add(new Service(serviceid, name, duration, null, type != "Schedule", group, cost, type));
				}
				return new Invoice(apt_id, cid, vin, date, services, status, first_name + " " + last_name, totalCost);
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}

	public ArrayList<InvoiceCustomer> get_invoice_customer(String current_scid) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				ArrayList<InvoiceCustomer> list = new ArrayList<>();
				this.stmt = this.conn.createStatement();
				Statement stmt1 = this.conn.createStatement();
				Statement stmt2 = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT apt_id, scid, apt_date, total_amount, vin FROM Appointments WHERE bill_paid = 'N' AND scid = '" + current_scid + "'" );
				while(rs.next()){
					String apt_id = rs.getString("apt_id");
					String scid = rs.getString("scid");
					String apt_date = rs.getString("apt_date");

					String vin = rs.getString("vin");
					int total_amount = rs.getInt("total_amount");

					ResultSet ts = stmt2.executeQuery("SELECT cid FROM Owns WHERE scid = '" + current_scid + "' AND vin = '" + vin + "'");
					ts.next();
					int cid = ts.getInt("cid");

					ResultSet ls = stmt1.executeQuery("SELECT first_name, last_name FROM Customers WHERE scid = '" + current_scid +"'AND cid = '" + cid + "'");
					ls.next();

					String first_name = ls.getString("first_name");
					String last_name = ls.getString("last_name");

					InvoiceCustomer inv_customer = new InvoiceCustomer(cid, first_name, last_name, apt_id, apt_date, total_amount);
					list.add(inv_customer);

						//try { ts.close(); } catch(Throwable whatever) {}

					//try { ls.close(); } catch(Throwable whatever) {}
				}
				return list;
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}


	public ArrayList<SwapRequestList> get_swap_request(String current_scid, String current_eid){
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				ArrayList<SwapRequestList> swap_list = new ArrayList<>();
				this.stmt = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT swap_id, eid_requestor, requestee_week, " +
																		"requestee_day, requestee_slot_start, requestee_slot_end " +
																	"FROM Swaps WHERE status = 'P' AND scid ='"+
																		current_scid + "' AND eid_requestee = '" + current_eid
																		+ "'");
				while(rs.next()){
					int swap_id = rs.getInt("swap_id");
					String eid_requestor = rs.getString("eid_requestor");
					int week = rs.getInt("requestee_week");
					int day = rs.getInt("requestee_day");
					int slot_start = rs.getInt("requestee_slot_start");
					int slot_end = rs.getInt("requestee_slot_end");

					this.stmt2 = this.conn.createStatement();
					this.rs2 = this.stmt2.executeQuery("SELECT first_name, last_name " +
																			"FROM Employees WHERE scid ='"+
																			current_scid + "' AND eid = '" + eid_requestor
																			+ "'");
					rs2.next();
					String first = rs2.getString("first_name");
					String last = rs2.getString("last_name");
					SwapRequestList swap = new SwapRequestList(swap_id, first, last,
																		week, day, slot_start, slot_end);
					swap_list.add(swap);
				}
				return swap_list;
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}



	public boolean get_request_timeoff(String current_eid, String current_scid,
																				int week, int day, int start_time,
																	 			int end_time)
	{
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.stmt = this.conn.createStatement();
			try {
				Integer m_day = (week-1)*7+day;
				String month_day = "";
				if (m_day < 10){
					month_day = "0" + m_day;
				} else {
					month_day = m_day.toString();
				}

				for (int i = start_time; i < end_time; i++) {
					this.rs = stmt.executeQuery("SELECT * FROM Day_Schedules WHERE eid = '"+
																			current_eid + "' AND scid = '" + current_scid
																			+ "' AND sch_date = '202409" + month_day +
																			"' AND dotw = '" + day + "' AND slot_time = '" +
																			i + "' AND slot = ' '");
					if (!rs.next()) {
						return false;
					}

					this.rs = stmt.executeQuery("SELECT * FROM Day_Schedules WHERE scid = '" +
																			current_scid + "' AND sch_date = '202409" +
																			month_day + "' AND dotw = '" + day +
																			"' AND slot_time = '" + i + "' AND slot <> 'L'");
					int count = 0;
					while (rs.next()) {
						count += 1;
					}
					if (count <= 3) {
						return false;
					}
				}

				this.conn.setAutoCommit(false);
				int rows_affected = 0;
				for (int i = start_time; i < end_time; i++) {
					rows_affected += stmt.executeUpdate("UPDATE Day_Schedules SET slot='T' " +
																								"WHERE eid='" + current_eid + "' AND " +
																								"scid='" + current_scid + "' AND " +
																								"sch_date= '202409"+month_day + "' AND " +
																								"slot_time = '" + i + "'");
				}
				if(rows_affected != end_time-start_time) {
					throw new Exception("Wrong");
				}
				conn.commit();
				return true;
			} catch (Exception e) {
				conn.rollback();
				//e.printStackTrace();
				return false;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return false;
		} finally {
			this.closeConnections();
		}
	}

	public float get_schedule_price(String vin, String scid, String schedule) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			try {
				this.stmt = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT manuf FROM Cars WHERE vin = '" + vin + "'");
				this.rs.next();
				String manuf = rs.getString("manuf");
				this.rs = stmt.executeQuery("SELECT price FROM Schedule_Prices WHERE scid = '" + scid + "' AND schedule = '" + schedule + "' AND car_manuf = '" + manuf + "'");
				this.rs.next();
				return rs.getFloat("price");
			} catch (SQLException e) {
				//e.printStackTrace();
				return -1;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return -1;
		} finally {
			this.closeConnections();
		}
	}

	public int get_schedule_duration(String vin, String scid, String schedule) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			try {
				this.stmt = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT manuf FROM Cars WHERE vin = '" + vin + "'");
				this.rs.next();
				String manuf = rs.getString("manuf");
				this.rs = stmt.executeQuery("SELECT duration FROM Schedule_Prices WHERE scid = '" + scid + "' AND schedule = '" + schedule + "' AND car_manuf = '" + manuf + "'");
				this.rs.next();
				return rs.getInt("duration");
			} catch (SQLException e) {
				//e.printStackTrace();
				return -1;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return -1;
		} finally {
			this.closeConnections();
		}
	}

	public ArrayList<Schedule> get_open_time_slots(String scid, int duration) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			try {
				ArrayList<Schedule> schedules = new ArrayList<>();
				HashMap<String, Integer> found = new HashMap<>();
				this.stmt = this.conn.createStatement();
				Statement stmt1 = this.conn.createStatement();
				rs = stmt.executeQuery("SELECT saturdays FROM Service_Centers WHERE scid = '" + scid + "'");
				rs.next();
				String sat = rs.getString("saturdays");
				this.rs = stmt.executeQuery("SELECT eid FROM Employees WHERE scid = '" + scid + "' AND title = 'Mechanic'");
				while(rs.next()) {
					String eid = rs.getString("eid");
					ResultSet ls = stmt1.executeQuery("SELECT hours_worked FROM Hourly_Emp WHERE eid = '" + eid + "' AND scid = '" + scid + "'");
					ls.next();
					if((ls.getInt("hours_worked") + duration) > 50) {
						continue;
					}
					for(int i = 1; i <= 30; i++) {
						if(i % 7 == 0 && sat.equals("N")) {
							continue;
						}
						ls = stmt1.executeQuery("SELECT slot, slot_time FROM Day_Schedules WHERE eid = '" + eid + "' AND sch_date LIKE '%" + String.format("%02d", i) +"'");

        				int arr[] = new int[11];
						int k = 0;
						while(ls.next()) {
							if(ls.getString("slot").equals(" ")) {
								arr[k++] = ls.getInt("slot_time");
							}
						}
						Arrays.sort(arr);
						int current = 0;
						int previous = 0;
						int start = 0;
						for(int j = 0; j < arr.length; j++) {
							if(arr[j] - previous == 1) {
								current++;
								previous++;
							}
							else {
								current = 0;
								previous = arr[j];
							}
							if(current >= duration) {
								start++;
								if(found.get(Integer.toString(i) + Integer.toString(arr[j] - (duration-1))) == null ) {
									schedules.add(new Schedule(eid, String.format("%02d", i), scid, arr[j] - (duration-1), i));
									found.put( Integer.toString(i) + Integer.toString(arr[j] - (duration-1)), j);
								}
								current = 0;
								j = start - 1;
								if(start < arr.length) {
									previous = arr[start] - 1;
								}
							}
						}




						// for(int j = 1; j <= 11; j++) {
						// 	ResultSet ls = stmt1.executeQuery("SELECT slot, sch_date FROM Day_Schedules WHERE eid = '" + eid + "' AND sch_date LIKE '%" + String.format("%02d", i) +"' AND slot_time = '" + j + "'");
						// 	if (ls.next()) {
						// 		String date = ls.getString("sch_date");
						// 		String day = date.substring(Math.max(date.length() - 2, 0));
						// 		if (!(ls.getString("slot").equals("S") || ls.getString("slot").equals("L"))) {
						// 			duration_count++;
						// 		}
						// 		else {
						// 			duration_count = 0;
						// 			start = j;
						// 		}
						// 		if(duration_count >= duration) {
						// 			if(found.get(Integer.toString(i) + Integer.toString(j)) == null ) {
						// 				System.out.println(Integer.toString(i) + " On " + start);
						// 				schedules.add(new Schedule(eid, Integer.toString(i), scid, start, i));
						// 				found.put( Integer.toString(i) + start, j);
						// 				start = j;
						// 			}
						// 		}
						// 		if(start == 11) {
						// 			start = 1;
						// 			duration_count = 0;
						// 			break;
						// 		}
						// 	}

						// }
						// ResultSet ls = stmt1.executeQuery("SELECT MIN(slot_time) FROM Day_Schedules WHERE eid = '" + eid + "' AND sch_date LIKE '%" + i +"' AND slot <> 'S' AND slot <> 'L'");
						// ls.next();
						// int start = ls.getInt(1);
						// ls = stmt1.executeQuery("SELECT dotw FROM Day_Schedules WHERE eid = '" + eid + "' AND sch_date LIKE '%" + i +"' AND slot <> 'S' AND slot <> 'L'");
						// ls.next();
						// int dotw = ls.getInt("dotw");
						// ls = stmt1.executeQuery("SELECT MAX(slot_time) FROM Day_Schedules WHERE eid = '" + eid + "' AND sch_date LIKE '%" + i +"' AND slot <> 'S' AND slot <> 'L'");
						// ls.next();
						// int end = ls.getInt(1);
						// if (end - start >= duration) {
						// 	schedules.add(new Schedule(eid, Integer.toString(i), scid, start, dotw));
						// }
					}
				}
				return schedules;
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}

	public ArrayList<Service> get_repair_services(String scid, String vin, String subtype) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			try {
				ArrayList<Service> services = new ArrayList<>();
				this.stmt = this.conn.createStatement();
				Statement stmt2 = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT manuf FROM Cars WHERE vin = '" + vin + "'");
				rs.next();
				String manuf = rs.getString("manuf");
				this.rs = stmt.executeQuery("SELECT * from Services WHERE car_manuf = '" + manuf + "' AND repair_service = 'Y' AND subtype = '" + subtype + "'");
				while(rs.next()) {
					String id = rs.getString("service_name");
					int duration = rs.getInt("duration");
					String name = rs.getString("service_name");
					String schedule = rs.getString("schedule");
					boolean repair = true;
					ResultSet ls = stmt2.executeQuery("SELECT price from Offer WHERE scid = '" + scid + "' AND service_name = '" + id + "' AND car_manuf = '" + manuf + "'");
					ls.next();
					float cost = ls.getFloat("price");

					Service service = new Service(id, name, duration, manuf, repair, schedule, cost, subtype);
					services.add(service);
				}
				return services;
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}

	public String get_next_schedule(String vin) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				this.stmt = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT last_mant_sch FROM Cars WHERE vin = '" + vin + "'");
				this.rs.next();
				String schedule = "A";
				String last_schedule = rs.getString("last_mant_sch");
				if(last_schedule.equals("A")) {
					schedule = "B";
				}
				else if(last_schedule.equals("B")) {
					schedule = "C";
				}
				return schedule;
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}

	public ArrayList<ServiceHistory> get_schedule_services(String vin, String scid, String schedule) {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				ArrayList<ServiceHistory> list = new ArrayList<>();
				this.stmt = this.conn.createStatement();
				Statement stmt1 = this.conn.createStatement();
				Statement stmt2 = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT manuf FROM Cars WHERE vin = '" + vin + "'");
				this.rs.next();
				String manuf = rs.getString("manuf");
				this.rs = stmt.executeQuery("SELECT service_name, repair_service FROM Services WHERE car_manuf = '" + manuf + "' AND service_name LIKE '%From Schedule: " + schedule +"'");
				while (this.rs.next()) {
					String type = "Maintanence";
					type += (rs.getString("repair_service").equals("Y") ? ", Repair" : "");
					list.add(new ServiceHistory(null, null, type, null, -1, -1, -1, null, rs.getString("service_name")));
				}
				return list;
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}

	public ArrayList<ServiceHistory> get_service_history(String vin, String scid) {

		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				ArrayList<ServiceHistory> list = new ArrayList<>();
				this.stmt = this.conn.createStatement();
				Statement stmt1 = this.conn.createStatement();
				Statement stmt2 = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT manuf FROM Cars WHERE vin = '" + vin + "'");
				this.rs.next();
				String manuf = rs.getString("manuf");
				this.rs = stmt.executeQuery("SELECT apt_date, total_amount, eid, start_time, duration, apt_id FROM Appointments WHERE vin = '" + vin +"'");
				while (this.rs.next()) {
					String eid = this.rs.getString("eid");
					String date = this.rs.getString("apt_date");
					int start_time = this.rs.getInt("start_time");
					int end_time = start_time + this.rs.getInt("duration");
					String apt_id = this.rs.getString("apt_id");
					ResultSet ls = stmt1.executeQuery("SELECT first_name, last_name FROM Employees WHERE eid = '" + eid + "' AND scid = '" + scid.strip() + "'");
					ls.next();
					String first_name = ls.getString("first_name");
					String last_name = ls.getString("last_name");
					ls = stmt1.executeQuery("SELECT service_name FROM Schedule WHERE apt_id = '" + apt_id +"'");
					while(ls.next()){
						String servicename = ls.getString("service_name");
						ResultSet ts = stmt2.executeQuery("SELECT service_id, repair_service FROM Services WHERE service_name = '" + servicename +"'");
						ts.next();
						String serviceid = ts.getString("service_id");
						String type = (ts.getString("repair_service").equals("Y") ? "Repair" : "Maintanence");
						ts = stmt2.executeQuery("SELECT price FROM Offer WHERE scid = '" + scid.strip() + "' AND service_name = '" + servicename + "' AND car_manuf = '" + manuf + "'");
						ts.next();
						float cost = ts.getFloat("price");
						ServiceHistory service_details = new ServiceHistory(serviceid, vin, type, first_name + " " + last_name, start_time, end_time, cost, date, servicename);
						list.add(service_details);
					}
					try { ls.close(); } catch(Throwable whatever) {}
				}
				return list;
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}

	//gets new cid for creating new customer from receptionist
	public int get_new_cid() {
		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			try {
				this.stmt = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT MAX(cid)  FROM Customers");
				while (this.rs.next()) {
					int cid = this.rs.getInt(1);
					return cid + 1;
				}
				return 1;

			} catch (SQLException e) {
				//e.printStackTrace();
				return -1;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return -1;
		} finally {
			this.closeConnections();
		}




	}


	public ArrayList<MechanicScheduleHistory> get_mechanic_schedule(String current_eid, String current_scid) {

		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				ArrayList<MechanicScheduleHistory> sch_list = new ArrayList<>();
				this.stmt = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT apt_date , dotw , start_time, duration " +
																		"FROM Appointments WHERE eid = '" + current_eid +
																		"' AND scid = '" + current_scid + "'" );
				while (this.rs.next()) {
					String apt_date = this.rs.getString("apt_date");
					int dotw = this.rs.getInt("dotw");
					int start_time  = this.rs.getInt("start_time");
					int duration  = this.rs.getInt("duration");

					MechanicScheduleHistory mechanic_schedule =
												new MechanicScheduleHistory(current_eid, current_scid,
																					apt_date, dotw, start_time, duration);
					sch_list.add(mechanic_schedule);
				}
				return sch_list;
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}





	public HashMap<String, String> load_repair_services_available() {

		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);

			try {
				this.stmt = this.conn.createStatement();
				this.rs = stmt.executeQuery("SELECT service_name, service_name, car_manuf FROM Services WHERE repair_service = 'Y' AND service_name NOT LIKE '%From Schedule: _'");
				HashMap<String, String> map = new HashMap<>();
				String service_id = null;
				String service_name = null;

				String temp = "";
				while (rs.next()) {
					service_id = this.rs.getString(1);
					service_name = this.rs.getString(2);
					service_name += " (" + this.rs.getString(3).strip() + ")";
					map.put(service_name, service_id);
				}

				return map;
			} catch (SQLException e) {
				//e.printStackTrace();
				return null;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return null;
		} finally {
			this.closeConnections();
		}
	}

	public int upload_repair_services_prices(HashMap<String, Float> rs_prices, String scid) {

		try {
			this.conn = DriverManager.getConnection(jdbcURL, this.username, this.password);
			this.conn.setAutoCommit(false);
			int rows_affected = 0;
			try {
				this.stmt = this.conn.createStatement();

				for (Map.Entry<String, Float> entry : rs_prices.entrySet()) {
					String service_id = entry.getKey();
					float service_price = entry.getValue();

					// check if it exists
					String[] car_list = {"Honda", "Nissan", "Toyota", "Lexus", "Infiniti"};
					for (String car_manuf : car_list){
						boolean exist = false;
						boolean check_Services = false;
						String sql = "SELECT * FROM Offer WHERE scid = '" + scid + "' AND service_name = '" + service_id + "' AND car_manuf = '" + car_manuf + "'";
						rs = this.stmt.executeQuery(sql);
						while(rs.next()){
							exist = true;
						}
						rs = this.stmt.executeQuery("SELECT * FROM Services WHERE service_name = '" + service_id + "' AND car_manuf = '" + car_manuf + "'");
						while(rs.next()){
							check_Services = true;
						}
						if (exist){
							String update_sql = "UPDATE Offer SET price = '" + service_price + "' WHERE scid = '" + scid + "' AND service_name = '" + service_id + "' AND car_manuf = '" + car_manuf + "'";
							rows_affected += this.stmt.executeUpdate(update_sql);
						}
						else{
							if (check_Services){
								rows_affected += this.stmt.executeUpdate("INSERT INTO Offer VALUES('" + scid + "','" + service_id + "', '" + car_manuf + "', '" + service_price +"')");
							}
						}
					}
				}

				conn.commit();
				return rows_affected;
			} catch (Exception e) {
				conn.rollback();
				//e.printStackTrace();
				return -1;
			}
		} catch(Throwable oops) {
			//oops.printStackTrace();
			return -1;
		} finally {
			this.closeConnections();
		}

	}

	public void closeConnections() {
		close(this.conn);
		close(this.stmt);
		close(this.rs);
	}

	private void close(Connection conn) {
		if(conn != null) {
			try {conn.close();} catch(Throwable whatever) {}
		}
	}

	private void close(Statement st) {
		if(st != null) {
			try { st.close(); } catch(Throwable whatever) {}
		}
	}

	private void close(ResultSet rs) {
		if(rs != null) {
			try { rs.close(); } catch(Throwable whatever) {}
		}
	}
}
