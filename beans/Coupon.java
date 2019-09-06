package com.dovdekeyser.groupon_springboot_web.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="coupons")
public class Coupon {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	
	@ManyToOne(fetch=FetchType.EAGER, cascade= { CascadeType.REFRESH})
	@JoinColumn(name="companyId")
	@JsonIgnore
	private Company company;
	
	private int amount;
	
	private String title,description,image;
	
	@Enumerated(EnumType.STRING)
	private Category categoryName;
	
	
	
	@ManyToMany(mappedBy="coupons", fetch=FetchType.EAGER)
	private List<Customer> customer = new ArrayList<>();
	
	private Date startDate, endDate;
	private double price;
	
	
	
	public Coupon() {}


	public Coupon(Company company, int amount, String title, String description, String image, Category categoryName,
			Date startDate, Date endDate, double price) {
		this.company = company;
		this.amount = amount;
		this.title = title;
		this.description = description;
		this.image = image;
		this.categoryName = categoryName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}

	
	
	public Coupon(int amount, String title, String description, String image, Category categoryName, Date startDate,
			Date endDate, double price) {
		this.amount = amount;
		this.title = title;
		this.description = description;
		this.image = image;
		this.categoryName = categoryName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}


	public Company getCompany() {
		return company;
	}


	public void setCompany(Company company) {
		this.company = company;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public Category getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(Category categoryName) {
		this.categoryName = categoryName;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getId() {
		return id;
	}

	/**
	 *  customer methods access the list of customer within the coupon
	 *  this is to make cascading issues easier to work with
	 *  
	 */
	

	public List<Customer> getCustomer() {
		return    customer;
	}

	public void addCustomer(Customer cust) {
		this.customer.add(cust);
	}
	
	public void removeCustomer(Customer cust) {
		this.customer.remove(cust);
	}
	
	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}

	
	public boolean hasCustomer(Customer cust) {
		for(Customer c : this.customer) {
			if(c.getId() == cust.getId()) {
				return true;
			}
		}
		return false;
		
	}


	// end customer methods
	
	
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", amount=" + amount + ", title=" + title
				+ ", description=" + description + ", image=" + image + ", categoryName=" + categoryName 
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", price=" + price + "]";
	}
	
}
