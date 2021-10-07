package com.rohitthebest.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.rohitthebest.hibernate.demo.entity.Student;

public class QueryStudentDemo {

	public static void main(String[] args) {
		
		// create session factory
		SessionFactory sessionFactory = new Configuration()
											.configure("hibernate.cfg.xml")
											.addAnnotatedClass(Student.class)
											.buildSessionFactory();

		// create session
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			
	
			// start a transaction
			session.beginTransaction();
			
			/**
			 * here we will use HQL for querying the student database
			 * we use the class name and the property name instead of database table name and column name
			 */
			// query students 
			List<Student> students = session.createQuery("from Student").getResultList();
			
			// display the students
			displayStudents(students);
			
			// query the students : lastName= 'Kumar'
			
			students = session.createQuery("from Student s where s.lastName='Kumar'").getResultList();
			System.out.println("\n\nStudents who have last name as Kumar");
			displayStudents(students);
			
			// query students : lastName='Kumar' OR firstName = 'Sagar'
			
			students = session.createQuery("from Student s where"
					+ " s.lastName='Kumar' OR s.firstName='Sagar'").getResultList();
			
			System.out.println("\n\nStudents who have last name as Kumar OR first name as Sagar");
			displayStudents(students);
			
			
			// query students : email LIKE '%gmail.com'
			students = session.createQuery("from Student s where"
					+ " s.email LIKE '%gmail.com'").getResultList();
			
			System.out.println("\n\nStudents who email address is LIKE %gmail.com");
			displayStudents(students);
			
			
			List<Object[]> all = session.createQuery("select s.firstName, s.email from Student s").getResultList();
			
			for(Object[] p : all) {
				
				System.out.print(p[0]);
				System.out.println("  " + p[1]);
			}
			
			// commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done");
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
			sessionFactory.close();
		}
		
	}

	private static void displayStudents(List<Student> students) {
		
		for(Student tempStudent : students) {
			
			System.out.println(tempStudent);
		}
	}

}

class P {
	
	String firstName;
	String email;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}

