package com.rohitthebest.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.rohitthebest.hibernate.demo.entity.Student;

public class ReadStudentDemo {

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
			
			Student student = new Student("Nidhi", "Kumari", "nidhi@ni.com");
			
			// start a transaction
			
			session.beginTransaction();
			
			// save the student object
			System.out.println("Saving the student object");
			session.save(student);
			
			// commit transaction
			
			session.getTransaction().commit();
			
			// NEW CODE
			
			// find out the student's id : primary key
			System.out.println("Saved student. Generated Id: " + student.getId());
			
			// now get a new session and start transaction
			
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			// retrieve student based on the id : primary key
			
			System.out.println("\nGetting student with id: " + student.getId());
			
			Student myStudent = session.get(Student.class, student.getId());
			
			System.out.println("Get complete:  " + myStudent);
			
			// commit the transaction
			
			session.getTransaction().commit();
			
			System.out.println("Done");
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sessionFactory.close();
		}
		
	}

}
