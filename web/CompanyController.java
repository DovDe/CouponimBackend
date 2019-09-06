package com.dovdekeyser.groupon_springboot_web.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dovdekeyser.groupon_springboot_web.beans.Category;
import com.dovdekeyser.groupon_springboot_web.beans.Company;
import com.dovdekeyser.groupon_springboot_web.beans.Coupon;
import com.dovdekeyser.groupon_springboot_web.errors.CouponAlreadyExists;
import com.dovdekeyser.groupon_springboot_web.errors.TitleExists;
import com.dovdekeyser.groupon_springboot_web.errors.WrongCompanyCoupon;
import com.dovdekeyser.groupon_springboot_web.facade.CompanyFacade;

@RestController
@RequestMapping("company")
@CrossOrigin(origins="http://localhost:4200")
public class CompanyController {
	
	@Autowired
	CompanyFacade compF;
	
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
	public ResponseEntity<?> addCoupon(@RequestBody Coupon coup, @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			try {
				compF.addCoupon(coup);
				return new ResponseEntity<Coupon>(coup,HttpStatus.CREATED);
			} catch (TitleExists e) {
				return getEntity(e);
			}
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	
	
	@PutMapping(path="/coupon/{token}")
	public ResponseEntity<?> updateCoupon(@RequestBody Coupon coup, @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			try {
				compF.updateCoupon(coup);
				return new ResponseEntity<Coupon>(coup,HttpStatus.OK);
			} catch (CouponAlreadyExists e) {
				return getEntity(e);
			}
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	
	@DeleteMapping(path="/coupon/{id}/{token}")
	public ResponseEntity<?> deleteCoupon(@PathVariable("id") int id, @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			compF.deleteCoupon(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);

	}

	@GetMapping(path="/coupon/id/{id}/{token}")
	public ResponseEntity<?> getCompanyCouponById(@PathVariable("id")int id , @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			try {
				Coupon coup = compF.getCompanyCouponById(id);
				return new ResponseEntity<Coupon>(coup,HttpStatus.OK);
			} catch (WrongCompanyCoupon e) {
				getEntity(e);
			}
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);

	}
	@GetMapping(path="/coupon/{token}")
	public ResponseEntity<?> getCompanyCoupons(@PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			ArrayList<Coupon> coupons = compF.getCompanyCoupons();
			return new ResponseEntity<List<Coupon>>(coupons,HttpStatus.OK);
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping(path="coupon/category/{cat}/{token}")
	public ResponseEntity<?> getCompanyCoupons(@PathVariable("cat") Category cat,@PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			return new ResponseEntity<List<Coupon>>(compF.getCompanyCouponsByCategory(cat),HttpStatus.OK);
		}	
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping(path="coupon/maxprice/{max}/{token}")
	public ResponseEntity<?>getCompanyCoupons(@PathVariable("max") double max ,@PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			return new ResponseEntity<List<Coupon>>(compF.getCompanyCouponsByMaxPrice(max) ,HttpStatus.OK);
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping(path="/{token}")
	public ResponseEntity<?> getCompanyDetails(@PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			Company comp =compF.getCompany();
			if(comp != null) return new ResponseEntity<Company>(comp,HttpStatus.OK);
			else return getEntity("No Company Found");
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
}
