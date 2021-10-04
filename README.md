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
 
 ### STEP 5 : Using hibernate configuration file i.e. hibernate.cfg.xml
 
 * Instead of using Connection object we can make a hibernate configuration file 
 * make a file called hibernate.cfg.xml and add the below code
 
 		<!DOCTYPE hibernate-configuration PUBLIC
        	"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        	"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

		<hibernate-configuration>

    			<session-factory>

        			<!-- JDBC Database connection settings -->
        			<property name="connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        			<property name="connection.url">jdbc:mysql://localhost:3306/hb_student_tracker?useSSL=false&amp;serverTimezone=UTC</property>
        			<property name="connection.username">hbstudent</property>
        			<property name="connection.password">hbstudent</property>

        			<!-- JDBC connection pool settings ... using built-in test pool -->
        			<property name="connection.pool_size">1</property>

        			<!-- Select our SQL dialect -->
        			<property name="dialect">org.hibernate.dialect.MySQLDialect</property>

        			<!-- Echo the SQL to stdout - for showing the sql query -->
        			<property name="show_sql">true</property>

				<!-- Set the current session context -->
				<property name="current_session_context_class">thread</property>
 
    			</session-factory>

		</hibernate-configuration>

* We can configure our file for connecting to the database

### STEP 6 : Annotations

* First we need to make a class i.e Student class that will be a pojo class and we will annotate it for mapping it to our database
* Below is the code with all the annotation we require for mapping a pojo class with the database

		package com.rohitthebest.hibernate.demo.entity;

		import javax.persistence.Column;
		import javax.persistence.Entity;
		import javax.persistence.Id;
		import javax.persistence.Table;

		/**
 		* 
 		* @author Rohit
 		*	Mapping this class to the database
 		*/

		@Entity
		@Table(name = "student")
		public class Student {

			@Id		// used on primary key
			@Column(name = "id") // used for defining the column name same as in database
			private int id;
	
			@Column(name = "first_name")
			private String firstName;
	
			@Column(name = "last_name")
			private String lastName;
	
			@Column(name = "email")
			private String email;
	
	
			public Student() {}


			public Student(String firstName, String lastName, String email) {
		
				this.firstName = firstName;
				this.lastName = lastName;
				this.email = email;
			}


			public int getId() {
				return id;
			}


			public void setId(int id) {
				this.id = id;
			}


			public String getFirstName() {
				return firstName;
			}


			public void setFirstName(String firstName) {
				this.firstName = firstName;
			}


			public String getLastName() {
				return lastName;
			}


			public void setLastName(String lastName) {
				this.lastName = lastName;
			}


			public String getEmail() {
				return email;
			}


			public void setEmail(String email) {
				this.email = email;
			}


			@Override
			public String toString() {
				return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
			}
	
	
		}
		
#### What is SessionFactory?
* Reads the hibernate config file
* Create Session objects
* Heavy-weight object
* Only create once for the entire app

#### What is Session?
* Wraps a JDBC connection
* Main object used to save/retrieve objects
* Short-lived object
* Retrieved from SessionFactory



 
