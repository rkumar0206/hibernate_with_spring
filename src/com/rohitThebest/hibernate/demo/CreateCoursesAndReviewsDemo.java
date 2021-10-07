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
