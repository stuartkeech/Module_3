package com;

public class Customer {
	private int customer_ID;
	private String firstname;
	private String middlename;
	private String lastname;
	private String DOB;
	private String gender;
	private String occupation;
	private double salary;
	private String marital_status;
	private int number_children;
	private String SIN;
	
	Customer(int customer_ID, String firstname, String middlename, String lastname, String DOB, String gender, String occupation, double salary, String marital_status, int number_children, String SIN) {
		this.customer_ID = customer_ID;
		this.firstname = firstname;
		this.middlename = middlename;
		this.lastname = lastname;
		this.DOB = DOB;
		this.gender = gender;
		this.occupation = occupation;
		this.salary = salary;
		this.marital_status = marital_status;
		this.number_children = number_children;
		this.SIN = SIN;
	}
	
	public int getCustomerId() {
		return this.customer_ID;
	}
	
	public String getFirstname() {
		return this.firstname;
	}
	
	public String getMiddlename() {
		return this.middlename;
	}
	
	public String getLastname() {
		return this.lastname;
	}
	
	public String getDOB() {
		return this.DOB;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public String getOccupation() {
		return this.occupation;
	}
	
	public double getSalary() {
		return this.salary;
	}
	
	public String getMaritalStatus() {
		return this.marital_status;
	}
	
	public int getNumberChildren() {
		return this.number_children;
	}
	
	public String getSIN() {
		return this.SIN;
	}

	public void setCustomerId(int customer_ID) {
		this.customer_ID = customer_ID;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public void setDOB(String DOB) {
		this.DOB = DOB;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public void setMaritalStatus(String marital_status) {
		this.marital_status = marital_status;
	}
	
	public void setNumberChildren(int number_children) {
		this.number_children = number_children;
	}
	
	public void setSIN(String SIN) {
		this.SIN = SIN;
	}
}
