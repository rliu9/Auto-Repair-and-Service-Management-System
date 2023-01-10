CSC 540 DATABASE MANAGEMENT CONCEPTS & SYSTEMS
PROJECT 1 AUTOR - Auto Repair and Service Management System


TEAM 3
Kris London - kllondon
Avi Choksi - ajchoks2
Seongsoo Kim - skim228
Rundi Liu - rliu26


Instructions to run the application
1. Add Oracle to your environment:
$ add oracle12 
      
2. Set the CLASSPATH variable to include the JDBC drivers:
$ export CLASSPATH=.:/afs/eos.ncsu.edu/software/oracle12/oracle/product/12.2/client/jdbc/lib/ojdbc8.jar
 
3. Create Credentials.txt, then add your Oracle (NCSU)'s  <username>  in the first line and <password> in the second line.

4. Compile each Java file:
$ javac @sources.txt

5. You can initialize Database by 
$ java InitializeDatabase
  
6.(Optional) Then if you want to populate data to Database, you can run:
$ java populate
  
7. Now, you can run the application by:
$ java UI
  
8.(Optional) If you want to see any Database Table, use this and enter the table's name:
$ java CheckDatabase