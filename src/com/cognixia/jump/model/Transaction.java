package com.cognixia.jump.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
	private LocalDateTime time;
	private double transVal;
	
	//denotes the root account and destination account by id of a transaction
	//if deposit or withdrawal, dest is null
	//if fundTransfer, root is sending account, dest is receiving account
	private Integer rootId;
	private Integer dest;

	
	
	public Transaction(LocalDateTime time, double transVal, Integer rootId, Integer dest) {
		super();
		this.time = time;
		this.transVal = transVal;
		this.rootId = rootId;
		this.dest = dest;
	}
	
	public String detType() {
		if(dest != null) {
			return "Funds Transfer";
		}
		else if (transVal > 0) {
			return "Deposit";
		}
		else { 
			return "Withdrawal";
		}
	}
 	
	@Override
	public int hashCode() {
		return Objects.hash(dest, rootId, time, transVal);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return dest == other.dest && rootId == other.rootId && Objects.equals(time, other.time)
				&& Double.doubleToLongBits(transVal) == Double.doubleToLongBits(other.transVal);
	}
	public int getRootId() {
		return rootId;
	}
	public void setRootId(int rootId) {
		this.rootId = rootId;
	}
	public int getDest() {
		return dest;
	}
	public void setDest(int dest) {
		this.dest = dest;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public double getTransVal() {
		return transVal;
	}
	public void setTransVal(double transVal) {
		this.transVal = transVal;
	}

	@Override
	public String toString() {
		if(this.detType().equals("Withdrawal")) {
			return "Transaction " + time + ": " + transVal + "withdrawn from account" + rootId;
		}
		else if(this.detType().equals("Deposit")) {
			return "Transaction " + time + ": " + transVal + "deposited to account" + rootId;
		}
		else {
			return "Transaction " + time + ": " + transVal + "transferred from account" + rootId + " to account " + dest ;
		}
		
	}
	
	
	
}
