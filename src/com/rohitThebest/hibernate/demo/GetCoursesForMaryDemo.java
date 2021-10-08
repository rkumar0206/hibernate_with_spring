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
