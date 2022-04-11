package com.cognixia.jump.controller;

import java.util.ArrayList;

import com.cognixia.jump.exceptions.TransactionException;
import com.cognixia.jump.model.Account;
import com.cognixia.jump.model.Transaction;
import com.cognixia.jump.model.User;

public class UserController {
	private int idCounter;
	private ArrayList<User> users;
	private ArrayList<Account> accounts;
	private TransactionController trans;
	
	
	public UserController() {
		super();
		this.idCounter = 0;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
		this.trans = new TransactionController();
	}

	public boolean authenticate(int id, String password) {
		if(users.get(id).login(id, password) ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public User getUser(int id) {
		return users.get(id);
	}
	public void printUser(int id) {
		System.out.println("User " + id);
		System.out.println("Name: " + users.get(id).getName());
		System.out.println("Address: " + users.get(id).getAddress());
		System.out.println("Number: " + users.get(id).getNumber());
		System.out.println("Balance: " + accounts.get(id).getAmount());
	}
	public void printTransactions(int id) {
		ArrayList<Transaction> temp = accounts.get(id).getTrans();
		for(int i = 0; i < temp.size(); i++) {
			System.out.println(temp.get(i));
		}
	}
	

	public void add(String nam, String add, String num, String pass, Double amou) {
		idCounter++;
		users.add(new User(idCounter, nam, add, num, pass));
		accounts.add(new Account(idCounter, amou, pass));
	}
	
	public void deposit(int id, double val) throws TransactionException {
		accounts.set(id, trans.deposit(accounts.get(id), val));
	}
	
	public void withdraw(int id, double val) throws TransactionException {
		accounts.set(id, trans.deposit(accounts.get(id), val));
	}
	
	public void transferFunds(int rootId, int destId, double val) throws TransactionException {
		Account[] updated = trans.fundTransfer(accounts.get(rootId), accounts.get(destId), val);
		accounts.set(rootId, updated[0]);
		accounts.set(destId, updated[1]);
	}
	

	public int getIdCounter() {
		return idCounter;
	}
	public void setIdCounter(int idCounter) {
		this.idCounter = idCounter;
	}
	public ArrayList<User> getUsers() {
		return users;
	}
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	public ArrayList<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}
	
	
}
