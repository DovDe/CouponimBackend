package com.dovdekeyser.groupon_springboot_web.web;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dovdekeyser.groupon_springboot_web.errors.CouponSystemException;
//import com.dovdekeyser.groupon_springboot_web.errors.CouponSystemException;
import com.dovdekeyser.groupon_springboot_web.errors.EmailPasswordMismatch;
import com.dovdekeyser.groupon_springboot_web.facade.Facade;
import com.dovdekeyser.groupon_springboot_web.login.ClientType;
import com.dovdekeyser.groupon_springboot_web.login.LoginManager;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class LoginController {

	
	@Autowired
	private Map<String,Session> tokensMap;
	
	@Autowired
	private LoginManager loginManager;
	
	@PostMapping("login")
	public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password, @RequestParam String type) {
		if(!type.equals("administrator")&&!type.equals("company")&&!type.equals("customer")) {
			return new ResponseEntity<>("Wrong type",HttpStatus.UNAUTHORIZED);
		}
		Session session=new Session();
		Facade facade=null;
		String token=UUID.randomUUID().toString();
		
		long lastAccessed=System.currentTimeMillis();
		try {
			facade=loginManager.login(email, password, ClientType.valueOf(type));
			session.setFacade(facade);
			session.setLastAccessed(lastAccessed);
			tokensMap.put(token, session); 
			
			return new ResponseEntity<>("{\"token\":\"" +token + "\"}",HttpStatus.OK);
		}catch(EmailPasswordMismatch | SQLException | CouponSystemException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);		
		} 
	}
	
	@PostMapping(path="logout/{token}")
	public ResponseEntity<String> logout(@PathVariable("token")String token){
		System.out.println("logout: " + token);
		tokensMap.remove(token);
		return new ResponseEntity<String>("{\"token\":\"" +"log out" + "\"}",HttpStatus.OK);
	}
	

}
