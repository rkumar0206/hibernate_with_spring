# hibernate_with_spring

### Many-To-Many relationship :

* Let's continue our previous example of Instructor and add a new table for __students__.
* Now each courses can have many students and each student can have many courses. Therefore, there is a many-to-many relationship between the __course__ and the __student__.
* Now for joining these two tables for having many-to-many relationship, there will be another join table called __course_student__ table. 
  * This table will contain the __id__ of both the student and the course table.

### How to join table for many-to-many relationship in __Hibernate__?

* First of all, let's have look at the below code

  * ##### Course.java
	    @ManyToMany(fetch = FetchType.LAZY ,cascade = {CascadeType.MERGE, CascadeType.PERSIST,
	    		CascadeType.REFRESH, CascadeType.REFRESH})
	    @JoinTable(
	    		name = "course_student",
	    		joinColumns = @JoinColumn(name= "course_id"),
	    		inverseJoinColumns = @JoinColumn(name = "student_id")
	    		)
	    private List<Student> students;
  
  * ##### Student.java
          @ManyToMany(fetch = FetchType.LAZY ,cascade = {CascadeType.MERGE, CascadeType.PERSIST,
	        		CascadeType.REFRESH, CascadeType.REFRESH})
	        @JoinTable(
	        		name = "course_student",
	        		joinColumns = @JoinColumn(name = "student_id"),
	        		inverseJoinColumns = @JoinColumn(name = "course_id")
	        		)
	        private List<Course> courses;

* Now let's try to understand the code, 
  * first we annotated it with __@ManyToMany()__ and made the cacade type for __All__ except for _delete_, as when a course is deleted, students must not be deleted and vice versa.
  * __@JoinTable() : 
    * A join table is typically used in the mapping of many-to-many and unidirectional one-to-many associations.
      It may also be used to map bidirectional many-to-one/one-to-many associations, unidirectional many-to-one relationships,
      and one-to-one associations (both bidirectional and unidirectional).
    * Here you must have observed the two parameters __joinColumns__ and __inverseJoinColumns__ and the values passed there for both Student and Course.
      * For the __Course.java__ class the first column should be the _course_id_ and the other side i.e. the inverse side should be the _student_id_. Therefore, the parameter
        __joinColumns__ in @JoinTable() is for the first column and __inverseJoinColumns__ is for other column.
      * Similarly, for __Student.java__ class the first column is _student_id__ and the inverse colum is _course_id_.

Below are the full code implmentations :

##### Course.java
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
        import javax.persistence.JoinTable;
        import javax.persistence.ManyToMany;
        import javax.persistence.ManyToOne;
        import javax.persistence.OneToMany;
        import javax.persistence.Table;
        
        @Entity
        @Table(name = "course")
        public class Course {
        
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
        	
        	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        	@JoinColumn(name = "course_id")
        	private List<Review> reviews;
        	
        	
        	/**
        	 * JoinTable is used for joining two tables by using there primary keys.
        	 * Here, the name of the table is course_student.
        	 * 
        	 * And for this Course class first side (or first column) is course_id column
        	 * and other side (or inverse side) is student_id column
        	 */
        	@ManyToMany(fetch = FetchType.LAZY ,cascade = {CascadeType.MERGE, CascadeType.PERSIST,
        			CascadeType.REFRESH, CascadeType.REFRESH})
        	@JoinTable(
        			name = "course_student",
        			joinColumns = @JoinColumn(name= "course_id"),
        			inverseJoinColumns = @JoinColumn(name = "student_id")
        			)
        	private List<Student> students;
        	
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
        
        	public List<Review> getReviews() {
        		return reviews;
        	}
        
        	public void setReviews(List<Review> reviews) {
        		this.reviews = reviews;
        	}
        	
        	public List<Student> getStudents() {
        		return students;
        	}
        
        	public void setStudents(List<Student> students) {
        		this.students = students;
        	}
        
        
        	// add convenience method
        
        	public void addStudent(Student student) {
        		
        		if (students == null) {
        			
        			students = new ArrayList<Student>();
        		}
        		
        		students.add(student);
        	}
        	
        	public void addReview(Review review) {
        		
        		if (reviews == null) {
        			
        			reviews = new ArrayList<>();
        		}
        		
        		reviews.add(review);
        		
        	}
        	
        	
        	@Override
        	public String toString() {
        		return "Course [id=" + id + ", title=" + title + "]";
        	}
        
        }

##### Student.java

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
        import javax.persistence.JoinTable;
        import javax.persistence.ManyToMany;
        import javax.persistence.ManyToOne;
        import javax.persistence.OneToMany;
        import javax.persistence.Table;
        
        @Entity
        @Table(name = "course")
        public class Course {
        
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
        	
        	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        	@JoinColumn(name = "course_id")
        	private List<Review> reviews;
        	
        	
        	/**
        	 * JoinTable is used for joining two tables by using there primary keys.
        	 * Here, the name of the table is course_student.
        	 * 
        	 * And for this Course class first side (or first column) is course_id column
        	 * and other side (or inverse side) is student_id column
        	 */
        	@ManyToMany(fetch = FetchType.LAZY ,cascade = {CascadeType.MERGE, CascadeType.PERSIST,
        			CascadeType.REFRESH, CascadeType.REFRESH})
        	@JoinTable(
        			name = "course_student",
        			joinColumns = @JoinColumn(name= "course_id"),
        			inverseJoinColumns = @JoinColumn(name = "student_id")
        			)
        	private List<Student> students;
        	
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
        
        	public List<Review> getReviews() {
        		return reviews;
        	}
        
        	public void setReviews(List<Review> reviews) {
        		this.reviews = reviews;
        	}
        	
        	public List<Student> getStudents() {
        		return students;
        	}
        
        	public void setStudents(List<Student> students) {
        		this.students = students;
        	}
        
        
        	// add convenience method
        
        	public void addStudent(Student student) {
        		
        		if (students == null) {
        			
        			students = new ArrayList<Student>();
        		}
        		
        		students.add(student);
        	}
        	
        	public void addReview(Review review) {
        		
        		if (reviews == null) {
        			
        			reviews = new ArrayList<>();
        		}
        		
        		reviews.add(review);
        		
        	}
        	
        	
        	@Override
        	public String toString() {
        		return "Course [id=" + id + ", title=" + title + "]";
        	}
        	
        }

##### CreateCoursesAndStudentsDemo
        package com.rohitThebest.hibernate.demo;
        
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;
        
        import com.rohitThebest.hibernate.demo.entity.Course;
        import com.rohitThebest.hibernate.demo.entity.Instructor;
        import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
        import com.rohitThebest.hibernate.demo.entity.Review;
        import com.rohitThebest.hibernate.demo.entity.Student;
        
        public class CreateCoursesAndStudentsDemo {
        
        	public static void main(String[] args) {
        
        		// create session factory
        		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
        				.addAnnotatedClass(Instructor.class)
        				.addAnnotatedClass(InstructorDetail.class)
        				.addAnnotatedClass(Course.class)
        				.addAnnotatedClass(Review.class)
        				.addAnnotatedClass(Student.class)
        				.buildSessionFactory();
        
        		// create session
        		Session session = sessionFactory.getCurrentSession();
        
        		try {
        
        			// start a transaction
        			session.beginTransaction();
        
        			// create a course
        			Course course = new Course("Pacman - How to score one million points");
        			session.save(course);
        			System.out.println("Saved the course : " + course);
        
        			// create the students
        			Student student1 = new Student("John", "Kumar", "jhon@gg.com");
        			Student student2 = new Student("Mary", "Kumari", "mary@mk.com");
        			
        			// add students to the course
        			course.addStudent(student1);
        			course.addStudent(student2);
        			
        			// save the students
        			System.out.println("\nSaving students...");
        			
        			session.save(student1);
        			session.save(student2);
        			
        			System.out.println("Saved students : " + course.getStudents());
        			
        			// commit transaction
        			session.getTransaction().commit();
        
        			System.out.println("Done saving");
        
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        
        			session.close();
        			sessionFactory.close();
        		}
        	}
        }
##### AddCoursesForMaryDemo.java
        package com.rohitThebest.hibernate.demo;
        
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;
        
        import com.rohitThebest.hibernate.demo.entity.Course;
        import com.rohitThebest.hibernate.demo.entity.Instructor;
        import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
        import com.rohitThebest.hibernate.demo.entity.Review;
        import com.rohitThebest.hibernate.demo.entity.Student;
        
        public class AddCoursesForMaryDemo {
        
        	public static void main(String[] args) {
        
        		// create session factory
        		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
        				.addAnnotatedClass(Instructor.class)
        				.addAnnotatedClass(InstructorDetail.class)
        				.addAnnotatedClass(Course.class)
        				.addAnnotatedClass(Review.class)
        				.addAnnotatedClass(Student.class)
        				.buildSessionFactory();
        
        		// create session
        		Session session = sessionFactory.getCurrentSession();
        
        		try {
        
        			// start a transaction
        			session.beginTransaction();
        
        			// get the student Mary from the database
        			int id = 2;
        			Student student = session.get(Student.class, id);
        			System.out.println("\nLoaded studentL " + student);
        			System.out.println("Courses : " + student.getCourses());
        			
        			// create more courses
        			Course course1 = new Course("Rubik's cube - How to speed cube");
        			Course course2 = new Course("Atari 2600 - Game development");
        			
        			// add student to courses
        			course1.addStudent(student);
        			course2.addStudent(student);
        			
        			// save the courses
        			System.out.println("\nSaving the courses...");
        			session.save(course1);
        			session.save(course2);
        			
        			// commit transaction
        			session.getTransaction().commit();
        
        			System.out.println("Done saving");
        
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        
        			session.close();
        			sessionFactory.close();
        		}
        	}
        }

##### GetCoursesForMaryDemo.java

        package com.rohitThebest.hibernate.demo;
        
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;
        
        import com.rohitThebest.hibernate.demo.entity.Course;
        import com.rohitThebest.hibernate.demo.entity.Instructor;
        import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
        import com.rohitThebest.hibernate.demo.entity.Review;
        import com.rohitThebest.hibernate.demo.entity.Student;
        
        public class GetCoursesForMaryDemo {
        
        	public static void main(String[] args) {
        
        		// create session factory
        		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
        				.addAnnotatedClass(Instructor.class)
        				.addAnnotatedClass(InstructorDetail.class)
        				.addAnnotatedClass(Course.class)
        				.addAnnotatedClass(Review.class)
        				.addAnnotatedClass(Student.class)
        				.buildSessionFactory();
        
        		// create session
        		Session session = sessionFactory.getCurrentSession();
        
        		try {
        
        			// start a transaction
        			session.beginTransaction();
        
        			// get the student Mary from the database
        			int id = 2;
        			Student student = session.get(Student.class, id);
        			System.out.println("\nLoaded student " + student);
        			System.out.println("Courses : " + student.getCourses());
        			
        			
        			// commit transaction
        			session.getTransaction().commit();
        
        			System.out.println("Done");
        
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        
        			session.close();
        			sessionFactory.close();
        		}
        	}
        }

##### DeletePackmanCourseDemo.java
        package com.rohitThebest.hibernate.demo;
        
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;
        
        import com.rohitThebest.hibernate.demo.entity.Course;
        import com.rohitThebest.hibernate.demo.entity.Instructor;
        import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
        import com.rohitThebest.hibernate.demo.entity.Review;
        import com.rohitThebest.hibernate.demo.entity.Student;
        
        public class DeletePackmanCourseDemo {
        
        	public static void main(String[] args) {
        
        		// create session factory
        		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
        				.addAnnotatedClass(Instructor.class)
        				.addAnnotatedClass(InstructorDetail.class)
        				.addAnnotatedClass(Course.class)
        				.addAnnotatedClass(Review.class)
        				.addAnnotatedClass(Student.class)
        				.buildSessionFactory();
        
        		// create session
        		Session session = sessionFactory.getCurrentSession();
        
        		try {
        
        			// start a transaction
        			session.beginTransaction();
        
        			// get the packman course from db
        			
        			int courseId = 10;
        			Course course = session.get(Course.class, courseId);
        			
        			// delete the course
        			System.out.println("Deleting course..");
        			session.delete(course);
        			
        			// commit transaction
        			session.getTransaction().commit();
        
        			System.out.println("Done");
        
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        
        			session.close();
        			sessionFactory.close();
        		}
        	}
        }

#####   DeleteMaryStudentDemo.java

        package com.rohitThebest.hibernate.demo;
        
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;
        
        import com.rohitThebest.hibernate.demo.entity.Course;
        import com.rohitThebest.hibernate.demo.entity.Instructor;
        import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
        import com.rohitThebest.hibernate.demo.entity.Review;
        import com.rohitThebest.hibernate.demo.entity.Student;
        
        public class DeleteMaryStudentDemo {
        
        	public static void main(String[] args) {
        
        		// create session factory
        		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
        				.addAnnotatedClass(Instructor.class)
        				.addAnnotatedClass(InstructorDetail.class)
        				.addAnnotatedClass(Course.class)
        				.addAnnotatedClass(Review.class)
        				.addAnnotatedClass(Student.class)
        				.buildSessionFactory();
        
        		// create session
        		Session session = sessionFactory.getCurrentSession();
        
        		try {
        
        			// start a transaction
        			session.beginTransaction();
        
        			// get the student Mary from the database
        			int id = 2;
        			Student student = session.get(Student.class, id);
        			System.out.println("\nLoaded student " + student);
        			System.out.println("Courses : " + student.getCourses());
        			
        			// delete student
        			System.out.println("\nDeleting student: " + student);
        			session.delete(student);
        
        			// commit transaction
        			session.getTransaction().commit();
        
        			System.out.println("Done");
        
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        
        			session.close();
        			sessionFactory.close();
        		}
        	}
        }
