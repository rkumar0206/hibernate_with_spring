# hibernate_with_spring
starting with hibernate using spring and mysql database

### STEP 1 : Setting up MySQL database

* Download MySQL server and connector
* Configure your MYSQL server
* Download MySQL Workbench for UI
* Add a new connection
* Create a database called hbstudent with password hbstudent
* create a table student with fields
  * ID - Integer, Auto-increment, primary key
  * firstName
  * lastName
  * email

### STEP 2 : Make new java project in eclipse

* make new java project with name, ex : hibernate_tutorial
* make a lib folder

### STEP 3 : Setting hibernate and MySQL connector in eclipse

* Download hibernate from hibernate.org
* extract the zip file 
* go to lib directory -> go to required folder -> copy the jar files
* paste the jar files to the lib directory of which you have creted in eclipse in hibernate_tutorial folder
* now open your MySQL connector folder and add there will be a jar file inside it, copy and paste it in lib folder of project
* configure the build path of the java project by going to its property and adding the jar files in classpath

### STEP 4 : Test the connection

* make a new package inside src named com.rohitthebest.jdbc
* create a main class named TestJdbc
* write the code to test the jdbc connection
    
      package com.rohitthebest.jdbc;

      import java.sql.Connection;
      import java.sql.DriverManager;

      public class TestJdbc {

    	  public static void main(String[] args) {

    		   String jdbcUrl = "jdbc:mysql://localhost:3306/hb_student_tracker?useSSL=false";
    		  String user = "hbstudent";
    		  String pass = "hbstudent";
		
    		  try {
			
	    		  System.out.println("Connecting to database: " + jdbcUrl);
			
	    		  Connection myConnection = DriverManager.getConnection(jdbcUrl, user, pass);
			
    			  System.out.println("Connection successful!!!");
			
			
    		  }catch (Exception e) {
			
    			  e.printStackTrace();
    		  }
    	  }

      }
 * Connection successful
