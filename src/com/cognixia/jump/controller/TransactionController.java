package com.cognixia.jump.controller;

import java.time.LocalDateTime;

import com.cognixia.jump.exceptions.TransactionException;
import com.cognixia.jump.model.Account;
import com.cognixia.jump.model.Transaction;

public class TransactionController {
	
	
	public Account deposit(Account acc, double val) throws TransactionException {
		if(val <= 0) {
			throw new TransactionException("Deposit value must be greater than zero");
		}
		acc.setAmount(acc.getAmount() + val);
		acc.addTransaction(new Transaction(LocalDateTime.now(), val, acc.getId(), null));
		return acc;
	}
	public Account withdraw(Account acc, double val) throws TransactionException {
		if(val <= 0) {
			throw new TransactionException("Withdrawal value must be greater than zero");
		}
		//overdraft fee
		if((acc.getAmount() - val) < 0) {
			val += 25.00;
		}
		val = val * -1;
		acc.setAmount(acc.getAmount() + val);
		acc.addTransaction(new Transaction(LocalDateTime.now(), val, acc.getId(), null));
		return acc;
	}
	
	public Account[] fundTransfer(Account roo, Account dest, double val) throws TransactionException {
		Account[] arr1 = new Account[2];
		if(val <= 0) {
			throw new TransactionException("Transfer value must be greater than zero");
		}
		dest.setAmount(dest.getAmount() + val);
		dest.addTransaction(new Transaction(LocalDateTime.now(), val, roo.getId(), dest.getId()));
		if((roo.getAmount() - val) < 0) {
			val += 25.00;
		}
		roo.setAmount(roo.getAmount() - val);
		roo.addTransaction(new Transaction(LocalDateTime.now(), val*-1, roo.getId(), dest.getId()));
		arr1[0] = roo;
		arr1[1] = dest;
		return arr1;
	}
	
	
}
