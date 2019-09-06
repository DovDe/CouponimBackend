package com.dovdekeyser.groupon_springboot_web.login;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dovdekeyser.groupon_springboot_web.beans.Company;
import com.dovdekeyser.groupon_springboot_web.beans.Customer;
import com.dovdekeyser.groupon_springboot_web.db.CompanyDBDAO;
import com.dovdekeyser.groupon_springboot_web.db.CustomerDBDAO;
import com.dovdekeyser.groupon_springboot_web.errors.CouponSystemException;
import com.dovdekeyser.groupon_springboot_web.errors.EmailPasswordMismatch;
import com.dovdekeyser.groupon_springboot_web.facade.AdminFacade;
import com.dovdekeyser.groupon_springboot_web.facade.ClientFacade;
import com.dovdekeyser.groupon_springboot_web.facade.CompanyFacade;
import com.dovdekeyser.groupon_springboot_web.facade.CustomerFacade;
import com.dovdekeyser.groupon_springboot_web.facade.Facade;

@Service
public class LoginManager {
	
	
	@Autowired
	protected AdminFacade adminF;
	
	
	@Autowired
	protected CompanyFacade compF;
	
	@Autowired
	protected CustomerFacade custF;
	
	public Facade login(String email, String password, ClientType type) throws CouponSystemException, EmailPasswordMismatch, SQLException{
		switch(type) {
		case administrator:
				int adminId = adminF.login(email, password);
				return adminF;
		case customer:
			int custId = custF.login(email, password);
			if(custId > 0) 	return custF;
		case company:
			int compId = compF.login(email, password);
			if(compId > 0) 	return compF;
		}
		throw new CouponSystemException("Client Not Found");
	}
	
	
}
