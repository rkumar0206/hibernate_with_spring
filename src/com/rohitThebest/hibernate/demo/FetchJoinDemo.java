package com.rohitThebest.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.rohitThebest.hibernate.demo.entity.Course;
import com.rohitThebest.hibernate.demo.entity.Instructor;
import com.rohitThebest.hibernate.demo.entity.InstructorDetail;

public class FetchJoinDemo {

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

			// option 2 using hibernate query HQL
			
			// get the instructor from db
			int id = 1;
			
			Query<Instructor> query = session.createQuery("select i from Instructor i "
					+ "JOIN FETCH i.courses "
					+ "where i.id=:theInstructorId",
					Instructor.class);
			
			// set parameter on query
			query.setParameter("theInstructorId", id);
			
			// execute query and get the instructor
			// Note : this will fetch all the courses as well because
			// of the query we set up above
			Instructor instructor = query.getSingleResult();
			
			System.out.println("rohitThebest : Instructor: " + instructor);
			
			// commit transaction
			session.getTransaction().commit();
			
			// close the session
			session.close();
			
			System.out.println("\nrohitThebest : the session is closed\n");
			
			// get course for the instructor
			System.out.println("rohitThebest : Courses: " + instructor.getCourses());

			System.out.println("Done");
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			session.close();
			sessionFactory.close();
		}
	}
}

