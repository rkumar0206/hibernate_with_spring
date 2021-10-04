package com.rohitthebest.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.rohitthebest.hibernate.demo.entity.Student;

public class CreateStudentDemo {

	public static void main(String[] args) {
		
		// create session factory
		SessionFactory sessionFactory = new Configuration()
											.configure("hibernate.cfg.xml")
											.addAnnotatedClass(Student.class)
											.buildSessionFactory();

		// create session
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			
	
			// create a student object
			
			System.out.println("Creating a new studnt object....");
			
			Student student = new Student("Paul", "Wall", "paul@luv2code.com");
			
			// start a transaction
			
			session.beginTransaction();
			
			// save the student object
			System.out.println("Saving the student object");
			session.save(student);
			
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
