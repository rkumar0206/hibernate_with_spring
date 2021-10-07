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
