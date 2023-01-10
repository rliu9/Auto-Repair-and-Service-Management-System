public class ServiceHistory {
    String id;
    String vin;
    String serviceType;
    String mechanicName;
    float cost;
    int start;
    int end;
    String date;
    String name;
    
    public ServiceHistory(String id, String vin, String serviceType, String mechanicName, int start, int end, float cost, String date, String name) {
        this.id = id;
        this.vin = vin;
        this.serviceType = serviceType;
        this.mechanicName = mechanicName;
        this.start = start;
        this.end = end;
        this.cost = cost;
        this.date = date;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getVin() {
        return vin;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public String getDate() {
        return date;
    }

    public float getCost() {
        return cost;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
