package com.cognixia.jump.model;

import java.util.ArrayList;
import java.util.Objects;

public class Account {
	
	private Integer id;
	private double amount;
	private String password;
	private ArrayList<Transaction> trans;
	
	public Account(Integer id, double amount, String password) {
		super();
		this.amount = amount;
		this.password = password;
		this.trans = new ArrayList<Transaction>(5);
		
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
		
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ArrayList<Transaction> getTrans() {
		return trans;
	}
	public void setTrans(ArrayList<Transaction> trans) {
		this.trans = trans;
	}
	@Override
	public int hashCode() {
		return Objects.hash(amount, password);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(password, other.password);
	}
	public void addTransaction(Transaction transaction) {
		trans.add(1, transaction);
		if(trans.size() > 5) {
			trans.remove(5);
		}
		
	}
	
	
	
	

}
