package com.rohitthebest.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.rohitthebest.hibernate.demo.entity.Student;

public class PrimaryKeyDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory sessionFactory = new Configuration()
											.configure("hibernate.cfg.xml")
											.addAnnotatedClass(Student.class)
											.buildSessionFactory();

		// create session
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			
	
			// create 3 student objects
			
			System.out.println("Creating 3 studnt objects....");
			
			Student student1 = new Student("Rohit", "Kumar", "rohit@gmail.com");
			Student student2 = new Student("Mohit", "Kumar", "mohit@moh.com");
			Student student3 = new Student("Sagar", "Singh", "sagar@sa.com");
			
			// start a transaction
			
			session.beginTransaction();
			
			// save the student object
			System.out.println("Saving the students...");
			session.save(student1);
			session.save(student2);
			session.save(student3);
			
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
