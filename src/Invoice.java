import java.util.ArrayList;

public class Invoice {
    String id;
    int cid;
    String vin;
    String date;
    ArrayList<Service> services;
    boolean status;
    String mechanicName;
    float totalCost;

    public Invoice(String id, int cid, String vin, String date, ArrayList<Service> services, boolean status, String mechanicName, float totalCost) {
        this.id = id;
        this.cid = cid;
        this.vin = vin;
        this.date = date;
        this.services = services;
        this.status = status;
        this.mechanicName = mechanicName;
        this.totalCost = totalCost;
    }
}