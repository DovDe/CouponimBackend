package com.dovdekeyser.groupon_springboot_web.errors;

public class UniqueNameException extends Exception {
	public UniqueNameException() {
		super("Name already exist!");
	}
	
	public UniqueNameException(String message) {
		super(message);
	}
}
