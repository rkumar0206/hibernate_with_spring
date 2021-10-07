# hibernate_with_spring

### Eager Vs Lazy Loading

* The __Lazy__ Fetch type is by default selected by Hibernate unless you explicitly mark Eager Fetch type. To be more accurate and concise, difference can be stated as below.

* __FetchType.LAZY__: This does not load the relationships unless you invoke it via the getter method.

* __FetchType.EAGER__: This loads all the relationships.

#### Pros and Cons of these two fetch types.

* __Lazy__ initialization improves performance by avoiding unnecessary computation and reduce memory requirements.

* __Eager__ initialization takes more memory consumption and processing speed is slow.

#### Continuing our previous example of Instructor and courses, lets change the fetch type to __Eager__ in __Instructor.java__

##### Instructor.java
        package com.rohitThebest.hibernate.demo.entity;
        
        import java.util.ArrayList;
        import java.util.List;
        
        import javax.persistence.CascadeType;
        import javax.persistence.Column;
        import javax.persistence.Entity;
        import javax.persistence.FetchType;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;
        import javax.persistence.JoinColumn;
        import javax.persistence.OneToMany;
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
        
        	@OneToOne(cascade = CascadeType.ALL)
        	@JoinColumn(name = "instructor_detail_id")
        	private InstructorDetail instructorDetail;
        
        	/**
        	 * FetchType.LAZY = This does not load the relationships unless 
        	 * you invoke it via the getter method.
        	 *
        	 * FetchType.EAGER = This loads all the relationships.
        	 * 
        	 * Lazy initialization improves performance by avoiding unnecessary 
        	 * computation and reduce memory requirements.
        	 * 
        	 * Eager initialization takes more memory consumption and processing speed is slow.
        	 */
        	// here instructor refers to instructor property in Course class
        	@OneToMany(fetch = FetchType.EAGER , mappedBy = "instructor", 
        			cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,
        			CascadeType.REFRESH })
        	private List<Course> courses;
        
        	public Instructor() {
        	}
        
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
        
        	public List<Course> getCourses() {
        		return courses;
        	}
        
        	public void setCourses(List<Course> courses) {
        		this.courses = courses;
        	}
        
        	@Override
        	public String toString() {
        		return "Instructor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
        				+ ", instructorDetail=" + instructorDetail + "]";
        	}
        
        	// add convenience methods for Bi-directional relationship
        	
        	public void add(Course tempCourse) {
        		
        		if (courses == null) {
        			
        			courses = new ArrayList<>();
        		}
        		
        		courses.add(tempCourse);
        		
        		tempCourse.setInstructor(this);
        		
        	}
        	
        }

##### EagerLazyDemo.java
        package com.rohitThebest.hibernate.demo;
        
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;
        
        import com.rohitThebest.hibernate.demo.entity.Course;
        import com.rohitThebest.hibernate.demo.entity.Instructor;
        import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
        
        public class EagerLazyDemo {
        
        	public static void main(String[] args) {
        		
        		// create session factory
        		SessionFactory sessionFactory = new Configuration()
        							.configure("hibernate.cfg.xml")
        							.addAnnotatedClass(Instructor.class)
        							.addAnnotatedClass(InstructorDetail.class)
        							.addAnnotatedClass(Course.class)
        							.buildSessionFactory();
        
        		// create session
        		Session session = sessionFactory.getCurrentSession();
        		
        		try {
        
        			// start a transaction
        			session.beginTransaction();
        
        			// get the instructor from db
        			int id = 1;
        			/**
        			 * Due to eager loading hibernate will fetch all the data in here, using
        			 * session.get(). 
        			 */
        			Instructor instructor = session.get(Instructor.class, id);
        			
        			System.out.println("rohitThebest : Instructor: " + instructor);
        			
        			// get course for the instructor
        			System.out.println("rohitThebest : Courses: " + instructor.getCourses());
        			
        			// commit transaction
        			session.getTransaction().commit();
        			
        			System.out.println("Done saving");
        			
        		}catch (Exception e) {
        			e.printStackTrace();
        		}finally {
        			
        			session.close();
        			sessionFactory.close();
        		}
        	}
        }

### Observe the output
<img src="https://github.com/rkumar0206/hibernate_with_spring/blob/main-5/img/eager_loading.PNG" />

* here when hibernate is running the query only once and fetching all the details of courses as well, without calling getter method for getCourses().
* Therefore, it is an eager loading

#### Now let's change the fetch type from eager to lazy in __Instructor.java__
	// here instructor refers to instructor property in Course class
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "instructor", 
			cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.REFRESH })
	private List<Course> courses;

### Now again observe the output
<img src="https://github.com/rkumar0206/hibernate_with_spring/blob/main-5/img/lazy_loading.png" />

* here we can sess that hibernate is running the query two times, one for getching the Instructor and InstructorDetail and second one for fetching the courses i.e. when getCourses() method is called.
* Therefore, it is a lazy loading, the content is loading only when the getter method is called.
* By default hibernate uses lazy loading, so we don't need to worry about that.

---

Now let's say we close the session before calling instructor.getCourse(). What will happen? Will the code run? 

			
	// commit transaction
	session.getTransaction().commit();
	
	// close the session
	session.close();
	
	// since it's a lazy loading, ... this should fail because hibernate will
	// not be able to access the session as it is closed
	// get course for the instructor
	System.out.println("rohitThebest : Courses: " + instructor.getCourses());

	
	System.out.println("Done saving");
			
* here it will throw __org.hibernate.LazyInitializationException__ as we are closing the session and then calling the instructor.getCourses() and because it is a lazy loading hibernate will try to fetch the data then and there, and will fail.

#### now let's try to resolve this issue

### Option 1
One way to resolve this problem is that we call the __instructor.getCourses()__ once before closing the session, and then if we try to call the that method again even after closing the session it will execute and will not throw any error.

	package com.rohitThebest.hibernate.demo;
	
	import org.hibernate.Session;
	import org.hibernate.SessionFactory;
	import org.hibernate.cfg.Configuration;
	
	import com.rohitThebest.hibernate.demo.entity.Course;
	import com.rohitThebest.hibernate.demo.entity.Instructor;
	import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
	
	public class EagerLazyDemo {
	
		public static void main(String[] args) {
			
			// create session factory
			SessionFactory sessionFactory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)
								.buildSessionFactory();
	
			// create session
			Session session = sessionFactory.getCurrentSession();
			
			try {
	
				// start a transaction
				session.beginTransaction();
	
				// get the instructor from db
				int id = 1;
				Instructor instructor = session.get(Instructor.class, id);
				
				System.out.println("rohitThebest : Instructor: " + instructor);
				
				
				/**
				 * Here we are calling instructor.getCourses() once before closing 
				 * the session and then if we call this method again after closing
				 * the session, it will not throw any exception.
				 */
				System.out.println("rohitThebest : Courses: " + instructor.getCourses());
				
				// commit transaction
				session.getTransaction().commit();
				
				// close the session
				session.close();
				
				System.out.println("\nrohitThebest : the session is closed\n");
				
				// get course for the instructor
				System.out.println("rohitThebest : Courses: " + instructor.getCourses());
	
				System.out.println("Done saving");
				
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				
				session.close();
				sessionFactory.close();
			}
		}
	}

