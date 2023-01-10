
public class InvoiceCustomer {

    int cid;
    String first_name;
    String last_name;
    String apt_id;
    String apt_date;
    int total_amount;
	
	public InvoiceCustomer (int cid, String first_name, String last_name, String apt_id, String apt_date, int total_amount){
		
		this.cid = cid;
		this.first_name = first_name;
		this.last_name = last_name;
		this.apt_id = apt_id;
		this.apt_date = apt_date;
		this.total_amount = total_amount;
		
	}
	
    public int getCid() {
        return cid;
    }

    public String getFirstname() {
        return first_name;
    }

    public String getLastname() {
        return last_name;
    }

    public String getAptid() {
        return apt_id;
    }

    public String getAptdate() {
        return apt_date;
    }

    public int total_amount() {
        return total_amount;
    }

  
}
