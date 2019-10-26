package com.item.pojo;

public class Student {
	private String name;
	private int id;
	private int age;
	private String address;
	
	
	public Student() {
	
	}
	
	
	public Student(String name, int id, int age, String address) {
		this.name = name;
		this.id = id;
		this.age = age;
		this.address = address;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
