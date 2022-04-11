package com.cognixia.jump.model;

import java.util.Objects;

public class User {

	private int id;
	private String name;
	private String address;
	private String number;
	private String password;
	
	
	
	
	public User(int id, String name, String address, String number, String password) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.number = number;
		this.password = password;

	}

	public boolean login(int log, String pass) {
		if(log == this.id && pass.equals(this.password)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(address, id, name, number, password);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(address, other.address)
				&& id == other.id
				&& Objects.equals(name, other.name) && Objects.equals(number, other.number)
				&& Objects.equals(password, other.password);
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", address=" + address + ", number=" + number + ", id=" + id + ", password="
				+ password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
