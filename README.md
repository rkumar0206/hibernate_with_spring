# hibernate_with_spring

### One-To-Many (Uni-directional)

* Continuing our prvious example ... let's say there is an another table called __Review__. Now a __course__ can have many reviews, therefore there will be a one-to-many relationship between the __course__ and the __review__.
* The cascade type for this will be CascadeType.ALL, as, when we delete a course all the reviews related to it must also be deleted.
* Below are the code implmetations :

##### Review.java
        package com.rohitThebest.hibernate.demo.entity;
        
        import javax.persistence.Column;
        import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;
        import javax.persistence.Table;
        
        @Entity
        @Table(name = "review")
        public class Review {
               	
        	@Id
        	@GeneratedValue(strategy = GenerationType.IDENTITY)
        	@Column(name = "id")
        	private int id;
        	
        	@Column(name = "comment")
        	private String comment;
        	
        	public Review() {
        	}
        
        	public Review(String comment) {
        		super();
        		this.comment = comment;
        	}
        
        	public int getId() {
        		return id;
        	}
        
        	public void setId(int id) {
        		this.id = id;
        	}
        
        	public String getComment() {
        		return comment;
        	}
        
        	public void setComment(String comment) {
        		this.comment = comment;
        	}
        
        	@Override
        	public String toString() {
        		return "Review [id=" + id + ", comment=" + comment + "]";
        	}
        
        }


##### Updated Course.java

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
        import javax.persistence.ManyToOne;
        import javax.persistence.OneToMany;
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
        	
        	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        	@JoinColumn(name = "course_id")
        	private List<Review> reviews;
        	
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
        
        	// add convenience method
        	
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
    
* Here we have added a field for reviews and annotated it with @OneToMany()
* __Note:__ here in @JoinColumn(name = "course_id") we have used a parameter name course_id which is not in the Review.java class. But it is in the __review__ table.
  * So, how @JoinColumn() knows where to find the join column.
  * The JoinColumn is actually fairly complex and it goes through a number of advanced steps to find the desired column.
  * The table in which it is found depends upon the context.
     * If the join is for a OneToOne or ManyToOne mapping using a foreign key mapping strategy, the foreign key column is in the table of the source entity or embeddable.
     * If the join is for a unidirectional OneToMany mapping using a foreign key mapping strategy, the foreign key is in the table of the target entity.
     * If the join is for a ManyToMany mapping or for a OneToOne or bidirectional ManyToOne/OneToMany mapping using a join table, the foreign key is in a join table.
     * If the join is for an element collection, the foreign key is in a collection table.
     * So as you can see, it depends on the context.
     * Here, we are using @OneToMany uni-directional (course has one-to-many reviews).
     * As a result, the join column / foreign key column is in the target entity. In this case, the target entity is the Review class. So, you will find the join column "course_id" in the "review" table.

##### CreateCoursesAndReviewsDemo.java
        package com.rohitThebest.hibernate.demo;
        
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;
        
        import com.rohitThebest.hibernate.demo.entity.Course;
        import com.rohitThebest.hibernate.demo.entity.Instructor;
        import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
        import com.rohitThebest.hibernate.demo.entity.Review;
        
        public class CreateCoursesAndReviewsDemo {
        
        	public static void main(String[] args) {
        
        		// create session factory
        		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
        				.addAnnotatedClass(Instructor.class)
        				.addAnnotatedClass(InstructorDetail.class)
        				.addAnnotatedClass(Course.class)
        				.addAnnotatedClass(Review.class)
        				.buildSessionFactory();
        
        		// create session
        		Session session = sessionFactory.getCurrentSession();
        
        		try {
        
        			// start a transaction
        			session.beginTransaction();
        
        			// create a course
        
        			Course course = new Course("Pacman - How to score one million points");
        
        			// add some reviews
        			course.addReview(new Review("Great course .. loved it!"));
        			course.addReview(new Review("Cool course, job well done"));
        			course.addReview(new Review("What a dub course, you are an idiot"));
        
        			// save the course .. and leverage the cascade all
        
        			System.out.println("Saving the course...");
        			System.out.println(course);
        			System.out.println(course.getReviews());
        
        			session.save(course);
        
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

##### GetCoursesAndReviewsDemo.java
        package com.rohitThebest.hibernate.demo;
        
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;
        
        import com.rohitThebest.hibernate.demo.entity.Course;
        import com.rohitThebest.hibernate.demo.entity.Instructor;
        import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
        import com.rohitThebest.hibernate.demo.entity.Review;
        
        public class GetCoursesAndReviewsDemo {
        
        	public static void main(String[] args) {
        
        		// create session factory
        		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
        				.addAnnotatedClass(Instructor.class)
        				.addAnnotatedClass(InstructorDetail.class)
        				.addAnnotatedClass(Course.class)
        				.addAnnotatedClass(Review.class)
        				.buildSessionFactory();
        
        		// create session
        		Session session = sessionFactory.getCurrentSession();
        
        		try {
        
        			// start a transaction
        			session.beginTransaction();
        
        			// get the course
        			
        			int id = 10;
        			Course course = session.get(Course.class, id);
        			
        			// print the course
        			System.out.println(course);
        			
        			// print the course reviews
        			System.out.println(course.getReviews());
        			
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

##### DeleteCoursesAndReviewsDemo.java
        package com.rohitThebest.hibernate.demo;
        
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;
        
        import com.rohitThebest.hibernate.demo.entity.Course;
        import com.rohitThebest.hibernate.demo.entity.Instructor;
        import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
        import com.rohitThebest.hibernate.demo.entity.Review;
        
        public class DeleteCoursesAndReviewsDemo {
        
        	public static void main(String[] args) {
        
        		// create session factory
        		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
        				.addAnnotatedClass(Instructor.class)
        				.addAnnotatedClass(InstructorDetail.class)
        				.addAnnotatedClass(Course.class)
        				.addAnnotatedClass(Review.class)
        				.buildSessionFactory();
        
        		// create session
        		Session session = sessionFactory.getCurrentSession();
        
        		try {
        
        			// start a transaction
        			session.beginTransaction();
        
        			// get the course	
        			int id = 10;
        			Course course = session.get(Course.class, id);
        			
        			// print the course
        			System.out.println(course);
        			
        			// print the course reviews
        			System.out.println(course.getReviews());
        			
        			
        			// delete the course
        			System.out.println("Deleting the course...");
        			// this will delete the reviews related to this course because of CacadeType.ALL
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

