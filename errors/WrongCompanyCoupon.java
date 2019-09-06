package com.dovdekeyser.groupon_springboot_web.errors;

public class WrongCompanyCoupon extends Exception {
	public WrongCompanyCoupon(String message) {
		super(message);
	}
	
	public WrongCompanyCoupon() {
		super("This coupon belongs to another company");
	}
}	
