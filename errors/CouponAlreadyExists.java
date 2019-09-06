package com.dovdekeyser.groupon_springboot_web.errors;

public class CouponAlreadyExists extends Exception {
	public CouponAlreadyExists() {
		super("This coupon already exists");
	}
	
	public CouponAlreadyExists(String message) {
		super(message);
	}
}
