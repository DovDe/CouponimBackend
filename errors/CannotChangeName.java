package com.dovdekeyser.groupon_springboot_web.errors;

public class CannotChangeName extends Exception {
	public CannotChangeName() {
		super("Cannot change company name");
	}
	
	public CannotChangeName(String message) {
		super(message);
	}
}
