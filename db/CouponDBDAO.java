package com.dovdekeyser.groupon_springboot_web.db;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dovdekeyser.groupon_springboot_web.beans.Category;
import com.dovdekeyser.groupon_springboot_web.beans.Coupon;
import com.dovdekeyser.groupon_springboot_web.beans.Customer;

@Component
public class CouponDBDAO {
	
	//************************DEPENDANCY ENJECTION *********************************************************	

	@Autowired
	public CouponRepository coupRepo;

	//*****************************************************************************************************
	//************************  BASIC CRUD  ***************************************************************
	//*****************************************************************************************************
	
	//************************ CREATE *********************************************************************
	public void addCoupon(Coupon coup) {
		coupRepo.save(coup);
	}
	
	//************************ READ   *********************************************************************
	public Coupon getCouponById(int id) {
		Optional<Coupon> coup = coupRepo.findById(id);
		if(coup.isPresent()) return coup.get();
		return null;
	}
	
	public ArrayList<Coupon> getAllCoupons(){
		return (ArrayList<Coupon>) coupRepo.findAll();
	}
	
	
	//************************ UPDATE *********************************************************************
	public void updateCoupon(Coupon coup) {
		if(coupRepo.existsById(coup.getId())) coupRepo.save(coup);
	}
	//************************ DELETE *********************************************************************
	
	public void deleteCoupon(int id) {
		coupRepo.deleteById(id);
	}
	
	//*****************************************************************************************************
	//************************  OTHER CRUD METHODS ********************************************************
	//*****************************************************************************************************
	
	//************************ CREATE *********************************************************************
	//************************ READ ***********************************************************************
	
	public ArrayList<Coupon>getCouponsByCustomer(Customer cust){
		return (ArrayList<Coupon>) coupRepo.getCouponsByCustomer(cust);
	}
	
	public ArrayList<Coupon> getCouponsByCompanyId(int id){
		return coupRepo.getCouponsByCompanyId(id);
	}
	
	public ArrayList<Coupon> getCouponsByIdAndCustomer(int couponId, Customer cust){
		return coupRepo.getCouponsByIdAndCustomer(couponId,cust);
	}
	public Coupon getCouponByCompanyIdAndTitle(int companyId, String title) {
		return coupRepo.getCouponByCompanyIdAndTitle(companyId, title);
	}
	
	public ArrayList<Coupon> getCouponsByCategoryName(Category categoryName){
		return coupRepo.getCouponsByCategoryName(categoryName);
	}
	
	public ArrayList<Coupon> getCouponsByCompanyIdAndCategoryName(int companyId, Category cat){
		return coupRepo.getCouponsByCompanyIdAndCategoryName(companyId, cat);
	}
	
	
	public ArrayList<Coupon> getCouponsByPriceLessThan(double maxPrice){
		return coupRepo.getCouponsByPriceLessThanEqual(maxPrice);
	}
	
	public ArrayList<Coupon> getCouponsByCompanyIdAndPriceLessThanEqual(int companyId,double price ){
		return coupRepo.getCouponsByCompanyIdAndPriceLessThanEqual(companyId, price);
	}
	
	public ArrayList<Coupon> getCouponsByCustomerIdAndCategoryName(int customerId, Category cat){
		return coupRepo.getCouponsByCustomerIdAndCategoryName( customerId, cat);
	}
	public ArrayList<Coupon> getCouponsByCustomerIdAndPriceLessThanEqual(int customerId, double maxPrice) {
		return coupRepo.getCouponsByCustomerIdAndPriceLessThanEqual(customerId,maxPrice);
	}
	
	//************************ UPDATE *********************************************************************
	//************************ DELETE *********************************************************************

	public void deleteCouponByCompanyId(int id) {
		coupRepo.deleteCouponsByCompanyId(id);
	}



}
