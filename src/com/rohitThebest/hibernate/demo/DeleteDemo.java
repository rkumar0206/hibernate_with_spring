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

