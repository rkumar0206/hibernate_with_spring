# hibernate_with_spring
more advance features with hibernate

### One-to-One-uni relationship 

* It’s a relationship where a record in one entity (table) is associated with exactly one record in another entity (table).
* We use ForeignKey to connect two tables for one-to-one relationship
  
  * ForeignKey : It is the primary key of one table which is saved as a column in another table. And therfore the table can have one-to-one relationship.

* Observe the below tables

<img src="https://github.com/rkumar0206/hibernate_with_spring/blob/main-2/img/Screenshot%20(1211).png" height="250"/>
 
Here there are two tables, i.e. Instructor and InstructorDetail. We can see that the primary key (ID) of the instructor_detail table is stored in the Instructor table and therefore they are related to each other by one-to-one relationship.

For joining two tables for one-to-one relationship using hibernate we can make use of annotations in entity classes.

* @OneToOne(cascade = CascadeType.ALL) :  This annotation is provided on the top on the property you want to store as a ForeignKey. 
  * Cascade : using this as a parameter any operation done on the Instructor table will also be done on the InstructorDetail table. For example : if we insert one Instructor object in the database, INstructorDetail object also be inserted in the database, and likewise it will happen for delete.

* @JoinColumn(name="instructor_detail") : It is used for decalring which property has to be used for joining the tables.
* Below is the code sample :
```java
      package com.rohitThebest.hibernate.demo.entity;

      import javax.persistence.CascadeType;
      import javax.persistence.Column;
      import javax.persistence.Entity;
      import javax.persistence.GeneratedValue;
      import javax.persistence.GenerationType;
      import javax.persistence.Id;
      import javax.persistence.JoinColumn;
      import javax.persistence.OneToOne;
      import javax.persistence.Table;

      @Entity
      @Table(name = "instructor")
      public class Instructor {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private int id;
	
	    @Column(name = "first_name")
	    private String firstName;
	
	    @Column(name = "last_name")
	    private String lastName;
	
	    @Column(name = "email")
	    private String email;
	
	    // set up mapping to InstrictorDetail entity
	    /**
	    * Setting one to one relationship between instructor and the instructor detail
	    * cascade : cascade means the operation done on Instructor table will also be done
	    * on the InstructorDetail table
	    * 
	    * CascadeType.ALl : It means that all the operations done on Instructor table
      *  i.e save, update , delete, etc. will also be done on the InstructorDetail table 
	    */
	    @OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "instructor_detail_id")
	    private InstructorDetail instructorDetail;
	
	    public Instructor() {}

	    public Instructor(String firstName, String lastName, String email) {
		    super();
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

	    public InstructorDetail getInstructorDetail() {
		    return instructorDetail;
	    }

	    public void setInstructorDetail(InstructorDetail instructorDetail) {
		    this.instructorDetail = instructorDetail;
	    }

	    @Override
	    public String toString() {
		    return "Instructor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				    + ", instructorDetail=" + instructorDetail + "]";
	      }
	
      }
      
```


### CreateDemo

```java

package com.rohitThebest.hibernate.demo;

	import org.hibernate.Session;
	import org.hibernate.SessionFactory;
	import org.hibernate.cfg.Configuration;
	import com.rohitThebest.hibernate.demo.entity.Instructor;
	import com.rohitThebest.hibernate.demo.entity.InstructorDetail;

	public class CreateDemo {

		public static void main(String[] args) {
		
			// create session factory
			SessionFactory sessionFactory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.buildSessionFactory();

			// create session
			Session session = sessionFactory.getCurrentSession();
		
			try {

				// create the objects
				Instructor instructor = 
						new Instructor("Rohit", "Kumar", "rohit@best.com");
				
				InstructorDetail instructorDetail = 
						new InstructorDetail(
								"http://www.rohit.com/youtube",
								"programming");
						
				// associate the objects
				instructor.setInstructorDetail(instructorDetail);
				
				
				// start a transaction
				session.beginTransaction();
				
				
				// save the instructor
				//
				// Note : this will also save the instructorDetails object
				// because if CascadeType.ALL
				System.out.println("Saving instructor: " + instructor);
				session.save(instructor);
				
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

```

### Delete Demo

```java

	package com.rohitThebest.hibernate.demo;

	import org.hibernate.Session;
	import org.hibernate.SessionFactory;
	import org.hibernate.cfg.Configuration;
	import com.rohitThebest.hibernate.demo.entity.Instructor;
	import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
	
	public class DeleteDemo {
	
		public static void main(String[] args) {
			
			// create session factory
			SessionFactory sessionFactory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.buildSessionFactory();
	
			// create session
			Session session = sessionFactory.getCurrentSession();
			
			try {
	
			
				// start a transaction
				session.beginTransaction();
			
				
				// get instructor by primary key / id
				int theId = 1;
				
				Instructor instructor = session.get(Instructor.class, theId);
				
				System.out.println("Found Instructor: " + instructor);
				
				// delete the instructor
			
				if (instructor != null) {
					
					System.out.println("Deleting: " + instructor);
					
					//Note : it will also delete instructor detail object associated with 
					// this object because of CascadeType.ALL
					//
					session.delete(instructor);
				}
				
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
```

### For one-to-one (bi-directional) switch to https://github.com/rkumar0206/hibernate_with_spring/tree/main-3 branch

