package com.roomreserve.service;

import java.util.List;

import com.roomreserve.entities.User;

public interface UserService {

	User registerUser(User user);
	
	List<User> getUsers();
	
	void deleteUser(String email);
	
	User getUser(String email);
}
