package com.dovdekeyser.groupon_springboot_web.db;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dovdekeyser.groupon_springboot_web.beans.Customer;

@Component
public class CustomerDBDAO {

	//************************DEPENDANCY ENJECTION *********************************************************

	@Autowired
	private CustomerRepository custRepo;
	
	//*****************************************************************************************************
	//************************  BASIC CRUD  ***************************************************************
	//*****************************************************************************************************


	//************************ CREATE *********************************************************************
	
	public void addCustomer(Customer cust) {
		custRepo.save(cust);
	}
	
	//************************ READ *********************************************************************

	public Customer getCustomerById(int id) {
		Optional<Customer> cust = custRepo.findById(id);
		if(cust.isPresent()) return cust.get();
		return null;
	}
	
	public ArrayList<Customer> getAllCustomers(){
		return (ArrayList<Customer>) custRepo.findAll();
	}
	
	//************************ UPDATE *********************************************************************
	
	public void updateCustomer(Customer cust) {
		if(custRepo.existsById(cust.getId())) custRepo.save(cust);
	}
	//************************ DELETE *********************************************************************

	public void deleteCustomer(int id) {
		custRepo.deleteById(id);
	}
	
	
	//*****************************************************************************************************
	//************************  OTHER CRUD METHODS ********************************************************
	//*****************************************************************************************************
	
	//************************ CREATE *********************************************************************
	
	
	
	//************************ READ ***********************************************************************
	
	
	public Customer getCustomerByEmailAndPassword(String email, String password) {
		return custRepo.getCustomerByEmailAndPassword(email, password);

	}
	public Customer getCustomerByEmail(String email) {
		return custRepo.getCustomerByEmail(email);
	}
	
	//************************ UPDATE *********************************************************************
	//************************ DELETE *********************************************************************

	
}
