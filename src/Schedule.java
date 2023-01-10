public class Schedule {
    String eid;
    String date;
    int dotw;
    int start_time;
    String scid;

    public Schedule(String eid, String date, String scid, int start_time, int dotw){
        this.eid = eid;
        this.date = date;
        this.scid = scid;
        this.start_time = start_time;
        this.dotw = dotw;
    }
}
