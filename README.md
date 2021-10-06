# hibernate_with_spring

### One-To-One (Bi-directional) :
* Recently we used One-To-One relationship as uni-directinal as we can get the InstructorDetail object from Instructor object but we were unable to get the Instructor object from
the InstructorDetail object. So for making it bi-directional we need to have something by which we can retrive Instructor from InstructorDetail.

* We use @OneToOne(mappedBy="property_name") : Using this we don't need to make any changes to the database and we can retrive the Instructor using the InstructorDetail object.
mappedBy parameter is used for defining the property name (column name) by which it has been declared in another table.

* Below is the code implmetation of InstructorDetail class

        package com.rohitThebest.hibernate.demo.entity;
        
        import javax.persistence.CascadeType;
        import javax.persistence.Column;
        import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;
        import javax.persistence.OneToOne;
        import javax.persistence.Table;
        
        @Entity
        @Table(name = "instructor_detail")
        public class InstructorDetail {
        
        	// annotate the class as an entity and map to db table
        	
        	// define the fields
        	
        	// annotate the fields with db column names
        	
        	// create constructor
        	
        	// generate getters / setters 
        	
        	// generate toString() method
        
        	@Id
        	@GeneratedValue(strategy = GenerationType.IDENTITY)
        	@Column(name = "id")
        	private int id;
        	
        	@Column(name = "youtube_channel")
        	private String youtubeChannel;
        	
        	@Column(name = "hobby")
        	private String hobby;
        	
        	// add new field for instructor (also add getter / setters)
        	
        	// add @OneToOne annotation
        	
        	/**
        	 * Note : we dont't need to change anything in the database to make these tables bi-directional.
        	 * 
        	 * mappedBy : It is used refer to the property by which this class object is
        	 * referred in the Instructor class.
        	 * 
        	 * CascadeType.All : It will cascade all operations done on InstructorDetail table
        	 * with Instructor table i.e. if we delete InstructorDetail object it will
        	 * also delete the Instructor object associated with it
        	 */
        	@OneToOne(mappedBy = "instructorDetail", cascade = CascadeType.ALL)
        	private Instructor instructor;
        	
        
        	public Instructor getInstructor() {
        		return instructor;
        	}
        
        	public void setInstructor(Instructor instructor) {
        		this.instructor = instructor;
        	}
        
        	public InstructorDetail() {}
        	
        	public InstructorDetail(String youtubeChannel, String hobby) {
        		super();
        		this.youtubeChannel = youtubeChannel;
        		this.hobby = hobby;
        	}
        
        	public int getId() {
        		return id;
        	}
        
        	public void setId(int id) {
        		this.id = id;
        	}
        
        	public String getYoutubeChannel() {
        		return youtubeChannel;
        	}
        
        	public void setYoutubeChannel(String youtubeChannel) {
        		this.youtubeChannel = youtubeChannel;
        	}
        
        	public String getHobby() {
        		return hobby;
        	}
        
        	public void setHobby(String hobby) {
        		this.hobby = hobby;
        	}
        
        	@Override
        	public String toString() {
        		return "InstructorDetail [id=" + id + ", youtubeChannel=" + youtubeChannel + ", hobby=" + hobby + "]";
        	}

        }


* And here is how we can use this 

        package com.rohitThebest.hibernate.demo;
        
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;
        import com.rohitThebest.hibernate.demo.entity.Instructor;
        import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
        
        public class GetInstructorDetailDemo {
        
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
        		
        			
        			// get the instructor detail object
        			int id = 2;
        			InstructorDetail instructorDetail = session.get(InstructorDetail.class, id);
        			
        			// print the instructor detail
        			System.out.println("instructor detail: " + instructorDetail);
        			
        			// print the associated instructor
        			System.out.println("the associated instructor : " + instructorDetail.getInstructor());
        		
        			// commit transaction
        			session.getTransaction().commit();
        			
        			System.out.println("Done");
        			
        		}catch (Exception e) {
        			e.printStackTrace();
        		}finally {
        			
        			// handle connection leak issue
        			session.close();			
        			sessionFactory.close();
        		}
        		
        	}
        
        }

* What if we want to delete only the InstructorDetail object and not the Instructor object from the database when we are deleting Instructor object.
  * We need to change the CascadeType in InstructorDetail class
  
  * Below is the code : 

                package com.rohitThebest.hibernate.demo.entity;
                
                import javax.persistence.CascadeType;
                import javax.persistence.Column;
                import javax.persistence.Entity;
                import javax.persistence.GeneratedValue;
                import javax.persistence.GenerationType;
                import javax.persistence.Id;
                import javax.persistence.OneToOne;
                import javax.persistence.Table;
                
                @Entity
                @Table(name = "instructor_detail")
                public class InstructorDetail {
                
                	// annotate the class as an entity and map to db table
                	
                	// define the fields
                	
                	// annotate the fields with db column names
                	
                	// create constructor
                	
                	// generate getters / setters 
                	
                	// generate toString() method
                
                	@Id
                	@GeneratedValue(strategy = GenerationType.IDENTITY)
                	@Column(name = "id")
                	private int id;
                	
                	@Column(name = "youtube_channel")
                	private String youtubeChannel;
                	
                	@Column(name = "hobby")
                	private String hobby;
                	
                	// add new field for instructor (also add getter / setters)
                	
                	// add @OneToOne annotation
                	
                	/**
                	 * Note : we dont't need to change anything in the database to make these tables bi-directional.
                	 * 
                	 * mappedBy : It is used refer to the property by which this class object is
                	 * referred in the Instructor class.
                	 * 
                	 * CascadeType.All : It will cascade all operations done on InstructorDetail table
                	 * with Instructor table i.e. if we delete InstructorDetail object it will
                	 * also delete the Instructor object associated with it
                	 */
                	//@OneToOne(mappedBy = "instructorDetail", cascade = CascadeType.ALL)
                	// changing the cascade type so as when InstructorDetail object is deleted 
                	// it does not delete the Instructor
                	@OneToOne(mappedBy = "instructorDetail", cascade = 
                		{CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
                	private Instructor instructor;
                	
                
                	public Instructor getInstructor() {
                		return instructor;
                	}
                
                	public void setInstructor(Instructor instructor) {
                		this.instructor = instructor;
                	}
                
                	public InstructorDetail() {}
                	
                	public InstructorDetail(String youtubeChannel, String hobby) {
                		super();
                		this.youtubeChannel = youtubeChannel;
                		this.hobby = hobby;
                	}
                
                	public int getId() {
                		return id;
                	}
                
                	public void setId(int id) {
                		this.id = id;
                	}
                
                	public String getYoutubeChannel() {
                		return youtubeChannel;
                	}
                
                	public void setYoutubeChannel(String youtubeChannel) {
                		this.youtubeChannel = youtubeChannel;
                	}
                
                	public String getHobby() {
                		return hobby;
                	}
                
                	public void setHobby(String hobby) {
                		this.hobby = hobby;
                	}
                
                	@Override
                	public String toString() {
                		return "InstructorDetail [id=" + id + ", youtubeChannel=" + youtubeChannel + ", hobby=" + hobby + "]";
                	}
                
                }
  * Here is the updated DeleteInstructorDetailDemo.java class

                package com.rohitThebest.hibernate.demo;
                
                import org.hibernate.Session;
                import org.hibernate.SessionFactory;
                import org.hibernate.cfg.Configuration;
                import com.rohitThebest.hibernate.demo.entity.Instructor;
                import com.rohitThebest.hibernate.demo.entity.InstructorDetail;
                
                public class DeleteInstructorDetailDemo {
                
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
                		
                			
                			// get the instructor detail object
                			int id = 3;
                			InstructorDetail instructorDetail = session.get(InstructorDetail.class, id);
                			
                			// print the instructor detail
                			System.out.println("instructor detail: " + instructorDetail);
                			
                			// print the associated instructor
                			System.out.println("the associated instructor : " + instructorDetail.getInstructor());
                		
                			
                			// now let's delete the instructor detail
                			// Note : It will not delete the Instructor associated with the 
                			// instructor detail because of Cascade.REMOVE is not used
                			//
                			System.out.println("Deleting instructorDetail : " + instructorDetail);
                			
                			// remove the associated object reference 
                			// break bi-directional link
                			instructorDetail.getInstructor().setInstructorDetail(null);
                			
                			session.delete(instructorDetail);
                			
                			// commit transaction
                			session.getTransaction().commit();
                			
                			System.out.println("Done");
                			
                		}catch (Exception e) {
                			e.printStackTrace();
                		}finally {
                			
                			// handle connection leak issue
                			session.close();			
                			sessionFactory.close();
                		}
                		
                	}
                
                }


### For one-to-many (bi-directional) switch to https://github.com/rkumar0206/hibernate_with_spring/tree/main-4 branch

