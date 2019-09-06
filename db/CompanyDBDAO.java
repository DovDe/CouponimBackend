package com.dovdekeyser.groupon_springboot_web.db;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dovdekeyser.groupon_springboot_web.beans.Company;

@Component
public class CompanyDBDAO {

	//************************DEPENDANCY ENJECTION *********************************************************	
	@Autowired
	private CompanyRepository compRepo;
	
	//*****************************************************************************************************
	//************************  BASIC CRUD  ***************************************************************
	//*****************************************************************************************************
	
	//************************ CREATE *********************************************************************
	
	public void addCompany(Company comp) {
		compRepo.save(comp);
	}
	//************************ READ *********************************************************************

	public Company getCompanyById(int id) {
		Optional<Company> comp  = compRepo.findById(id);	
		if(comp.isPresent()) return comp.get();
		return null;
	}



	
	public ArrayList<Company>  getAllCompanies() {
		return (ArrayList<Company>) compRepo.findAll();
	}
	
	
	
	//************************ UPDATE *********************************************************************

	public void updateCompany(Company comp) {
		if(compRepo.existsById(comp.getId())) {
			compRepo.save(comp);
		}
	}
	//************************ DELETE *********************************************************************

	public void deleteCompany(int id) {
		compRepo.deleteById(id);
	}
	
	
	//*****************************************************************************************************
	//************************  OTHER CRUD METHODS ********************************************************
	//*****************************************************************************************************
	
		//************************ CREATE *********************************************************************
	//************************ READ ***********************************************************************
	public Company getCompanybyEmail(String email) {
		return compRepo.getCompanyByEmail(email);	
	}
	public Company getCompanyByName(String name) {
		return compRepo.getCompanyByName(name);
	}
	
	public Company getCompanyByEmailAndPassword(String email, String password) {
		return compRepo.getCompanyByEmailAndPassword(email, password);
	}
	
	
	//************************ UPDATE *********************************************************************
	//************************ DELETE *********************************************************************
}
