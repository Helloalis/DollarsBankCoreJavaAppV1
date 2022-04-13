package com.cognixia.jump.dao;

import java.util.List;

public interface AccountDAO<Account> {

	List<Account> getAllAccounts();
	Account getAccountById(int id);
	
	boolean addAccount(Account object);
	
	boolean deleteAccountById(int id);
	
	boolean updateAccount(Account object);
	
}