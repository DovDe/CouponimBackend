package com.dovdekeyser.groupon_springboot_web.errors;

public class TitleExists extends Exception {
	public TitleExists() {
		super("Title already Exists");
	}
	
	public TitleExists(String message) {
		super(message);
	}
}
