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

### STEP 7 : Saving our student object to database

* create session factory
* create session
* create a student object
* save the student object
* start a transaction
* commit transaction

		package com.rohitthebest.hibernate.demo;

		import org.hibernate.Session;
		import org.hibernate.SessionFactory;
		import org.hibernate.cfg.Configuration;

		import com.rohitthebest.hibernate.demo.entity.Student;

		public class CreateStudentDemo {

			public static void main(String[] args) {
		
				// create session factory
				SessionFactory sessionFactory = new Configuration()
									.configure("hibernate.cfg.xml")
									.addAnnotatedClass(Student.class)
									.buildSessionFactory();

				// create session
		
				Session session = sessionFactory.getCurrentSession();
		
				try {
			
	
					// create a student object
			
					System.out.println("Creating a new studnt object....");
			
					Student student = new Student("Paul", "Wall", "paul@luv2code.com");
			
					// start a transaction
			
					session.beginTransaction();
			
					// save the student object
					System.out.println("Saving the student object");
					session.save(student);
			
					// commit transaction
			
					session.getTransaction().commit();
			
					System.out.println("Done saving");
			
			
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					sessionFactory.close();
				}
		
			}

		}

#### More about Primary key

* For Auto incrementing our id we just need to annotate the id variable in our pojo class as following
				
		@Id                                                   // used on primary key
		@GeneratedValue(strategy = GenerationType.IDENTITY)  // will auto generate the id
		@Column(name = "id")                                // used for defining the column name same as in database
		private int id;
		
* Generation Types
	* GenerationType.AUTO : Pick an appropriate strategy for the particular database
	* GenerationType.IDENTITY : Assign primary keys using database identity column
	* GenerationType.SEQUENCE : Assgn primary keys using a database sequence
	* GenerationType.TABLE : Assign primary keys using an underlying database table to ensure uniquness

* We can also define our custom generation strategy
	* create an implementation of org.hibernate.id.IdentifierGenerator
	* override the method public Serializable generate(...) and add your custom implementation
	* Note : the custom generated id must be unique for every entry, should work in high-volume and multi-threaded environment

### STEP 8 : Retrieving object
* We can retrieve an object using the primary key and using a get method on the session object
* Example : 

		package com.rohitthebest.hibernate.demo;

		import org.hibernate.Session;
		import org.hibernate.SessionFactory;
		import org.hibernate.cfg.Configuration;

		import com.rohitthebest.hibernate.demo.entity.Student;

		public class ReadStudentDemo {

			public static void main(String[] args) {
		
				// create session factory
				SessionFactory sessionFactory = new Configuration()
									.configure("hibernate.cfg.xml")
									.addAnnotatedClass(Student.class)
									.buildSessionFactory();

				// create session
		
				Session session = sessionFactory.getCurrentSession();
		
				try {
			
	
					// create a student object
			
					System.out.println("Creating a new studnt object....");
			
					Student student = new Student("Nidhi", "Kumari", "nidhi@ni.com");
			
					// start a transaction
			
					session.beginTransaction();
			
					// save the student object
					System.out.println("Saving the student object");
					session.save(student);
			
					// commit transaction
			
					session.getTransaction().commit();
			
					// NEW CODE
			
					// find out the student's id : primary key
					System.out.println("Saved student. Generated Id: " + student.getId());
			
					// now get a new session and start transaction
			
					session = sessionFactory.getCurrentSession();
					session.beginTransaction();
			
					// retrieve student based on the id : primary key

					System.out.println("\nGetting student with id: " + student.getId());
			
					Student myStudent = session.get(Student.class, student.getId());
			
					System.out.println("Get complete:  " + myStudent);
			
					// commit the transaction
			
					session.getTransaction().commit();
			
					System.out.println("Done");
			
			
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					sessionFactory.close();
				}
		
			}

		}


### STEP 9 : Queruing the students table using hibernate
* we can query on student table using HQL which is known as __Hibernate Query Language__
* Below is the code implmentation for queruing the table in different ways

		package com.rohitthebest.hibernate.demo;

		import java.util.List;
		import org.hibernate.Session;
		import org.hibernate.SessionFactory;
		import org.hibernate.cfg.Configuration;
		import com.rohitthebest.hibernate.demo.entity.Student;

		public class QueryStudentDemo {

			public static void main(String[] args) {
		
				// create session factory
				SessionFactory sessionFactory = new Configuration()
									.configure("hibernate.cfg.xml")
									.addAnnotatedClass(Student.class)
									.buildSessionFactory();

				// create session
		
				Session session = sessionFactory.getCurrentSession();
		
				try {
			
	
					// start a transaction
					session.beginTransaction();
			
					/**
			 		* here we will use HQL for querying the student database
			 		* we use the class name and the property name instead of database table name and column name
			 		*/
					// query students 
					List<Student> students = session.createQuery("from Student").getResultList();
			
					// display the students
					displayStudents(students);
			
					// query the students : lastName= 'Kumar'
			
					students = session.createQuery("from Student s where s.lastName='Kumar'").getResultList();
					System.out.println("\n\nStudents who have last name as Kumar");
					displayStudents(students);
			
					// query students : lastName='Kumar' OR firstName = 'Sagar'
			
					students = session.createQuery("from Student s where"
							+ " s.lastName='Kumar' OR s.firstName='Sagar'").getResultList();
			
					System.out.println("\n\nStudents who have last name as Kumar OR first name as Sagar");
					displayStudents(students);
			
			
					// query students : email LIKE '%gmail.com'
					students = session.createQuery("from Student s where"
							+ " s.email LIKE '%gmail.com'").getResultList();
			
					System.out.println("\n\nStudents who email address is LIKE %gmail.com");
					displayStudents(students);
			
					
					// commit transaction
					session.getTransaction().commit();
			
					System.out.println("Done");
			
			
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					sessionFactory.close();
				}
		
			}

			private static void displayStudents(List<Student> students) {
		
				for(Student tempStudent : students) {
			
					System.out.println(tempStudent);
				}
			}

		}

### STEP 10 : Updating objects
* we use session.createQuery("update Student set email='foo@gmail.com' where id = 2").executeUpdate();
* Below is the implmentation

		package com.rohitthebest.hibernate.demo;

		import org.hibernate.Session;
		import org.hibernate.SessionFactory;
		import org.hibernate.cfg.Configuration;

		import com.rohitthebest.hibernate.demo.entity.Student;

		public class UpdateStudentDemo {

			public static void main(String[] args) {
		
				// create session factory
				SessionFactory sessionFactory = new Configuration()
									.configure("hibernate.cfg.xml")
									.addAnnotatedClass(Student.class)
									.buildSessionFactory();

				// create session
		
				Session session = sessionFactory.getCurrentSession();
		
				try {
			
					int studentId = 1;
			
					session.beginTransaction();
			
					// retrieve student based on the id : primary key
			
					System.out.println("\nGetting student with id: " + studentId);
			
					Student myStudent = session.get(Student.class, studentId);
			
					System.out.println("Updating student...");
			
					myStudent.setFirstName("Scooby");
			
					// commit the transaction
					session.getTransaction().commit();
			
			
					// NEW CODE
			
					session = sessionFactory.getCurrentSession();
					session.beginTransaction();
			
					// update email for all students or we can use where clause to update a specific object
					System.out.println("Update email for all students");
			
					session.createQuery("update Student set email='foo@gmail.com'")
							.executeUpdate();
			
					session.getTransaction().commit();
			
					System.out.println("Done");
			
			
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					sessionFactory.close();
				}
		
			}

		}

 
 ### STEP 11 : Deleting object
 
 * we can delete a object using session.delete(object)
 * Or we can delete using session.createQuery(delete from Student where id = 2).executeUpdate();
 * Below is the implmetation
		
		package com.rohitthebest.hibernate.demo;

		import org.hibernate.Session;
		import org.hibernate.SessionFactory;
		import org.hibernate.cfg.Configuration;

		import com.rohitthebest.hibernate.demo.entity.Student;

		public class DeleteStudentDemo {

			public static void main(String[] args) {
		
				// create session factory
				SessionFactory sessionFactory = new Configuration()
									.configure("hibernate.cfg.xml")
									.addAnnotatedClass(Student.class)
									.buildSessionFactory();

				// create session
				Session session = sessionFactory.getCurrentSession();
		
				try {
			
					int studentId = 1;
			
					session.beginTransaction();
			
					// retrieve student based on the id : primary key
			
					System.out.println("\nGetting student with id: " + studentId);
			
					Student myStudent = session.get(Student.class, studentId);
			
					// delete the student
					System.out.println("Deleting student: " + myStudent);
					session.delete(myStudent);
		
					// delete the student id =2
					System.out.println("Deleting student with id =2");
					session.createQuery("delete from Student where id =2").executeUpdate();
			
					// commit the transaction
					session.getTransaction().commit();

					System.out.println("Done");
			
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					sessionFactory.close();
				}
		
			}

		}

### For more advance hibernate switch to https://github.com/rkumar0206/hibernate_with_spring/tree/main-2 branch
