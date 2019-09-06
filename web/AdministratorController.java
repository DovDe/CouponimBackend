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

import com.dovdekeyser.groupon_springboot_web.beans.Company;
import com.dovdekeyser.groupon_springboot_web.beans.Customer;
import com.dovdekeyser.groupon_springboot_web.errors.CustomerDoesNotExist;
import com.dovdekeyser.groupon_springboot_web.errors.UniqueEmailException;
import com.dovdekeyser.groupon_springboot_web.errors.UniqueNameException;
import com.dovdekeyser.groupon_springboot_web.facade.AdminFacade;


@RestController
@RequestMapping("administrator")
@CrossOrigin(origins="http://localhost:4200")
public class AdministratorController {
	
	@Autowired
	private AdminFacade adminF;
	

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
	


 

	/**
	 *          *********************COMPANY METHODS**********************************
	 */


	
	@PostMapping(path="/company/{token}")
	public ResponseEntity<?> addCompany(@RequestBody  Company comp , @PathVariable("token") String token){
		Session session = isActive(token);
		
		if(session != null) {
			try {
				adminF.addCompany(comp);
				session.setLastAccessed(System.currentTimeMillis());
				return new ResponseEntity<Company>(comp,HttpStatus.CREATED);
			} catch (UniqueEmailException e) {
				return getEntity(e);
			} catch (UniqueNameException e) {
				return getEntity(e);
			}	
			
		}else {
			return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@PutMapping(path="company/{token}")
	public ResponseEntity<?> updateCompany(@RequestBody  Company comp, @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			try {
				adminF.updateCompany(comp);
				return new ResponseEntity<Company>(comp,HttpStatus.OK);
			}catch(Exception e) {return getEntity(e);}
		}
		else return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	
	@DeleteMapping(path="/company/{id}/{token}")
	public ResponseEntity<?> deleteCompany(@PathVariable("id") int id, @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			adminF.deleteCompany(id);
			session.setLastAccessed(System.currentTimeMillis());
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping(path="/company/{token}")
	public ResponseEntity<?> getAllCompanies(@PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
	 		return new ResponseEntity<List<Company>>( adminF.getAllCompanies(),HttpStatus.OK);
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping(path="/company/{id}/{token}")
	public ResponseEntity<?> getOneCompany(@PathVariable("id") int id, @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			Company comp = adminF.getCompanyById(id);
			if(comp != null) return new ResponseEntity<Company>(comp,HttpStatus.OK);
			else return getEntity("Company Not Found");
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 *          *********************CUSTOMER METHODS**********************************
	 */
	
	@PostMapping(path="/customer/{token}")
	public ResponseEntity<?>addCustomer(@RequestBody Customer cust, @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			try {
				adminF.addCustomer(cust);
				return new ResponseEntity<Customer>(cust,HttpStatus.CREATED);
			} catch (UniqueEmailException e) {
				return getEntity(e);
			}
		}
		return new ResponseEntity<String>("Session Timeout",HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping(path="/customer/{token}")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer cust, @PathVariable("token") String token){
		Session session = isActive(token);
		if(session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			try {
				adminF.updateCustomer(cust);
				return new ResponseEntity<Customer>(cust,HttpStatus.OK);
			} catch (CustomerDoesNotExist e) {
				return getEntity(e);
			} catch (UniqueEmailException e) {
				return getEntity(e);
			}
		}
		return new ResponseEntity<String>("Session Timeout", HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping(path="/customer/{token}")
	public ResponseEntity<?> getAllCustomer(@PathVariable("token") String token){
		Session session = isActive(token);
		if(session !=null) {
			session.setLastAccessed(System.currentTimeMillis());
			ArrayList<Customer> customers = adminF.getAllCustomers();
			return new ResponseEntity<List<Customer>>(customers,HttpStatus.OK);
		}
		return new ResponseEntity<String>("Session Timeout", HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping(path="/customer/{id)/{token}")
	public ResponseEntity<?> getOneCustomer(@PathVariable("id") int id ,@PathVariable("token") String token){
		Session session = isActive(token);
		if(session !=null) {
			session.setLastAccessed(System.currentTimeMillis());
			Customer cust = adminF.getCustomerById(id);
			if( cust != null) return new ResponseEntity<Customer>(cust,HttpStatus.OK);
			else return getEntity("No Customer Found");
		}
		return new ResponseEntity<String>("Session Timeout", HttpStatus.UNAUTHORIZED);	
	}
	
	@DeleteMapping(path="/customer/{id}/{token}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") int id,@PathVariable("token") String token){
		Session session = isActive(token);
		if(session !=null) {
			session.setLastAccessed(System.currentTimeMillis());
			adminF.deleteCustomer(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Session Timeout", HttpStatus.UNAUTHORIZED);	
	}
}
