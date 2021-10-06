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
					new Instructor("Mohit", "Kumar", "mohit@intelli.com");
			
			InstructorDetail instructorDetail = 
					new InstructorDetail(
							"http://www.mohit.com/youtube",
							"linux");
					
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
			
			session.close();
			sessionFactory.close();
		}
	}
}

