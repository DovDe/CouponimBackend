package com.dovdekeyser.groupon_springboot_web.db;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dovdekeyser.groupon_springboot_web.beans.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
		Customer getCustomerByEmailAndPassword(String email, String password);
		Customer getCustomerByEmail(String email);
}
