package com.dovdekeyser.groupon_springboot_web.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sound.midi.SysexMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dovdekeyser.groupon_springboot_web.beans.Category;
import com.dovdekeyser.groupon_springboot_web.beans.Coupon;
import com.dovdekeyser.groupon_springboot_web.beans.Customer;
import com.dovdekeyser.groupon_springboot_web.errors.CouponAlreadyPurchased;
import com.dovdekeyser.groupon_springboot_web.errors.NoMoreCouponsLeft;
import com.dovdekeyser.groupon_springboot_web.facade.CustomerFacade;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins="http://localhost:4200")
public class CustomerController {

	
	@Autowired
	CustomerFacade custF;
	
	@Autowired
	private Map<String,Session> tokensMap;
	
	private Session isActive(String token) {
		return tokensMap.get(token);
	}
	
	public static ResponseEntity<String> getEntity(Exception e){
		return new ResponseEntity<String>( "{\"Server Error\":\""+ e.getMessage() +"\"}",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	public static ResponseEntity<String> getEntity(String s){
		return new ResponseEntity<String>( "{\"Server Error\":\""+ s +"\"}",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(path="/coupon/{token}")
	public ResponseEntity<?> purchaseCoupon(@RequestBody Coupon coup, @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			try {
				custF.purchaseCoupon(coup);
				return new ResponseEntity<Coupon>(coup,HttpStatus.CREATED);
			} catch (NoMoreCouponsLeft | CouponAlreadyPurchased e) {
				return getEntity(e);
			}
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping(path="/coupon/purchased/{token}")
	public ResponseEntity<?> getCustomerCoupons( @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			return new ResponseEntity<List<Coupon>>(custF.getAllCustomerCoupons() ,HttpStatus.OK);
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	

	@GetMapping(path="/coupon/{token}")
	public ResponseEntity<?> getAllCoupons(@PathVariable("token")String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			return new ResponseEntity<List<Coupon>>(custF.getAllCoupons(),HttpStatus.OK);
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);

	}
	
	
	@GetMapping(path="/coupon/category/{cat}/{token}")
	public ResponseEntity<?> getPurchadsedCustomerCouponsByCategory(@PathVariable("cat") Category cat, @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			ArrayList<Coupon> coupons =custF.getAllCustomerCouponsByCategory(cat);
			return new ResponseEntity<List<Coupon>>(coupons,HttpStatus.OK);
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping(path="/all/coupon/category/{cat}/{token}")
	public ResponseEntity<?> getAllCustomerCustomerCouponsByCategory(@PathVariable("cat") Category cat, @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			ArrayList<Coupon> coupons =custF.getCouponsByCategory(cat);
			return new ResponseEntity<List<Coupon>>(coupons,HttpStatus.OK);
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping(path="/coupon/maxprice/{max}/{token}")
	public ResponseEntity<?> getPurchasedCustomerCouponsByPrice(@PathVariable("max") double max, @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			ArrayList<Coupon> coupons =custF.getAllCustomerCouponsByMaxPrice(max);
			return new ResponseEntity<List<Coupon>>(coupons,HttpStatus.OK);
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping(path="/all/coupon/maxprice/{max}/{token}")
	public ResponseEntity<?> getCustomerCoupons(@PathVariable("max") double max, @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			ArrayList<Coupon> coupons =custF.getCouponsByMaxPrice(max);
			return new ResponseEntity<List<Coupon>>(coupons,HttpStatus.OK);
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	@GetMapping(path="/{token}")
	public ResponseEntity<?> getCustomerDetails( @PathVariable String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			Customer cust = custF.getCustomerDetails();
			if(cust !=null) return new ResponseEntity<Customer>(cust, HttpStatus.OK);
			else return getEntity("No Customer Found");
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	
}







