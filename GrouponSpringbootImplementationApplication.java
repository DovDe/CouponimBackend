package com.dovdekeyser.groupon_springboot_web;

import java.sql.SQLException;


//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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
import com.dovdekeyser.groupon_springboot_web.job.CouponExpirationDailyJob;
import com.dovdekeyser.groupon_springboot_web.web.SessionExpiration;


@SpringBootApplication
public class GrouponSpringbootImplementationApplication {

	public static void main(String[] args)  {
		ConfigurableApplicationContext context = SpringApplication.run(GrouponSpringbootImplementationApplication.class, args);
		
//		Test test = context.getBean(Test.class);
		CouponExpirationDailyJob cleaner = context.getBean(CouponExpirationDailyJob.class);
		SessionExpiration tokenCleaner = context.getBean(SessionExpiration.class);
		
		
		Thread cleanerThread = new Thread(cleaner);
		cleanerThread.start();
		
		Thread logoutThread = new Thread(tokenCleaner);
		logoutThread.start();
//		
		
//		try {
//			test.insertData();
//		} catch (EmailPasswordMismatch e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UniqueEmailException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UniqueNameException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TitleExists e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoMoreCouponsLeft e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (CouponAlreadyPurchased e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		try {
//			test.showCase();
//		} catch (EmailPasswordMismatch e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TitleExists e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UniqueEmailException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UniqueNameException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (CompanyDoesNotExist e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (CustomerDoesNotExist e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (CouponAlreadyExists e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoMoreCouponsLeft e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (CouponAlreadyPurchased e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (CannotChangeName e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
//		try {
//			test.testingPurchaseCoupon();
//		} catch (EmailPasswordMismatch | SQLException | UniqueEmailException | UniqueNameException | TitleExists
//				| NoMoreCouponsLeft | CouponAlreadyPurchased e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
