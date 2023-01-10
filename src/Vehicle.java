import java.sql.CallableStatement;
import java.sql.ResultSet;

public class Vehicle {
    String vin;
    String manu;
    String scid;
    int mileage;
    int year;
    int cid;
    Database data = new Database();

    public Vehicle(String vin) {
        try {
            CallableStatement stmt = null;
            String SQL = "SELECT * FROM Cars " + "WHERE vin = ?}";
            stmt = Database.conn.prepareCall(SQL);
            stmt.setString(1, vin); 
            ResultSet rs = stmt.executeQuery();
            this.vin = rs.getString("vin");
            manu = rs.getString("manu");
            mileage = rs.getInt("mileage");
            year = rs.getInt("year");
            
            stmt = null;
            SQL = "SELECT * FROM Ownes WHERE vin = ?}";
            stmt = Database.conn.prepareCall(SQL);
            stmt.setString(1, vin); 
            rs = stmt.executeQuery();
            scid = rs.getString("scid");
            cid = rs.getInt("cid");
        } catch(Throwable oops) {
            oops.printStackTrace();
        }

    }

    public Vehicle(String scid, int cid, String vin, String manu, int mileage, int year) {
        this.vin = vin;
        this.manu = manu;
        this.mileage = mileage;
        this.year = year;
        this.scid = scid;
        this.cid = cid;
        try {
            CallableStatement stmt = null;
            String SQL = "Insert into Cars values (?, ?, ?, ?, ?)}";
            stmt = Database.conn.prepareCall(SQL);
            stmt.setString(1, vin);
            stmt.setString(1, manu);
            stmt.setInt(1, mileage);
            stmt.setInt(1, year);
			stmt.executeUpdate();
            SQL = "Insert into Owns values (?, ?, ?)}";
            stmt = Database.conn.prepareCall(SQL);
            stmt.setString(1, vin);
            stmt.setString(1, scid);
            stmt.setInt(1, cid);
			stmt.executeUpdate();
        } catch(Throwable oops) {
            oops.printStackTrace();
        }
    }

    public void delete() {
        try {
            CallableStatement stmt = null;
            String SQL = "DELETE FROM Cars " + "WHERE vin = ?}";
            stmt = Database.conn.prepareCall(SQL);
            stmt.setString(1, vin);
			stmt.executeUpdate();
        } catch(Throwable oops) {
            oops.printStackTrace();
        }

    }
}