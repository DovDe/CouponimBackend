package com.dovdekeyser.groupon_springboot_web.errors;

public class CouponSystemException extends Exception {
	public CouponSystemException(String message) {
		super(message);
	}
	
	public CouponSystemException(String message, Exception origin) {
		super(message,origin);
	}
	
}
