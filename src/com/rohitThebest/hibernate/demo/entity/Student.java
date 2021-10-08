package com.rohitThebest.hibernate.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 
 * @author Rohit
 *	Mapping this class to the database
 */

@Entity
@Table(name = "student")
public class Student {

	@Id		// used on primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // will auto generate the id
	@Column(name = "id") // used for defining the column name same as in database
	private int id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	
	/**
	 * JoinTable is used for joining two tables by using there primary keys.
	 * Here, the name of the table is course_student.
	 * 
	 * And for this Student class first side (or first column) is student_id column
	 * and other side (or inverse side) is course_id column
	 */
	@ManyToMany(fetch = FetchType.LAZY ,cascade = {CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.REFRESH})
	@JoinTable(
			name = "course_student",
			joinColumns = @JoinColumn(name = "student_id"),
			inverseJoinColumns = @JoinColumn(name = "course_id")
			)
	private List<Course> courses;

	
	public Student() {}


	public Student(String firstName, String lastName, String email) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public List<Course> getCourses() {
		return courses;
	}


	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}


	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
	
	
}
