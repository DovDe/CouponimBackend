package com.dovdekeyser.groupon_springboot_web.facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dovdekeyser.groupon_springboot_web.beans.Company;
import com.dovdekeyser.groupon_springboot_web.beans.Coupon;
import com.dovdekeyser.groupon_springboot_web.beans.Customer;
import com.dovdekeyser.groupon_springboot_web.errors.CannotChangeName;
import com.dovdekeyser.groupon_springboot_web.errors.CompanyDoesNotExist;
import com.dovdekeyser.groupon_springboot_web.errors.CustomerDoesNotExist;
import com.dovdekeyser.groupon_springboot_web.errors.EmailPasswordMismatch;
import com.dovdekeyser.groupon_springboot_web.errors.UniqueEmailException;
import com.dovdekeyser.groupon_springboot_web.errors.UniqueNameException;

@Service
public class AdminFacade extends ClientFacade {
	
	
	private int loggedIn;

	public AdminFacade() {}
	
	
	//************************  LOGIN  *********************************************************************

	@Override
	public int login(String email, String password) throws EmailPasswordMismatch, SQLException {
		if(! email.equals("admin@email.com") || ! password.equals("admin")) throw new EmailPasswordMismatch();
		this.loggedIn = 1;
		return 1;
	}
	
	
	//*****************************************************************************************************
	//**************************    CRUD METHODS   ********************************************************
	//*****************************************************************************************************
	
	//************************ CREATE *********************************************************************
	public void addCompany(Company comp) throws UniqueEmailException, UniqueNameException  {
		if(compDB.getCompanybyEmail(comp.getEmail()) != null) throw new UniqueEmailException();
		if(compDB.getCompanyByName(comp.getName()) != null) throw new UniqueNameException();
		compDB.addCompany(comp);		
	}
	
	public void addCustomer(Customer cust) throws UniqueEmailException {
		if(custDB.getCustomerByEmail(cust.getEmail()) != null) throw new UniqueEmailException();
		custDB.addCustomer(cust);
	}
	
	//************************ READ ***********************************************************************
	
	public ArrayList<Company> getAllCompanies(){
		return compDB.getAllCompanies();
	}
	
	public Company getCompanyById(int id) {
		return compDB.getCompanyById(id);
	}
	
	
	public ArrayList<Customer> getAllCustomers(){
		return custDB.getAllCustomers();
	}
	
	public Customer getCustomerById(int id) {
		return custDB.getCustomerById(id);
	}
	
	//************************ UPDATE *********************************************************************
	
	public void updateCompany(Company comp) throws CompanyDoesNotExist, CannotChangeName {
		
		
		Company updatedCompany = compDB.getCompanyById(comp.getId());
		
		if(updatedCompany ==null) throw new CompanyDoesNotExist();
		if(! comp.getName().equals(updatedCompany.getName())) throw new CannotChangeName();
		updatedCompany.setEmail(comp.getEmail());
		updatedCompany.setPassword(comp.getPassword());
		compDB.updateCompany(updatedCompany);
	}
	
	public void updateCustomer(Customer cust) throws CustomerDoesNotExist, UniqueEmailException {
		Customer updatedCustomer = custDB.getCustomerById(cust.getId());
		if(updatedCustomer == null) throw new CustomerDoesNotExist();
		if(custDB.getCustomerByEmail(cust.getEmail()) != null && updatedCustomer.getId() != cust.getId()) throw new UniqueEmailException();

		updatedCustomer.setEmail(cust.getEmail());
		updatedCustomer.setFirstName(cust.getFirstName());
		updatedCustomer.setLastName(cust.getLastName());
		updatedCustomer.setPassword(cust.getPassword());
		updatedCustomer.setCoupons(cust.getCoupons());
		
		custDB.updateCustomer(updatedCustomer);
	}
	//************************ DELETE *********************************************************************
	public void deleteCompany(int id) {
		ArrayList<Coupon> coupons = coupDB.getCouponsByCompanyId(id);
		for (Coupon coupon : coupons) {	
			List<Customer> currentCustomers = coupon.getCustomer();
			Customer cust;
			for (int i = 0; i < currentCustomers.size(); i++) {
				cust = currentCustomers.get(i);		
				cust.removeCoupon(coupon);
				custDB.updateCustomer(cust);
			}
			
		}
		
		compDB.deleteCompany(id);
	}
	
	public void deleteCustomer(int id) {
		custDB.deleteCustomer(id);
	}
	


}
