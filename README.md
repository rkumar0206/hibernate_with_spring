# hibernate_with_spring

### OneToMany (Bi-Directinal)

* Continuing our previous example, let's say we have one more table named __Course__, now here consider a __instructor__ can have many __courses__, but a __course__ can have onle one instructor.
* Therefore, in this scenario there will be a one to many relationship between the instructor and the course.
* In hibernate we can use annotations to define a oneTomany relationship.

* Below are the code inplementations

##### Course.java
        package com.rohitThebest.hibernate.demo.entity;
        
        import javax.persistence.CascadeType;
        import javax.persistence.Column;
        import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;
        import javax.persistence.JoinColumn;
        import javax.persistence.ManyToOne;
        import javax.persistence.Table;
        
        @Entity
        @Table(name = "course")
        public class Course {
        
        	// define our fields
        	
        	// define constructors
        	
        	// define getters/ setters
        	
        	// define toString
        	
        	// annotate fields
        	
        	@Id
        	@GeneratedValue(strategy = GenerationType.IDENTITY)
        	@Column(name = "id")
        	private int id;
        	
        	@Column(name = "title")
        	private String title;
        	
        	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
        			CascadeType.REFRESH, CascadeType.REFRESH})
        	@JoinColumn(name = "instructor_id")
        	private Instructor instructor;
        	
        	public Course() {
        
        	}
        
        	public Course(String title) {
        		super();
        		this.title = title;
        	}
        
        	public int getId() {
        		return id;
        	}
        
        	public void setId(int id) {
        		this.id = id;
        	}
        
        	public String getTitle() {
        		return title;
        	}
        
        	public void setTitle(String title) {
        		this.title = title;
        	}
        
        	public Instructor getInstructor() {
        		return instructor;
        	}
        
        	public void setInstructor(Instructor instructor) {
        		this.instructor = instructor;
        	}
        
        	@Override
        	public String toString() {
        		return "Course [id=" + id + ", title=" + title + "]";
        	}
        	
        }
        
##### Instructor.java
        package com.rohitThebest.hibernate.demo.entity;
        
        import java.util.ArrayList;
        import java.util.List;
        
        import javax.persistence.CascadeType;
        import javax.persistence.Column;
        import javax.persistence.Entity;
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
        
        	// set up mapping to InstrictorDetail entity
        	/**
        	 * Setting one to one relationship between instructor and the instructor detail
        	 * cascade : cascade means the operation done on Instructor table will also be
        	 * done on the InstructorDetail table
        	 * 
        	 * CascadeType.ALl : It means that all the operations done on Instructor table
        	 * i.e save, update , delete, etc. will also be done on the InstructorDetail
        	 * table
        	 */
        	@OneToOne(cascade = CascadeType.ALL)
        	@JoinColumn(name = "instructor_detail_id")
        	private InstructorDetail instructorDetail;
        
        	/**
        	 * @OneToMany means this instructor can have many courses and is mapped by
        	 * instructor property in Course class
        	 * 
        	 * Also it will have all the cascade types but not for deleting or removing
        	 */
        	// here instructor refers to instructor property in Course class
        	@OneToMany(mappedBy = "instructor", 
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

#### CreateCoursesDemo.java
        package com.rohitThebest.hibernate.demo;
        
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;
        
        import com.rohitThebest.hibernate.demo.entity.Course;
        import com.rohitThebest.hibernate.demo.entity.Instructor;
        import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
        
        public class CreateCoursesDemo {
        
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
        			
        			// create some courses
        			Course tempCourse1 = new Course("Air Guitar - The Ultimate Guide");
        			Course tempCourse2 = new Course("The Pinball Masterclass");
        			
        			// add courses to instructor
        			instructor.add(tempCourse1);
        			instructor.add(tempCourse2);
        			
        			
        			// save the courses
        			session.save(tempCourse1);
        			session.save(tempCourse2);
        			
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

##### GetInstructorCoursesDemo.java
        package com.rohitThebest.hibernate.demo;
        
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;
        
        import com.rohitThebest.hibernate.demo.entity.Course;
        import com.rohitThebest.hibernate.demo.entity.Instructor;
        import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
        
        public class GetInstructorCoursesDemo {
        
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
        			
        			System.out.println("Instructor: " + instructor);
        			
        			// get course for the instructor
        			System.out.println("Courses: " + instructor.getCourses());
        			
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

##### DeleteCourseDemo.java
        package com.rohitThebest.hibernate.demo;
        
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;
        
        import com.rohitThebest.hibernate.demo.entity.Course;
        import com.rohitThebest.hibernate.demo.entity.Instructor;
        import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
        
        public class DeleteCourseDemo {
        
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
        
        			// get the course 
        			int id = 10;
        			Course course = session.get(Course.class, id);
        			
        			// delete course
        			//Note : It will only delete the course and not the instructor 
        			// because of cascade type we set up
        			session.delete(course);
        			
        			// commit transaction
        			session.getTransaction().commit();
        			
        			System.out.println("Done deleting");
        			
        		}catch (Exception e) {
        			e.printStackTrace();
        		}finally {
        			
        			session.close();
        			sessionFactory.close();
        		}
        	}
        }

### For Eager vs Lazy Loading switch to https://github.com/rkumar0206/hibernate_with_spring/tree/main-5 branch

