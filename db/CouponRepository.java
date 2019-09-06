package com.dovdekeyser.groupon_springboot_web.db;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dovdekeyser.groupon_springboot_web.beans.Category;
import com.dovdekeyser.groupon_springboot_web.beans.Coupon;
import com.dovdekeyser.groupon_springboot_web.beans.Customer;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	
	ArrayList<Coupon> getCouponsByCompanyId(int id);
	void deleteCouponsByCompanyId(int company_id);
	Coupon getCouponByCompanyIdAndTitle(int companyId, String title);
	
	
	
	ArrayList<Coupon> getCouponsByCategoryName(Category categoryName);
	ArrayList<Coupon> getCouponsByPriceLessThanEqual(double maxPrice);
	
	ArrayList<Coupon> getCouponsByCompanyIdAndCategoryName(int id, Category categoryName);
	ArrayList<Coupon> getCouponsByCompanyIdAndPriceLessThanEqual(int id,double price );
	
	ArrayList<Coupon> getCouponsByCustomer(Customer cust);
	ArrayList<Coupon> getCouponsByIdAndCustomer(int couponId, Customer cust);
	ArrayList<Coupon> getCouponsByCustomerIdAndCategoryName(int customerId, Category cat);
	ArrayList<Coupon> getCouponsByCustomerIdAndPriceLessThanEqual(int customerId, double maxPrice);
	
	// new try
	
}
