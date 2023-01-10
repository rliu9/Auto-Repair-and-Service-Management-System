import java.util.Scanner;
import java.sql.*;
import java.util.*;
import java.io.File;

public class CustomerViewProfile {
	private int selection;
	public Scanner input;
	public Database db;
    private int cid;
    private String scid;

	public CustomerViewProfile(Scanner input, Database db, int cid, String scid) {
		this.input = input;
		this.db = db;
        this.cid = cid;
        this.scid = scid;
	}

	public void display() {
        System.out.println("\n------ Your Profile ------\n");
        db.viewProfile(cid, scid);
        db.print_all_cars(cid, scid);
        while(true){
            System.out.println();
            System.out.println("1) Go Back");
            selection = input.nextInt();
            if (selection != 1){
                System.out.println("Invalid input!");
            }
            else{
                break;
            }
        }
        
        
	}

}