package com.dovdekeyser.groupon_springboot_web.facade;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dovdekeyser.groupon_springboot_web.db.CompanyDBDAO;
import com.dovdekeyser.groupon_springboot_web.db.CouponDBDAO;
import com.dovdekeyser.groupon_springboot_web.db.CustomerDBDAO;
import com.dovdekeyser.groupon_springboot_web.errors.EmailPasswordMismatch;

public abstract class ClientFacade implements Facade{
	
	@Autowired
	protected CustomerDBDAO custDB;
	@Autowired
	protected CouponDBDAO coupDB ;
	@Autowired
	protected CompanyDBDAO compDB;
	
	public abstract int login (String email, String password) throws EmailPasswordMismatch, SQLException;

}
