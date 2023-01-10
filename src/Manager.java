import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
	int id;
	String name;
	int telephone;
	String address;
	double salary;
	String email;
	ServiceCenter store;

	public Manager(int id, String name, int telephone, String address, double salary, String email) {
		this.id = id;
		this.name = name;
		this.telephone = telephone;
		this.address = address;
		this.salary = salary;
		this.email = email;
	}

	public Manager(int id) {

	}
	
	public void createStore(int scid, String address, int telephone, boolean saturday, double min_wage, double max_wage, double hourly_rate, ArrayList<Mechanic> mechanics, Receptionist receptionist, HashMap<Service, Double> services) {
		store = new ServiceCenter(this, scid, address, telephone, saturday, min_wage, max_wage, hourly_rate, mechanics, receptionist, services);
	}

	public void createStore(int scid, String address, int telephone, double min_wage, double max_wage, double hourly_rate) {
		store = new ServiceCenter(this, scid, address, telephone, min_wage, max_wage, hourly_rate);
	}

	public void addMechanic(Mechanic mechanic) {
		store.addMechanic(mechanic);
	}

	public void addReceptionist(Receptionist receptionist) {
		store.addReceptionist(receptionist);
	}

	public void addService(Service service, double price) {
		store.addService(service, price);
	}

	public void openSaturday(boolean openSaturday) {
		store.setSaturday(openSaturday);
	}
	
	
}
