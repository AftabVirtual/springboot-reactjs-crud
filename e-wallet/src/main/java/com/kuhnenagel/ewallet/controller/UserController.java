package com.kuhnenagel.ewallet.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kuhnenagel.ewallet.model.User;
import com.kuhnenagel.ewallet.service.UserService;

@RestController
@RequestMapping(value = "/api")
public class UserController {

	@Autowired
	private UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/users", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(userService.list(), HttpStatus.OK);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Optional<User>> getUser(@PathVariable(name = "id", required = true) String id) {
		return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<String> deleteUser(@PathVariable(name = "id", required = true) String id) {
		logger.info("###### Invoking deleteUser");
		userService.deleteUser(id);
		return new ResponseEntity<>("User Deleted", HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/users/{uid}/wallets/{wid}/balance", method = RequestMethod.GET, produces = "application/json")
	public double getBalance(@PathVariable(name = "uid", required = true) String uid,
			@PathVariable(name = "wid", required = true) String wid) {
		return userService.getBalance(uid, wid);
	}

	@RequestMapping(value = "/users/{uid}/wallets/{wid}/topup", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Double> topUp(@PathVariable(name = "uid", required = true) String uid,
			@PathVariable(name = "wid", required = true) String wid, @RequestBody double topup) {
		return new ResponseEntity<Double>(userService.topUp(uid, wid, topup), HttpStatus.CREATED);
	}
}
