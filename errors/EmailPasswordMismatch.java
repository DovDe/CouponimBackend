package com.dovdekeyser.groupon_springboot_web.errors;

public class EmailPasswordMismatch extends Exception {
	public EmailPasswordMismatch(String message) {
		super(message);
	}
	
	public EmailPasswordMismatch() {
		super("The email and password do not match");
	}
}
