package service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import model.Customer;

public class CustomerService {
	private static CustomerService instance;
	private Set<Customer> customers;

	private CustomerService() {
		customers = new HashSet<Customer>();
	}
	
	public static CustomerService getInstance() {
		if (instance == null) {
			instance = new CustomerService();
		}
		
		return instance;
	}
	
	public void addCustomer(String email, String firstName, String lastName) {
		Customer newCustomer = new Customer(firstName, lastName, email);
		if (customers.contains(newCustomer)) {
			System.out.println("This customer already exists");
		} else {
			customers.add(newCustomer);
		}
	}
	
	public Customer getCustomer(String customerEmail) {
		for (Customer customer : customers) {
			if (customer.getEmail() == customerEmail) {
				return customer;
			}
		}
		
		return null;
		//return new Customer(null, null, customerEmail);
	}
	
	public Collection<Customer> getAllCustomers() {
		return customers;
	}
	
}
