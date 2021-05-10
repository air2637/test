package com.zw.spring.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zw.spring.domain.User;
import com.zw.spring.exception.UserNotExistException;
import com.zw.spring.repository.UserRepository;

@RestController
@RequestMapping(value="/user/")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody User newUser) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		newUser.setPassword(encoder.encode(newUser.getPassword()));
		
		newUser = userRepo.save(newUser);
		newUser.setPassword(null);
		
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value="get/{userId}", method=RequestMethod.GET)
	public ResponseEntity<User> getUserInfo(@PathVariable Long userId) throws UserNotExistException{
		
		Optional<User> registered = userRepo.findById(userId);
		
		
		
		if(registered.isPresent()) {
			
			registered.get().setPassword(null);
			
			return new ResponseEntity<User>(registered.get(), HttpStatus.OK);
		}
		
		throw new UserNotExistException("Given userId " + userId + " not exists");
		
	}
	
	@RequestMapping(value="/deactivate/{userId}", method=RequestMethod.PUT)
	public ResponseEntity<User> deactivateUser(@PathVariable Long userId) throws UserNotExistException{
		
		Optional<User> registered = userRepo.findById(userId);
		
		if(registered.isPresent()) {
			
			User inactivated = registered.get();
			inactivated.setStatus("INACTIVE");
			inactivated = userRepo.save(inactivated);
			inactivated.setPassword(null);
			return new ResponseEntity<User>(inactivated, HttpStatus.OK);
		}
		
		throw new UserNotExistException("Given userId " + userId + " not exists");
		
	}

}
