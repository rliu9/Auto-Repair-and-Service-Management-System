//package project1;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/*

Checks username and password and directs to the right page according to the person's
identiy ex) Manager, Mechanic, Customer


*/
public class SignIn {
	private String userID;
	private String password;
	HashMap<String, String> identity = new HashMap<String, String>();
	HashMap<String, String> logincredential = new HashMap<String, String>();
	
	// setting a name and this person's role into the map
	public void identitySetter(String first, String last)
	{
		identity.put(first, last);
		
	}
	
	// getting the map of person's name and its role
	public HashMap<String, String> identityGetter(){
		
		
		return identity;
	}
	
	// setting a username and password into the map
	public void  loginSetter(String username, String password)
	{
		logincredential.put(username, password);
	}		
	
	// getting the map of username and its password
	public HashMap<String, String> loginGetter(){
		
		return logincredential;
	}
	 
	
	
	private Scanner input = new Scanner(System.in);
	public void display() {
		System.out.println("------ Sign-in ------");
		
		
		while (true){
		 try {
			 	// Test Case Manager
			    identitySetter("John Doe", "Manager");
			    loginSetter("John Doe", "Doe");
			    
			    // Test Case Mechanic
			    identitySetter("Bob Boom", "Mechanic");
			    loginSetter("Bob Boom", "Boom");
			 	
			 	// Test case Customer
			    identitySetter("Alex Sid", "Customer");
			    loginSetter("Alex Sid", "Sid");
			    
			    System.out.println(
				"Please enter the input the following details in the order shown below\n" +
				"A. User ID\n" +
				"B. Password"	
				);
			 	
		    	userID = input.nextLine();
		 	    //input.nextLine();
		 	    password = input.next();
		 	    input.nextLine();
		    }
		 catch (InputMismatchException e) {
		    	System.out.println("invalid input type.");
		    	input.next();
		    }
		 
		 System.out.println("userID '" + userID + "' password '" + password + "'");
		 
		 try {
			 if (loginGetter().get(userID.toString()).equals(password.toString()) )
			 {
				 
				 System.out.println("Login successful");
				 
				 if (identity.get(userID.toString()).equals("Manager")) {
					 
					 
					 System.out.println("directing to Manager page");
					 
					 // direct it toward manager
					 
				 }
				
				 else if (identity.get(userID.toString()).equals("Mechanic")) {
					 
					 System.out.println("directing to Mechanic page");
					 
				 }
				 
				 
				 else if (identity.get(userID.toString()).equals("Customer")) {
					 
					 System.out.println("directing to Customer page");
				 }
				 
				 
				 break;
			 }
			 
		
			 else {
				 System.out.println("UserID or Password doesnt match. Please try again");
			 }
		 }
		 catch(Exception e) {
			 System.out.println("UserID or Password doesnt match. Please try again");
		 }
		 
			
		}
	}
}