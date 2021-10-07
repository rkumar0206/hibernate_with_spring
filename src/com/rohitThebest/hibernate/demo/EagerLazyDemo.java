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

