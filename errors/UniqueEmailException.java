package com.dovdekeyser.groupon_springboot_web.errors;

public class UniqueEmailException extends Exception {
	public UniqueEmailException() {
		super("Email already exist!");
	}
	
	public UniqueEmailException(String message) {
		super(message);
	}
}
