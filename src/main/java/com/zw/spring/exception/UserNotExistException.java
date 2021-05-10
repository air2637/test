package com.zw.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotExistException extends Exception {
	
	
	public UserNotExistException() {
		
	}
	
	public UserNotExistException(String message) {
		super(message);
	}
	
	public UserNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
