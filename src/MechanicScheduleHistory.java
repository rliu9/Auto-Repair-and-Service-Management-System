public class MechanicScheduleHistory {

    public String eid;
    public String scid;
    public String schedule_date;
    public int dotw;
    public int start_time;
    public int duration;

	public MechanicScheduleHistory(String eid, String scid, String schedule_date,
                                    int dotw, int start_time, int duration) {
		this.eid = eid;
		this.scid = scid;
		this.schedule_date = schedule_date;
		this.dotw = dotw;
		this.start_time = start_time;
		this.duration = duration;
	}
}
