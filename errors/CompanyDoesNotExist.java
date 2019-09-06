package com.dovdekeyser.groupon_springboot_web.errors;

public final class CompanyDoesNotExist extends Exception {
	
	public CompanyDoesNotExist() {
		super("Company does not exist");
	}
	public CompanyDoesNotExist(String message) {
		super(message);
	}
}
