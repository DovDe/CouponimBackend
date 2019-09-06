package com.dovdekeyser.groupon_springboot_web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="companies")
public class Company  {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(unique=true, updatable=false)
	private String name;
	
	@Column(unique=true)
	private String email;
	
	private String password;
	
	
	@OneToMany(mappedBy="company", cascade= { CascadeType.REMOVE,CascadeType.PERSIST}, fetch=FetchType.EAGER)
	@JsonIgnore
	private List<Coupon> coupons = new ArrayList<>();


	public Company() {}


	public Company(String name, String email, String password, List<Coupon> coupons) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}
	public Company(String name, String email, List<Coupon> coupons) {
		this.name = name;
		this.email = email;
		this.coupons = coupons;
	}


	public Company(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public List<Coupon> getCoupons() {
		return coupons;
	}


	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}


	public int getId() {
		return id;
	}
	
 
	/**
	 * 
	 * these are special methods added to make working with coupons and cascading issues 
	   easier to deal with they access the coupons list on this bean and should access
	   access that the coup bean and update it as well
	 */
	
	public void addCoupon(Coupon coup) {
		coup.setCompany(this);
		this.coupons.add(coup);	
	}
	
	public void removeCoupon(Coupon coup) {
		coup.setCompany(null);
		this.coupons.remove(coup);
	}
	
	public Coupon getCompanyCouponById(Coupon coup) {
		for(Coupon c: this.coupons) {
			if(c.getId() == coup.getId()) return coup;
		}
		return null;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", coupons="
				+ getCoupons() + "]";
	}

}
