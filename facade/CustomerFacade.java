package com.dovdekeyser.groupon_springboot_web.facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dovdekeyser.groupon_springboot_web.beans.Category;
import com.dovdekeyser.groupon_springboot_web.beans.Coupon;
import com.dovdekeyser.groupon_springboot_web.beans.Customer;
import com.dovdekeyser.groupon_springboot_web.errors.CouponAlreadyPurchased;
import com.dovdekeyser.groupon_springboot_web.errors.EmailPasswordMismatch;
import com.dovdekeyser.groupon_springboot_web.errors.NoMoreCouponsLeft;

@Service
public class CustomerFacade extends ClientFacade {

	private int customerId;
	
	
	
	public CustomerFacade() {
		this.customerId = -1;
	}

	

	//********  LOGIN  ***********************

	@Override
	public int login(String email, String password) throws EmailPasswordMismatch, SQLException {
		Customer cust = custDB.getCustomerByEmailAndPassword(email, password);
		if(cust == null) throw new EmailPasswordMismatch();
		customerId = cust.getId();
		return customerId;
	}
	//***********************************
	//*********    CRUD METHODS   *******************
	//***********************************
	
	//******** CREATE ***********************

	public void purchaseCoupon(Coupon coup) throws NoMoreCouponsLeft, CouponAlreadyPurchased {
		
		Customer cust = this.getCustomerDetails();	
		
		if(cust.hasCoupon(coup) || coup.hasCustomer(cust) )throw new CouponAlreadyPurchased();
		if (coup.getAmount()<1)throw new NoMoreCouponsLeft(); 
		
		Coupon coupRO = coupDB.getCouponById(coup.getId());
		
		coupRO.setAmount(coup.getAmount()-1);
		cust.addCoupon(coupRO);
		custDB.updateCustomer(cust);
		coupDB.updateCoupon(coupRO);
		
	}
	//******** READ *************************
	
	public ArrayList<Coupon> getAllCoupons(){
		return coupDB.getAllCoupons();
	}
	
	//***********************************


	public ArrayList<Coupon> getAllCustomerCoupons(){
		return coupDB.getCouponsByCustomer(this.getCustomerDetails());
	}
	

	//***********************************

	public ArrayList<Coupon> getAllCustomerCouponsByCategory(Category category){
		return coupDB.getCouponsByCustomerIdAndCategoryName(customerId, category);
	}
	
	//***********************************
	
	public ArrayList<Coupon> getCouponsByCategory(Category category){
		return coupDB.getCouponsByCategoryName(category);

		
	}
	//***********************************
	
	public ArrayList<Coupon> getAllCustomerCouponsByMaxPrice(double maxPrice){
		return coupDB.getCouponsByCustomerIdAndPriceLessThanEqual(customerId,maxPrice);
	}
	//***********************************
	
	public ArrayList<Coupon> getCouponsByMaxPrice(double maxPrice){
		return coupDB.getCouponsByPriceLessThan(maxPrice);
	}
	//***********************************

	public Customer getCustomerDetails() {
		return custDB.getCustomerById(customerId);
	}

}
