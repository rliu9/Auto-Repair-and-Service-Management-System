public class SwapRequestList {

  public int swap_id;
  public String firstname_requestor;
  public String lastname_requestor;
  public int week;
  public int day;
  public int time_start;
  public int time_end;

	public SwapRequestList (int swap_id, String firstname_requestor,
                          String lastname_requestor, int week, int day,
                          int time_start, int time_end){
    this.swap_id = swap_id;
    this.firstname_requestor = firstname_requestor;
    this.lastname_requestor = lastname_requestor;
    this.week = week;
    this.day = day;
    this.time_start = time_start;
    this.time_end = time_end;
	}

}
