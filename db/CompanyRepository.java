package com.dovdekeyser.groupon_springboot_web.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dovdekeyser.groupon_springboot_web.beans.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {	
		Company getCompanyByEmail(String email);
		Company getCompanyByName(String name);
		Company getCompanyByEmailAndPassword(String email, String password);
		
}
