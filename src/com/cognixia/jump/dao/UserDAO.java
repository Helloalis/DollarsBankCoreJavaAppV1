package com.cognixia.jump.dao;

import java.util.List;

public interface UserDAO<User> {

	List<User> getAll();
	User getById(int id);
	
	boolean add(User object);
	
	boolean deleteById(int id);
	boolean delete(User object);
	
	boolean update(User object);
	
}