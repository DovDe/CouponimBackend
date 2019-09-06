package com.dovdekeyser.groupon_springboot_web.job;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dovdekeyser.groupon_springboot_web.beans.Coupon;
import com.dovdekeyser.groupon_springboot_web.beans.Customer;
import com.dovdekeyser.groupon_springboot_web.db.CouponDBDAO;
import com.dovdekeyser.groupon_springboot_web.errors.CustomerDoesNotExist;
import com.dovdekeyser.groupon_springboot_web.errors.UniqueEmailException;
import com.dovdekeyser.groupon_springboot_web.facade.AdminFacade;

@Component
public class CouponExpirationDailyJob implements Runnable {

	@Autowired
	private CouponDBDAO couponsDBDAO;
	
	@Autowired 
	private AdminFacade adminF;


	private boolean quit = false;

	
	
	@Override
	public void run() {
		int millisecondsInDay = 1000 * 60; //* 60 * 24;
		ArrayList<Coupon> coupons;
		System.out.println("thread running");
		while (!quit) {
				coupons = couponsDBDAO.getAllCoupons();
				for (Coupon coupon : coupons) {
					Calendar cal = Calendar.getInstance();

					if (coupon.getEndDate().before(new Date(cal.getTimeInMillis()))) {
						String title = coupon.getTitle();
						
						
						List<Customer> customers = coupon.getCustomer();
						Customer cust;
						for (int i = 0; i < customers.size(); i++) {
							cust = customers.get(i);
							cust.removeCoupon(coupon);
							
								try {
									adminF.updateCustomer(cust);
								} catch (CustomerDoesNotExist e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (UniqueEmailException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.println("Deleted coupon with title: " + title);
						
						}
						
						coupon.setCustomer(null);
						coupon.setCompany(null);
						couponsDBDAO.updateCoupon(coupon);
						couponsDBDAO.deleteCoupon(coupon.getId());

					}
				}
				
			try {
				Thread.sleep(millisecondsInDay);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());

			}

		}

	}

	public void stop() {

		quit = true;

	}

}
