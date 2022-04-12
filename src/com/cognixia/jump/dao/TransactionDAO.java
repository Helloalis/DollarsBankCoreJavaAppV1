package com.cognixia.jump.dao;

import java.util.List;

import com.cognixia.jump.model.Transaction;

public interface TransactionDAO<Transaction> {

	List<Transaction> getAllTransactions();
	List<Transaction> getTransactionsByRoot(int id);
	
	boolean addTransaction(Transaction object);
	boolean deleteTransaction(Transaction object);
	
	boolean updateTransaction(Transaction object);
	
}