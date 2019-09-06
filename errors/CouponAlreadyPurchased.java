package com.dovdekeyser.groupon_springboot_web.errors;

public class CouponAlreadyPurchased extends Exception {
	public CouponAlreadyPurchased(String message) {
		super(message);
	}
	public CouponAlreadyPurchased() {
		super("This coupon was already purchased");
	}
}
