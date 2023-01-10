public class Service {
    String name;
    String id;
    int duration;
    String car_manuf;
    String subtype;
    boolean repair;
    boolean schedule;
    String group;
    float cost;
    
    public Service( String id, String name, int duration, String car_manuf, boolean repair, String group, float cost, String subtype) {
        this.name = name;
        this.id = id;
        this.duration = duration;
        this.car_manuf = car_manuf;
        this.repair = repair;
        this.group = group;
        this.cost = cost;
        this.subtype = subtype;
        schedule = false;
    }

    public Service( String group, float cost, int duration) {
        this.name = group;
        this.group = group;
        this.cost = cost;
        this.duration = duration;
        schedule = true;
    }
}
