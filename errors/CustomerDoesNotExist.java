package com.dovdekeyser.groupon_springboot_web.errors;

public class CustomerDoesNotExist extends Exception {
	public CustomerDoesNotExist(String message) {
		super(message);
	}
	
	public CustomerDoesNotExist() {
		super("The customer does not exist");
	}
}
