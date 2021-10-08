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
