package com.dovdekeyser.groupon_springboot_web;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dovdekeyser.groupon_springboot_web.beans.Category;
import com.dovdekeyser.groupon_springboot_web.beans.Company;
import com.dovdekeyser.groupon_springboot_web.beans.Coupon;
import com.dovdekeyser.groupon_springboot_web.beans.Customer;
import com.dovdekeyser.groupon_springboot_web.db.CouponDBDAO;
import com.dovdekeyser.groupon_springboot_web.errors.CannotChangeName;
import com.dovdekeyser.groupon_springboot_web.errors.CompanyDoesNotExist;
import com.dovdekeyser.groupon_springboot_web.errors.CouponAlreadyExists;
import com.dovdekeyser.groupon_springboot_web.errors.CouponAlreadyPurchased;
import com.dovdekeyser.groupon_springboot_web.errors.CouponSystemException;
import com.dovdekeyser.groupon_springboot_web.errors.CustomerDoesNotExist;
import com.dovdekeyser.groupon_springboot_web.errors.EmailPasswordMismatch;
import com.dovdekeyser.groupon_springboot_web.errors.NoMoreCouponsLeft;
import com.dovdekeyser.groupon_springboot_web.errors.TitleExists;
import com.dovdekeyser.groupon_springboot_web.errors.UniqueEmailException;
import com.dovdekeyser.groupon_springboot_web.errors.UniqueNameException;
import com.dovdekeyser.groupon_springboot_web.facade.AdminFacade;
import com.dovdekeyser.groupon_springboot_web.facade.CompanyFacade;
import com.dovdekeyser.groupon_springboot_web.facade.CustomerFacade;
import com.dovdekeyser.groupon_springboot_web.job.CouponExpirationDailyJob;
import com.dovdekeyser.groupon_springboot_web.login.ClientType;
import com.dovdekeyser.groupon_springboot_web.login.LoginManager;



@Component
public class Test {
	
	
	@Autowired
	protected LoginManager LoginM;
	
	@Autowired
	protected CouponExpirationDailyJob cleaner;
	
	// comment in for testing 
	@Autowired
	protected CouponDBDAO coupDb;
	
	Calendar cal = Calendar.getInstance();
	Date start = new Date(cal.getTimeInMillis());
	Date end = new Date(start.getTime() +TimeUnit.DAYS.toMillis(20));
	
	Category cat1 = Category.electronic;
	Category cat2 = Category.food;
	Category cat3 = Category.vacation;
	
	public void showCase() throws EmailPasswordMismatch, SQLException, TitleExists,
								  UniqueEmailException,  UniqueNameException, CompanyDoesNotExist,
								  CustomerDoesNotExist, CouponAlreadyExists, NoMoreCouponsLeft,
								  CouponAlreadyPurchased, CannotChangeName, CouponSystemException {
		 
		//************************************************************************************************************
		//**********************************  THREAD TESTING   **************************************************
		//************************************************************************************************************

		Thread t1= new Thread(cleaner);
		t1.start();
		

	
		Company comp1 = new Company("TEST", "aaaa@email.com", "1234");
		Company comp2 = new Company("Cola", "cola@email.com", "1234");
		Company comp3 = new Company("Apple", "apple@email.com", "1234");
		
		
		Coupon coup1 = new Coupon(100, "10% off", "10% off any canon camera", "canon.jpg",cat1, start, end, 1000.0);
		Coupon coup2 = new Coupon(50, "1+1", "pizza", "bad food - lets say raw chicken or something.jpg", cat2, start, end, 50.0);
		Coupon coup3 = new Coupon( 40	, "100% off", "get this for free because we love you", "free.jpg" , cat3, start, end, 500.3);
		Coupon coup4 = new Coupon( 1, "100% off", "testing", "image.jpg", cat1, start, end, 5000);
				
		Customer	 cust1 = new Customer("Dov", "Dekeyser", "dov@email.com", "1234");
		Customer	 cust2 = new Customer("Eric", "Illuz", "eric@email.com", "1234");
		Customer	 cust3 = new Customer("Ofir", "Shemer", "ofir@email.com", "1234");
		
		//************************************************************************************************************
		//************************ ADMIN TESTING *********************************************************************
		//************************************************************************************************************
		
		AdminFacade 	adminF = (AdminFacade) LoginM.login("admin@admin", "admin", ClientType.administrator);

		//**********************    Add Companies Test  ****************************************************************
		System.out.println("");
		System.out.println("admin.addCompany(): ");
		
		adminF.addCompany(comp1);
		adminF.addCompany(comp2);
		adminF.addCompany(comp3);  
		

//**********************    Get Companies Test  ****************************************************************
		System.out.println();
		System.out.println("Testing Admin: - getAllCompanies(): " + adminF.getAllCompanies());
		System.out.println("Test Admin: - getCompanyById(): " + adminF.getCompanyById(comp1.getId()));		
////**********************    Update Companies Test  ****************************************************************	
		
		System.out.println("");
		System.out.println("admin.updateCompany(): ");
		
		
		comp1.setPassword("new password");
		adminF.updateCompany(comp1);
		System.out.println("company reset password");
		System.out.println("admin.updateCompany() - showing company after: " + adminF.getCompanyById(comp1.getId()) );
////**********************    Delete Companies Test  ****************************************************************
		System.out.println("");
		System.out.println("admin.deleteCompany():");

		
		/**
		 * add a customer to the db
		 * add coupon to the company 
		 * customer purchase the coupon 
		 *  then I will delete the company
		 *  make sure that all (company coupon and purchase) are gone
		 */

		adminF.addCustomer(cust1);
		
		CompanyFacade   compF  = (CompanyFacade) LoginM.login(comp1.getEmail(),comp1.getPassword(), ClientType.company);	
		compF.addCoupon(coup1);
			
		CustomerFacade  custF  = (CustomerFacade)  LoginM.login(cust1.getEmail(),cust1.getPassword(), ClientType.customer);
		custF.purchaseCoupon(coup1); 
		
		// deleting company
		adminF.deleteCompany(comp1.getId());
		System.out.println("admin showing all companies: " + adminF.getAllCompanies());
		System.out.println("checking if customer still owns coupons " +  custF.getAllCustomerCoupons());
		System.out.println("checking if coupon is in database " + 		coupDb.getAllCoupons());
	
		
		////**********************   Add Customers Test  ****************************************************************	
		adminF.addCustomer(cust2);
		adminF.addCustomer(cust3);
////***********************   Get Customers Test  ****************************************************************
		System.out.println();
		System.out.println("Admin.getAllCustomers(): " + adminF.getAllCustomers());
		System.out.println("admin.getCustomerById(): " + adminF.getCustomerById(cust1.getId()));		
////*************************  Update Customers Test  ****************************************************************
	
		/*
		 * set first name , last name and email
		 * login to company and coup2 
		 * login to admin and add coupon to customer
		 * update customer
		 * check customer
		 */
		System.out.println();
		System.out.println("Admin.updateCustomer(): ");
		System.out.println("updating cust1 firstname lastname and email and coupons");

		
		cust1.setFirstName("Bear");
		cust1.setLastName("The Caesar");
		cust1.setEmail("bear@email.com");
		
		
		compF = (CompanyFacade) LoginM.login(comp2.getEmail(),comp2.getPassword(), ClientType.company);
		compF.addCoupon(coup2);
		
		cust1.addCoupon(coup2);
		adminF.updateCustomer(cust1);
		
		System.out.println("admin.updateCustomer() " + cust1);
					
////*************************  Delete Customer Test  ****************************************************************
		System.out.println();
		System.out.println("Admin.deleteCustomer():");
		adminF.deleteCustomer(cust1.getId());	
		
		System.out.println("admin.deleteCustomer() showing all customers after " + adminF.getAllCustomers());
		System.out.println("admin.deleteCustomer() checking that coupon still exists " + coupDb.getAllCoupons() );
		System.out.println();
		System.out.println("END ADMIN TEST  - Everything Works :-)" );	
		System.out.println();
		
		
////************************************************************************************************************
////************************ COMPANY TESTING *******************************************************************
////************************************************************************************************************

////**********************    Add Coupons Test  ****************************************************************
		System.out.println();
		System.out.println("   ---------------  Testing Company Facade ------------");	
		
		System.out.println();
		System.out.println("company.addCoupon(): ");
		compF.addCoupon(coup3);
		
////**********************    get Company Coupons Test  ****************************************************************
		System.out.println();
		System.out.println("company.getCompanyCoupons(): " + compF.getCompanyCoupons());
////**********************    get Company Coupons by Category Test  ****************************************************************
		System.out.println();
		System.out.println( "company.getCouponsByCategory(): " +compF.getCompanyCouponsByCategory(cat3));		
////**********************    get Company Coupons by Max Price Test  ****************************************************************
		System.out.println();
		System.out.println("company.getCouponsByMaxPrice(): " + compF.getCompanyCouponsByMaxPrice(300));
////**********************    get Company Test  ****************************************************************
		System.out.println();
		System.out.println("company.getCompany(): " + compF.getCompany());	
////**********************    update Coupon Test  ****************************************************************
		System.out.println("");
		System.out.println("company.updateCoupon(): ");
		
		// getting coupon from db since there is no direct access I made a helper function in the beans and 
		// accessed it via the Company Facade
		
		Coupon updatingCoupon = compF.getCompany().getCompanyCouponById(coup2);
		System.out.println("getting coupon from db for comparison: " + updatingCoupon);

		
		updatingCoupon.setAmount(2);
		updatingCoupon.setCategoryName(cat1);
		coup2.setDescription("earphones");
		Date newEnd = new Date(end.getTime() -TimeUnit.DAYS.toMillis(10));
		updatingCoupon.setEndDate(newEnd);
		updatingCoupon.setImage("earpods.jpg");
		updatingCoupon.setTitle("earphones suck");
		
		compF.updateCoupon(updatingCoupon);
		System.out.println("getting coupon direct from db to compare " + updatingCoupon);
////**********************    delete Coupon Test  ****************************************************************
		System.out.println();
		compF.deleteCoupon(updatingCoupon.getId());
		System.out.println("company.deleteCoupon(): -should have only ony coupon " + compF.getCompanyCoupons());
		System.out.println("---------------------    END COMPANY TEST   ------- :-)");
		System.out.println();
////************************************************************************************************************
////************************ CUSTOMER TESTING *******************************************************************
////************************************************************************************************************
		System.out.println();
		System.out.println("----------- Customer Facade Testing  ----------------");
		custF = (CustomerFacade)  LoginM.login("ofir@email.com","1234", ClientType.customer);
		//**********************   purchase Coupon Test  **************************************
		System.out.println("");
		Coupon tempCoup = compF.getCompany().getCompanyCouponById(coup3);
		custF.purchaseCoupon(tempCoup);
		System.out.println("customer.purchaseCoupon(): customer should have coupon with id of " + tempCoup.getId()+ " " + custF.getCustomerDetails().getCoupons());

		//**********************   get All Customer Coupons Test  ***********************
		System.out.println();
		System.out.println("customer.getAllCustomerCoupons(): " + custF.getAllCustomerCoupons());
	
		//**********************   get All Customer Coupons By Category Test  ***********************
		System.out.println();
		System.out.println( "customer.getAllCustomerCouponsByCat(): " + custF.getAllCustomerCouponsByCategory(cat3));	
		//**********************   get All Customer Coupons By Max Price Test  ***********************
		System.out.println();
		System.out.println("customer.getAllCustomerCouponsByMaxPrice():  "+  custF.getAllCustomerCouponsByMaxPrice(5001.0));
			//**********************   get All Customer Details Test  ***********************
		System.out.println();
		System.out.println(custF.getCustomerDetails());
		System.out.println();
		System.out.println("END TESTING ALL WORKS GREAT :-)  ");		
			
			//**********************    Close cleaner  ****************************************************************
		
			cleaner.stop();

	}

	
	
	public void insertData() throws EmailPasswordMismatch, SQLException, UniqueEmailException, 
									UniqueNameException, TitleExists, 
									NoMoreCouponsLeft, CouponAlreadyPurchased, CouponSystemException {
		
		
		Thread t1= new Thread(cleaner);
		t1.start();
		

	
		Company comp1 = new Company("TEST", "aaaa@email.com", "1234");
		Company comp2 = new Company("Cola", "cola@email.com", "1234");
		Company comp3 = new Company("Apple", "apple@email.com", "1234");
		
		
		Coupon coup1 = new Coupon(100, "10% off", "10% off any canon camera", "canon.jpg",cat1, start, end, 1000.0);
		Coupon coup2 = new Coupon(50, "1+1", "pizza", "bad food - lets say raw chicken or something.jpg", cat2, start, end, 50.0);
		Coupon coup3 = new Coupon( 40	, "100% off", "get this for free because we love you", "free.jpg" , cat3, start, end, 500.3);
		Coupon coup4 = new Coupon( 1, "100% free", "testing", "image.jpg", cat1, start, end, 5000);
				
		Customer	 cust1 = new Customer("Dov", "Dekeyser", "dov@email.com", "1234");
		Customer	 cust2 = new Customer("Eric", "Illuz", "eric@email.com", "1234");
		Customer	 cust3 = new Customer("Ofir", "Shemer", "ofir@email.com", "1234");
		
		AdminFacade 	adminF = (AdminFacade) LoginM.login("admin@email.com", "admin", ClientType.administrator);
		adminF.addCompany(comp1);
		adminF.addCompany(comp2);
		adminF.addCompany(comp3);
		
		adminF.addCustomer(cust1);
		adminF.addCustomer(cust2);
		adminF.addCustomer(cust3);

		CompanyFacade   compF  = (CompanyFacade) LoginM.login(comp1.getEmail(),comp1.getPassword(), ClientType.company);
		CustomerFacade  custF  = (CustomerFacade)  LoginM.login(cust1.getEmail(),cust1.getPassword(), ClientType.customer);

		compF.addCoupon(coup1);
		compF.addCoupon(coup2);
		
		compF = (CompanyFacade) LoginM.login(comp2.getEmail(), comp2.getPassword(), ClientType.company);
		compF.addCoupon(coup3);
		compF.addCoupon(coup4);
		
		
		custF.purchaseCoupon(coup1);
		custF.purchaseCoupon(coup2);
		custF.purchaseCoupon(coup3);
		
		custF  = (CustomerFacade)  LoginM.login(cust2.getEmail(),cust2.getPassword(), ClientType.customer);
		custF.purchaseCoupon(coup1);
		custF.purchaseCoupon(coup2);
		custF.purchaseCoupon(coup3);
		
		custF  = (CustomerFacade)  LoginM.login(cust3.getEmail(),cust3.getPassword(), ClientType.customer);
		custF.purchaseCoupon(coup4);
		custF.purchaseCoupon(coup1);
	}

}
