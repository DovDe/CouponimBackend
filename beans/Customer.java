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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="customers")
public class Customer {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	
	private String firstName, lastName, password;
	
	@Column(unique=true)
	private String email;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade= {CascadeType.PERSIST} )
	@JoinTable(name="customers_vs_coupons" , joinColumns=@JoinColumn(name="customer"),inverseJoinColumns=@JoinColumn(name="coupon"))
	@JsonIgnore
	private List<Coupon> coupons =  new ArrayList<>();


	public Customer() {}

	

	
	public Customer(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}




	public Customer(String firstName, String lastName, String email, String password, List<Coupon> coupons) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}


	
	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
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


	/**
	 * 
	 * All the coupon methods work on the coupons list in this bean
	 * they are meant to make cascading issues easier to work with
	 */
	
	public List<Coupon> getCoupons() {
		return coupons;
	}


	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public void addCoupon(Coupon coup) {
		coup.addCustomer(this);
		this.coupons.add(coup);
	}
	
	public void removeCoupon(Coupon coup) {
		coup.removeCustomer(this);
		this.coupons.remove(coup);
	}
	
	public boolean hasCoupon(Coupon coup) {
		for(Coupon c : this.coupons) {
			if(c.getId() == coup.getId()) return true;
		}
		return false;
	}
	
	// end coupon methods 
	public int getId() {
		return id;
	}


	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", coupons=" + this.getCoupons() + "]";
	};
	
	
	
	
	
	
}
