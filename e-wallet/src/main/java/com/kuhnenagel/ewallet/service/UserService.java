package com.kuhnenagel.ewallet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.kuhnenagel.ewallet.model.User;


public interface UserService {
	
	public User createUser(User user);
	public void deleteUser(String id);
	public Optional<User> getUser(String id);
	public List<User> list();
	public boolean isNameAvailable(String userName);
	public double getBalance(String uid, String wid);
	public double topUp(String uid, String wid, double topup);

}
