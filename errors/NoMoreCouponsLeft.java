package com.dovdekeyser.groupon_springboot_web.errors;

public class NoMoreCouponsLeft extends Exception {
	public NoMoreCouponsLeft() {
		super("Title already Exists");
	}
	
	public NoMoreCouponsLeft(String message) {
		super(message);
	}
}
